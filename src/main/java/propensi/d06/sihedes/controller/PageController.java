package propensi.d06.sihedes.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import propensi.d06.sihedes.model.DepartemenModel;
import propensi.d06.sihedes.model.ProblemModel;
import propensi.d06.sihedes.model.RequestModel;
import propensi.d06.sihedes.model.UserModel;
import propensi.d06.sihedes.service.ProblemService;
import propensi.d06.sihedes.service.RequestService;
import propensi.d06.sihedes.service.UserService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class PageController {
    @Autowired
    UserService userService;

    @Autowired
    RequestService requestService;

    @Autowired
    ProblemService problemService;

    @RequestMapping("/")
    public String home(Model model){
        UserModel user = userService.getUserbyUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        DepartemenModel dept = user.getDepartemen();
        int allProblemPengaju = problemService.getProblemByPengaju(user).size();
        int allRequestPengaju = requestService.getRequestByPengaju(user).size();
        int closedProblemPengaju = problemService.getProblemByStatusIdAndPengaju(8L, user).size();
        int closedRequestPengaju = requestService.findRequestsByStatusIdAndPengaju(8L, user).size();
        int doneProblemPengaju = problemService.getProblemByStatusIdAndPengaju(7L, user).size();
        int doneRequestPengaju = requestService.findRequestsByStatusIdAndPengaju(7L, user).size();

        int yourTickets = allProblemPengaju+allRequestPengaju-closedProblemPengaju-closedRequestPengaju;
        int inProgressTickets = allProblemPengaju+allRequestPengaju-closedProblemPengaju-closedRequestPengaju-doneProblemPengaju-doneRequestPengaju;
        int waitingAssignmentTickets = problemService.getProblemByStatusId(4L).size()+requestService.findRequestsByStatusId(4L).size();
        int waitingIndividualAssignmentTickets = problemService.getProblemByStatusIdAndDepartmentResolver(5L, dept).size()+requestService.findRequestsByStatusIdAndResolverDepartment(5L,dept).size();
        int onGoingResolverTickets = problemService.getProblemByStatusIdAndResolver(6L, user).size()+requestService.findRequestsByStatusIdAndResolver(6L, user).size();
        int doneTickets=doneProblemPengaju+doneRequestPengaju;

        model.addAttribute("yourTickets", yourTickets);
        model.addAttribute("inProgressTickets", inProgressTickets);
        model.addAttribute("doneTickets", doneTickets);
        model.addAttribute("waitingAssignment", waitingAssignmentTickets);
        model.addAttribute("waitingIndividual", waitingIndividualAssignmentTickets);
        model.addAttribute("onGoingResolverTickets", onGoingResolverTickets);

        // ProblemModel
        List<ProblemModel> listProblem = new ArrayList<>();
        List<RequestModel> listRequest = new ArrayList<>();
        if(user.getId_role().getId_role() == Long.parseLong("2")) {
            List<ProblemModel> problems = problemService.getProblemByStatusId(Long.parseLong("4"));
            List<RequestModel> requests = requestService.findRequestsByStatusId(Long.parseLong("4"));
            listProblem.addAll(problems);
            if (problems.size()>3){
                listProblem = listProblem.subList(0,3);
            }
            listRequest.addAll(requests);
            if (requests.size()>3){
                listRequest = listRequest.subList(0,3);
            }
            List<ProblemModel> onGoingProblems = problemService.getProblemByStatusIdAndResolver(6L, user);
            List<RequestModel> onGoingRequests = requestService.findRequestsByStatusIdAndResolver(6L, user);
            if (onGoingProblems.size()>3){
                model.addAttribute("onGoingProblems", onGoingProblems.subList(0,3));
            } else {
                model.addAttribute("onGoingProblems", onGoingProblems);
            }
            if (onGoingRequests.size()>3){
                model.addAttribute("onGoingRequests", onGoingRequests.subList(0,3));
            } else {
                model.addAttribute("onGoingProblems", onGoingProblems);
            }
            if (onGoingProblems.size()>0){
                model.addAttribute("hasProblemOG", true);
            }
            if (onGoingRequests.size()>0){
                model.addAttribute("hasRequestOG", true);
            }

            Map<String, Long> mapData = new HashMap<String, Long>();
            Map<String, Long>[] pieChartData = new Map[1];
            pieChartData[0] = mapData;


            model.addAttribute("pieChartData", pieChartData);

        }
        else if (user.getId_role().getId_role() == Long.parseLong("3") || user.getId_role().getId_role() == Long.parseLong("6")){
            List<ProblemModel> problems = problemService.getProblemByPengaju(user);
            List<RequestModel> requests = requestService.getRequestByPengaju(user);
            listProblem.addAll(problems);
            listRequest.addAll(requests);
            List<ProblemModel> doneProblems = problemService.getProblemByStatusIdAndPengaju(7L, user);
            List<RequestModel> doneRequests = requestService.findRequestsByStatusIdAndPengaju(7L, user);
            if (doneProblems.size()>3){
                model.addAttribute("doneProblems", doneProblems.subList(0,3));
            } else {
                model.addAttribute("doneProblems", doneProblems);
            }
            if (doneRequests.size()>3){
                model.addAttribute("doneRequests", doneRequests.subList(0,3));
            } else {
                model.addAttribute("doneRequests", doneRequests);
            }
        }
        else {
            List<ProblemModel> problems = problemService.getProblemByStatusIdAndDepartmentResolver(5L,dept);
            List<RequestModel> requests = requestService.findRequestsByStatusIdAndResolverDepartment(5L,dept);
            listProblem.addAll(problems);
            listRequest.addAll(requests);
        }
        if (listProblem.size()>3){
            listProblem = listProblem.subList(0,3);
        }
        if (listRequest.size()>3){
            listRequest = listRequest.subList(0,3);
        }

        //Checking
        boolean hasProblem = listProblem.size() > 0;
        model.addAttribute("hasProblem", hasProblem);
        boolean hasRequest = listRequest.size() > 0;
        model.addAttribute("hasRequest", hasRequest);


        model.addAttribute("listProblem", listProblem);
        model.addAttribute("listRequest", listRequest);
        model.addAttribute("user",user);

        return "home";
    }

    @RequestMapping("/login")
    public String login(){
        return "login";
    }
}
