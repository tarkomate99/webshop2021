package hu.rf1.webshop.Webshop2.Model;


import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;
    @Column(nullable = false, unique = true, length = 45)
    private String email;
    @Column(nullable = true)
    private String user_type;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private Integer iranyito;
    @Column(nullable = false)
    private String city;
    @Column(nullable = false)
    private String address;

    private String activation;

    private Boolean enabled;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = {@JoinColumn(name="user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")}
    )
    private Set<Role> roles = new HashSet<Role>();

    public User() {
    }

    public User(Long id, String name, String email, String user_type, String password, Integer iranyito, String city, String address) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.user_type = user_type;
        this.password = password;
        this.iranyito = iranyito;
        this.city = city;
        this.address = address;
    }

    public String getActivation() {
        return activation;
    }

    public void setActivation(String activation) {
        this.activation = activation;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Integer getIranyito() {
        return iranyito;
    }

    public void setIranyito(Integer iranyito) {
        this.iranyito = iranyito;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void addRoles(String roleName){
        if(this.roles == null || this.roles.isEmpty()){
            this.roles = new HashSet<>();
            this.roles.add(new Role(roleName));
        }
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }


    @Override
    public String toString() {
        return "Users{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", user_type='" + user_type + '\'' +
                ", password='" + password + '\'' +
                ", iranyito=" + iranyito +
                ", city='" + city + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
