package propensi.d06.sihedes.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import propensi.d06.sihedes.model.BOAModel;
import propensi.d06.sihedes.model.DepartemenModel;
import propensi.d06.sihedes.model.SLAModel;
import propensi.d06.sihedes.model.UserModel;
import propensi.d06.sihedes.service.BOAService;
import propensi.d06.sihedes.service.DepartemenService;
import propensi.d06.sihedes.service.SLAService;
import propensi.d06.sihedes.service.UserService;

import java.util.List;

@Controller
public class SLAController {

    @Autowired
    SLAService slaService;

    @Autowired
    DepartemenService departemenService;

    @Autowired
    UserService userService;

    @Autowired
    BOAService boaService;


    @GetMapping("/sla")
    public String viewDepartmentSLA(Model model){
        List<DepartemenModel> listDepartment = departemenService.getListDepartment();
        model.addAttribute("listDepartment",listDepartment);

        UserModel user = userService.getUserbyUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        model.addAttribute("user",user);

        return "daftar-department-sla";
    }

    @GetMapping("/sla/daftar")
    public String viewAllSLA(Model model){
        List<SLAModel> listSLA = slaService.getListSLA();
        model.addAttribute("listSLA",listSLA);

        UserModel user = userService.getUserbyUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        model.addAttribute("user",user);

        return "daftar-sla";
    }

    @GetMapping(value="/sla/daftar/{id_dept}")
    public String viewSlaDept(
            @PathVariable(value="id_dept") Long id_dept, Model model){
            DepartemenModel departemenSpesifik = departemenService.findDepartemenById(id_dept);

            List<SLAModel> listSLA = slaService.getAllSLAByDepartemen(departemenSpesifik);
            model.addAttribute("listSLA",listSLA);

            String departemenNama = departemenSpesifik.getNama_departemen();
            model.addAttribute("departemenNama",departemenNama);


            UserModel user = userService.getUserbyUsername(SecurityContextHolder.getContext().getAuthentication().getName());
            model.addAttribute("user",user);

        return "daftar-sla";
    }

    @GetMapping("/sla/daftar/detail/{id}")
    public String detailSLA(
            @PathVariable(value = "id") Long id, Model model){
            SLAModel sla = slaService.getSLAById(id);
            String namaSla = sla.getNama_sla();
            model.addAttribute("sla",sla);
            model.addAttribute("namaSla",namaSla);
            model.addAttribute("listBOA",sla.getListSLABOA());

            UserModel user = userService.getUserbyUsername(SecurityContextHolder.getContext().getAuthentication().getName());
            model.addAttribute("user",user);

        return "detail-sla";
    }

    @GetMapping("/sla/daftar/tambah")
    public String formTambahSLA(Model model){
        List<DepartemenModel> listDepartemen = departemenService.findAll();
        List<BOAModel> listBOA = boaService.findAll();
        model.addAttribute("listDepartemen",listDepartemen);
        model.addAttribute("listBOA",listBOA);
        return "form-add-sla";
    }

    @PostMapping("/sla/daftar/tambah")
    public String postTambahSLA(
            @ModelAttribute SLAModel sla,
            @RequestParam("completion_time_number") String completion_time_number,
            @RequestParam("completion_time_period") String completion_time_period,
            Model model){
        sla.setCompletion_time(completion_time_number + " " + completion_time_period);
        slaService.addSLA(sla);

        String link = "redirect:/sla/daftar/" + sla.getDepartemen().getId_dept();
        return link;

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

        String link = "redirect:/sla/daftar/" + sla.getDepartemen().getId_dept();
        return link;
    }

    @GetMapping("/sla/daftar/delete/{id}")
    public String deleteSLA(@PathVariable Long id, Model model){
        SLAModel sla = slaService.getSLAById(id);
        String link = "redirect:/sla/daftar/" + slaService.getSLAById(id).getDepartemen().getId_dept();

        slaService.deleteSLA(sla);

        return link;
    }
}
