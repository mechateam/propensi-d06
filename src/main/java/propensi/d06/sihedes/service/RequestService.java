package propensi.d06.sihedes.service;

import propensi.d06.sihedes.model.RequestModel;

public interface RequestService {

    RequestModel getRequestById(Long id);
    RequestModel updateApprovalRequest(RequestModel request);
    RequestModel rejectApprovalRequest(RequestModel request);
}
