package hu.rf1.webshop.Webshop2.Controller;

import hu.rf1.webshop.Webshop2.Model.Users;
import hu.rf1.webshop.Webshop2.Repository.UserRepository;
import hu.rf1.webshop.Webshop2.Service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @RequestMapping("/users")
    public String usersPage(Model model){
        List<Users> user = repository.findAll();
        model.addAttribute("userslist", user);
        return "users";
    }

    @RequestMapping("/users/new")
    public String newFormForUser(Model model){
        Users user = new Users();
        model.addAttribute("users", user);
        return "registration";
    }

    @PostMapping("/users/add")
    public String addUser(Users user){
        List<Users> usr = repository.findAll();
        for(Users felhasznalok : usr){
            if(!Objects.equals(felhasznalok.getEmail(), user.getEmail()) && !Objects.equals(felhasznalok.getName(), user.getName())){
                repository.save(user);
            }else{
                return "redirect:/";
            }
        }
        return "redirect:/login";
    }

    @RequestMapping("/edit/users/{id}")
    public ModelAndView editForm(@PathVariable("id")long id){
        ModelAndView mw = new ModelAndView("edit_user");
        Users user = repository.findById(id).orElse(null);
        mw.addObject("users", user);
        return mw;
    }

    @RequestMapping("/delete/users/{id}")
    public String deleteUser(@PathVariable("id")long id){
        repository.deleteById(id);
        return "redirect:/";
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
        model.addAttribute("user", new Users());
        return "registration";
    }

    @RequestMapping(value = "/reg", method = RequestMethod.POST)
    public String greetingSubmit(@ModelAttribute Users user){
        System.out.println("Uj USER");
        log.info("Uj user!");
        log.info(user.getEmail());
//        emailService.sendMessage(user.getEmail()); TODO nem működik vmiért
//        log.debug(user.getName());
//        log.debug(user.getPassword());
        return "auth/login";
    }
}
