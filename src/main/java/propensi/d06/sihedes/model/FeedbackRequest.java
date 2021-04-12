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
@Table(name="feedback_request")
public class FeedbackRequest implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_log;

    @NotNull
    @Size(max = 255)
    @Column(name = "description", nullable = false)
    private String description;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX")
    @Column(name = "created_date", nullable = false)
    private Date created_date;

    @NotNull
    @Column(name = "score",nullable = false)
    private Integer score;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_request", referencedColumnName = "id_request", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private RequestModel request;

    public Long getId_log() {
        return id_log;
    }

    public void setId_log(Long id_log) {
        this.id_log = id_log;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreated_date() {
        return created_date;
    }

    public void setCreated_date(Date created_date) {
        this.created_date = created_date;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public RequestModel getRequest() {
        return request;
    }

    public void setRequest(RequestModel request) {
        this.request = request;
    }
}
