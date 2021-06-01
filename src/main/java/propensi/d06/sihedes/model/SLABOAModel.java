package propensi.d06.sihedes.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "sla_boa")
@JsonIgnoreProperties(value={"sla", "boa"}, allowSetters=true)
public class SLABOAModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_sla", referencedColumnName = "id_sla", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private SLAModel sla;

    @ManyToOne(fetch = FetchType.EAGER, optional = false, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_boa", referencedColumnName = "id_boa", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private BOAModel boa;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public SLAModel getSla() {
        return sla;
    }

    public void setSla(SLAModel sla) {
        this.sla = sla;
    }

    public BOAModel getBoa() {
        return boa;
    }

    public void setBoa(BOAModel boa) {
        this.boa = boa;
    }
}
