package hu.rf1.webshop.Webshop2.Model;


import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "rendelesek")
public class Rendelesek {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private Integer termek_id;
    @Column(nullable = false)
    private Integer osszeg;
    @Column(nullable = true)
    private LocalDateTime date;
    @Column(nullable = false)
    private Integer user_id;

    public Rendelesek() {
    }

    public Rendelesek(long id, Integer termek_id, Integer osszeg, LocalDateTime date, Integer user_id) {
        this.id = id;
        this.termek_id = termek_id;
        this.osszeg = osszeg;
        this.date = date;
        this.user_id = user_id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Integer getTermek_id() {
        return termek_id;
    }

    public void setTermek_id(Integer termek_id) {
        this.termek_id = termek_id;
    }

    public Integer getOsszeg() {
        return osszeg;
    }

    public void setOsszeg(Integer osszeg) {
        this.osszeg = osszeg;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }
}
