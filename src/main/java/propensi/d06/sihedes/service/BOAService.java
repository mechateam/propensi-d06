package propensi.d06.sihedes.service;

import propensi.d06.sihedes.model.BOAModel;

import java.util.List;

public interface BOAService {
    List<BOAModel> findAll();
    List<BOAModel> findAllByRank(Integer rank);
}
