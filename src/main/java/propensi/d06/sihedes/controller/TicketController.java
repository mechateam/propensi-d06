package propensi.d06.sihedes.controller;

import org.springframework.beans.factory.parsing.Problem;
import org.springframework.security.core.context.SecurityContextHolder;
import propensi.d06.sihedes.model.*;
import propensi.d06.sihedes.service.*;
import org.springframework.beans.factory.annotation.*;
import propensi.d06.sihedes.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import propensi.d06.sihedes.service.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
public class TicketController {

    @Autowired
    private SLAService slaService;

    @Autowired
    private SLABOAService slaboaService;

    @Autowired
    private RequestService requestService;

    @Autowired
    private UserService userService;

    @Autowired
    private StatusService statusService;

    @Autowired
    private ProblemService problemService;

    @Autowired
    private DepartemenService departemenService;


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
        if (problem.getStatus().getId_status() == 1){
            return "detailProblem";
        }
        else if (problem.getStatus().getId_status() == 4){
            return "assignResolverProblem";
        } else{
            return "allTickets";
        }
    }

    // @GetMapping("/problem/resolver")
    // public String detailResolverProblem(
    //         @ModelAttribute ProblemModel problem,
    //         Model model) {

    //     return "assignResolverProblem";
    // }

    // @PostMapping("/problem/detail")
    // public String ResolveProblem(
    //     @RequestParam(value = "jenisResolver") Long id,
    //     @ModelAttribute ProblemModel problem,
    //     RedirectAttributes redir) {
    //     problem.setResolver_departemen(problemService.getDepById(id));
    //     problemService.updateProblem(problem);
    //     return "allTickets";
    // }



    @Deprecated
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
            return "redirect:/tickets";
        }
    }

    @GetMapping("/ticket/add")
    public String addTicket(Model model) {
        model.addAttribute("problem", new ProblemModel());
        UserModel user = userService.getUserbyUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        DepartemenModel departemen = user.getDepartemen();

//        List<DepartemenModel> deptList = departemenService.getListDepartment();
//        List<DepartemenModel> deptSLA = null;
//        Hashtable<DepartemenModel, List<SLAModel>> my_dict = new Hashtable<DepartemenModel, List<SLAModel>>();
//        for(DepartemenModel dept : deptList){
//            if (dept.getListSLA().size() != 0){
//                deptSLA.add(dept);
//            }
//        }
//
//        for(DepartemenModel dept : deptSLA){
//            List<SLAModel> getSla = slaService.getAllSLAByDepartemen(dept);
//            my_dict.put(dept, getSla);
//        }


        model.addAttribute("departemen", departemen);
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

    // ini buat approval, mau digabung sama yg atas juga gapapa
    @GetMapping("/request/detail/{id}")
    public String detailRequestApproval(@PathVariable Long id, HttpServletRequest req, Model model){

        UserModel userLoggedin = userService.getUserbyUsername(req.getRemoteUser());
        RequestModel request = requestService.getRequestById(id);
        SLAModel sla = request.getSla();
        List<SLABOAModel> listBOA = slaboaService.getSLABOABySLAId(sla.getId_sla());

        if (request.getStatus().getNamaStatus().equals("Waiting for Approval")){

            if (request.getId_approver() == null){
                for (SLABOAModel boa: listBOA) {
                    if (boa.getBoa().getRank() ==1){
                        request.setId_approver(boa.getBoa().getUser().getId_user());
                    }
                }

            }

            Long idApprover = new Long(request.getId_approver());
            model.addAttribute("user",userLoggedin);
            model.addAttribute("request",request);
            model.addAttribute("userApproval", userService.getUserbyId(idApprover));

            return "detailRequestApproval";
        }

        return null;
    }

    @PostMapping("/request/approve")
    public String approveRequest(@ModelAttribute RequestModel request, Model model){
        requestService.updateApprovalRequest(request);
        model.addAttribute("request",request);
        return "redirect:/tickets";
    }

    @PostMapping("/request/reject")
    public String rejectRequest(@ModelAttribute RequestModel request, Model model){
        requestService.rejectApprovalRequest(request);
        model.addAttribute("request",request);
        return "redirect:/tickets";
    }
}
