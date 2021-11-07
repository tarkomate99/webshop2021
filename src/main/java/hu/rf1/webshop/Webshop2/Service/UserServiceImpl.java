package hu.rf1.webshop.Webshop2.Service;

import hu.rf1.webshop.Webshop2.Model.Users;
import hu.rf1.webshop.Webshop2.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private UserRepository userRepository;


    @Autowired
    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public Users findByEmail(String email){
        return userRepository.findByEmail(email);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = findByEmail(username);
        if(user == null){
            throw new UsernameNotFoundException(username);
        }
        return new UserDetailsImpl(user);
    }
}
