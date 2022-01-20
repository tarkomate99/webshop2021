package hu.rf1.webshop.Webshop2.Controller;

import hu.rf1.webshop.Webshop2.DAO.RendelesekDAO;
import hu.rf1.webshop.Webshop2.DAO.TermekDAO;
import hu.rf1.webshop.Webshop2.DAO.UserDAO;
import hu.rf1.webshop.Webshop2.Model.Rendelesek;
import hu.rf1.webshop.Webshop2.Model.Termekek;
import hu.rf1.webshop.Webshop2.Model.User;
import hu.rf1.webshop.Webshop2.Repository.RendelesekRepository;
import hu.rf1.webshop.Webshop2.Repository.TermekekRepository;
import hu.rf1.webshop.Webshop2.Repository.UserRepository;
import hu.rf1.webshop.Webshop2.Service.EmailService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
public class RendelesekController {

    private final Log log = LogFactory.getLog(this.getClass());


    @Autowired
    RendelesekRepository repository;

    @Autowired
    TermekekRepository termekekRepository;

    @Autowired
    UserRepository userRepository;

    private EmailService emailService;

    private String user_email;

    private Long ID;

    private Integer USER_id;

    private List<Rendelesek> user_rendeles;

    private String user_typ;

    private String termek_name;

    @Autowired
    private RendelesekDAO rendelesekDAO;

    @Autowired
    private TermekDAO termekDAO;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    public void setEmailService(EmailService emailService){
        this.emailService = emailService;
    }

    @RequestMapping("/orders")
    public String ordersPage(Model model, HttpServletRequest request){
        List<Rendelesek> order = repository.findAll();
        model.addAttribute("orderslist", order);
        user_typ=request.getSession().getAttribute("user_type").toString();
        return "user_orders";
    }

    @RequestMapping("/myorders")
    public String myOrders(Model model, HttpServletRequest request){
        user_email=request.getSession().getAttribute("username").toString();
        User user = userRepository.findByEmail(user_email);
        Long ID = user.getId();
        Integer usr_id = ID.intValue();
        log.info(usr_id);
        model.addAttribute("orderslist", rendelesekDAO.getOrdersById(usr_id));
        return "user_orders";
    }

    Long termek_id;
    @RequestMapping("/orders/new/{id}")
    public String newOrder(Model model, @PathVariable("id")long id, HttpSession session, HttpServletRequest request){
        termek_id=id;
        Rendelesek order = new Rendelesek();
        Termekek termek = termekekRepository.findById(id).orElse(null);
        user_email=request.getSession().getAttribute("username").toString();
        User user = userDAO.listUser(user_email);
        model.addAttribute("termek", termek);
        model.addAttribute("orders", order);
        model.addAttribute("user", user);
        log.info(session.getAttribute("username"));
        String email = (String) session.getAttribute("username");
        termek_name=termek.getName();
        emailService.sendOrderMessage(email, termek.getName(), termek.getPrice().toString());
        return "rendeles";
    }


    @PostMapping("/orders/add")
    public String addOrder(@RequestParam("price_prod") Integer osszeg, @RequestParam("iranyito") Integer iranyito, @RequestParam("city") String city, @RequestParam("address") String address){
        Termekek termek = termekekRepository.findById(termek_id).orElse(null);
//        order.setTermek_id((int)termek.getId());
//        order.setOsszeg(termek.getPrice());
//        User user = userRepository.findByEmail(user_email);
//        log.info(user_email);
//        log.info("Price Prod "+ price_prod);
//        ID = user.getId();
//        USER_id = ID.intValue();
//        order.setUser_id(USER_id);
//        order.setDate(LocalDateTime.now());
        User user = userDAO.listUser(user_email);
        Long ID = user.getId();
        Integer id = ID.intValue();
        Integer prod_id = termek_id.intValue();
        Rendelesek rendeles = new Rendelesek(prod_id,osszeg,LocalDateTime.now(),id);
        if(termek.getNofitems()==0){
            return "sikertelen_megrendelés";
        }else{
            rendelesekDAO.insertOrder(rendeles);
            userDAO.updateUser(user_email,iranyito,city,address);
        }
        return "redirect:/myorders";
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
    public String deleteOrder(@PathVariable("id")long id, HttpServletRequest request){
        repository.deleteById(id);
        if (request.getSession().getAttribute("user_type")== "admin"){
            return "redirect:/orders";
        }else{
            return "redirect:/myorders";
        }

    }

}
