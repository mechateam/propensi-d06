package propensi.d06.sihedes.service;

import propensi.d06.sihedes.model.RequestModel;

import java.util.List;

public interface RequestService {
    List<RequestModel> findAll();
//    List<RequestModel> findAllDesc();
    RequestModel findRequestById(Long id);
}
