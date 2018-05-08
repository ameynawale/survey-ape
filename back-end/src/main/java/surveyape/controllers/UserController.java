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
import surveyape.exceptions.InternalServerException;
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
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = {"*"})
public class UserController {

    @Autowired
    private MailService mailService;
    @Autowired
    private UserService userService;

    private Map<String, String> jsonResponse = null;

    @RequestMapping(path="/login", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> login(@RequestBody User user, HttpSession httpSession) {

        jsonResponse = new HashMap<>();

        // User doesn't exist
        String uniqueInfo = userService.fetchUniqueUser(user);
        if(uniqueInfo.equalsIgnoreCase("AVAILABLE")) {
            jsonResponse.put("message", "The username doesn't have an account. Please create an account. ");
            return new ResponseEntity<>(jsonResponse, HttpStatus.NOT_FOUND);
        }

        User existingUser  = userService.login(user);

        if(existingUser != null) {
            httpSession.setAttribute("email", existingUser.getEmail());
            return new ResponseEntity<>(existingUser, HttpStatus.OK);
        } else {
            // Invalid credentials
            jsonResponse.put("message", "The username and password you entered did not match our records. Please double-check and try again.");
            return new ResponseEntity<>(jsonResponse, HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(path="/signup", method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> signUp(@RequestBody User user) {

        User checkUser = userService.getUser(user.getEmail());

        if (checkUser == null) {
            User newUser = userService.addUser(user);
            if (newUser != null) {
                // check if the user is added to the database  and them send a mail.
                try {
                    System.out.println("mail service before check point: " + newUser.getCode());
                    mailService.sendSimpleMessage(newUser);
                    System.out.println("mail service check point");
                } catch (MailException e) {
                    System.out.println("error: " + e.getMessage());
                }
                return new ResponseEntity<>(newUser, HttpStatus.OK);
            } else {
                throw new InternalServerException("Internal Server Error");
            }

        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @RequestMapping(path="/signUpVerification", method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> signUpVerification(@RequestBody User user) {

        User checkUser = userService.getUser(user.getEmail());

        if (checkUser.getEmail() != null) {

            if ((checkUser.getCode()).equalsIgnoreCase(user.getCode())) {

                userService.activateUser(user);

                return new ResponseEntity<>(checkUser, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } else {
            throw new InternalServerException("Internal Server Error");

        }
    }

    @CheckSession @RequestMapping(path="/logout", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> logoutUser(HttpSession httpSession) {

        Map<String, String> errorResponse = new HashMap<>();

        httpSession.invalidate();

        errorResponse.put("message", "Logout successful.");
        return new ResponseEntity<>(errorResponse, HttpStatus.OK);
    }

}