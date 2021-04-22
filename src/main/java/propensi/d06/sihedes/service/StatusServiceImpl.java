package propensi.d06.sihedes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import propensi.d06.sihedes.model.StatusModel;
import propensi.d06.sihedes.repository.StatusDb;

import javax.transaction.Transactional;


@Service
@Transactional
public class StatusServiceImpl implements StatusService {

    @Autowired
    StatusDb statusDb;

    public StatusModel getStatusByStatusName(String name){
        return statusDb.findByNamaStatus(name);
    }
}
