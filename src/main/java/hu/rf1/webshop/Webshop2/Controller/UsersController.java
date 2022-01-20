package hu.rf1.webshop.Webshop2.Controller;

import hu.rf1.webshop.Webshop2.DAO.UserDAO;
import hu.rf1.webshop.Webshop2.Model.User;
import hu.rf1.webshop.Webshop2.Repository.UserRepository;
import hu.rf1.webshop.Webshop2.Repository.UsersRepo;
import hu.rf1.webshop.Webshop2.Service.EmailService;
import hu.rf1.webshop.Webshop2.Service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Objects;

@Controller
public class UsersController {

    @Autowired
    UserRepository repository;

    private EmailService emailService;

    private UserService userService;

    private String user_typ;

    @Autowired
    private UserDAO userDAO;


    @Autowired
    public void setUserService(UserService userService){ this.userService=userService;}

    @Autowired
    public void setEmailService(EmailService emailService){
        this.emailService = emailService;
    }

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @RequestMapping("/users")
    public String usersPage(Model model, HttpServletRequest request){
        List<User> user = repository.findAll();
        model.addAttribute("userslist", user);
        user_typ=request.getSession().getAttribute("user_type").toString();
        if (!Objects.equals(user_typ, "admin")){
            return "error";
        }else {
            return "users";
        }
    }

    @RequestMapping("/users/new")
    public String newFormForUser(Model model){
        User user = new User();
        model.addAttribute("users", user);
        if (!Objects.equals(user_typ, "admin")){
            return "error";
        }else {
            return "registration";
        }
    }

    @PostMapping("/users/add")
    public String addUser(User user){
        user.setEnabled(true);
        repository.save(user);
        if (!Objects.equals(user_typ, "admin")){
            return "error";
        }else {
            return "redirect:/users";
        }
    }

    @RequestMapping("/edit/users/{id}")
    public ModelAndView editForm(@PathVariable("id")long id){
        ModelAndView mw = new ModelAndView("edit_user");
        User user = repository.findById(id).orElse(null);
        mw.addObject("users", user);
        return mw;
    }

//    @RequestMapping("/delete/users/{id}")
//    public String deleteUser(@PathVariable("id")long id){
//        repository.deleteById(id);
//        if (!Objects.equals(user_typ, "admin")){
//            return "error";
//        }else {
//            return "redirect:/users";
//        }
//    }

    @RequestMapping("/delete/users/{id}")
    public String deleteUser(@PathVariable("id") int id){
        userDAO.deleteUser(id);

        return "redirect:/users";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login?logout";
    }
    @RequestMapping("/registration")
    public String registration(Model model){
        model.addAttribute("user", new User());
        return "registration";
    }

    //@RequestMapping(value = "/reg", method = RequestMethod.POST)
    @PostMapping("/reg")
    public String greetingSubmit(@ModelAttribute User user){
            System.out.println("Uj USER");
            log.info("Uj user!");
            log.info(user.getEmail());
            log.debug(user.getName());
            log.debug(user.getPassword());
            userService.registerUser(user);
        return "auth/login";
    }

    @RequestMapping(path = "/activation/{code}", method = RequestMethod.GET)
    public String activation(@PathVariable("code") String code, HttpServletResponse response){
        String result = userService.userActivation(code);
        return "auth/login";
    }
}
