package propensi.d06.sihedes.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Table(name = "req_boa")
@JsonIgnoreProperties(value={"request", "boa"}, allowSetters=true)
public class RequestBOAModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "request", referencedColumnName = "id_request", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private RequestModel request;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "boa", referencedColumnName = "id_boa", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private BOAModel boa;

    // Setter & Getter

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public RequestModel getRequest() {
        return request;
    }

    public void setRequest(RequestModel request) {
        this.request = request;
    }

    public BOAModel getBoa() {
        return boa;
    }

    public void setBoa(BOAModel boa) {
        this.boa = boa;
    }
}

