package propensi.d06.sihedes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import propensi.d06.sihedes.model.*;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.Optional;

import java.util.List;

@Repository
@Transactional
public interface ProblemDb extends JpaRepository<ProblemModel, Long> {
    Optional<ProblemModel> findById(Long id);
    List<ProblemModel> findAllByResolverDepartemen(DepartemenModel departemen);
    List<ProblemModel> findAllByPengaju(UserModel user);
    List<ProblemModel> findProblemModelsByStatus(StatusModel statusModel);
    List<ProblemModel> findProblemModelsByStatusAndResolver(StatusModel statusModel, UserModel resolver);
    List<ProblemModel> findProblemModelsByStatusAndPengaju(StatusModel statusModel, UserModel userModel);
    List<ProblemModel> findProblemModelsByStatusAndResolverDepartemen(StatusModel statusModel, DepartemenModel departemenModel);
    @Query(value = "SELECT * FROM problem WHERE created_date Like %?1%", nativeQuery = true)
    List<ProblemModel> findProblemModelsByCreatedDateContaining(@Param("createdDate") String createdDate);

}
