package hu.rf1.webshop.Webshop2.Controller;

import hu.rf1.webshop.Webshop2.Model.Ertekelesek;
import hu.rf1.webshop.Webshop2.Model.Rendelesek;
import hu.rf1.webshop.Webshop2.Model.Termekek;
import hu.rf1.webshop.Webshop2.Model.User;
import hu.rf1.webshop.Webshop2.Repository.ErtekelesekRepository;
import hu.rf1.webshop.Webshop2.Repository.TermekekRepository;
import hu.rf1.webshop.Webshop2.Repository.UserRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Controller
public class ErtekelesekController {

    private final Log log = LogFactory.getLog(this.getClass());

    @Autowired
    ErtekelesekRepository repository;

    @Autowired
    TermekekRepository termekekRepository;

    @Autowired
    UserRepository userRepository;

    private String user_email;

    private String user_type;

    @RequestMapping("/ratings")
    public String ratingsPage(Model model, HttpServletRequest request){
        List<Ertekelesek> ertekeles = repository.findAll();
        model.addAttribute("ratinglist", ertekeles);
        user_type=request.getSession().getAttribute("user_type").toString();
        if (request.getSession().getAttribute("user_type")!="admin"){
            return "error";
        }else {
            return "ertekelesek";
        }
    }

    Long termek_id;
    @RequestMapping("/ratings/new/{id}")
    public String newRating(Model model, @PathVariable("id")long id, HttpServletRequest request){
        termek_id=id;
        Ertekelesek rating = new Ertekelesek();
        Termekek termek = termekekRepository.findById(id).orElse(null);
        model.addAttribute("termek", termek);
        model.addAttribute("ratings", rating);
        user_email=request.getSession().getAttribute("username").toString();
        log.info(user_email);
        user_type=request.getSession().getAttribute("user_type").toString();
        return "new_rating";

    }

    @PostMapping("/ratings/add")
    public String addRating(Ertekelesek rating){
        Termekek termekek = termekekRepository.findById(termek_id).orElse(null);
        User user = userRepository.findByEmail(user_email);
        Long id = user.getId();
        Integer user_id = id.intValue();
        rating.setUser_id(user_id);
        rating.setTermek_id(Math.toIntExact(termek_id));
        rating.setDate(LocalDateTime.now());
        repository.save(rating);
        if (!user_type.equals("admin")){
            return "redirect:/";
        }else
        return "redirect:/ratings";
    }

    @PostMapping("/ratings/update")
    public String updateRating(Ertekelesek rating){
        repository.save(rating);
        if (!Objects.equals(user_type, "admin")){
            return "error";
        }else {
            return "redirect:/ratings";
        }
    }

    @RequestMapping("/edit/ratings/{id}")
    public ModelAndView editForm(@PathVariable("id")long id){
        ModelAndView mw = new ModelAndView("edit_rating");
        Ertekelesek rating = repository.findById(id).orElse(null);
        mw.addObject("ratings", rating);
        return mw;
    }


    @RequestMapping("/delete/ratings/{id}")
    public String deleteRating(@PathVariable("id")long id){
        repository.deleteById(id);
        if (!Objects.equals(user_type, "admin")){
            return "error";
        }else {
            return "ertekelesek";
        }
    }

}
