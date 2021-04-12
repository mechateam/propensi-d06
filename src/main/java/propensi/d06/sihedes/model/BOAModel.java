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
@Table(name="boa")
public class BOAModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_boa;

    @OneToOne(mappedBy = "boa", fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private UserModel user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_dept", referencedColumnName = "id_dept", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private DepartemenModel departemen;

    @OneToMany(mappedBy = "boa", fetch = FetchType.LAZY)
    private List<RequestBOAModel> listRequestBOA;

    public Long getId_boa() {
        return id_boa;
    }

    public void setId_boa(Long id_boa) {
        this.id_boa = id_boa;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public DepartemenModel getDepartemen() {
        return departemen;
    }

    public void setDepartemen(DepartemenModel departemen) {
        this.departemen = departemen;
    }

    public List<RequestBOAModel> getListRequestBOA() {
        return listRequestBOA;
    }

    public void setListRequestBOA(List<RequestBOAModel> listRequestBOA) {
        this.listRequestBOA = listRequestBOA;
    }
}