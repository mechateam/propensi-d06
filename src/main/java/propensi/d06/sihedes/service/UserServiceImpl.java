package propensi.d06.sihedes.service;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import propensi.d06.sihedes.model.DepartemenModel;
import propensi.d06.sihedes.model.ProblemModel;
import propensi.d06.sihedes.model.StatusModel;
import propensi.d06.sihedes.model.UserModel;
import propensi.d06.sihedes.repository.UserDb;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDb userDb;

    @Override
    public UserModel addUser(UserModel user) {
        String pass = encrypt(user.getPassword());
        user.setPassword(pass);
        return userDb.save(user);
    }

    @Override
    public String encrypt(String password){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(password);
        return hashedPassword;
    }

    @Override
    public UserModel getUserbyUsername(String username) {
        return userDb.findByUsername(username);
    }

    @Override
    public List<UserModel> getListUserbyDepartemen(DepartemenModel departemen) {
        return userDb.findUserModelsByDepartemen(departemen);
    }


    @Override
    public boolean isMatch(String newPassword, String oldPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(newPassword, oldPassword);
    }

    @Override
    public UserModel getUserbyId(Long id){ return userDb.findById(id).get();}

    @Override
    public UserModel changePass(UserModel user){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        UserModel targetUser = userDb.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        String existingPassword = user.getPassword();
        String dbPassword = targetUser.getPassword();

        if (passwordEncoder.matches(existingPassword,dbPassword)){
            String pass = encrypt(user.getNew_pass());
            targetUser.setPassword(pass);
            userDb.save(targetUser);
            return targetUser;
        }
        else {
            return null;
        }
    }
    @Override
    public UserModel updateUser(UserModel user) {
        UserModel targetUser = userDb.findById(user.getId_user()).get();
//        try {
            targetUser.setEmail(user.getEmail());
            targetUser.setNo_hp(user.getNo_hp());
            userDb.save(targetUser);
            return targetUser;
//        } catch (NullPointerException nullPointerException) {
//            return null;
//        }
    }
}
