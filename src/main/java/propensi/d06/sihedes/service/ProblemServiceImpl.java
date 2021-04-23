package propensi.d06.sihedes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import propensi.d06.sihedes.model.*;
import propensi.d06.sihedes.repository.*;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ProblemServiceImpl implements ProblemService{
    @Autowired
    ProblemDb problemDb;

    @Autowired
    DepartemenDb departemenDb;

    @Override
    public DepartemenModel getDepById(Long id){
        return departemenDb.findById(id).get();
    }

    @Override
    public void updateProblem(ProblemModel problem) {
        problemDb.save(problem);
    }

    @Override
    public List<ProblemModel> findAll() { return problemDb.findAll(); }
//
//    @Override
//    public List<ProblemModel> findAllDesc() { return problemDb.findAllByOrderByIdDesc();}

    @Override
    public ProblemModel findProblemById(Long id) {
        return problemDb.findById(id).get();
    }

    @Override
    public void addProblem(ProblemModel problem) {
        problemDb.save(problem);
    }
}
