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
    @Column(name = "status", nullable = false)
    private Integer status;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "created_date", nullable = false)
    private Date created_date;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "finished_date", nullable = true)
    private Date finished_date;

    @NotNull
    @Size(max = 255)
    @Column(name = "description", nullable = false)
    private String description;

    @NotNull
    @Size(max = 255)
    @Column(name = "nama_pengaju", nullable = false)
    private String nama_pengaju;

    @NotNull
    @Column(name = "request_type", nullable = false)
    private Integer request_type;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "resolver", referencedColumnName = "id_user", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private UserModel resolver;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sla", referencedColumnName = "id_sla", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private SLAModel sla;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pengaju", referencedColumnName = "id_user", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private UserModel pengaju;

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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreated_date() {
        return created_date;
    }

    public void setCreated_date(Date created_date) {
        this.created_date = created_date;
    }

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

    public Integer getRequest_type() {
        return request_type;
    }

    public void setRequest_type(Integer request_type) {
        this.request_type = request_type;
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
}


