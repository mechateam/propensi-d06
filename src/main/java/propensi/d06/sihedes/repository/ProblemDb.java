package propensi.d06.sihedes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import propensi.d06.sihedes.model.*;

import javax.transaction.Transactional;
import java.util.Optional;

import java.util.List;

@Repository
@Transactional
public interface ProblemDb extends JpaRepository<ProblemModel, Long> {
    Optional<ProblemModel> findById(Long id);
    List<ProblemModel> findAllByResolverDepartemen(DepartemenModel departemen);

}
