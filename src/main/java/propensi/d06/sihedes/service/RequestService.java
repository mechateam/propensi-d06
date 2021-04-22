package propensi.d06.sihedes.service;

import propensi.d06.sihedes.model.*;

public interface RequestService {
    void updateRequest(RequestModel request);
    DepartemenModel getDepById(Long id);
}

