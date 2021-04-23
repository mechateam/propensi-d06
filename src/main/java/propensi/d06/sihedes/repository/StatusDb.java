package propensi.d06.sihedes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import propensi.d06.sihedes.model.StatusModel;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface StatusDb extends JpaRepository<StatusModel, Long> {
//    Optional<RequestModel> findById_request(Long id);
//    List<RequestModel> findAllByOrderById_requestDesc();
}
