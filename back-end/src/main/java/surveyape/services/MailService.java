package surveyape.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import surveyape.models.User;
import surveyape.entity.UserEntity;
/**
 * MailService provides the business logic for all the user operations
 *
 * @author Hanisha Thirtham
 *
 */
import surveyape.models.User;

import java.util.Random;

@Service
public class MailService{


    public JavaMailSender emailSender;

    private UserEntity userEntity;
    @Autowired
    public /*void*/ MailService(JavaMailSender javaMailSender) {
        this.emailSender = javaMailSender;
    }

    public String getCode(){
       /* double randomNumber = Math.random() * ((9999 - 1000) + 1) + 1000;
        String code = Double.toString(randomNumber);
        System.out.println(randomNumber);
        String[] codes;
       // if(code != null) {
             codes = code.split(".");
//        int randomCode = Integer.valueOf(codes[0]);

    //    }*/

      //  return codes[0];
        Random rand = new Random();
        String id = String.format("%04d", rand.nextInt(10000));
      //  System.out.println(id);
        return id;

    }
    public void sendSimpleMessage (User user) throws MailException{

        //  System.out.println();
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail());
        message.setSubject("Welcome to the Survey Ape : Sign up");
        message.setFrom("thirthamhanisha@gmail.com");
        message.setText("Hi " + user.getFirstname()+"," + " Welcome to the Survey Ape, here is your registration code: " +user.getCode());
       // System.out.println("Hi " + user.getFirstname()+"," + " Welcome to the Survey Ape, here is your registration code: " +user.getCode());
        emailSender.send(message);

    }
}