package propensi.d06.sihedes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import propensi.d06.sihedes.model.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface RequestDb extends JpaRepository<RequestModel, Long> {
//    Optional<RequestModel> findById_request(Long id);
//    List<RequestModel> findAllByOrderById_requestDesc();
}
