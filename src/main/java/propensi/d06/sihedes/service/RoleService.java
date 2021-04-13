package propensi.d06.sihedes.service;

import propensi.d06.sihedes.model.RoleModel;

import java.util.List;

public interface RoleService {
    List<RoleModel> findAll();
    RoleModel findRoleById(Long id);
}
