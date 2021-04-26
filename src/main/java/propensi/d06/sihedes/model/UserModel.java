package propensi.d06.sihedes.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;

@Entity
@Table(name="user")
public class UserModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_user")
    private Long id_user;

    @NotNull
    @Size(max = 50)
    @Column(name = "username", nullable = false)
    private String username;

    @NotNull
    @Lob
    @Column(name = "password", nullable = false)
    @Type(type = "org.hibernate.type.TextType")
    private String password;

    @NotNull
    @Size(max=255)
    @Column(name = "nama", nullable = false)
    private String nama;

    @NotNull
    @Size(max=255)
    @Column(name = "email", nullable = false)
    private String email;

    @NotNull
    @Size(max=50)
    @Column(name = "no_hp", nullable = false)
    private String no_hp;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_role", referencedColumnName = "id_role", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private RoleModel id_role;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_dept", referencedColumnName = "id_dept", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private DepartemenModel departemen;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_boa", referencedColumnName = "id_boa")
    private BOAModel boa;

    @OneToMany(mappedBy = "resolver", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<RequestModel> listResolverRequest;

    @OneToMany(mappedBy = "pengaju", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<RequestModel> listPengajuRequest;

    @OneToMany(mappedBy = "resolver", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ProblemModel> listResolverProblem;

    @OneToMany(mappedBy = "pengaju", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ProblemModel> listPengajuProblem;

    @OneToMany(mappedBy = "manager", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<RequestModel> listManagerRequest;

    public Long getId_user() {
        return id_user;
    }

    public void setId_user(Long id_user) {
        this.id_user = id_user;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNo_hp() {
        return no_hp;
    }

    public void setNo_hp(String no_hp) {
        this.no_hp = no_hp;
    }

    public RoleModel getId_role() {
        return id_role;
    }

    public void setId_role(RoleModel id_role) {
        this.id_role = id_role;
    }

    public DepartemenModel getDepartemen() {
        return departemen;
    }

    public void setDepartemen(DepartemenModel departemen) {
        this.departemen = departemen;
    }

    public BOAModel getBoa() {
        return boa;
    }

    public void setBoa(BOAModel boa) {
        this.boa = boa;
    }

    public List<RequestModel> getListResolverRequest() {
        return listResolverRequest;
    }

    public void setListResolverRequest(List<RequestModel> listResolverRequest) {
        this.listResolverRequest = listResolverRequest;
    }

    public List<RequestModel> getListPengajuRequest() {
        return listPengajuRequest;
    }

    public void setListPengajuRequest(List<RequestModel> listPengajuRequest) {
        this.listPengajuRequest = listPengajuRequest;
    }

    public List<ProblemModel> getListResolverProblem() {
        return listResolverProblem;
    }

    public void setListResolverProblem(List<ProblemModel> listResolverProblem) {
        this.listResolverProblem = listResolverProblem;
    }

    public List<ProblemModel> getListPengajuProblem() {
        return listPengajuProblem;
    }

    public void setListPengajuProblem(List<ProblemModel> listPengajuProblem) {
        this.listPengajuProblem = listPengajuProblem;
    }

    public List<RequestModel> getListManagerRequest() {
        return listManagerRequest;
    }

    public void setListManagerRequest(List<RequestModel> listManagerRequest) {
        this.listManagerRequest = listManagerRequest;
    }
}
