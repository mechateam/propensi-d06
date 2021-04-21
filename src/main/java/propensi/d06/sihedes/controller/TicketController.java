package propensi.d06.sihedes.controller;
import propensi.d06.sihedes.model.ProblemModel;
import propensi.d06.sihedes.model.RequestModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import propensi.d06.sihedes.model.UserModel;
import propensi.d06.sihedes.service.UserService;

import java.util.ArrayList;
import java.util.List;

@Controller
public class TicketController {

//    @Autowired
//    private ProblemService problemService;
//
//    @Autowired
//    private RequestService requestService;
    @Autowired
    private UserService userService;

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

    @GetMapping("problem/individual")
    public String individualProblem(
            @ModelAttribute ProblemModel problem,
            Model model){
        //nanti dijadiin conditional based on status di detailproblem
        //List<UserModel> userList = userService.getListUserbyDepartemen(problem.getResolver().getDepartemen());
        //model.addAttribute("userList", userList);
        return "individual-problem";
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

    @GetMapping("request/individual")
    public String individualRequest(
            @ModelAttribute RequestModel request,
            Model model){
        //nanti dijadiin conditional based on status di detailrequest
        return "individual-request";
    }

}
