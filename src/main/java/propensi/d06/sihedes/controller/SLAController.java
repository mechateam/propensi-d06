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
    public String viewDepartmentSLA(Model model){
        List<DepartemenModel> listDepartment = departemenService.getListDepartment();
        model.addAttribute("listDepartment",listDepartment);
        return "daftar-department-sla";
    }

    @GetMapping("/sla/daftar")
    public String viewAllSLA(Model model){
        List<SLAModel> listSLA = slaService.getListSLA();
        model.addAttribute("listSLA",listSLA);
        return "daftar-sla";
    }

        @RequestMapping(value="/sla/daftar/{id_dept}")
    public String viewSlaDept(
            @PathVariable(value="id_dept") Long id_dept, Model model){
            DepartemenModel departemenNama = departemenService.findDepartemenById(id_dept);
            List<SLAModel> listSLA = slaService.getAllSLAByDepartemen(departemenNama);
            model.addAttribute("listSLA",listSLA);
        return "daftar-sla";
    }

    @GetMapping("/sla/daftar/detail/{id}")
    public String detailSLA(
            @PathVariable(value = "id") Long id,
            Model model
    ){
        SLAModel sla = slaService.getSLAById(id);
        model.addAttribute("sla",sla);
        model.addAttribute("listBOA",sla.getListSLABOA());
        return "detail-sla";
    }

    @GetMapping("/sla/daftar/tambah")
    public String formTambahSLA(Model model){
        List<DepartemenModel> listDepartemen = departemenService.findAll();
        model.addAttribute("listDepartemen",listDepartemen);
        return "form-add-sla";
    }

    @PostMapping("/sla/daftar/tambah")
    public String postTambahSLA(@ModelAttribute SLAModel sla, Model model){
        slaService.addSLA(sla);
        return "redirect:/sla";
    }

    @GetMapping("/sla/daftar/update/{id}")
    public String formUpdateSLA(@PathVariable Long id, Model model){
        SLAModel sla = slaService.getSLAById(id);
        List<DepartemenModel> listDepartemen = departemenService.findAll();
        model.addAttribute("listDepartemen",listDepartemen);
        model.addAttribute("sla",sla);
        return "form-update-sla";
    }

    @PostMapping("/sla/daftar/update")
    public String putUpdateSLA(@ModelAttribute SLAModel sla, Model model){
        slaService.updateSLA(sla);
        return "redirect:/sla";
    }

    @GetMapping("/sla/daftar/delete/{id}")
    public String deleteSLA(@PathVariable Long id, Model model){
        SLAModel sla = slaService.getSLAById(id);
        slaService.deleteSLA(sla);
        return "redirect:/sla";
    }
}
