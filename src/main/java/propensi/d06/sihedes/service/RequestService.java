package propensi.d06.sihedes.service;

import propensi.d06.sihedes.model.*;

import java.util.List;

public interface RequestService {
    void updateRequest(RequestModel request);
    DepartemenModel getDepById(Long id);
    List<RequestModel> findAll();
    List<RequestModel> getRequestByDepartment(DepartemenModel departemen);
    RequestModel getRequestById(Long id);
    RequestModel updateApprovalRequest(RequestModel request);
    RequestModel rejectApprovalRequest(RequestModel request);
}
