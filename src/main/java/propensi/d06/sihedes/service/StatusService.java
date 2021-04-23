package propensi.d06.sihedes.service;

import propensi.d06.sihedes.model.StatusModel;

import java.util.List;

public interface StatusService {
    List<StatusModel> findAll();
    //    List<ProblemModel> findAllDesc();
    StatusModel findStatusById(Long id);

    StatusModel getStatusByStatusName(String name);
}
