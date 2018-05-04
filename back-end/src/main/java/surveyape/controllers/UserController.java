package surveyape.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import surveyape.models.User;
import surveyape.services.UserService;

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
        System.out.println(user.getCode());
        System.out.println("-----------");
        boolean checkUser = userService.getUser(user.getEmail());
        if(!checkUser){
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
}