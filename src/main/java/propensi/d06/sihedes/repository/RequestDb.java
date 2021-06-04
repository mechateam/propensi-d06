package propensi.d06.sihedes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import propensi.d06.sihedes.model.*;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface RequestDb extends JpaRepository<RequestModel, Long> {
    List<RequestModel> findAllByResolverDepartemen(DepartemenModel departemen);
    List<RequestModel> findAllByIdApprover(Long id);
    List<RequestModel> findAllByPengaju(UserModel user);
    List<RequestModel> findRequestModelsByStatus(StatusModel statusModel);
    List<RequestModel> findRequestModelsByStatusAndResolver(StatusModel statusModel, UserModel resolver);
    List<RequestModel> findRequestModelsByStatusAndPengaju(StatusModel statusModel, UserModel userModel);
    List<RequestModel> findRequestModelsByStatusAndResolverDepartemen(StatusModel statusModel, DepartemenModel departemenModel);
    @Query(value = "SELECT * FROM request WHERE created_date Like %?1%", nativeQuery = true)
    List<RequestModel> findRequestModelsByCreatedDateContaining(@Param("createdDate") String createdDate);
}
