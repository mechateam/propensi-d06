package propensi.d06.sihedes.controller;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import propensi.d06.sihedes.model.*;
import propensi.d06.sihedes.repository.SLADb;
import propensi.d06.sihedes.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletOutputStream;
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


    @GetMapping("/tickets")
    public String listTickets(
            @ModelAttribute ProblemModel problem,
            RequestModel request,
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size,
            Model model) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(5);

        Page<RequestModel> requestPage = requestService.findPaginated(PageRequest.of(currentPage - 1, pageSize), requestService.findAll());
        Page<ProblemModel> problemPage = problemService.findPaginated(PageRequest.of(currentPage - 1, pageSize), problemService.findAll());

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

    @GetMapping("/mytickets")
    public String myTickets(
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

    @GetMapping("/pending")
    public String pendingTickets(
            @ModelAttribute ProblemModel problem,
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size,
            RequestModel request,
            Model model) {

        UserModel user = userService.getUserbyUsername(SecurityContextHolder.getContext().getAuthentication().getName());

        // ProblemModel
        List<ProblemModel> listProblem = problemService.getProblemByDepartemen(user.getDepartemen());
        List<ProblemModel> listPendingProblem = new ArrayList<>();
        for(ProblemModel pendingProblem : listProblem){
            if(pendingProblem.getStatus().getId_status() == 5){
                listPendingProblem.add(pendingProblem);
            }
            else if ((pendingProblem.getStatus().getId_status() == 6) && (pendingProblem.getResolver().getId_user() == user.getId_user())){
                listPendingProblem.add(pendingProblem);
            }
            else if ((user.getId_role().getId_role() == 2) && (pendingProblem.getStatus().getId_status() == 4)){
                listPendingProblem.add(pendingProblem);
            }

        }
        model.addAttribute("listProblem", listPendingProblem);

        // RequestModel
        List<RequestModel> listRequest = requestService.getRequestByDepartment(user.getDepartemen());
        List<RequestModel> listReqApproval = requestService.findAllRequestBasedOnIdApprover(user);
        listRequest.addAll(listReqApproval);
        model.addAttribute("listRequest", listRequest);

        int currentPage = page.orElse(1);
        int pageSize = size.orElse(5);

        Page<RequestModel> requestPage = requestService.findPaginated(PageRequest.of(currentPage - 1, pageSize), listRequest);
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
        boolean hasProblem = listProblem.size() > 0;
        model.addAttribute("hasProblem", hasProblem);
        boolean hasRequest = listRequest.size() > 0;
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
        List<LogProblemModel> logs = problem.getListLog();
        List<UserModel> resolvers = new ArrayList<>();
        UserModel user = userService.getUserbyUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        resolvers.add(user);
        if (user.getId_role().getId_role()==4){
            resolvers = userService.getListUserbyDepartemen(problem.getResolverDepartemen());
        }
        model.addAttribute("logs",logs);
        model.addAttribute("problem",problem);
        model.addAttribute("user",user);
        model.addAttribute("resolvers", resolvers);
//        if (problem.getStatus().getId_status() == 1 || problem.getStatus().getId_status() == 3 || problem.getStatus().getId_status() == 6 ){
//            return "detailProblem";
//        }
        // if (problem.getStatus().getId_status() == 4){
        //     return "assignResolverProblem";
        // }
//        else if (problem.getStatus().getId_status() == 5){
//            return "individual-problem";
        // else {
            return "detailProblem";
        // }
    }

    // @GetMapping("/problem/resolver/{id_problem}")
    // public String detailResolveProblem(
    //     @PathVariable(value="id_problem") Long id_problem,
    //     Model model
    // ){
    //     ProblemModel problem = problemService.findProblemById(id_problem);
    //     List<LogProblemModel> logs = problem.getListLog();
    //     model.addAttribute("logs", logs);
    //     model.addAttribute("problem",problem);
    //     return "assignResolverProblem";
    // }

    @PostMapping("/problem/resolver/{id_problem}")
    public String ResolveProblem(
        @RequestParam(value = "jenisResolver") Long id,
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

        return "redirect:/tickets";
    }

    // @GetMapping("/problem/individual/{id_problem}")
    // public String detailIndividualProblem(
    //         @PathVariable(value="id_problem") Long id_problem,
    //         Model model
    // ){
    //     ProblemModel problem = problemService.findProblemById(id_problem);
    //     List<UserModel> resolvers = userService.getListUserbyDepartemen(problem.getResolverDepartemen());
    //     UserModel user = userService.getUserbyUsername(SecurityContextHolder.getContext().getAuthentication().getName());
    //     List<LogProblemModel> logs = problem.getListLog();
    //     model.addAttribute("logs", logs);
    //     model.addAttribute("user",user);
    //     model.addAttribute("problem",problem);
    //     model.addAttribute("resolvers", resolvers);
    //     return "individual-problem";
    // }

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
        log.setDescription("Problem Created");
        log.setPosted_date(new Date());
        log.setProblem(problem);
        log.setCreatedBy(user);
        logProblemService.addLog(log);

        return "redirect:/tickets";
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


        return "redirect:/tickets";
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

        return "redirect:/tickets";
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
        return "redirect:/tickets";
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
        return "redirect:/tickets";
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

        if(request.getStatus().getId_status() == 5){
            List<UserModel> listResolver = userService.getListUserbyDepartemen(request.getResolverDepartemen());
            model.addAttribute("resolverList", listResolver);
        }
        else if (request.getStatus().getNamaStatus().equals("Waiting for Approval")){

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
        model.addAttribute("request",request);
        model.addAttribute("logs", allLogs);
        return "detailRequest";

    }

    // @GetMapping("/request/resolver")
    // public String detailResolveRequest(
    //         @ModelAttribute RequestModel request,
    //         Model model) {

    //     return "assignResolverRequest";
    // }
    
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
        problem.setCreated_date(dateNow);
        problemService.makeCode(problem);

        problemService.addProblem(problem);

        LogProblemModel log = new LogProblemModel();
        log.setDescription("Created, Waiting for Assignment");
        log.setPosted_date(dateNow);
        log.setProblem(problem);
        log.setCreatedBy(user);
        logProblemService.addLog(log);



        return "redirect:/tickets";
    }

    @PostMapping("/request/add")
    public String requestSubmit(
            @ModelAttribute RequestModel request,
            Model model) {
        Date dateNow = new java.util.Date();
        request.setCreated_date(dateNow);
        requestService.makeCode(request);
        requestService.addRequest(request);
        UserModel user = userService.getUserbyUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        LogRequestModel log = new LogRequestModel();
        log.setCreatedBy(user);
        log.setDescription("Created, Waiting for Approval");
        log.setPosted_date(new Date());
        log.setRequest(request);
        logRequestService.addLog(log);
        return "redirect:/tickets";
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
    public String approveRequest(@ModelAttribute RequestModel request, Model model){
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

    @GetMapping("/slm")
    public String slm(Model model) {

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

    @GetMapping("/request/vendor/{id_request}")
    public String assignVendor(
            @PathVariable(value="id_request") Long id_request,
            Model model) {
        RequestModel req = requestService.getRequestById(id_request);
        String subject = req.getSubject();
        List<VendorModel> listVendor = vendorService.getListVendor();
        model.addAttribute("listVendor",listVendor);
        model.addAttribute("request", req);
        return "assignVendor";
    }

    @PostMapping("/request/done")
    public String submitVendor(
            @ModelAttribute RequestModel request,
            Model model) {
        RequestModel newReq = requestService.vendorRequest(request);
        UserModel user = userService.getUserbyUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        LogRequestModel log = new LogRequestModel();
        log.setCreatedBy(user);
        log.setDescription("Request Resolved by Vendor");
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


}