package propensi.d06.sihedes.service;

import propensi.d06.sihedes.model.*;

import java.util.List;

public interface ProblemService {
    void updateProblem(ProblemModel problem);
    DepartemenModel getDepById(Long id);
    List<ProblemModel> findAll();
    List<ProblemModel> getProblemByDepartemen(DepartemenModel departemen);
    ProblemModel findProblemById(Long id);
    void addProblem(ProblemModel problem);
    ProblemModel updateProblemStatus(ProblemModel problem);
}
