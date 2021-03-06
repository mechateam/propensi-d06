package propensi.d06.sihedes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.parsing.Problem;
import org.springframework.data.domain.*;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import propensi.d06.sihedes.model.*;
import propensi.d06.sihedes.repository.*;

import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

@Service
@Transactional
public class ProblemServiceImpl implements ProblemService{
    @Autowired
    ProblemDb problemDb;

    @Autowired
    DepartemenDb departemenDb;

    @Autowired
    StatusDb statusDb;

    @Autowired
    UserDb userDb;

    @Override
    public DepartemenModel getDepById(Long id){
        return departemenDb.findById(id).get();
    }

    @Override
    public void updateProblem(ProblemModel problem) {
        problemDb.save(problem);
    }

    @Override
    public ProblemModel updateProblemStatus(ProblemModel problem) {
        ProblemModel targetProblem = problemDb.findById(problem.getId_problem()).get();
        try{
            UserModel user = userDb.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
            long idStatus = targetProblem.getStatus().getId_status();
            if(idStatus == 5){
                targetProblem.setResolver(user);
            }
            System.out.println("ini status sebelum" + idStatus);
            long newStatus = idStatus+1;
            System.out.println("ini new status" + newStatus);
            StatusModel status = statusDb.findById(newStatus).get();
            
            targetProblem.setStatus(status);
            if(targetProblem.getStatus().getId_status() == 7){
                Date dateNow = new java.util.Date();
                targetProblem.setFinished_date(dateNow);
                testDelayStatus(targetProblem);
            }
            problemDb.save(targetProblem);
            System.out.println("Ini Status :" + targetProblem.getStatus().getNamaStatus());
            return targetProblem;
        }
        catch (NullPointerException nullPointerException) {
            return null;
        }

    }

    @Override
    public String getRandomChar(int length) {
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

        // create random string builder
        StringBuilder sb = new StringBuilder();

        // create an object of Random class
        Random random = new Random();

        // specify length of random string

        for (int i = 0; i < length; i++) {

            // generate random index number
            int index = random.nextInt(alphabet.length());

            // get character specified by index
            // from the string
            char randomChar = alphabet.charAt(index);

            // append the character to string builder
            sb.append(randomChar);
        }
        return sb.toString();
    }

    @Override
    public String getRandomInt(int length) {
        String alphabet = "1234567890";

        // create random string builder
        StringBuilder sb = new StringBuilder();

        // create an object of Random class
        Random random = new Random();

        // specify length of random string

        for (int i = 0; i < length; i++) {

            // generate random index number
            int index = random.nextInt(alphabet.length());

            // get character specified by index
            // from the string
            char randomChar = alphabet.charAt(index);

            // append the character to string builder
            sb.append(randomChar);
        }
        return sb.toString();
    }

    @Override
    public void makeCode(ProblemModel problem){
        StringBuilder code =new StringBuilder();
        code.append("PR");

        Date datenow = problem.getCreatedDate();
        SimpleDateFormat format = new SimpleDateFormat("ddMMyyyy");
        String dateString = format.format( datenow  );
        code.append(dateString);
        String secondRandom = getRandomChar(2);
        code.append(secondRandom);
        String lastRandom = getRandomInt(2);
        code.append(lastRandom);

        String result = code.toString();
        problem.setCodeProblem(result);
    }

    @Override
    public List<ProblemModel> findAll() { return problemDb.findAll(Sort.by("codeProblem").ascending()); }

    @Override
    public List<ProblemModel> getProblemByDepartemen(DepartemenModel departemen) {
        return problemDb.findAllByResolverDepartemen(departemen);}

    @Override
    public ProblemModel findProblemById(Long id) {
        return problemDb.findById(id).get();
    }

    @Override
    public void addProblem(ProblemModel problem) {
        problemDb.save(problem);
    }

    @Override
    public Page<ProblemModel> findPaginated(Pageable pageable, List<ProblemModel> requests){

        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<ProblemModel> list;
        if (requests.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, requests.size());
            list = requests.subList(startItem, toIndex);
        }

        Page<ProblemModel> requestPage
                = new PageImpl<ProblemModel>(list, PageRequest.of(currentPage, pageSize), requests.size());
        return requestPage;
    }

    @Override
    public List<ProblemModel> getProblemByPengaju(UserModel user){
        return problemDb.findAllByPengaju(user);
    }

    @Override
    public ProblemModel vendorRequest(ProblemModel problem) {
        ProblemModel targetProblem = problemDb.findById(problem.getId_problem()).get();
        try {
            UserModel user = userDb.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
            long idStatus = targetProblem.getStatus().getId_status();
            long newStatus = idStatus+1;
            targetProblem.setProbVendor(problem.getProbVendor());
            StatusModel status = statusDb.findById(newStatus).get();
            targetProblem.setStatus(status);
            Date dateNow = new java.util.Date();
            targetProblem.setFinished_date(dateNow);
            problemDb.save(targetProblem);
            return targetProblem;
        } catch (NullPointerException nullPointerException) {
            return null;
        }
    }

    @Override
    public void testDelayStatus(ProblemModel problem){
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                ProblemModel targetProblem = problemDb.findById(problem.getId_problem()).get();
                StatusModel status = statusDb.findByNamaStatus("Closed");
                targetProblem.setStatus(status);
                problemDb.save(targetProblem);
            }
        };
        timer.schedule(timerTask, 172800000);
    }

    @Override
    public List<ProblemModel> getProblemByStatusId(Long id) {
        return problemDb.findProblemModelsByStatus(statusDb.findById(id).get());
    }

    @Override
    public List<ProblemModel> getProblemByStatusIdAndResolver(Long id, UserModel user) {
        return problemDb.findProblemModelsByStatusAndResolver(statusDb.findById(id).get(), user);
    }

    @Override
    public List<ProblemModel> getProblemByStatusIdAndPengaju(Long id, UserModel userModel) {
        return problemDb.findProblemModelsByStatusAndPengaju(statusDb.findById(id).get(), userModel);
    }

    @Override
    public List<ProblemModel> getProblemByStatusIdAndDepartmentResolver(Long id, DepartemenModel departemenModel) {
        return problemDb.findProblemModelsByStatusAndResolverDepartemen(statusDb.findById(id).get(), departemenModel);
    }

    @Override
    public List<ProblemModel> getProblemByCreatedDateMonth(int month) {
        List<ProblemModel> allProblemByMonth = new ArrayList<>();

        for (ProblemModel a : problemDb.findAll()) {
            int bulan = a.getCreatedDate().getMonth() + 1;
            if (bulan == month) {
                allProblemByMonth.add(a);
            }
        }
        return allProblemByMonth;
    }
}
