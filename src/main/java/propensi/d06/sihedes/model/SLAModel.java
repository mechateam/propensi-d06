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
@Table(name="sla")
public class SLAModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_sla;

    @NotNull
    @Size(max = 255)
    @Column(name = "nama_sla", nullable = false)
    private String nama_sla;

    @NotNull
    @Size(max = 255)
    @Column(name = "description", nullable = false)
    private String description;

//    @NotNull
//    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX")
//    @Column(name = "completion_time", nullable = false)
//    private Date completion_time;

    @NotNull
    @Size(max = 255)
    @Column(name = "completion_time", nullable = false)
    private String completion_time;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_dept", referencedColumnName = "id_dept", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private DepartemenModel departemen;

    @OneToMany(mappedBy = "sla", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<RequestModel> listRequest;

    @OneToMany(mappedBy = "sla", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<SLABOAModel> listSLABOA;

    public Long getId_sla() {
        return id_sla;
    }

    public void setId_sla(Long id_sla) {
        this.id_sla = id_sla;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCompletion_time() {
        return completion_time;
    }

    public void setCompletion_time(String completion_time) {
        this.completion_time = completion_time;
    }

    public DepartemenModel getDepartemen() {
        return departemen;
    }

    public void setDepartemen(DepartemenModel departemen) {
        this.departemen = departemen;
    }

    public List<RequestModel> getListRequest() {
        return listRequest;
    }

    public void setListRequest(List<RequestModel> listRequest) {
        this.listRequest = listRequest;
    }

    public List<SLABOAModel> getListSLABOA() {
        return listSLABOA;
    }

    public void setListSLABOA(List<SLABOAModel> listSLABOA) {
        this.listSLABOA = listSLABOA;
    }

    public String getNama_sla() {
        return nama_sla;
    }

    public void setNama_sla(String nama_sla) {
        this.nama_sla = nama_sla;
    }
}
