package propensi.d06.sihedes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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
}
