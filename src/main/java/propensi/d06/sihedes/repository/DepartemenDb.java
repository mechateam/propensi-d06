package propensi.d06.sihedes.repository;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import propensi.d06.sihedes.model.DepartemenModel;
import propensi.d06.sihedes.model.SLAModel;


@Repository
@Transactional
public interface DepartemenDb extends JpaRepository<DepartemenModel, Long> {
    Optional<DepartemenModel> findById(Long id);
}
