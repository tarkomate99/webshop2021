package hu.rf1.webshop.Webshop2.Controller;

import hu.rf1.webshop.Webshop2.DAO.ErtekelesDAO;
import hu.rf1.webshop.Webshop2.DAO.TermekDAO;
import hu.rf1.webshop.Webshop2.Model.Ertekelesek;
import hu.rf1.webshop.Webshop2.Model.Termekek;
import hu.rf1.webshop.Webshop2.Repository.ErtekelesekRepository;
import hu.rf1.webshop.Webshop2.Repository.TermekekRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class TermekekController {

    @Autowired
    TermekekRepository repository;

    @Autowired
    ErtekelesekRepository ertekelesekRepository;

    @Autowired
    private ErtekelesDAO ertekelesDAO;

    @Autowired
    private TermekDAO termekDAO;

    private final Log log = LogFactory.getLog(this.getClass());

    @RequestMapping("/termekek/**")
    public String indexPage(Model model){
        List<Termekek> prod = repository.findAll();
        for(Termekek production : prod){
            Long ID = production.getId();
            Integer id = ID.intValue();
            //log.info(ertekelesDAO.getRatingById(id));
            termekDAO.updateRatingById(id,ertekelesDAO.getRatingById(id));
        }
        model.addAttribute("productlist", prod);
        return "termekek";
    }

    @RequestMapping("/prod-info/{id}")
    public @ResponseBody Termekek ProdInfo(@PathVariable("id") int id, Model model){
        return termekDAO.getProductById(id);
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
        Long ID = prod.getId();
        Integer id = ID.intValue();
        ertekelesDAO.insertAfterProductInsert(LocalDateTime.now(),"admin insert",prod.getRating(),id,1);
        return "redirect:/termekek";
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
        return "redirect:/termekek";
    }


}
