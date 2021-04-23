package propensi.d06.sihedes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import propensi.d06.sihedes.model.ProblemModel;
import propensi.d06.sihedes.model.StatusModel;
import propensi.d06.sihedes.repository.ProblemDb;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ProblemServiceImpl implements ProblemService{

    @Autowired
    ProblemDb problemDb;

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
