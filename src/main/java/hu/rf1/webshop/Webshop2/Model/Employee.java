package hu.rf1.webshop.Webshop2.Model;


import javax.persistence.*;

@Entity
@Table(name = "employyes")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String name;
    @Column(name = "Subject")
    private String subject;

    public Employee() {

    }

    public Employee(long id, String name, String subject) {
        this.id = id;
        this.name = name;
        this.subject = subject;
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

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
