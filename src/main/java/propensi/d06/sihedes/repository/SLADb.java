package propensi.d06.sihedes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import propensi.d06.sihedes.model.DepartemenModel;
import propensi.d06.sihedes.model.SLAModel;

import java.util.List;

@Repository
public interface SLADb extends JpaRepository<SLAModel, Long> {
    List<SLAModel> findAllByDepartemen(DepartemenModel departemen);
}
