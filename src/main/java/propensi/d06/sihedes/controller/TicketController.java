package propensi.d06.sihedes.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import propensi.d06.sihedes.model.*;
import propensi.d06.sihedes.repository.*;
import propensi.d06.sihedes.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

    @Autowired
    private LogProblemService logProblemService;

    @Autowired
    private LogRequestService logRequestService;

    @Autowired
    private VendorService vendorService;

    @Autowired
    private SLADb slaDb;

    @Autowired
    private FeedbackProblemService feedbackProblemService;

    @Autowired
    private FeedbackRequestService feedbackRequestService;


    @GetMapping("/tickets")
    public String listTickets(
            @ModelAttribute ProblemModel problem,
            RequestModel request,
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size,
            Model model){

        List<ProblemModel> listProblem;
        List<RequestModel> listRequest;
        UserModel user = userService.getUserbyUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        DepartemenModel dept = user.getDepartemen();

        // ProblemModel
        if (user.getId_role().getId_role() == 2){
            listProblem = problemService.findAll();
            listRequest = requestService.findAll();
        }
        else if (user.getId_role().getId_role() == 3){
            listProblem = problemService.getProblemByPengaju(user);
            List<RequestModel> listRequestDuplicate;
            listRequestDuplicate = requestService.getRequestByPengaju(user);
            listRequestDuplicate.addAll(requestService.findAllRequestBasedOnIdApprover(user));
            listRequest = listRequestDuplicate.stream().distinct().collect(Collectors.toList());
        }
        else{
            List<ProblemModel> listProblemDuplicate;
            List<RequestModel> listRequestDuplicate;

            listProblemDuplicate = problemService.getProblemByPengaju(user);
            listProblemDuplicate.addAll(problemService.getProblemByDepartemen(user.getDepartemen()));

            listRequestDuplicate = requestService.getRequestByPengaju(user);
            listRequestDuplicate.addAll(requestService.findAllRequestBasedOnIdApprover(user));
            listRequestDuplicate.addAll(requestService.getRequestByDepartment(user.getDepartemen()));

            listProblem = listProblemDuplicate.stream().distinct().collect(Collectors.toList());
            listRequest = listRequestDuplicate.stream().distinct().collect(Collectors.toList());
        }


        //Checking
        boolean hasProblem = listProblem.size() > 0;
        model.addAttribute("hasProblem", hasProblem);
        boolean hasRequest = listRequest.size() > 0;
        model.addAttribute("hasRequest", hasRequest);



        int currentPage = page.orElse(1);
        int pageSize = size.orElse(5);

        Page<RequestModel> requestPage = requestService.findPaginated(PageRequest.of(currentPage - 1, pageSize), listRequest);
        Page<ProblemModel> problemPage = problemService.findPaginated(PageRequest.of(currentPage - 1, pageSize), listProblem);

        model.addAttribute("requestPage", requestPage);
        model.addAttribute("problemPage", problemPage);

        int totalPagesRequest = requestPage.getTotalPages();
        int totalPagesProblem = problemPage.getTotalPages();
        if (totalPagesRequest > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPagesRequest)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        if (totalPagesProblem > 0) {
            List<Integer> pageNumbersProblem = IntStream.rangeClosed(1, totalPagesProblem)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbersProblem", pageNumbersProblem);
        }


        model.addAttribute("listProblem", listProblem);
        model.addAttribute("listRequest", listRequest);

        // Return view template yang diinginkan
        return "allTickets";
    }

    @GetMapping("/mytickets")
    public String myTickets(
            @ModelAttribute ProblemModel problem,
            RequestModel request,
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size,
            Model model) {

        // ProblemModel
        UserModel user = userService.getUserbyUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        List<ProblemModel> listProblem = problemService.getProblemByPengaju(user);
        model.addAttribute("listProblem", listProblem);

        // RequestModel
        List<RequestModel> listRequest = requestService.getRequestByPengaju(user);
        model.addAttribute("listRequest", listRequest);

        //Checking
        boolean hasProblem = listProblem.size() > 0;
        model.addAttribute("hasProblem", hasProblem);
        boolean hasRequest = listRequest.size() > 0;
        model.addAttribute("hasRequest", hasRequest);

        int currentPage = page.orElse(1);
        int pageSize = size.orElse(5);

        Page<RequestModel> requestPage = requestService.findPaginated(PageRequest.of(currentPage - 1, pageSize), listRequest);
        Page<ProblemModel> problemPage = problemService.findPaginated(PageRequest.of(currentPage - 1, pageSize), listProblem);

        model.addAttribute("requestPage", requestPage);
        model.addAttribute("problemPage", problemPage);

        int totalPagesRequest = requestPage.getTotalPages();
        int totalPagesProblem = problemPage.getTotalPages();
        if (totalPagesRequest > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPagesRequest)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        if (totalPagesProblem > 0) {
            List<Integer> pageNumbersProblem = IntStream.rangeClosed(1, totalPagesProblem)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbersProblem", pageNumbersProblem);
        }

        // Return view template yang diinginkan
        return "myTickets";
    }

    @GetMapping("/pending")
    public String pendingTickets(
            @ModelAttribute ProblemModel problem,
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size,
            Model model) {

        List<ProblemModel> listPendingProblem;
        List<RequestModel> listPendingRequest;

        UserModel user = userService.getUserbyUsername(SecurityContextHolder.getContext().getAuthentication().getName());

        if (user.getId_role().getId_role()==2){
            List<ProblemModel> listProblemDuplicate;
            List<RequestModel> listRequestDuplicate;

            listProblemDuplicate = problemService.getProblemByStatusId(new Long(3));
            listProblemDuplicate.addAll(problemService.getProblemByStatusId(new Long(4)));
            listProblemDuplicate.addAll(problemService.getProblemByStatusId(new Long(5)));
            listProblemDuplicate.addAll(problemService.getProblemByStatusId(new Long(6)));

            listRequestDuplicate = requestService.findRequestsByStatusId(new Long(3));
            listRequestDuplicate.addAll(requestService.findRequestsByStatusId(new Long(4)));
            listRequestDuplicate.addAll(requestService.findRequestsByStatusId(new Long(5)));
            listRequestDuplicate.addAll(requestService.findRequestsByStatusId(new Long(6)));

            listPendingProblem = listProblemDuplicate.stream().distinct().collect(Collectors.toList());
            listPendingRequest = listRequestDuplicate.stream().distinct().collect(Collectors.toList());
        }
        else if (user.getId_role().getId_role() == 3 ){
            listPendingRequest = requestService.findAllRequestBasedOnIdApprover(user);
            listPendingProblem = new ArrayList<>();
        }
        else{
            List<ProblemModel> listProblemDuplicate;
            List<RequestModel> listRequestDuplicate;

            listProblemDuplicate = problemService.getProblemByStatusIdAndDepartmentResolver(new Long(3),user.getDepartemen());
            listProblemDuplicate.addAll(problemService.getProblemByStatusIdAndDepartmentResolver(new Long(4),user.getDepartemen()));
            listProblemDuplicate.addAll(problemService.getProblemByStatusIdAndDepartmentResolver(new Long(5),user.getDepartemen()));
            listProblemDuplicate.addAll(problemService.getProblemByStatusIdAndDepartmentResolver(new Long(6),user.getDepartemen()));

            listRequestDuplicate = requestService.findRequestsByStatusIdAndResolverDepartment(new Long(3),user.getDepartemen());
            listRequestDuplicate.addAll(requestService.findRequestsByStatusIdAndResolverDepartment(new Long(4),user.getDepartemen()));
            listRequestDuplicate.addAll(requestService.findRequestsByStatusIdAndResolverDepartment(new Long(5),user.getDepartemen()));
            listRequestDuplicate.addAll(requestService.findRequestsByStatusIdAndResolverDepartment(new Long(6),user.getDepartemen()));
            listRequestDuplicate.addAll(requestService.findAllRequestBasedOnIdApprover(user));

            listPendingProblem = listProblemDuplicate.stream().distinct().collect(Collectors.toList());
            listPendingRequest = listRequestDuplicate.stream().distinct().collect(Collectors.toList());
        }

        model.addAttribute("listProblem", listPendingProblem);
        model.addAttribute("listRequest", listPendingRequest);

        int currentPage = page.orElse(1);
        int pageSize = size.orElse(5);

        Page<RequestModel> requestPage = requestService.findPaginated(PageRequest.of(currentPage - 1, pageSize), listPendingRequest);
        Page<ProblemModel> problemPage = problemService.findPaginated(PageRequest.of(currentPage - 1, pageSize), listPendingProblem);

        model.addAttribute("requestPage", requestPage);
        model.addAttribute("problemPage", problemPage);

        int totalPagesRequest = requestPage.getTotalPages();
        int totalPagesProblem = problemPage.getTotalPages();
        if (totalPagesRequest > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPagesRequest)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        if (totalPagesProblem > 0) {
            List<Integer> pageNumbersProblem = IntStream.rangeClosed(1, totalPagesProblem)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbersProblem", pageNumbersProblem);
        }
        //Checking
        boolean hasProblem = listPendingProblem.size() > 0;
        model.addAttribute("hasProblem", hasProblem);
        boolean hasRequest = listPendingRequest.size() > 0;
        model.addAttribute("hasRequest", hasRequest);

        // Return view template yang diinginkan
        return "pendingTickets";
    }

    @GetMapping("/problem/detail/{id_problem}")
    public String detailProblem(
            @PathVariable(value="id_problem") Long id_problem,
            Model model)
    {
        ProblemModel problem = problemService.findProblemById(id_problem);

        List<UserModel> resolvers = new ArrayList<>();
        UserModel user = userService.getUserbyUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        resolvers.add(user);
        if (user.getId_role().getId_role()==4){
            resolvers = userService.getListUserbyDepartemen(problem.getResolverDepartemen());
        }

        if(problem.getStatus().getId_status() == 8){
            FeedbackProblem feedback = feedbackProblemService.findFeedbackByProblem(problem);
            model.addAttribute("feedback",feedback);
        }

        List<LogProblemModel> allLogs = problem.getListLog();
        List<LogProblemModel> logs = new ArrayList<>();
        for(int i = allLogs.size()-1 ; i > -1 ;i--)
        {
            logs.add(allLogs.get(i));
        }

        model.addAttribute("logs", logs);

        model.addAttribute("problem",problem);
        model.addAttribute("user",user);
        model.addAttribute("resolvers", resolvers);

        return "detailProblem";

    }

    @PostMapping("/problem/resolver/{id_problem}")
    public String ResolveProblem(
        @RequestParam(value = "jenisResolver", required = false) Long id,
        @PathVariable Long id_problem, Model model,
        RedirectAttributes redir) {
        UserModel user = userService.getUserbyUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        ProblemModel problem = problemService.findProblemById(id_problem);
        long idStatus = 5;
        StatusModel status = statusService.findStatusById(idStatus);
        problem.setStatus(status);

        System.out.println(problem.getId_problem());
        problem.setResolverDepartemen(problemService.getDepById(id));
        problemService.updateProblem(problem);

        LogProblemModel log = new LogProblemModel();
        log.setDescription(status.getNamaStatus());
        log.setPosted_date(new Date());
        log.setProblem(problem);
        log.setCreatedBy(user);
        logProblemService.addLog(log);

        String link = "redirect:/problem/detail/" + problem.getId_problem();
        return link;
    }

    @GetMapping("/request/individual/{id_request}")
    public String detailRequestProblem(
            @PathVariable(value="id_request") Long id_request,
            Model model
    ){
        RequestModel request = requestService.getRequestById(id_request);
        List<UserModel> resolvers = userService.getListUserbyDepartemen(request.getResolverDepartemen());
        UserModel user = userService.getUserbyUsername(SecurityContextHolder.getContext().getAuthentication().getName());
//        List<LogProblemModel> logs = problem.getListLog();
//        model.addAttribute("logs", logs);
        model.addAttribute("user",user);
        model.addAttribute("request",request);
        model.addAttribute("resolvers", resolvers);
        return "individual-request";
    }

    @PostMapping("/problem/individual/{id_problem}")
    public String resolveIndividualProblem(
            @RequestParam(value = "individual") Long id,
            @PathVariable Long id_problem, Model model,
            RedirectAttributes redir) {
        UserModel user = userService.getUserbyUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        ProblemModel problem = problemService.findProblemById(id_problem);
        long idStatus = 6;
        StatusModel status = statusService.findStatusById(idStatus);
        problem.setStatus(status);
        problem.setResolver(userService.getUserbyId(id));

        problemService.updateProblem(problem);

        LogProblemModel log = new LogProblemModel();
        log.setDescription("In Progress");
        log.setPosted_date(new Date());
        log.setProblem(problem);
        log.setCreatedBy(user);
        logProblemService.addLog(log);

        String link = "redirect:/problem/detail/" + problem.getId_problem();
        return link;
    }

    @PostMapping("/request/individual/{id_request}")
    public String resolveIndividualRequest(
            @RequestParam(value = "individual") Long id,
            @PathVariable Long id_request, Model model,
            RedirectAttributes redir) {
        UserModel user = userService.getUserbyUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        RequestModel request = requestService.getRequestById(id_request);
        long idStatus = 6;
        StatusModel status = statusService.findStatusById(idStatus);
        request.setStatus(status);
        request.setResolver(userService.getUserbyId(id));

        requestService.updateRequest(request);

        LogRequestModel log = new LogRequestModel();
        log.setCreatedBy(user);
        log.setDescription(status.getNamaStatus());
        log.setPosted_date(new Date());
        log.setRequest(request);
        logRequestService.addLog(log);

        String link = "redirect:/request/detailin/" + request.getId_request();
        return link;
    }

    @GetMapping("/problem/individual/return/{id_problem}")
    public String returnIndividualProblem(
            @PathVariable Long id_problem, Model model,
            RedirectAttributes redir) {
        UserModel user = userService.getUserbyUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        ProblemModel problem = problemService.findProblemById(id_problem);
        long idStatus = 4;
        StatusModel status = statusService.findStatusById(idStatus);
        problem.setStatus(status);
        problem.setResolverDepartemen(null);
        problemService.updateProblem(problem);

        LogProblemModel log = new LogProblemModel();
        log.setDescription("Returned to Helpdesk");
        log.setPosted_date(new Date());
        log.setProblem(problem);
        log.setCreatedBy(user);
        logProblemService.addLog(log);

        String link = "redirect:/problem/detail/" + problem.getId_problem();
        return link;
    }

    @GetMapping("/request/individual/return/{id_request}")
    public String returnIndividualRequest(
            @PathVariable Long id_request, Model model,
            RedirectAttributes redir) {
        RequestModel request = requestService.getRequestById(id_request);
        long idStatus = 4;
        StatusModel status = statusService.findStatusById(idStatus);
        request.setStatus(status);
        request.setResolverDepartemen(null);
        requestService.updateRequest(request);

//        LogProblemModel log = new LogProblemModel();
//        log.setDescription("Returned to Helpdesk");
//        log.setPosted_date(new Date());
//        log.setProblem(problem);
//        logProblemService.addLog(log);
        String link = "redirect:/request/detailin/" + request.getId_request();
        return link;
    }



    @PostMapping("/problem/update")
    public String acceptProblem(
            @ModelAttribute ProblemModel problem,
            Model model) {
        UserModel user = userService.getUserbyUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        System.out.println("Ini controller :" + problem.getDescription());
        ProblemModel newProb = problemService.updateProblemStatus(problem);
        model.addAttribute("problem",newProb);

        LogProblemModel log = new LogProblemModel();
        log.setDescription(newProb.getStatus().getNamaStatus());
        log.setPosted_date(new Date());
        log.setProblem(newProb);
        log.setCreatedBy(user);
        logProblemService.addLog(log);
        String link = "redirect:/problem/detail/" + problem.getId_problem();
        return link;
    }

//    @Deprecated
    @GetMapping("/request/detailin/{id_request}")
    public String detailRequest(
            @PathVariable(value="id_request") Long id_request, HttpServletRequest req,
            Model model){
        RequestModel request = requestService.getRequestById(id_request);
        List<LogRequestModel> logs = request.getListLogRequest();
        List<LogRequestModel> allLogs = new ArrayList<>();
        for(int i = logs.size()-1 ; i > -1 ;i--)
        {
         allLogs.add(logs.get(i));
            }
        System.out.println(" ini size logs : " + logs.size());
        UserModel userLoggedin = userService.getUserbyUsername(req.getRemoteUser());
        SLAModel sla = request.getSla();
        List<SLABOAModel> listBOA = slaboaService.getSLABOABySLAId(sla.getId_sla());
        List<UserModel> listResolver = new ArrayList<>();
        listResolver.add(userLoggedin);
        if (userLoggedin.getId_role().getId_role()==4){
            listResolver = userService.getListUserbyDepartemen(request.getResolverDepartemen());
        }
        if (request.getStatus().getNamaStatus().equals("Waiting for Approval")){

            if (request.getIdApprover() == null){
                for (SLABOAModel boa: listBOA) {
                    if (boa.getBoa().getRank() ==1){
                        request.setIdApprover(boa.getBoa().getUser().getId_user());
                    }
                }

            }

            Long idApprover = new Long(request.getIdApprover());
            model.addAttribute("user",userLoggedin);
            model.addAttribute("request",request);
            model.addAttribute("logs", allLogs);
            model.addAttribute("userApproval", userService.getUserbyId(idApprover));

            return "detailRequestApproval";
        }
        if(request.getStatus().getId_status() == 8){
            System.out.println(request.getPengaju().getId_user() + " itu pengaju dan user yang login " + userLoggedin.getId_user());
            FeedbackRequest feedback = feedbackRequestService.findFeedbackByRequest(request);
            model.addAttribute("feedback",feedback);
            // if(feedbackRequestService.findFeedbackByRequest(request) != null){
            //     FeedbackRequest feedback = feedbackRequestService.findFeedbackByRequest(request);
            //     model.addAttribute("feedback",feedback);
            // } else{
            //     FeedbackRequest feedbackbaru = new FeedbackRequest();
            //     feedbackbaru.setDescription("");
            //     feedbackbaru.setRequest(request);
            //     feedbackbaru.setCreated_date(new Date());
            //     feedbackRequestService.addFeedback(feedbackbaru);
            //     model.addAttribute("feedback",feedbackbaru);
            // }
        }

        model.addAttribute("resolverList", listResolver);
        model.addAttribute("user",userLoggedin);
        model.addAttribute("request",request);
        model.addAttribute("logs", allLogs);
        return "detailRequest";

    }
    
    @PostMapping("/request/update")
    public String accReq(
            @ModelAttribute RequestModel request,
            Model model) {
        RequestModel newReq = requestService.updateRequestStatus(request);
        UserModel user = userService.getUserbyUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        LogRequestModel log = new LogRequestModel();
        log.setCreatedBy(user);
        if(newReq.getStatus().getId_status() == Long.parseLong("6")){
            log.setDescription("Request Accepted and Assigned");
        }
        else if (newReq.getStatus().getId_status() == Long.parseLong("7")){
            log.setDescription("Request Resolved");
        }

        log.setPosted_date(new Date());
        log.setRequest(request);
        logRequestService.addLog(log);
        List<LogRequestModel> allLogs = newReq.getListLogRequest();
        List<LogRequestModel> logs = new ArrayList<>();
        for(int i = allLogs.size()-1 ; i > -1 ;i--)
        {
            logs.add(allLogs.get(i));
        }
        model.addAttribute("requestManager",userService.getUserbyId(user.getId_user()));
        model.addAttribute("request",newReq);
        model.addAttribute("logs", logs);
        String link = "redirect:/request/detailin/" + request.getId_request();
        return link;

    }

    @GetMapping("/request/add")
    public String addRequest(Model model) {
//        model.addAttribute("problem", new ProblemModel());
        UserModel user = userService.getUserbyUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        DepartemenModel departemen = user.getDepartemen();

        model.addAttribute("deptList",departemenService.getListDepartment());
        model.addAttribute("departemen", departemen);
        model.addAttribute("request", new RequestModel());
        return "createRequest";
    }

    @GetMapping("/problem/add")
    public String addProblem(Model model) {
        model.addAttribute("problem", new ProblemModel());
        UserModel user = userService.getUserbyUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        DepartemenModel departemen = user.getDepartemen();

        model.addAttribute("deptList",departemenService.getListDepartment());
        model.addAttribute("departemen", departemen);
//        model.addAttribute("request", new RequestModel());
        return "createProblem";
    }

    @PostMapping("/problem/add")
    public String problemSubmit(
            @ModelAttribute ProblemModel problem,
            RedirectAttributes redir,
            Model model) {

        long idStatus = 4;
        StatusModel status = statusService.findStatusById(idStatus);
        problem.setStatus(status);

        UserModel user = userService.getUserbyUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        problem.setPengaju(user);

        Date dateNow = new java.util.Date();
        problem.setCreatedDate(dateNow);
        problemService.makeCode(problem);

        problemService.addProblem(problem);

        LogProblemModel log = new LogProblemModel();
        log.setDescription("Created, Waiting for Assignment");
        log.setPosted_date(dateNow);
        log.setProblem(problem);
        log.setCreatedBy(user);
        logProblemService.addLog(log);

        String link = "redirect:/problem/detail/" + problem.getId_problem();
        return link;

    }

    @PostMapping("/request/add")
    public String requestSubmit(
            @ModelAttribute RequestModel request,
            Model model) {
        Date dateNow = new java.util.Date();
        request.setCreatedDate(dateNow);
        requestService.makeCode(request);
        requestService.addRequest(request);
        UserModel user = userService.getUserbyUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        LogRequestModel log = new LogRequestModel();
        log.setCreatedBy(user);
        log.setDescription("Created, Waiting for Approval");
        log.setPosted_date(new Date());
        log.setRequest(request);
        logRequestService.addLog(log);
        String link = "redirect:/request/detailin/" + request.getId_request();
        return link;
    }

    // ini buat approval, mau digabung sama yg atas juga gapapa
    @GetMapping("/request/detail/{id}")
    public String detailRequestApproval(@PathVariable Long id, HttpServletRequest req, Model model){

        UserModel userLoggedin = userService.getUserbyUsername(req.getRemoteUser());
        RequestModel request = requestService.getRequestById(id);
        SLAModel sla = request.getSla();
        List<SLABOAModel> listBOA = slaboaService.getSLABOABySLAId(sla.getId_sla());
        List<LogRequestModel> allLogs = request.getListLogRequest();
        List<LogRequestModel> logs = new ArrayList<>();
        for(int i = allLogs.size()-1 ; i > -1 ;i--)
        {
            logs.add(allLogs.get(i));
        }

        if (request.getStatus().getNamaStatus().equals("Waiting for Approval")){

            if (request.getIdApprover() == null){
                for (SLABOAModel boa: listBOA) {
                    if (boa.getBoa().getRank() ==1){
                        request.setIdApprover(boa.getBoa().getUser().getId_user());
                    }
                }

            }

            Long idApprover = new Long(request.getIdApprover());
            model.addAttribute("user",userLoggedin);
            model.addAttribute("request",request);
            model.addAttribute("userApproval", userService.getUserbyId(idApprover));
            model.addAttribute("logs", logs);


            return "detailRequestApproval";
        }

        return null;
    }

    @PostMapping("/request/approve")
    public String approveRequest(@ModelAttribute RequestModel request, Model model) throws AddressException, MessagingException, IOException {
        requestService.updateApprovalRequest(request);

        /// for Log
        UserModel user = userService.getUserbyUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        LogRequestModel log = new LogRequestModel();
        log.setCreatedBy(user);
        log.setDescription("Approved");
        log.setPosted_date(new Date());
        log.setRequest(request);
        logRequestService.addLog(log);

        model.addAttribute("request",request);
        return "redirect:/tickets";
    }

    @PostMapping("/request/reject")
    public String rejectRequest(@ModelAttribute RequestModel request, Model model){
        requestService.rejectApprovalRequest(request);

        ///for log
        UserModel user = userService.getUserbyUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        LogRequestModel log = new LogRequestModel();
        log.setCreatedBy(user);
        log.setDescription("Rejected");
        log.setPosted_date(new Date());
        log.setRequest(request);
        logRequestService.addLog(log);
        model.addAttribute("request",request);
        return "redirect:/tickets";
    }

    @ResponseBody
    @GetMapping("/loadslabydepartemen/{id}")
    public List<SLAModel> loadSLAByDepartemen(@PathVariable Long id){
        return this.slaDb.findAllByDepartemen(departemenService.findDepartemenById(id));
    }

    @ResponseBody
    @GetMapping("loadslabyid/{id}")
    public SLAModel loadSLAById(@PathVariable Long id){
        return this.slaDb.findById(id).get();
    }

    @GetMapping("/slm")
    public String slm(Model model) {
        HashMap<DepartemenModel, Double> rankDept = new HashMap<DepartemenModel, Double>();
        List<DepartemenModel> listDept = departemenService.findAll();

        for (int i=0; i < listDept.size(); i++){
            Double totalTicket = 0.0;
            Double ticketCompleted = 0.0;

            totalTicket+= new Double(listDept.get(i).getListRequest().size());
            totalTicket+= new Double(listDept.get(i).getListProblem().size());

            for (int j=0;j<listDept.get(i).getListRequest().size();j++){
                String status = listDept.get(i).getListRequest().get(j).getStatus().getNamaStatus();
                if (status.equals("Done") || status.equals("Closed")){
                    ticketCompleted+= 1.0;
                }
            }

            for (int j=0;j<listDept.get(i).getListProblem().size();j++){
                String status = listDept.get(i).getListProblem().get(j).getStatus().getNamaStatus();
                if (status.equals("Done") || status.equals("Closed")){
                    ticketCompleted+= 1.0;
                }
            }

            rankDept.put(listDept.get(i),(ticketCompleted/totalTicket)*100);
        }
        Map<DepartemenModel, Double> sortedMap = rankDept.entrySet().stream()
                .sorted(Comparator.comparingDouble(e -> -e.getValue()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (a, b) -> { throw new AssertionError(); },
                        LinkedHashMap::new
                ));
        model.addAttribute("sortedRankDept",sortedMap);

        return "serviceLevelManagement";
    }

    @GetMapping("/ticket/export/excel")
    public void exportToExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=ReportTicket" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);

        List<RequestModel> listRequests = requestService.findAll();
        List<ProblemModel> listProblems = problemService.findAll();

        RequestExcelExporter excelExporter = new RequestExcelExporter(listRequests,listProblems);

        excelExporter.export(response);
    }

    @GetMapping("/problem/vendor/{id_problem}")
    public String assignVendorToProblem(
            @PathVariable(value="id_problem") Long id_problem,
            Model model) {
        ProblemModel problem = problemService.findProblemById(id_problem);
        List<VendorModel> listVendor = vendorService.getListVendor();
        model.addAttribute("listVendor",listVendor);
        model.addAttribute("hasVendor", true);
        model.addAttribute("problem", problem);
        return "vendorProblem";
    }

    @GetMapping("/request/vendor/{id_request}")
    public String assignVendor(
            @PathVariable(value="id_request") Long id_request,
            Model model) {
        RequestModel req = requestService.getRequestById(id_request);
        String subject = req.getSubject();
        List<VendorModel> listVendor = vendorService.getListVendor();
        model.addAttribute("listVendor",listVendor);
        model.addAttribute("request", req);
        model.addAttribute("hasVendor", true);
        return "vendorRequest";
    }

    @PostMapping("/request/done")
    public String submitVendorRequest(
            @ModelAttribute RequestModel request,
            Model model) {
        if(request.getReqVendor() == null){
            String link = "redirect:/request/vendor/" + request.getId_request();
            return link;
        }
        else {
            RequestModel newReq = requestService.vendorRequest(request);
            UserModel user = userService.getUserbyUsername(SecurityContextHolder.getContext().getAuthentication().getName());
            LogRequestModel log = new LogRequestModel();
            log.setCreatedBy(user);
            log.setDescription("Request Resolved by Vendor" + request.getReqVendor().getNama());
            log.setPosted_date(new Date());
            log.setRequest(request);
            logRequestService.addLog(log);
            List<LogRequestModel> allLogs = newReq.getListLogRequest();
            List<LogRequestModel> logs = new ArrayList<>();
            for (int i = allLogs.size() - 1; i > -1; i--) {
                logs.add(allLogs.get(i));
            }
            model.addAttribute("requestManager", userService.getUserbyId(user.getId_user()));
            model.addAttribute("request", newReq);
            model.addAttribute("logs", logs);
            String link = "redirect:/request/detailin/" + request.getId_request();
            return link;
        }

    }

    @PostMapping("/problem/done")
    public String submitVendorProblem(
            @ModelAttribute ProblemModel problem,
            Model model) {
        if(problem.getProbVendor() == null){
            String link = "redirect:/problem/vendor/" + problem.getId_problem();
            return link;
        }
        else {
            ProblemModel newProb = problemService.vendorRequest(problem);
            UserModel user = userService.getUserbyUsername(SecurityContextHolder.getContext().getAuthentication().getName());

            LogProblemModel log = new LogProblemModel();
            log.setDescription("Problem Resolved by Vendor " + problem.getProbVendor().getNama());
            log.setPosted_date(new Date());
            log.setProblem(problem);
            log.setCreatedBy(user);
            logProblemService.addLog(log);

            List<LogProblemModel> allLogs = newProb.getListLog();
            List<LogProblemModel> logs = new ArrayList<>();
            for (int i = allLogs.size() - 1; i > -1; i--) {
                logs.add(allLogs.get(i));
            }

            model.addAttribute("logs", logs);
            model.addAttribute("problem", newProb);
            model.addAttribute("hasVendor", true);
            String link = "redirect:/problem/detail/" + problem.getId_problem();
            return link;
        }
    }


    @PostMapping("/problem/close/{id_problem}")
    public String closeProblem(
            @RequestParam(value = "feedback") String comments,
            @PathVariable Long id_problem, Model model,
            RedirectAttributes redir) {
        UserModel user = userService.getUserbyUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        ProblemModel problem = problemService.findProblemById(id_problem);
        problemService.updateProblemStatus(problem);
        problemService.updateProblem(problem);

        FeedbackProblem feedback = new FeedbackProblem();
        feedback.setDescription(comments);
        feedback.setProblem(problem);
        feedback.setCreated_date(new Date());
        // feedback.setScore(0);
        feedbackProblemService.addFeedback(feedback);

        LogProblemModel log = new LogProblemModel();
        log.setDescription("Problem Closed");
        log.setPosted_date(new Date());
        log.setProblem(problem);
        log.setCreatedBy(user);
        logProblemService.addLog(log);
        String link = "redirect:/problem/detail/" + problem.getId_problem();
        return link;
    }

    @PostMapping("/request/close/{id_request}")
    public String closeRequest(
            @RequestParam(value = "feedback") String comments,
            @PathVariable Long id_request, Model model,
            RedirectAttributes redir) {
        UserModel user = userService.getUserbyUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        RequestModel request = requestService.getRequestById(id_request);
        requestService.updateRequestStatus(request);
        requestService.updateRequest(request);

        FeedbackRequest feedback = new FeedbackRequest();
        feedback.setDescription(comments);
        feedback.setRequest(request);
        feedback.setCreated_date(new Date());
        // feedback.setScore(0);
        feedbackRequestService.addFeedback(feedback);

        LogRequestModel log = new LogRequestModel();
        log.setDescription("Request Closed");
        log.setPosted_date(new Date());
        log.setRequest(request);
        log.setCreatedBy(user);
        logRequestService.addLog(log);
        String link = "redirect:/request/detailin/" + request.getId_request();
        return link;
    }
}