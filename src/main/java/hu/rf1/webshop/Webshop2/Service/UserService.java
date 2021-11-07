package hu.rf1.webshop.Webshop2.Service;

import hu.rf1.webshop.Webshop2.Model.Users;

public interface UserService {

    public Users findByEmail(String email);

}
