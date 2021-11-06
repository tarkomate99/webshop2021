package hu.rf1.webshop.Webshop2.Controller;

import hu.rf1.webshop.Webshop2.Model.Ertekelesek;
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
public class TermekekController {

    @Autowired
    TermekekRepository repository;

    @Autowired
    ErtekelesekRepository ertekelesekRepository;

    @RequestMapping("/")
    public String indexPage(Model model){
        List<Termekek> prod = repository.findAll();
//        List<Ertekelesek> ratings = ertekelesekRepository.findAll();
//        Double rating = 0.0;
//        Integer prod_rates = 0;
//        for (Termekek termek : prod){
//            for (Ertekelesek rate : ratings){
//                if (termek.getId() == rate.getTermek_id()){
//                    rating+=rate.getRating();
//                    prod_rates+=1;
//                }
//            }
//        }
//        System.out.println(rating/prod_rates);
        model.addAttribute("productlist", prod);
        return "index";
    }
    @RequestMapping("/new")
    public String newFormForTermek(Model model){
        Termekek prod = new Termekek();
        model.addAttribute("termekek", prod);
        return "new_production";
    }

    @PostMapping("/add")
    public String addTermek(Termekek prod){
        repository.save(prod);
        return "redirect:/";
    }

    @RequestMapping("/edit/{id}")
    public ModelAndView editForm(@PathVariable("id")long id){
        ModelAndView mw = new ModelAndView("edit_prod");
        Termekek prod = repository.findById(id).orElse(null);
        mw.addObject("termekek",prod);
        return mw;
    }

    @RequestMapping("/delete/{id}")
    public String deleteTermek(@PathVariable("id")long id){
        repository.deleteById(id);
        return "redirect:/";
    }


}
