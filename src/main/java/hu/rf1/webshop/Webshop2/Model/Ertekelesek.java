package hu.rf1.webshop.Webshop2.Model;


import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "ertekelesek")
public class Ertekelesek {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "user_id")
    private Integer user_id;
    @Column(name = "termek_id")
    private Integer termek_id;
    @Column(name = "rating")
    private Double rating;
    @Column(name = "description")
    private String description;
    @Column(name = "date")
    private LocalDateTime date;

    public Ertekelesek() {
    }

    public Ertekelesek(long id, Integer user_id, Integer termek_id, Double rating, String description, LocalDateTime date) {
        this.id = id;
        this.user_id = user_id;
        this.termek_id = termek_id;
        this.rating = rating;
        this.description = description;
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Integer getTermek_id() {
        return termek_id;
    }

    public void setTermek_id(Integer termek_id) {
        this.termek_id = termek_id;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
