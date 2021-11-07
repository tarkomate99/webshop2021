package hu.rf1.webshop.Webshop2.Service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

public class EmailService {

    private final Log log = LogFactory.getLog(this.getClass());

    @Value("${spring.mail.username}")
    private String MESSAGE_FROM;

    private JavaMailSender javaMailSender;

    @Autowired
    public void setJavaMailSender(JavaMailSender javaMailSender){
        this.javaMailSender=javaMailSender;
    }

    public void sendMessage(String email){
        SimpleMailMessage message = null;

        try{
            message = new SimpleMailMessage();
            message.setFrom(MESSAGE_FROM);
            message.setTo(email);
            message.setSubject("Sikeres regisztrálás!");
            message.setText("Kedves" + email + "! \n \n Köszönjük, hogy regisztráltál a Webshopunkra!");
            javaMailSender.send(message);

        } catch (Exception e){
            log.error("Hiba az e-mail küldésekör az alábbi címre: " + email + " " + e);
        }
    }

}
