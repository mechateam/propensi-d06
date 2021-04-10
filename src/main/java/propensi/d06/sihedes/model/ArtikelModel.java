package propensi.d06.sihedes.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;


@Entity
@Table(name="artikel")
public class ArtikelModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_artikel;

    @NotNull
    @Size(max = 255)
    @Column(name = "title", nullable = false)
    private String title;

    @NotNull
    @Size(max = 255)
    @Column(name = "category", nullable = false)
    private String category;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "created-date", nullable = false)
    private Date created_date;

    @NotNull
    @Size(max = 255)
    @Column(name = "tags", nullable = false)
    private String tags;

    public Long getId_artikel() {
        return id_artikel;
    }

    public void setId_artikel(Long id_artikel) {
        this.id_artikel = id_artikel;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Date getCreated_date() {
        return created_date;
    }

    public void setCreated_date(Date created_date) {
        this.created_date = created_date;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }
}
