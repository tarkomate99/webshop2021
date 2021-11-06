package hu.rf1.webshop.Webshop2.Controller;

import hu.rf1.webshop.Webshop2.Model.Ertekelesek;
import hu.rf1.webshop.Webshop2.Model.Rendelesek;
import hu.rf1.webshop.Webshop2.Model.Termekek;
import hu.rf1.webshop.Webshop2.Repository.ErtekelesekRepository;
import hu.rf1.webshop.Webshop2.Repository.TermekekRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class ErtekelesekController {

    @Autowired
    ErtekelesekRepository repository;

    @Autowired
    TermekekRepository termekekRepository;

    @RequestMapping("/ratings")
    public String ratingsPage(Model model){
        List<Ertekelesek> ertekeles = repository.findAll();
        model.addAttribute("ratinglist", ertekeles);
        return "ertekelesek";
    }

    Long termek_id;
    @RequestMapping("/ratings/new/{id}")
    public String newRating(Model model, @PathVariable("id")long id){
        termek_id=id;
        Ertekelesek rating = new Ertekelesek();
        Termekek termek = termekekRepository.findById(id).orElse(null);
        model.addAttribute("termek", termek);
        model.addAttribute("ratings", rating);
        return "ertekelesek";
    }

    @PostMapping("/ratings/add")
    public String addRating(Ertekelesek rating){
        Termekek termekek = termekekRepository.findById(termek_id).orElse(null);
        repository.save(rating);
        return "redirect:/ertekelesek";
    }

    @PostMapping("/ratings/update")
    public String updateRating(Ertekelesek rating){
        repository.save(rating);
        return "redirect:/ertekelesek";
    }

    @RequestMapping("/edit/ratings/{id}")
    public ModelAndView editForm(@PathVariable("id")long id){
        ModelAndView mw = new ModelAndView("edit_rating");
        Ertekelesek rating = repository.findById(id).orElse(null);
        mw.addObject("orders", rating);
        return mw;
    }


    @RequestMapping("/delete/ratings/{id}")
    public String deleteOrder(@PathVariable("id")long id){
        repository.deleteById(id);
        return "redirect:/ertekelesek";
    }

}
