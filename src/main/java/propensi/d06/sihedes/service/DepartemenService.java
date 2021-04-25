package propensi.d06.sihedes.service;

import propensi.d06.sihedes.model.DepartemenModel;

import java.util.List;

public interface DepartemenService {
    List<DepartemenModel> findAll();
    DepartemenModel findDepartemenById(Long id);
    List<DepartemenModel> getListDepartment();
}
