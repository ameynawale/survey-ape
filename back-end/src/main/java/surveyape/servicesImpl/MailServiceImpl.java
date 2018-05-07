/*
package surveyape.servicesImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import surveyape.services.MailService;
import surveyape.models.User;

@Service*/
/**//*

public class MailServiceImpl implements MailService {


    public JavaMailSender emailSender;

    @Autowired
    public */
/*void*//*
 MailService(JavaMailSender javaMailSender) {
        this.emailSender = javaMailSender;
    }

    public String getCode(){
        int randomNumber = (int)Math.random() * ((9999 - 1000) + 1) + 1000;
        String code = Integer.toString(randomNumber);
        return code;
    }
    public void sendSimpleMessage (User user) throws MailException{

      //  System.out.println();
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail());
        message.setSubject("Registration Code to Sign up");
        message.setFrom("thirthamhanisha@gmail.com");
        message.setText(this.getCode());
        emailSender.send(message);

    }
}*/
