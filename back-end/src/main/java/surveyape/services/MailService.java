package surveyape.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
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


    @Value("${survey.email.subject}")
    String emailSubject;

    @Value("${survey.email.from}")
    String emailFrom;

    public JavaMailSender emailSender;

    private UserEntity userEntity;
    @Autowired
    public /*void*/ MailService(JavaMailSender javaMailSender) {
        this.emailSender = javaMailSender;
    }
    @Autowired
    SurveyService surveyService;

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
        message.setSubject(emailSubject);
        message.setFrom(emailFrom);
        message.setText("Hi " + user.getFirstname()+"," + " Here is your registration code, please confirm your Email ID: " +user.getCode());
       // System.out.println("Hi " + user.getFirstname()+"," + " Welcome to the Survey Ape, here is your registration code: " +user.getCode());
        emailSender.send(message);

    }

    public void sendConfirmationMessage (User user) throws MailException{

        //  System.out.println();
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail());
        message.setSubject("Welcome to Survey");
        message.setFrom(emailFrom);
        message.setText("Hi " +"," + " Welcome to the Survey Ape. Thank you for registering, your account is verified");
        // System.out.println("Hi " + user.getFirstname()+"," + " Welcome to the Survey Ape, here is your registration code: " +user.getCode());
        emailSender.send(message);

    }

    public void sendpublishMailGeneral (String URL, String email) throws MailException{
        //  System.out.println();
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Welcome to Survey");
        message.setFrom(emailFrom);
        message.setText("Hi " +"," + " This is the URL for the survey<br/>" + URL +"<br/>"+ "This is the link to the QR Scan code for the survey <br/> " + "http://localhost:8080/QR/qrcode?text="+URL);
        // System.out.println("Hi " + user.getFirstname()+"," + " Welcome to the Survey Ape, here is your registration code: " +user.getCode());
        emailSender.send(message);

    }

    public void sendUniqueMail (String URL, String email) throws MailException{
        //  System.out.println();
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Welcome to Survey");
        message.setFrom(emailFrom);
        message.setText("Hi " +"," + " This is the URL for the survey you registered on SurveyApe<br/>" + URL +"<br/>");
        // System.out.println("Hi " + user.getFirstname()+"," + " Welcome to the Survey Ape, here is your registration code: " +user.getCode());
        emailSender.send(message);

    }
}