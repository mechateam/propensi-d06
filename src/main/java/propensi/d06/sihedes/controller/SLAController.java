package propensi.d06.sihedes.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import propensi.d06.sihedes.model.*;
import propensi.d06.sihedes.service.*;

import javax.servlet.http.HttpServletRequest;
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

    @Autowired
    SLABOAService slaboaService;


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
        List<BOAModel> listBOASatu = boaService.findAllByRank(1);
        List<BOAModel> listBOADua = boaService.findAllByRank(2);
        List<BOAModel> listBOATiga = boaService.findAllByRank(3);
        model.addAttribute("listDepartemen",listDepartemen);
        model.addAttribute("listBOASatu",listBOASatu);
        model.addAttribute("listBOADua",listBOADua);
        model.addAttribute("listBOATiga",listBOATiga);
        return "form-add-sla";
    }

    @PostMapping("/sla/daftar/tambah")
    public String postTambahSLA(
            @ModelAttribute SLAModel sla,
            @RequestParam("completion_time_number") String completion_time_number,
            @RequestParam("completion_time_period") String completion_time_period,
            @RequestParam(value = "rank_satu", required = false) Integer[] boxRankSatu,
            @RequestParam(value = "rank_dua", required = false) Integer[] boxRankDua,
            @RequestParam(value = "rank_tiga", required = false) Integer[] boxRankTiga,
            Model model){
        sla.setCompletion_time(completion_time_number + " " + completion_time_period);
        slaService.addSLA(sla);

        if (boxRankSatu != null){
            for (int i=0; i < boxRankSatu.length;i++){
                Long j = new Long(boxRankSatu[i]);
                SLABOAModel targetSLABOA = new SLABOAModel();
                targetSLABOA.setBoa(boaService.findById(j).get());
                targetSLABOA.setSla(sla);
                slaboaService.addSLABOA(targetSLABOA);
            }
        }

        if (boxRankDua != null){
            for (int i=0; i < boxRankDua.length;i++){
                Long j = new Long(boxRankDua[i]);
                SLABOAModel targetSLABOA = new SLABOAModel();
                targetSLABOA.setBoa(boaService.findById(j).get());
                targetSLABOA.setSla(sla);
                slaboaService.addSLABOA(targetSLABOA);
            }
        }

        if (boxRankTiga != null){
            for (int i=0; i < boxRankTiga.length;i++){
                Long j = new Long(boxRankTiga[i]);
                SLABOAModel targetSLABOA = new SLABOAModel();
                targetSLABOA.setBoa(boaService.findById(j).get());
                targetSLABOA.setSla(sla);
                slaboaService.addSLABOA(targetSLABOA);
            }
        }

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
