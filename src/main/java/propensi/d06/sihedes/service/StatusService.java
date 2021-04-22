package propensi.d06.sihedes.service;

import propensi.d06.sihedes.model.StatusModel;

public interface StatusService {

    StatusModel getStatusByStatusName(String name);
}
