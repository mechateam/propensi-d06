package propensi.d06.sihedes.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import propensi.d06.sihedes.model.*;

import java.util.List;

public interface ProblemService {
    void updateProblem(ProblemModel problem);
    DepartemenModel getDepById(Long id);
    List<ProblemModel> findAll();
    List<ProblemModel> getProblemByPengaju(UserModel user);
    List<ProblemModel> getProblemByDepartemen(DepartemenModel departemen);
    List<ProblemModel> getProblemByStatusId(Long id);
    List<ProblemModel> getProblemByStatusIdAndResolver(Long id, UserModel user);
    List<ProblemModel> getProblemByStatusIdAndPengaju(Long id, UserModel userModel);
    List<ProblemModel> getProblemByStatusIdAndDepartmentResolver(Long id, DepartemenModel departemenModel);
    ProblemModel findProblemById(Long id);
    void addProblem(ProblemModel problem);
    ProblemModel updateProblemStatus(ProblemModel problem);
    void makeCode(ProblemModel problem);
    String getRandomChar(int length);
    String getRandomInt(int length);
    Page<ProblemModel> findPaginated(Pageable pageable, List<ProblemModel> requests);
   ProblemModel vendorRequest(ProblemModel problem);
   void testDelayStatus(ProblemModel problem);
}
