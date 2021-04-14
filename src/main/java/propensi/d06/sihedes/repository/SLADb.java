package propensi.d06.sihedes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import propensi.d06.sihedes.model.SLAModel;

@Repository
public interface SLADb extends JpaRepository<SLAModel, Long> {
}