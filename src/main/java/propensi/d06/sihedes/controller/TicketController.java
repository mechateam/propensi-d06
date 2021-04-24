package propensi.d06.sihedes.controller;

import org.springframework.beans.factory.parsing.Problem;
import org.springframework.security.core.context.SecurityContextHolder;
import propensi.d06.sihedes.model.*;
import propensi.d06.sihedes.service.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Date;
import java.util.List;

import javax.print.DocFlavor.STRING;

@Controller
public class TicketController {

    @Autowired
    private ProblemService problemService;

    @Autowired
    private RequestService requestService;

    @Autowired
    private StatusService statusService;

    @Autowired
    private UserService userService;

    @GetMapping("/tickets")
    public String listTickets(
            @ModelAttribute ProblemModel problem,
            RequestModel request,
            Model model) {

        // ProblemModel
        List<ProblemModel> listProblem = problemService.findAll();
        model.addAttribute("listProblem", listProblem);

        // RequestModel
        List<RequestModel> listRequest = requestService.findAll();
        model.addAttribute("listRequest", listRequest);

        //Checking
        boolean hasProblem = listProblem.size() > 0;
        model.addAttribute("hasProblem", hasProblem);
        boolean hasRequest = listRequest.size() > 0;
        model.addAttribute("hasRequest", hasRequest);

        // Return view template yang diinginkan
        return "allTickets";
    }

    @GetMapping("/problem/detail/{id_problem}")
    public String detailProblem(
            @PathVariable(value="id_problem") Long id_problem,
            Model model)
    {
        System.out.println("Ini Jawabannya " + id_problem);
        ProblemModel problem = problemService.findProblemById(id_problem);
        model.addAttribute("problem",problem);  
        return "detailProblem";
    }

    @GetMapping("/problem/resolver/{id_problem}")
    public String detailResolveProblem(
        @PathVariable(value="id_problem") Long id_problem,
        Model model
    ){
        ProblemModel problem = problemService.findProblemById(id_problem);
        model.addAttribute("problem",problem);
        // if (problem.getStatus().getId_status() == 1){
        //     return "detailProblem";
        // }
        // else if (problem.getStatus().getId_status() == 4){
        //     return "assignResolverProblem";
        // } else{
        //     return "allTickets";
        // } 
        return "assignResolverProblem";
    }

    @PostMapping("/problem/resolver/{id_problem}")
    public String ResolveProblem(
        @RequestParam(value = "jenisResolver") Long id,
        @PathVariable Long id_problem, Model model,
        RedirectAttributes redir) {
        ProblemModel problem = problemService.findProblemById(id_problem);
        long idStatus = 5;
        StatusModel status = statusService.findStatusById(idStatus);
        problem.setStatus(status);
        
        System.out.println(problem.getId_problem());
        problem.setResolver_departemen(problemService.getDepById(id));
        problemService.updateProblem(problem);
        return "redirect:/tickets";
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
        // if (request.getStatus().getId_status() == 1){
        //         return "detailRequest";
        //     }
        // else if (request.getStatus().getId_status() == 4){
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

    // Cancelled
    // @PostMapping("/request/resolver")
    // public String ResolveRequest(
    //     @RequestParam(value = "jenisResolver") Long id,
    //     @ModelAttribute RequestModel request,
    //     RedirectAttributes redir) {
    //     if (id == 0){
    //         redir.addFlashAttribute("gagal", "Resolver Departemen belum dipilih!");
    //         return "redirect:/request/resolver"; 
    //     } else {
    //         request.setResolver_departemen(requestService.getDepById(id));
    //         requestService.updateRequest(request);
    //         return "redirect:/tickets";
    //     }
    // }

    @GetMapping("/ticket/add")
    public String addTicket(Model model) {
        model.addAttribute("problem", new ProblemModel());
        model.addAttribute("request", new RequestModel());
        return "createTicket";
    }

    @PostMapping("/problem/add")
    public String problemSubmit(
            @ModelAttribute ProblemModel problem,
            RedirectAttributes redir,
            Model model) {

        long idStatus = 1;
        StatusModel status = statusService.findStatusById(idStatus);
        problem.setStatus(status);

        UserModel user = userService.getUserbyUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        problem.setPengaju(user);

        Date dateNow = new java.util.Date();
        problem.setCreated_date(dateNow);

        problemService.addProblem(problem);
        return "redirect:/tickets";
    }

    @PostMapping("/request/add")
    public String requestSubmit(
            @ModelAttribute RequestModel request,
            Model model) {

        model.addAttribute("request", new RequestModel());
        return "detailRequest";
    }
}
