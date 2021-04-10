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
@Table(name="role")
public class RoleModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_role;

    @NotNull
    @Size(max = 50)
    @Column(name = "nama_role", nullable = false)
    private String nama_role;

    @OneToMany(mappedBy = "id_role", fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private List<UserModel> userRole;

    public Long getId_role() {
        return id_role;
    }

    public void setId_role(Long id_role) {
        this.id_role = id_role;
    }

    public String getNama_role() {
        return nama_role;
    }

    public void setNama_role(String nama_role) {
        this.nama_role = nama_role;
    }

    public List<UserModel> getUserRole() {
        return userRole;
    }

    public void setUserRole(List<UserModel> userRole) {
        this.userRole = userRole;
    }
}


