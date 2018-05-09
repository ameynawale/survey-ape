package surveyape.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import surveyape.aspects.CheckSession;
import surveyape.converters.Convertors;
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

    private Map<String, String> jsonResponse = null;

    @CheckSession @RequestMapping(path="/create", method= RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createSurvey(@RequestBody Survey survey) {
        System.out.println("-----------");

        Survey newSurvey = surveyService.createSurvey(survey);

        return new ResponseEntity<>(newSurvey, HttpStatus.CREATED);
    }

    @CheckSession @RequestMapping(path="/publish", method= RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> publishSurvey(@RequestBody Survey survey) {
        System.out.println("-----------");

        Survey newSurvey = surveyService.publishSurvey(survey);

        return new ResponseEntity<>(newSurvey, HttpStatus.OK);
    }

    @RequestMapping(path="/validateEmail", method= RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> validateEmail(@RequestBody Survey survey) {

        String sessionEmail = Convertors.fetchSessionEmail();

//        survey.getInvitees().forEach(invitee -> System.out.println(invitee));
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
            jsonResponse.put("message", "User is not invited");
            return new ResponseEntity<>(jsonResponse, HttpStatus.UNAUTHORIZED);
        }
        else if(closedCheck.equalsIgnoreCase("HAS_COMPLETED")) {
            jsonResponse.put("message", "User has already completed");
            return new ResponseEntity<>(jsonResponse, HttpStatus.UNAUTHORIZED);
        }
        else if(closedCheck.equalsIgnoreCase("SURVEY_NOT_FOUND")) {
            jsonResponse.put("message", "Survey not found");
            return new ResponseEntity<>(jsonResponse, HttpStatus.NOT_FOUND);
        }
        else {
            jsonResponse.put("message", "User can take the survey!");
            return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
        }
    }
}
