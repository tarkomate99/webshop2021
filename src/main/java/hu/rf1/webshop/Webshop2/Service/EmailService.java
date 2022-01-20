package hu.rf1.webshop.Webshop2.Service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.internet.MimeMessage;

@Service
public class EmailService {

    private final Log log = LogFactory.getLog(this.getClass());

    @Value("${spring.mail.username}")
    private String MESSAGE_FROM;

    @Value("https://webshop2021.herokuapp.com")
    private String service_url;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private SpringTemplateEngine templateEngine;

    @Autowired
    public void setJavaMailSender(JavaMailSender javaMailSender){
        this.javaMailSender=javaMailSender;
    }

    public void sendMessage(String email, String key){
        MimeMessage message =javaMailSender.createMimeMessage();

        try{
            MimeMessageHelper helper = new MimeMessageHelper(message, false, "utf-8");
            String htmlMsg =
                    "<body>"
                    +"<h2>Kedves " + email + "!</h2>"
                    +"Koszonjuk, hogy regisztraltal a Webshopunkra!<br><br>"
                    +"Ahhoz, hogy be tudj jelentkezni elobb aktivalnod kell a fiokodat!<br>"
                    +"Amit az alabbi linkre rattintva tudsz megtenni!<br><br>"
                    +"<a href='"+service_url+"/activation/"+key+"'><button style='background-color: #4CAF50;border: none;color: white;padding: 15px 32px;text-align: center;text-decoration: none;display: inline-block;font-size: 16px;margin: 4px 2px;cursor: pointer;'>Fiok aktivalasa!</button></a>"
                    +"</body>";
            message.setContent(htmlMsg, "text/html");
            helper.setTo(email);
            helper.setFrom(MESSAGE_FROM);
            helper.setSubject("Sikeres regisztrálás!");
            javaMailSender.send(message);

        } catch (Exception e){
            log.error("Hiba az e-mail küldésekör az alábbi címre: " + email + " " + e);
        }
    }

    public void sendOrderMessage(String email, String termek, String price){
        SimpleMailMessage message = null;

        try{
            message = new SimpleMailMessage();
            message.setFrom(MESSAGE_FROM);
            message.setTo(email);
            message.setSubject("Sikeres megrendelés!");
            message.setText("Kedves " + email + "! \n \n Sikeresen megrendelted a következő terméket: " + termek + ",\n Ár: "+ price + " Ft!");
            javaMailSender.send(message);
        } catch (Exception e){
            log.error("Hiba az e-mail küldésekör az alábbi címre: " + email + " " + e);
        }
    }

    public void sendContactMail(String emailFrom, String msg){
        SimpleMailMessage message = null;

        try{
            message = new SimpleMailMessage();
            message.setFrom(emailFrom);
            message.setTo(MESSAGE_FROM);
            message.setSubject("Felhasználó email!");
            message.setText(msg);
            javaMailSender.send(message);
        } catch (Exception e){
            log.error("Hiba az e-mail küldésekör az alábbi címre: " + emailFrom + " " + e);
        }
    }
    public void sendContactMailReply(String email, String fname, String lname){
        SimpleMailMessage message = null;

        try{
            message = new SimpleMailMessage();
            message.setFrom(MESSAGE_FROM);
            message.setTo(email);
            message.setSubject("Kapcsolat felvétel!");
            message.setText("Kedves " + fname + " " + lname + "!" + " \n \n Megkaptuk a kapcsolat felvételi E-mailjét!" + "\n \n Munkatársaink feldolgozzák az üzenetet és " +
                    "minél előbb válaszolnak." + "\n Addig is türelmét kérjük." + "\n \n Üdvözlettel: Webshop Team.");
            javaMailSender.send(message);
        } catch (Exception e){
            log.error("Hiba az e-mail küldésekör az alábbi címre: " + email + " " + e);
        }
    }

}
