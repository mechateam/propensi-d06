package propensi.d06.sihedes.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import propensi.d06.sihedes.model.RoleModel;
import propensi.d06.sihedes.model.UserModel;
import propensi.d06.sihedes.service.DepartemenService;
import propensi.d06.sihedes.service.RoleService;
import propensi.d06.sihedes.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {


    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private DepartemenService departemenService;

    // Menampilkan form ddd user
    @RequestMapping("/add")
    public String addUserForm(Model model){
        // Menampilkan list role
        model.addAttribute("listRole", roleService.findAll());
        model.addAttribute("listDepartemen", departemenService.findAll());
        return "form-add-user";
    }

    // Mengirimkan hasil form yang telah diisi
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addUserSubmit(@ModelAttribute UserModel user){
        System.out.println("aku no telp"+user.getNo_hp());

        // Menambahkan user ke internal sipelatihan dan mengirimkan ke sipegawai
        userService.addUser(user);

        return "redirect:/user/add";
    }

    @GetMapping("/profil")
    public String profilePage(Model model){
        UserModel user = userService.getUserbyUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        model.addAttribute("user",user);
        return "profil";
    }

    @GetMapping("/changepass")
    public String changePassPage(Model model){
        UserModel user = userService.getUserbyUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        model.addAttribute("user",user);
        return "change_pass";

    }
    @PostMapping("/changepass")
    public String changePassSubmit(@ModelAttribute UserModel user, Model model, RedirectAttributes attributes) {
        UserModel userLoggedIn = userService.getUserbyUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        model.addAttribute("user",userLoggedIn);
        if (userService.changePass(user) != null){
            model.addAttribute("message","Password Berhasil Diganti");
            return "change_pass";
        }
        else {
            model.addAttribute("message","Password anda salah");
            return "change_pass";
        }
    }
}

