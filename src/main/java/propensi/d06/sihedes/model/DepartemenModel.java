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
@Table(name="departemen")
public class DepartemenModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_dept;

    @NotNull
    @Size(max = 50)
    @Column(name = "nama_departemen", nullable = false)
    private String nama_departemen;

    @NotNull
    @Size(max = 50)
    @Column(name = "nama_kepala", nullable = false)
    private String nama_kepala;

    @OneToMany(mappedBy = "departemen", fetch = FetchType.LAZY)
    private List<UserModel> listUser;

    @OneToMany(mappedBy = "departemen", fetch = FetchType.LAZY)
    private List<BOAModel> listBOA;

    @OneToMany(mappedBy = "departemen", fetch = FetchType.LAZY)
    private List<SLAModel> listSLA;

    public Long getId_dept() {
        return id_dept;
    }

    public void setId_dept(Long id_dept) {
        this.id_dept = id_dept;
    }

    public String getNama_departemen() {
        return nama_departemen;
    }

    public void setNama_departemen(String nama_departemen) {
        this.nama_departemen = nama_departemen;
    }

    public String getNama_kepala() {
        return nama_kepala;
    }

    public void setNama_kepala(String nama_kepala) {
        this.nama_kepala = nama_kepala;
    }

    public List<UserModel> getListUser() {
        return listUser;
    }

    public void setListUser(List<UserModel> listUser) {
        this.listUser = listUser;
    }

    public List<BOAModel> getListBOA() {
        return listBOA;
    }

    public void setListBOA(List<BOAModel> listBOA) {
        this.listBOA = listBOA;
    }

    public List<SLAModel> getListSLA() {
        return listSLA;
    }

    public void setListSLA(List<SLAModel> listSLA) {
        this.listSLA = listSLA;
    }
}
