package propensi.d06.sihedes.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import propensi.d06.sihedes.model.DepartemenModel;
import propensi.d06.sihedes.model.SLAModel;
import propensi.d06.sihedes.repository.DepartemenDb;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class DepartemenServiceImpl implements DepartemenService {

    @Autowired
    DepartemenDb departemenDb;

    @Override
    public List<DepartemenModel> findAll() { return departemenDb.findAll(); }

    @Override
    public DepartemenModel findDepartemenById(Long id) {
        return departemenDb.findById(id).get();
    }

    @Override
    public List<DepartemenModel> getListDepartment(){
        return departemenDb.findAll();
    }
}
