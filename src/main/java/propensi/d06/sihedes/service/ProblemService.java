package propensi.d06.sihedes.service;

import propensi.d06.sihedes.model.*;

public interface ProblemService {
    void updateProblem(ProblemModel problem);
    DepartemenModel getDepById(Long id);
}
