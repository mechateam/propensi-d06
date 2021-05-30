package propensi.d06.sihedes.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import propensi.d06.sihedes.model.*;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import java.io.IOException;
import java.util.List;

public interface RequestService {
    void updateRequest(RequestModel request);
    DepartemenModel getDepById(Long id);
    List<RequestModel> findAll();
    List<RequestModel> getRequestByDepartment(DepartemenModel departemen);
    List<RequestModel> getRequestByPengaju(UserModel user);
    RequestModel getRequestById(Long id);
    RequestModel updateApprovalRequest(RequestModel request);
    RequestModel vendorRequest(RequestModel request);
    RequestModel rejectApprovalRequest(RequestModel request);
    void addRequest(RequestModel request);
    Page<RequestModel> findPaginated(Pageable pageable, List<RequestModel> requests);
    RequestModel updateRequestStatus(RequestModel request);
    List<RequestModel> findAllRequestBasedOnIdApprover(UserModel user);
    void makeCode(RequestModel request);
    String getRandomChar(int length);
    String getRandomInt(int length);
    void testDelayStatus(RequestModel request);
    void sendmail(String emailRecipient) throws AddressException, MessagingException, IOException;
}
