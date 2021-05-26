package propensi.d06.sihedes.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="request")
public class RequestModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_request;

    @NotNull
    @Size(max=14)
    @Column(name="codeRequest", nullable = false, unique = true)
    private String codeRequest;

    public String getCodeRequest() {
        return codeRequest;
    }

    public void setCodeRequest(String codeRequest) {
        this.codeRequest = codeRequest;
    }

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "created_date", nullable = false)
    private Date created_date;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "target_date", nullable = false)
    private Date target_date;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "finished_date", nullable = true)
    private Date finished_date;

    @NotNull
    @Size(max = 255)
    @Column(name = "description", nullable = false)
    private String description;

    @NotNull
    @Size(max = 255)
    @Column(name = "location", nullable = false)
    private String location;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @NotNull
    @Size(max = 255)
    @Column(name = "subject", nullable = false)
    private String subject;

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @NotNull
    @Column(name = "phone_number", nullable = false)
    private Long phone_number;

    public Long getPhone_number() { return phone_number; }

    public void setPhone_number(Long phone_number) { this.phone_number = phone_number; }

    @NotNull
    @Size(max = 255)
    @Column(name = "background", nullable = false)
    private String background;

    public String getBackground() { return background; }

    public void setBackground(String background) { this.background = background; }

    @NotNull
    @Size(max = 255)
    @Column(name = "nama_pengaju", nullable = false)
    private String nama_pengaju;

    @Column(name = "id_approver", nullable = true)
    private Long idApprover;


    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "id_resolver", referencedColumnName = "id_user", nullable = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private UserModel resolver;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "id_departemen", referencedColumnName = "id_dept", nullable = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private DepartemenModel resolverDepartemen;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "req_departemen", referencedColumnName = "id_dept", nullable = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private DepartemenModel requestDepartemen;

    public DepartemenModel getRequestDepartemen() {
        return requestDepartemen;
    }

    public void setRequestDepartemen(DepartemenModel requestDepartemen) {
        this.requestDepartemen = requestDepartemen;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_sla", referencedColumnName = "id_sla", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private SLAModel sla;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_pengaju", referencedColumnName = "id_user", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private UserModel pengaju;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_status", referencedColumnName = "id_status", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private StatusModel status;

    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "id_vendor", referencedColumnName = "id_vendor", nullable = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private VendorModel reqVendor;

    public VendorModel getReqVendor() {
        return reqVendor;
    }

    public void setReqVendor(VendorModel reqVendor) {
        this.reqVendor = reqVendor;
    }

    @OneToMany(mappedBy = "request", fetch = FetchType.LAZY)
    private List<RequestBOAModel> listRequestBOA;

    @OneToMany(mappedBy = "request", fetch = FetchType.LAZY)
    private List<LogRequestModel> listLogRequest;

    @OneToMany(mappedBy = "request", fetch = FetchType.LAZY)
    private List<FeedbackRequest> listFeedback;

    public Long getId_request() {
        return id_request;
    }

    public void setId_request(Long id_request) {
        this.id_request = id_request;
    }

    public StatusModel getStatus() {
        return status;
    }

    public void setStatus(StatusModel status) {
        this.status = status;
    }

    public Date getCreated_date() {
        return created_date;
    }

    public void setCreated_date(Date created_date) {
        this.created_date = created_date;
    }

    public Date getTarget_date() { return target_date; }

    public void setTarget_date(Date target_date) { this.target_date = target_date; }

    public Date getFinished_date() {
        return finished_date;
    }

    public void setFinished_date(Date finished_date) {
        this.finished_date = finished_date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNama_pengaju() {
        return nama_pengaju;
    }

    public void setNama_pengaju(String nama_pengaju) {
        this.nama_pengaju = nama_pengaju;
    }

    public UserModel getResolver() {
        return resolver;
    }

    public void setResolver(UserModel resolver) {
        this.resolver = resolver;
    }

    public SLAModel getSla() {
        return sla;
    }

    public void setSla(SLAModel sla) {
        this.sla = sla;
    }

    public UserModel getPengaju() {
        return pengaju;
    }

    public void setPengaju(UserModel pengaju) {
        this.pengaju = pengaju;
    }

    public List<RequestBOAModel> getListRequestBOA() {
        return listRequestBOA;
    }

    public void setListRequestBOA(List<RequestBOAModel> listRequestBOA) {
        this.listRequestBOA = listRequestBOA;
    }

    public List<LogRequestModel> getListLogRequest() {
        return listLogRequest;
    }

    public void setListLogRequest(List<LogRequestModel> listLogRequest) {
        this.listLogRequest = listLogRequest;
    }

    public List<FeedbackRequest> getListFeedback() {
        return listFeedback;
    }

    public void setListFeedback(List<FeedbackRequest> listFeedback) {
        this.listFeedback = listFeedback;
    }

    public Long getIdApprover() {
        return idApprover;
    }

    public void setIdApprover(Long idApprover) {
        this.idApprover = idApprover;
    }

    public DepartemenModel getResolverDepartemen() {
        return resolverDepartemen;
    }

    public void setResolverDepartemen(DepartemenModel resolverDepartemen) {
        this.resolverDepartemen = resolverDepartemen;
    }
}


