package hu.rf1.webshop.Webshop2.Controller;

import hu.rf1.webshop.Webshop2.Model.Rendelesek;
import hu.rf1.webshop.Webshop2.Model.Termekek;
import hu.rf1.webshop.Webshop2.Repository.RendelesekRepository;
import hu.rf1.webshop.Webshop2.Repository.TermekekRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Controller
public class RendelesekController {

    @Autowired
    RendelesekRepository repository;

    @Autowired
    TermekekRepository termekekRepository;

    @RequestMapping("/orders")
    public String ordersPage(Model model){
        List<Rendelesek> order = repository.findAll();
        model.addAttribute("orderslist", order);
        return "rendelesek";
    }
    Long termek_id;
    @RequestMapping("/orders/new/{id}")
    public String newOrder(Model model, @PathVariable("id")long id){
        termek_id=id;
        Rendelesek order = new Rendelesek();
        Termekek termek = termekekRepository.findById(id).orElse(null);
        model.addAttribute("termek", termek);
        model.addAttribute("orders", order);
        return "rendeles";
    }


    @PostMapping("/orders/add")
    public String addOrder(Rendelesek order){
        Termekek termek = termekekRepository.findById(termek_id).orElse(null);
        order.setTermek_id((int)termek.getId());
        order.setOsszeg(termek.getPrice());
        order.setUser_id(1); //TODO Bejelentkezett felhasználó id!
        order.setDate(LocalDateTime.now());
        repository.save(order);
        return "redirect:/orders";
    }

    @PostMapping("/orders/update")
    public String updateOrder(Rendelesek order){
        repository.save(order);
        return "redirect:/orders";
    }

    @RequestMapping("/edit/orders/{id}")
    public ModelAndView editForm(@PathVariable("id")long id){
        ModelAndView mw = new ModelAndView("edit_order");
        Rendelesek order = repository.findById(id).orElse(null);
        mw.addObject("orders", order);
        return mw;
    }


    @RequestMapping("/delete/orders/{id}")
    public String deleteOrder(@PathVariable("id")long id){
        repository.deleteById(id);
        return "redirect:/orders";
    }

}
