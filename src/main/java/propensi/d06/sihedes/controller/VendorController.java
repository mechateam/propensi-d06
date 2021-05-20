package propensi.d06.sihedes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import propensi.d06.sihedes.model.DepartemenModel;
import propensi.d06.sihedes.model.SLAModel;
import propensi.d06.sihedes.model.UserModel;
import propensi.d06.sihedes.model.VendorModel;
import propensi.d06.sihedes.service.DepartemenService;
import propensi.d06.sihedes.service.VendorService;
import propensi.d06.sihedes.service.UserService;

import java.util.List;

@Controller
public class VendorController {

    @Autowired
    VendorService vendorService;

    @GetMapping("/vendor")
    public String viewVendor(Model model){
        List<VendorModel> listVendor = vendorService.getListVendor();
        model.addAttribute("listVendor",listVendor);

//        UserModel user = userService.getUserbyUsername(SecurityContextHolder.getContext().getAuthentication().getName());
//        model.addAttribute("user",user);
        return "daftar-vendor";
    }

    @GetMapping("/vendor/tambah")
    public String formTambahVendor(Model model){

        return "form-add-vendor";
    }

    @PostMapping("/vendor/tambah")
    public String postTambahVendor(
            @ModelAttribute VendorModel vendor, Model model){
        vendorService.addVendor(vendor);
        return "redirect:/vendor";
    }

    @GetMapping("/vendor/{id}")
    public String detailVendor(
            @PathVariable(value = "id") Long id, Model model){
        VendorModel vendor = vendorService.getVendorbyId(id);
        model.addAttribute("vendor",vendor);

//        UserModel user = userService.getUserbyUsername(SecurityContextHolder.getContext().getAuthentication().getName());
//        model.addAttribute("user",user);

        return "detail-vendor";
    }


}
