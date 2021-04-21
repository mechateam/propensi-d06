package propensi.d06.sihedes.controller;
import propensi.d06.sihedes.model.ProblemModel;
import propensi.d06.sihedes.model.RequestModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class TicketController {

//    @Autowired
//    private ProblemService problemService;
//
//    @Autowired
//    private RequestService requestService;

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
//        // Return view template yang diinginkan
        return "detailProblem";
    }

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
        return "detailRequest";
    }

    @GetMapping("/ticket/add")
    public String addTicket(Model model) {
        model.addAttribute("problem", new ProblemModel());
        model.addAttribute("request", new RequestModel());
        return "createTicket";
    }

    @PostMapping("/problem/add")
    public String problemSubmit(
            @ModelAttribute ProblemModel problem,
            Model model) {

        model.addAttribute("problem", new ProblemModel());
        return "detailProblem";
    }

    @PostMapping("/request/add")
    public String requestSubmit(
            @ModelAttribute RequestModel request,
            Model model) {

        model.addAttribute("request", new RequestModel());
        return "detailRequest";
    }
}
