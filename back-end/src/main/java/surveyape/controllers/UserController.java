package surveyape.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import surveyape.aspects.CheckSession;
import surveyape.converters.Convertors;
import surveyape.entity.UserEntity;
import surveyape.models.User;
import surveyape.respositories.UserRepository;
import surveyape.services.MailService;
import surveyape.services.UserService;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * UserController a RestController class which manages the User's details.
 * 
 * @author Suhas Hunsimar
 *
 */
@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MailService mailService;
    @Autowired
    private UserService userService;


    @RequestMapping(path="/login", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> login(@RequestBody User user) {

        User registeredUser  = userService.login(user);
        if(registeredUser != null){
            return new ResponseEntity<>(registeredUser, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("not found",HttpStatus.NOT_FOUND);
        }
//        User registeredUser  = userService.login(user);
//        if(registeredUser != null){
//            if(registeredUser.getPassword().equals(user.getPassword())){
//                return new ResponseEntity<>(registeredUser, HttpStatus.OK);
//            } else {
//                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//            }
//        } else{
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//        return null;
    }

    @RequestMapping(path="/signup", method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> signUp(@RequestBody User user) {
        System.out.println("-----------");
        System.out.println(user);
        System.out.println(user.getEmail());
        System.out.println(user.getFirstname());
      //  System.out.println(mailService.getCode());
        System.out.println(user.getEmail());
        System.out.println("-----------");
        boolean checkUser = userService.getUser(user.getEmail());
        System.out.println(checkUser);
        if(!checkUser){
            System.out.println("check use is false");
            User newUser = userService.addUser(user);
            if(newUser != null){
                // check if the user is added to the database  and them send a mail.
                try{
                    System.out.println("mail service before check point" + newUser.getCode());
                    mailService.sendSimpleMessage(newUser);
                    System.out.println("mail service check point");
                }catch(MailException e){
                    System.out.println("error: " +e.getMessage());
                }
                return new ResponseEntity<>(newUser, HttpStatus.OK);
            } else{
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }

        } else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @RequestMapping(path="/signUpVerification", method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> signUpVerification(@RequestBody User user) {
        System.out.println("-----------");
        //System.out.println(code);
        System.out.println(user.getEmail());
        System.out.println(user.getFirstname());
        //  System.out.println(mailService.getCode());
        System.out.println(user.getEmail());
        System.out.println("-----------");
        boolean checkUser = userService.getUser(user.getCode());
        System.out.println(checkUser);
        if(!checkUser){
            System.out.println("check use is false");
           /* try{
                System.out.println("mail service before check point");
                mailService.sendSimpleMessage(user);
                System.out.println("mail service check point");
            }catch(MailException e){
                System.out.println("error: " +e.getMessage());
            }*/
            User newUser = userService.addUser(user);
            if(newUser != null){
                return new ResponseEntity<>(newUser, HttpStatus.OK);
            } else{
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @CheckSession @RequestMapping(path="/logout", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> logoutUser(HttpSession httpSession) {

        Map<String, String> errorResponse = new HashMap<>();

        httpSession.invalidate();

        errorResponse.put("message", "Logout successful.");
        return new ResponseEntity<>(errorResponse, HttpStatus.OK);
    }

}