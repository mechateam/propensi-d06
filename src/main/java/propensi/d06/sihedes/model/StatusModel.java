package propensi.d06.sihedes.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name="status")
public class StatusModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_status;

    @NotNull
    @Size(max = 50)
    @Column(name = "nama_status", nullable = false)
    private String namaStatus;

    @OneToMany(mappedBy = "status", fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private List<RequestModel> listRequest;

    @OneToMany(mappedBy = "status", fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private List<ProblemModel> listProblem;

    public Long getId_status() {
        return id_status;
    }

    public void setId_status(Long id_status) {
        this.id_status = id_status;
    }

    public String getNamaStatus() {
        return namaStatus;
    }

    public void setNamaStatus(String namaStatus) {
        this.namaStatus = namaStatus;
    }

    public List<RequestModel> getListRequest() {
        return listRequest;
    }

    public void setListRequest(List<RequestModel> listRequest) {
        this.listRequest = listRequest;
    }

    public List<ProblemModel> getListProblem() {
        return listProblem;
    }

    public void setListProblem(List<ProblemModel> listProblem) {
        this.listProblem = listProblem;
    }

}