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
@Table(name="log_problem")
public class LogProblemModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_log;

    @NotNull
    @Size(max = 255)
    @Column(name = "description", nullable = false)
    private String description;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX")
    @Column(name = "posted_date", nullable = false)
    private Date posted_date;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_problem", referencedColumnName = "id_problem", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private ProblemModel problem;

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

    public Date getPosted_date() {
        return posted_date;
    }

    public void setPosted_date(Date posted_date) {
        this.posted_date = posted_date;
    }

    public ProblemModel getProblem() {
        return problem;
    }

    public void setProblem(ProblemModel problem) {
        this.problem = problem;
    }
}
