package propensi.d06.sihedes.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import propensi.d06.sihedes.model.DepartemenModel;
import propensi.d06.sihedes.model.SLAModel;
import propensi.d06.sihedes.service.DepartemenService;
import propensi.d06.sihedes.service.SLAService;

import java.util.List;

@Controller
public class SLAController {

    @Autowired
    SLAService slaService;

    @Autowired
    DepartemenService departemenService;


    @GetMapping("/sla")
    public String viewAllSLA(Model model){
        List<SLAModel> listSLA = slaService.getListSLA();
        model.addAttribute("listSLA",listSLA);
        return "daftar-sla";
    }

    @GetMapping("/sla/detail/{id}")
    public String detailSLA(
            @PathVariable(value = "id") Long id,
            Model model
    ){
        SLAModel sla = slaService.getSLAById(id);
        model.addAttribute("sla",sla);
        model.addAttribute("listBOA",sla.getListSLABOA());
        return "detail-sla";
    }

    @GetMapping("/sla/tambah")
    public String formTambahSLA(Model model){
        List<DepartemenModel> listDepartemen = departemenService.findAll();
        model.addAttribute("listDepartemen",listDepartemen);
        return "form-add-sla";
    }

    @PostMapping("/sla/tambah")
    public String postTambahSLA(@ModelAttribute SLAModel sla, Model model){
        slaService.addSLA(sla);
        return "redirect:/sla";
    }

    @GetMapping("/sla/update/{id}")
    public String formUpdateSLA(@PathVariable Long id, Model model){
        SLAModel sla = slaService.getSLAById(id);
        List<DepartemenModel> listDepartemen = departemenService.findAll();
        model.addAttribute("listDepartemen",listDepartemen);
        model.addAttribute("sla",sla);
        return "form-update-sla";
    }

    @PostMapping("/sla/update")
    public String putUpdateSLA(@ModelAttribute SLAModel sla, Model model){
        slaService.updateSLA(sla);
        return "redirect:/sla";
    }

    @GetMapping("/sla/delete/{id}")
    public String deleteSLA(@PathVariable Long id, Model model){
        SLAModel sla = slaService.getSLAById(id);
        slaService.deleteSLA(sla);
        return "redirect:/sla";
    }
}
