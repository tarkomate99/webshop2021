package hu.rf1.webshop.Webshop2.Controller;

import hu.rf1.webshop.Webshop2.Model.Users;
import hu.rf1.webshop.Webshop2.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Objects;

@Controller
public class UsersController {

    @Autowired
    UserRepository repository;

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
        return "register";
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

    @RequestMapping("/login")
    public String login(HttpSession session){
        return "login";
    }


    @RequestMapping("/login-sucess")
    public String login(HttpSession session, Model model, @RequestParam("username")String username, @RequestParam("jelszo") String jelszo){
        List<Users> user = repository.findAll();
        for( Users usr : user){
            if(!Objects.equals(usr.getName(), username) && !Objects.equals(usr.getPassword(), jelszo)){
                return "login";
            }else if(Objects.equals(usr.getName(), username) && Objects.equals(usr.getPassword(),jelszo)){
                session.setAttribute("user", usr);
            }

        }
        return "redirect:/";
    }

    @RequestMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute("user");
        return "redirect:/";
    }

}
