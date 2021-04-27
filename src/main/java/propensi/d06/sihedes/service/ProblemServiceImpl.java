package propensi.d06.sihedes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import propensi.d06.sihedes.model.*;
import propensi.d06.sihedes.repository.*;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.Collections;
import java.util.List;

@Service
@Transactional
public class ProblemServiceImpl implements ProblemService{
    @Autowired
    ProblemDb problemDb;

    @Autowired
    DepartemenDb departemenDb;

    @Autowired
    StatusDb statusDb;

    @Autowired
    UserDb userDb;

    @Override
    public DepartemenModel getDepById(Long id){
        return departemenDb.findById(id).get();
    }

    @Override
    public void updateProblem(ProblemModel problem) {
        problemDb.save(problem);
    }

    @Override
    public ProblemModel updateProblemStatus(ProblemModel problem) {
        ProblemModel targetProblem = problemDb.findById(problem.getId_problem()).get();
        try{
            UserModel user = userDb.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
            long idStatus = targetProblem.getStatus().getId_status();
            if(idStatus == 5){
                targetProblem.setResolver(user);
            }
            long newStatus = idStatus+1;
            StatusModel status = statusDb.findById(newStatus).get();
            targetProblem.setStatus(status);
            if(targetProblem.getStatus().getId_status() == 7){
                Date dateNow = new java.util.Date();
                targetProblem.setFinished_date(dateNow);
            }
            problemDb.save(targetProblem);
            System.out.println("Ini Status :" + targetProblem.getStatus().getNamaStatus());
            return targetProblem;
        }
        catch (NullPointerException nullPointerException) {
            return null;
        }

    }

    @Override
    public List<ProblemModel> findAll() { return problemDb.findAll(); }

    @Override
    public List<ProblemModel> getProblemByDepartemen(DepartemenModel departemen) {
        return problemDb.findAllByResolverDepartemen(departemen);}

    @Override
    public ProblemModel findProblemById(Long id) {
        return problemDb.findById(id).get();
    }

    @Override
    public void addProblem(ProblemModel problem) {
        problemDb.save(problem);
    }

    @Override
    public Page<ProblemModel> findPaginated(Pageable pageable, List<ProblemModel> requests){

        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<ProblemModel> list;
        if (requests.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, requests.size());
            list = requests.subList(startItem, toIndex);
        }

        Page<ProblemModel> requestPage
                = new PageImpl<ProblemModel>(list, PageRequest.of(currentPage, pageSize), requests.size());
        return requestPage;
    }


}
