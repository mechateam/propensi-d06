package propensi.d06.sihedes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import propensi.d06.sihedes.model.RoleModel;
import propensi.d06.sihedes.repository.RoleDb;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class RoleServiceImpl implements RoleService{

    @Autowired
    RoleDb roleDb;

    @Override
    public List<RoleModel> findAll() { return roleDb.findAll(); }

    @Override
    public RoleModel findRoleById(Long id) {
        return roleDb.findById(id).get();
    }
}
