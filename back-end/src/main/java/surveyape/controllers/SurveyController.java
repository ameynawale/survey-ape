package surveyape.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import surveyape.aspects.CheckSession;
import surveyape.converters.Convertors;
import surveyape.respositories.SurveyRepository;
import surveyape.entity.SurveyEntity;
import surveyape.models.Survey;
import surveyape.services.SurveyService;

import java.util.HashMap;
import java.util.Map;

/**
 * SurveyController a RestController class which manages the Surveys.
 *
 * @author Manali Jain
 *
 */
@RestController
@RequestMapping("/survey")
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = {"*"})
public class SurveyController {

    @Autowired
    private SurveyService surveyService;
    @Autowired
    private SurveyRepository surveyRepository;

    private Map<String, String> jsonResponse = null;

    @CheckSession @RequestMapping(path="/create", method= RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createSurvey(@RequestBody Survey survey) {
        System.out.println("-----------");

        Survey newSurvey = surveyService.createSurvey(survey);

        return new ResponseEntity<>(newSurvey, HttpStatus.CREATED);
    }

    @RequestMapping(path="/validateEmail", method= RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> validateEmail(@RequestBody Survey survey) {

        String sessionEmail = Convertors.fetchSessionEmail();

        if (sessionEmail != null) {
            return respondClosedCheck(surveyService.isInvitedOrHasCompleted(sessionEmail, survey.getSurveyid()));
        }
        else if (survey.getEmail() != null) {
            return respondClosedCheck(surveyService.isInvitedOrHasCompleted(survey.getEmail(), survey.getSurveyid()));
        }

        jsonResponse = new HashMap<>();
        jsonResponse.put("message", "Invalid details provided.");
        return new ResponseEntity<>(jsonResponse, HttpStatus.BAD_REQUEST);
    }


    private ResponseEntity<?> respondClosedCheck(String closedCheck){

        jsonResponse = new HashMap<>();

        if(closedCheck.equalsIgnoreCase("NOT_INVITED")) {
            jsonResponse.put("message", "You are not invited to take the survey. Please enter correct URL");
            return new ResponseEntity<>(jsonResponse, HttpStatus.UNAUTHORIZED);
        }
        else if(closedCheck.equalsIgnoreCase("HAS_COMPLETED")) {
            jsonResponse.put("message", "You have already completed the survey");
            return new ResponseEntity<>(jsonResponse, HttpStatus.ALREADY_REPORTED);
        }
        else if(closedCheck.equalsIgnoreCase("SURVEY_NOT_FOUND")) {
            jsonResponse.put("message", "Survey not found. Please enter correct URL");
            return new ResponseEntity<>(jsonResponse, HttpStatus.NOT_FOUND);
        }
        else {
            jsonResponse.put("message", "User can take the survey!");
            return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
        }
    }
    @RequestMapping(path="/validateSurvey", method= RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> generalSurvey(@RequestBody Survey survey) {
        System.out.println("-----------");
        if(surveyService.isPublished(survey))
        {
            if(surveyService.isValid(survey)){
                System.out.println("the survey is published and is valid");
                return new ResponseEntity<>("the survey is published and is valid", HttpStatus.OK);
            }
            else{
                System.out.println("the survey is not valid");
                return new ResponseEntity<>("the survey is not valid", HttpStatus.BAD_REQUEST);
            }
        }
        else{
            System.out.println("the survey is not published");
            return new ResponseEntity<>("the survey is not published", HttpStatus.BAD_REQUEST);
        }
    }

    /*@RequestMapping(path="/saveSurvey", method= RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> generalSurvey(@RequestBody Survey survey) {

    }*/
    @RequestMapping(path="/stat", method= RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> surveyStat(@RequestBody Survey survey) {
        System.out.println("-----------");
        System.out.println(survey.getSurveyid());
        // Survey newSurvey = surveyService.createSurvey(survey);
        SurveyEntity surveyEntity = surveyRepository.findBySurveyid(survey.getSurveyid());
        System.out.println(surveyEntity.getCreatedon() + " " + surveyEntity.getValidity() );

        return new ResponseEntity<>(survey.getSurveyid(), HttpStatus.OK);
    }
}
