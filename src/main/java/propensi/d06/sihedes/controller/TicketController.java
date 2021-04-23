package propensi.d06.sihedes.controller;

import propensi.d06.sihedes.model.*;
import propensi.d06.sihedes.service.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class TicketController {
    @Autowired
    private ProblemService problemService;
    
    @Autowired
    private RequestService requestService;

    @GetMapping("/tickets")
    public String listTickets(
            @ModelAttribute ProblemModel problem,
            RequestModel request,
            Model model) {

//        // Mendapatkan semua HotelModel
//        List<HotelModel> listHotel = hotelService.getHotelListOrderByIdDesc();
//
//        // Add variabel semua HotelModel ke 'listHotel' untuk dirender pada thymeleaf
//        model.addAttribute("listHotel", listHotel);
//
//
//        boolean hasHotel = listHotel.size() > 0;
//        model.addAttribute("hasHotel", hasHotel);
//
//        model.addAttribute("listHotel", listHotel);
//        // Return view template yang diinginkan
        return "allTickets";
    }

    @GetMapping("/problem/detail")
    public String detailProblem(
            @ModelAttribute ProblemModel problem,
            Model model) {

//        // Mendapatkan semua HotelModel
//        List<HotelModel> listHotel = hotelService.getHotelListOrderByIdDesc();
//
//        // Add variabel semua HotelModel ke 'listHotel' untuk dirender pada thymeleaf
//        model.addAttribute("listHotel", listHotel);
//
//
//        boolean hasHotel = listHotel.size() > 0;
//        model.addAttribute("hasHotel", hasHotel);
//
//        model.addAttribute("listHotel", listHotel);
    //    Return view template yang diinginkan
            // if (problem.getStatus().getNama_status() == "open"){
            //     return "detailProblem";
            // }
            // else if (problem.getStatus().getNama_status() == "Waiting for Assignment"){
            //     return "assignResolverProblem";
            // }
        return "detailProblem";
    }

    @GetMapping("/problem/resolver")
    public String detailResolverProblem(
            @ModelAttribute ProblemModel problem,
            Model model) {

        return "assignResolverProblem";
    }

    // @PostMapping("/problem/detail")
    // public String ResolveProblem(
    //     @RequestParam(value = "jenisResolver") Long id,
    //     @ModelAttribute ProblemModel problem,
    //     RedirectAttributes redir) {
    //     problem.setResolver_departemen(problemService.getDepById(id));
    //     problemService.updateProblem(problem);
    //     return "allTickets";
    // }


    @GetMapping("/request/detail")
    public String detailRequest(
            @ModelAttribute RequestModel request,
            Model model) {

//        // Mendapatkan semua HotelModel
//        List<HotelModel> listHotel = hotelService.getHotelListOrderByIdDesc();
//
//        // Add variabel semua HotelModel ke 'listHotel' untuk dirender pada thymeleaf
//        model.addAttribute("listHotel", listHotel);
//
//
//        boolean hasHotel = listHotel.size() > 0;
//        model.addAttribute("hasHotel", hasHotel);
//
//        model.addAttribute("listHotel", listHotel);
//        // Return view template yang diinginkan
        // if (request.getStatus().getNama_status() == "open"){
        //         return "detailRequest";
        //     }
        // else if (request.getStatus().getNama_status() == "Waiting for Assignment"){
        //     return "assignResolverRequest";
        // }
        return "detailRequest";
    }

    @GetMapping("/request/resolver")
    public String detailResolveRequest(
            @ModelAttribute RequestModel request,
            Model model) {

        return "assignResolverRequest";
    }

    @PostMapping("/request/resolver")
    public String ResolveRequest(
        @RequestParam(value = "jenisResolver") Long id,
        @ModelAttribute RequestModel request,
        RedirectAttributes redir) {
        if (id == 0){
            redir.addFlashAttribute("gagal", "Resolver Departemen belum dipilih!");
            return "redirect:/request/resolver"; 
        } else {
            request.setResolver_departemen(requestService.getDepById(id));
            requestService.updateRequest(request);
            return "allTickets";
        }
    }
}