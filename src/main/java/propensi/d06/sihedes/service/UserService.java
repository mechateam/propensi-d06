package propensi.d06.sihedes.service;

import propensi.d06.sihedes.model.DepartemenModel;
import propensi.d06.sihedes.model.UserModel;

import java.util.List;

public interface UserService {
    UserModel addUser(UserModel user);
    String encrypt(String password);
    UserModel getUserbyUsername(String username);
<<<<<<< HEAD
    List<UserModel> getListUserbyDepartemen(DepartemenModel departemen);
=======
    UserModel getUserbyId(Long id);
>>>>>>> 3f774e20f97fa871367dc845719b82e1aa480505
    boolean isMatch(String newPassword, String oldPassword);
}