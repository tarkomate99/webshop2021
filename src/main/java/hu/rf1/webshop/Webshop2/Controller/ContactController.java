package hu.rf1.webshop.Webshop2.Controller;

import hu.rf1.webshop.Webshop2.Model.Ertekelesek;
import hu.rf1.webshop.Webshop2.Service.EmailService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ContactController {


    private final Log log = LogFactory.getLog(this.getClass());

    private EmailService emailService;

    @Autowired
    public void setEmailService(EmailService emailService){
        this.emailService = emailService;
    }

    @RequestMapping("/contact")
    public String contactPage(Model model){
        return "contact";
    }

    @RequestMapping("/contact/send")
    public String newContact(@RequestParam(value = "fname")String fname, @RequestParam(value = "lname")String lname,
                             @RequestParam(value = "email")String email, @RequestParam(value = "comment")String comment){
        emailService.sendContactMail(email,comment);
        emailService.sendContactMailReply(email, fname, lname);
        return "emailsent";
    }



}
