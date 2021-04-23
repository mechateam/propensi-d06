package propensi.d06.sihedes.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import propensi.d06.sihedes.model.BOAModel;
import propensi.d06.sihedes.model.RequestModel;
import propensi.d06.sihedes.model.SLABOAModel;
import propensi.d06.sihedes.model.SLAModel;
import propensi.d06.sihedes.repository.RequestDb;
import propensi.d06.sihedes.repository.SLABOADb;
import propensi.d06.sihedes.repository.SLADb;
import propensi.d06.sihedes.repository.StatusDb;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class RequestServiceImpl implements RequestService {
    @Autowired
    RequestDb requestDb;

    @Autowired
    SLABOADb slaboaDb;

    @Autowired
    SLADb slaDb;

    @Autowired
    StatusDb statusDb;

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
}
