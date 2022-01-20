package hu.rf1.webshop.Webshop2.Service;

import hu.rf1.webshop.Webshop2.Model.Role;
import hu.rf1.webshop.Webshop2.Model.User;
import hu.rf1.webshop.Webshop2.Repository.RoleRepository;
import hu.rf1.webshop.Webshop2.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private UserRepository userRepository;

    private RoleRepository roleRepository;

    private EmailService emailService;

    private final String USER_ROLE = "USER";

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository){
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }
    @Autowired
    public void setEmailService(EmailService emailService){
        this.emailService = emailService;
    }

    @Override
    public User findByEmail(String email){
        return userRepository.findByEmail(email);
    }

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByEmail(username);
        if(user == null){
            throw new UsernameNotFoundException(username);
        }
        return new UserDetailsImpl(user);
    }

    @Override
    public String registerUser(User userToRegister){
        User userCheck = userRepository.findByEmail(userToRegister.getEmail());

        if(userCheck != null){
            return "alreadyExists";
        }

        Role userRole = roleRepository.findByRole(USER_ROLE);

        if(userRole!= null){
            userToRegister.getRoles().add(userRole);
        } else {
            userToRegister.addRoles(USER_ROLE);
        }
        String key = generateKey();
        userToRegister.setEnabled(false);
        userToRegister.setActivation(key);
        String encodedPassword = bCryptPasswordEncoder.encode(userToRegister.getPassword());
        userToRegister.setPassword(encodedPassword);
        userRepository.save(userToRegister);
        emailService.sendMessage(userToRegister.getEmail(),key);

        return "ok";
    }

    public String generateKey(){
        String key = "";
        Random random = new Random();
        char[] word = new char[16];
        for (int j=0; j<word.length; j++){
            word[j] = (char)('a'+random.nextInt(26));
        }
        return new String(word);

    }

    @Override
    public String userActivation(String code){
        User user = userRepository.findByActivation(code);
        if(user == null){
            return "noresult";
        }
        user.setEnabled(true);
        user.setActivation("");
        userRepository.save(user);
        return "ok";
    }

}
