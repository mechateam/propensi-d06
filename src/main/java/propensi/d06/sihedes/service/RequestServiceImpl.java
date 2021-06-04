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
    public RequestModel updateApprovalRequest(RequestModel request) throws AddressException, MessagingException, IOException{

        RequestModel targetRequest = requestDb.findById(request.getId_request()).get();
        List<SLABOAModel> listBOA = slaboaDb.findAllBySla(targetRequest.getSla());
        Integer currentRank=0;
        SLABOAModel currentBOA = listBOA.get(0);

        for (SLABOAModel boa:listBOA){
            if (targetRequest.getIdApprover().equals(boa.getBoa().getUser().getId_user())){
                currentRank = boa.getBoa().getRank();
                currentBOA = boa;
            }
        }

        for (SLABOAModel boa: listBOA){

            if (targetRequest.getIdApprover() == boa.getBoa().getUser().getId_user()){
                if (currentRank == listBOA.size()){
                    targetRequest.setIdApprover(new Long(-1));
                }
            }
            if ( !boa.equals(currentBOA) && boa.getBoa().getRank() == currentRank){
                sendmail(boa.getBoa().getUser().getEmail());
                targetRequest.setIdApprover(boa.getBoa().getUser().getId_user());
            }

            else if (boa.getBoa().getRank() == currentRank+1 ){
                sendmail(boa.getBoa().getUser().getEmail());
                targetRequest.setIdApprover(boa.getBoa().getUser().getId_user());
            }
            else if (boa.getBoa().getRank() == currentRank+2 ){
                sendmail(boa.getBoa().getUser().getEmail());
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

        Date datenow = request.getCreatedDate();
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

        request.setCreatedDate(new Date());
        request.setPengaju(userPembuat);
        request.setStatus(statusDb.findByNamaStatus("Waiting for Approval"));
        request.setRequestDepartemen(userPembuat.getDepartemen());
        request.setResolverDepartemen(request.getSla().getDepartemen());


        SLAModel sla = request.getSla();
        List<SLABOAModel> listBOA = slaboaDb.findAllBySla(sla);
        SLABOAModel minRankBoa = listBOA.get(0);

        for (SLABOAModel boa: listBOA) {
            if (boa.getBoa().getRank() < minRankBoa.getBoa().getRank()){
                minRankBoa = boa;
            }
        }
        request.setIdApprover(minRankBoa.getBoa().getId_boa());

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

        Transport.send(msg);
    }

    @Override
    public List<RequestModel> findRequestsByStatusId(Long id) {
        return requestDb.findRequestModelsByStatus(statusDb.findById(id).get());
    }

    @Override
    public List<RequestModel> findRequestsByStatusIdAndResolver(Long id, UserModel resolver) {
        return requestDb.findRequestModelsByStatusAndResolver(statusDb.findById(id).get(), resolver);
    }

    @Override
    public List<RequestModel> findRequestsByStatusIdAndPengaju(Long id, UserModel user) {
        return requestDb.findRequestModelsByStatusAndPengaju(statusDb.findById(id).get(), user);
    }

    @Override
    public List<RequestModel> findRequestsByStatusIdAndResolverDepartment(Long id, DepartemenModel departemenModel) {
        return requestDb.findRequestModelsByStatusAndResolverDepartemen(statusDb.findById(id).get(), departemenModel);
    }

    @Override
    public List<RequestModel> findRequestByCreatedDateMonth(String month) {
        return requestDb.findRequestModelsByCreatedDateContaining("-"+month+"-");
    }
}
