package propensi.d06.sihedes.controller;


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
import java.util.List;
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

        // ProblemModel
        List<ProblemModel> listProblem = new ArrayList<>();
        List<RequestModel> listRequest = new ArrayList<>();
        if(user.getId_role().getId_role() == Long.parseLong("2")) {
            List<ProblemModel> problems = problemService.findAll();
            List<RequestModel> requests = requestService.findAll();
            listProblem.addAll(problems);
            listRequest.addAll(requests);
        }
        else if (user.getId_role().getId_role() == Long.parseLong("3")){
            List<ProblemModel> problems = problemService.getProblemByPengaju(user);
            List<RequestModel> requests = requestService.getRequestByPengaju(user);
            listProblem.addAll(problems);
            listRequest.addAll(requests);
        }
        else {
            List<ProblemModel> problems = problemService.getProblemByDepartemen(dept);
            List<RequestModel> requests = requestService.getRequestByDepartment(dept);
            listProblem.addAll(problems);
            listRequest.addAll(requests);
        }

        //Checking
        boolean hasProblem = listProblem.size() > 0;
        model.addAttribute("hasProblem", hasProblem);
        boolean hasRequest = listRequest.size() > 0;
        model.addAttribute("hasRequest", hasRequest);



//        int currentPage = page.orElse(1);
//        int pageSize = size.orElse(5);
//
//        Page<RequestModel> requestPage = requestService.findPaginated(PageRequest.of(currentPage - 1, pageSize), listRequest);
//        Page<ProblemModel> problemPage = problemService.findPaginated(PageRequest.of(currentPage - 1, pageSize), listProblem);
//
//        model.addAttribute("requestPage", requestPage);
//        model.addAttribute("problemPage", problemPage);
//
//        int totalPagesRequest = requestPage.getTotalPages();
//        int totalPagesProblem = problemPage.getTotalPages();
//        if (totalPagesRequest > 0) {
//            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPagesRequest)
//                    .boxed()
//                    .collect(Collectors.toList());
//            model.addAttribute("pageNumbers", pageNumbers);
//        }
//
//        if (totalPagesProblem > 0) {
//            List<Integer> pageNumbersProblem = IntStream.rangeClosed(1, totalPagesProblem)
//                    .boxed()
//                    .collect(Collectors.toList());
//            model.addAttribute("pageNumbersProblem", pageNumbersProblem);
//        }


        model.addAttribute("listProblem", listProblem);
        model.addAttribute("listRequest", listRequest);

        return "home";
    }

    @RequestMapping("/login")
    public String login(){
        return "login";
    }
}
