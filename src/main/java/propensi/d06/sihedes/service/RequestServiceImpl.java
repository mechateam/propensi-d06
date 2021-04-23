package propensi.d06.sihedes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import propensi.d06.sihedes.model.*;
import propensi.d06.sihedes.repository.*;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class RequestServiceImpl implements RequestService{
    @Autowired
    RequestDb requestDb;

    @Autowired
    DepartemenDb departemenDb;

    @Override
    public DepartemenModel getDepById(Long id){
        return departemenDb.findById(id).get();
    }

    @Override
    public void updateRequest(RequestModel request) {
        requestDb.save(request);
    }

    @Override
    public List<RequestModel> findAll() { return requestDb.findAll(); }

//    @Override
//    public List<RequestModel> findAllDesc() { return requestDb.findAllByOrderById_requestDesc(); }

    @Override
    public RequestModel findRequestById(Long id) { return requestDb.findById(id).get(); }
}
