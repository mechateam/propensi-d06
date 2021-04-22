package propensi.d06.sihedes.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import propensi.d06.sihedes.model.StatusModel;

@Repository
public interface StatusDb extends JpaRepository<StatusModel, Long> {

    StatusModel findByNamaStatus(String name);
}
