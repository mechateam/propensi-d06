package propensi.d06.sihedes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import propensi.d06.sihedes.model.RequestModel;
import propensi.d06.sihedes.repository.RequestDb;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class RequestServiceImpl implements RequestService{

    @Autowired
    RequestDb requestDb;

    @Override
    public List<RequestModel> findAll() { return requestDb.findAll(); }

//    @Override
//    public List<RequestModel> findAllDesc() { return requestDb.findAllByOrderById_requestDesc(); }

    @Override
    public RequestModel findRequestById(Long id) { return requestDb.findById(id).get(); }
}
