package propensi.d06.sihedes.service;

import propensi.d06.sihedes.model.ProblemModel;

import java.util.List;

public interface ProblemService {
    List<ProblemModel> findAll();
//    List<ProblemModel> findAllDesc();
    ProblemModel findProblemById(Long id);
    void addProblem(ProblemModel problem);
}
