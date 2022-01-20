package hu.rf1.webshop.Webshop2.Service;


import hu.rf1.webshop.Webshop2.Model.User;

public interface UserService {

    public String registerUser(User user);

    public User findByEmail(String email);

    public String userActivation(String code);

}
