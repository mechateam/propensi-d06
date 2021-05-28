package propensi.d06.sihedes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import propensi.d06.sihedes.model.BOAModel;

import java.util.List;

public interface BOADb extends JpaRepository<BOAModel, Long> {
    List<BOAModel> findAllByRank(Integer rank);
}
