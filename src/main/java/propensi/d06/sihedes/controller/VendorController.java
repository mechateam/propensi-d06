package propensi.d06.sihedes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import propensi.d06.sihedes.model.*;
import propensi.d06.sihedes.service.*;

import java.util.List;

@Controller
public class VendorController {

    @Autowired
    VendorService vendorService;

    @Autowired
    private ProblemService problemService;

    @Autowired
    private RequestService requestService;

    @GetMapping("/vendor")
    public String viewVendor(Model model){
        List<VendorModel> listVendor = vendorService.getListVendor();
        model.addAttribute("listVendor",listVendor);

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

        List<RequestModel> listRequest = vendor.getListVendorRequest();
        boolean hasRequest = listRequest.size() > 0;
        model.addAttribute("hasRequest", hasRequest);
        model.addAttribute("listRequest", listRequest);

        List<ProblemModel> listProblem = vendor.getListVendorProblem();
        boolean hasProblem = listProblem.size() > 0;
        model.addAttribute("hasProblem", hasProblem);
        model.addAttribute("listProblem", listProblem);

        return "detail-vendor";
    }

    @GetMapping("/vendor/update/{id}")
    public String formUpdateVendor(@PathVariable Long id, Model model){
        VendorModel vendor = vendorService.getVendorbyId(id);
        model.addAttribute("vendor",vendor);
        return "form-update-vendor";
    }

    @PostMapping("/vendor/update")
    public String putUpdateSLA(@ModelAttribute VendorModel vendor, Model model){
        vendorService.updateVendor(vendor);

        String link = "redirect:/vendor/" + vendor.getId_vendor();
        return link;
    }

    @GetMapping("/vendor/delete/{id}")
    public String deleteVendor(@PathVariable Long id, Model model){
        VendorModel vendor = vendorService.getVendorbyId(id);
        vendorService.deleteVendor(vendor);
        return "redirect:/vendor";
    }



}
