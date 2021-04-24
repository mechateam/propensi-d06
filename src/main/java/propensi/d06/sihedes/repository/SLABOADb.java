package propensi.d06.sihedes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import propensi.d06.sihedes.model.DepartemenModel;
import propensi.d06.sihedes.model.SLABOAModel;
import propensi.d06.sihedes.model.SLAModel;

import java.util.List;
import java.util.Optional;

@Repository
public interface SLABOADb extends JpaRepository<SLABOAModel, Long> {
    List<SLABOAModel> findAllBySla(SLAModel sla);
    SLABOAModel findByBoa_Rank(Integer rank);
}

