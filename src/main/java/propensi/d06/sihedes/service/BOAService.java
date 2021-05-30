package propensi.d06.sihedes.service;

import propensi.d06.sihedes.model.BOAModel;

import java.util.List;
import java.util.Optional;

public interface BOAService {
    List<BOAModel> findAll();
    List<BOAModel> findAllByRank(Integer rank);
    Optional<BOAModel> findById(Long id);
}
