package propensi.d06.sihedes.service;

import propensi.d06.sihedes.model.UserModel;

public interface UserService {
    UserModel addUser(UserModel user);
    String encrypt(String password);
    UserModel getUserbyUsername(String username);
    UserModel getUserbyId(Long id);
    boolean isMatch(String newPassword, String oldPassword);
}