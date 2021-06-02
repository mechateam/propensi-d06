package propensi.d06.sihedes.service;

import propensi.d06.sihedes.model.DepartemenModel;
import propensi.d06.sihedes.model.UserModel;

import java.util.List;

public interface UserService {
    UserModel addUser(UserModel user);
    String encrypt(String password);
    UserModel getUserbyUsername(String username);
    UserModel getUserbyId(Long id);
    boolean isMatch(String newPassword, String oldPassword);
    List<UserModel> getListUserbyDepartemen(DepartemenModel departemen);
    UserModel changePass(UserModel user);
    UserModel updateUser(UserModel user);
}