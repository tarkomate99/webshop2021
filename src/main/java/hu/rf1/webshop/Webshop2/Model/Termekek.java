package hu.rf1.webshop.Webshop2.Model;

import javax.persistence.*;

@Entity
@Table(name = "termekek")
public class Termekek {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private Integer price;

    @Column(name = "nofitems")
    private Integer nofitems;

    @Column(name = "rating")
    private Double rating;

    @Column(name = "category")
    private String category;

    public Termekek() {
    }

    public Termekek(long id, String name, Integer price, Integer nofitems, Double rating, String category) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.nofitems = nofitems;
        this.rating = rating;
        this.category = category;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getNofitems() {
        return nofitems;
    }

    public void setNofitems(Integer nofitems) {
        this.nofitems = nofitems;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
