package propensi.d06.sihedes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import propensi.d06.sihedes.model.RequestModel;
import propensi.d06.sihedes.model.SLABOAModel;
import propensi.d06.sihedes.model.SLAModel;
import propensi.d06.sihedes.repository.RequestDb;
import propensi.d06.sihedes.repository.SLABOADb;
import propensi.d06.sihedes.repository.SLADb;
import propensi.d06.sihedes.repository.StatusDb;
import org.springframework.security.core.context.SecurityContextHolder;
import propensi.d06.sihedes.model.*;
import propensi.d06.sihedes.repository.*;

import javax.mail.*;
import javax.mail.internet.*;
import javax.transaction.Transactional;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Transactional
public class RequestServiceImpl implements RequestService{
    @Autowired
    RequestDb requestDb;

    @Autowired
    DepartemenDb departemenDb;

    @Autowired
    SLABOADb slaboaDb;

    @Autowired
    SLADb slaDb;

    @Autowired
    StatusDb statusDb;

    @Autowired
    UserDb userDb;


    @Override
    public DepartemenModel getDepById(Long id){
        return departemenDb.findById(id).get();
    }

    @Override
    public void updateRequest(RequestModel request) {
        requestDb.save(request);
    }

    @Override
    public List<RequestModel> findAll() { return requestDb.findAll(Sort.by("codeRequest").ascending()); }
    @Override
    public List<RequestModel> getRequestByDepartment(DepartemenModel departemen){ return  requestDb.findAllByResolverDepartemen(departemen); }


    @Override
    public RequestModel getRequestById(Long id){
        return requestDb.findById(id).get();
    }

    @Override
    public RequestModel updateApprovalRequest(RequestModel request){

        RequestModel targetRequest = requestDb.findById(request.getId_request()).get();
        List<SLABOAModel> listBOA = slaboaDb.findAllBySla(targetRequest.getSla());
        Integer currentRank=0;

        for (SLABOAModel boa:listBOA){
            if (targetRequest.getIdApprover().equals(boa.getBoa().getUser().getId_user())){
                currentRank = boa.getBoa().getRank();
            }
        }

        for (SLABOAModel boa: listBOA){
            System.out.println("id approver"+targetRequest.getIdApprover());
            System.out.println(boa.getBoa().getUser().getId_user());

            if (targetRequest.getIdApprover() == boa.getBoa().getUser().getId_user()){
                if (currentRank == listBOA.size()){
                    targetRequest.setIdApprover(new Long(-1));
                }
            }

            if (boa.getBoa().getRank() == currentRank+1){
                targetRequest.setIdApprover(boa.getBoa().getUser().getId_user());
            }
        }

        if (targetRequest.getIdApprover() == -1){
            targetRequest.setStatus(statusDb.findByNamaStatus("Assigned"));
        }

        return targetRequest;
    }

    @Override
    public RequestModel rejectApprovalRequest(RequestModel request){
        RequestModel targetRequest = requestDb.findById(request.getId_request()).get();

        try{
            targetRequest.setIdApprover(new Long(-1));
            targetRequest.setStatus(statusDb.findByNamaStatus("Closed"));
            return targetRequest;
        }
        catch (NullPointerException nullException){
            return null;
        }

    }

    @Override
    public RequestModel updateRequestStatus(RequestModel request) {
        RequestModel targetRequest = requestDb.findById(request.getId_request()).get();
        try{
            UserModel user = userDb.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
            long idStatus = targetRequest.getStatus().getId_status();
            if(idStatus == 5){
                targetRequest.setResolver(request.getResolver());
            }
            long newStatus = idStatus+1;
            targetRequest.setResolver(request.getResolver());
            StatusModel status = statusDb.findById(newStatus).get();
            targetRequest.setStatus(status);
            if(targetRequest.getStatus().getId_status() == 7){
                Date dateNow = new java.util.Date();
                targetRequest.setFinished_date(dateNow);
                testDelayStatus(targetRequest);
            }
            requestDb.save(targetRequest);
            return targetRequest;
        }
        catch (NullPointerException nullPointerException) {
            return null;
        }

    }

    @Override
    public Page<RequestModel> findPaginated(Pageable pageable, List<RequestModel> requests){

        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<RequestModel> list;
        if (requests.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, requests.size());
            list = requests.subList(startItem, toIndex);
        }

        Page<RequestModel> requestPage
                = new PageImpl<RequestModel>(list, PageRequest.of(currentPage, pageSize), requests.size());
        return requestPage;
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
    public void makeCode(RequestModel request){
        StringBuilder code =new StringBuilder();
        code.append("RQ");

        Date datenow = request.getCreated_date();
        SimpleDateFormat format = new SimpleDateFormat("ddMMyyyy");
        String dateString = format.format( datenow  );
        code.append(dateString);
        String secondRandom = getRandomChar(2);
        code.append(secondRandom);
        String lastRandom = getRandomInt(2);
        code.append(lastRandom);

        String result = code.toString();
        request.setCodeRequest(result);
    }

    @Override
    public void addRequest(RequestModel request){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        UserModel userPembuat = userDb.findByUsername(currentPrincipalName);

        request.setCreated_date(new Date());
        request.setPengaju(userPembuat);
        request.setStatus(statusDb.findByNamaStatus("Waiting for Approval"));
        request.setRequestDepartemen(userPembuat.getDepartemen());
        request.setResolverDepartemen(request.getSla().getDepartemen());


        SLAModel sla = request.getSla();
        System.out.println("ini sla"+sla.getNama_sla());
        List<SLABOAModel> listBOA = slaboaDb.findAllBySla(request.getSla());
        System.out.println("Size BoA"+listBOA.size());

        for (SLABOAModel boa: listBOA) {
            System.out.println("BoA Rank"+ boa.getBoa().getRank());
            if (boa.getBoa().getRank() == 1){
                System.out.println("Masuk");
                request.setIdApprover(boa.getBoa().getUser().getId_user());
            }
        }

        requestDb.save(request);
    }

    @Override
    public List<RequestModel> findAllRequestBasedOnIdApprover(UserModel user){
        return requestDb.findAllByIdApprover(user.getId_user());
    }

    @Override
    public List<RequestModel> getRequestByPengaju(UserModel user){
        return requestDb.findAllByPengaju(user);
    }


    @Override
    public RequestModel vendorRequest(RequestModel request) {
        RequestModel targetRequest = requestDb.findById(request.getId_request()).get();
        try {
            UserModel user = userDb.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
            long idStatus = targetRequest.getStatus().getId_status();
            long newStatus = idStatus+1;
            targetRequest.setReqVendor(request.getReqVendor());
            StatusModel status = statusDb.findById(newStatus).get();
            targetRequest.setStatus(status);
                Date dateNow = new java.util.Date();
                targetRequest.setFinished_date(dateNow);
            requestDb.save(targetRequest);
            return targetRequest;
        } catch (NullPointerException nullPointerException) {
            return null;
        }
    }

    @Override
    public void testDelayStatus(RequestModel request){
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                RequestModel targetRequest = requestDb.findById(request.getId_request()).get();
                StatusModel status = statusDb.findByNamaStatus("Closed");
                targetRequest.setStatus(status);
                requestDb.save(targetRequest);
            }
        };
        timer.schedule(timerTask, 172800000);
    }

    @Override
    public void sendmail(String emailRecipient) throws AddressException, MessagingException, IOException {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("emailsfkticket@gmail.com", "Admin123$");
            }
        });

        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress("emailsfkticket@gmail.com", false));

        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailRecipient));

        msg.setSubject("Ticket Need Approval");
        msg.setContent("Sebuah request telah masuk dan butuh approval anda", "text/html");
        msg.setSentDate(new Date());

        MimeBodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setContent("Tutorials point email", "text/html");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);

//        attachPart.attachFile("/var/tmp/image19.png");
//        multipart.addBodyPart(attachPart);
//        msg.setContent(multipart);
        Transport.send(msg);
    }
}
