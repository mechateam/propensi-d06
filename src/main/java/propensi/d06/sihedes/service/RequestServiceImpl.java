package propensi.d06.sihedes.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import propensi.d06.sihedes.model.BOAModel;
import propensi.d06.sihedes.model.RequestModel;
import propensi.d06.sihedes.model.SLABOAModel;
import propensi.d06.sihedes.model.SLAModel;
import propensi.d06.sihedes.repository.RequestDb;
import propensi.d06.sihedes.repository.SLABOADb;
import propensi.d06.sihedes.repository.SLADb;
import propensi.d06.sihedes.repository.StatusDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import propensi.d06.sihedes.model.*;
import propensi.d06.sihedes.repository.*;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.awt.print.Book;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class RequestServiceImpl implements RequestService{
    @Autowired
    RequestDb requestDb;

    @Autowired
    DepartemenDb departemenDb;

    @Autowired
    SLABOADb slaboaDb;

    @Autowired
    SLADb slaDb;

    @Autowired
    StatusDb statusDb;

    @Autowired
    UserDb userDb;


    @Override
    public DepartemenModel getDepById(Long id){
        return departemenDb.findById(id).get();
    }

    @Override
    public void updateRequest(RequestModel request) {
        requestDb.save(request);
    }

    @Override
    public List<RequestModel> findAll() { return requestDb.findAll(); }

//    @Override
//    public List<RequestModel> findAllDesc() { return requestDb.findAllByOrderById_requestDesc(); }

    @Override
    public RequestModel findRequestById(Long id) { return requestDb.findById(id).get(); }

    @Override
    public RequestModel getRequestById(Long id){
        return requestDb.findById(id).get();
    }

    @Override
    public RequestModel updateApprovalRequest(RequestModel request){

        RequestModel targetRequest = requestDb.findById(request.getId_request()).get();
        List<SLABOAModel> listBOA = slaboaDb.findAllBySla(targetRequest.getSla());
        Integer currentRank=0;

        for (SLABOAModel boa:listBOA){
            if (targetRequest.getId_approver().equals(boa.getBoa().getUser().getId_user())){
                currentRank = boa.getBoa().getRank();
            }
        }

        for (SLABOAModel boa: listBOA){
            System.out.println("id approver"+targetRequest.getId_approver());
            System.out.println(boa.getBoa().getUser().getId_user());

            if (targetRequest.getId_approver() == boa.getBoa().getUser().getId_user()){
                if (currentRank == listBOA.size()){
                    targetRequest.setId_approver(new Long(-1));
                }
            }

            if (boa.getBoa().getRank() == currentRank+1){
                targetRequest.setId_approver(boa.getBoa().getUser().getId_user());
            }
        }

        if (targetRequest.getId_approver() == -1){
            targetRequest.setStatus(statusDb.findByNamaStatus("Waiting for Assignment"));
        }

        return targetRequest;
    }

    @Override
    public RequestModel rejectApprovalRequest(RequestModel request){
        RequestModel targetRequest = requestDb.findById(request.getId_request()).get();

        try{
            targetRequest.setId_approver(new Long(-1));
            targetRequest.setStatus(statusDb.findByNamaStatus("Closed"));
            return targetRequest;
        }
        catch (NullPointerException nullException){
            return null;
        }

    }

    @Override
    public Page<RequestModel> findPaginated(Pageable pageable){
        List<RequestModel> requests = requestDb.findAll();
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<RequestModel> list;
        if (requests.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, requests.size());
            list = requests.subList(startItem, toIndex);
        }

        Page<RequestModel> requestPage
                = new PageImpl<RequestModel>(list, PageRequest.of(currentPage, pageSize), requests.size());
        return requestPage;
    }

    @Override
    public void addRequest(RequestModel request){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        request.setCreated_date(new Date());
        request.setPengaju(userDb.findByUsername(currentPrincipalName));
        request.setStatus(statusDb.findByNamaStatus("Requested"));
        requestDb.save(request);
    }
}
