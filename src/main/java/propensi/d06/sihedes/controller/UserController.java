package propensi.d06.sihedes.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
}
