package surveyape.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import surveyape.aspects.CheckSession;
import surveyape.converters.Convertors;
import surveyape.entity.QuestionsEntity;
import surveyape.models.SurveyListing;
import surveyape.models.User;
import surveyape.respositories.ResponseRepository;
import surveyape.respositories.SurveyRepository;
import surveyape.respositories.QuestionRepository;
import surveyape.entity.SurveyEntity;
import surveyape.models.Survey;
import surveyape.models.Response;
import surveyape.services.SurveyService;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private ResponseRepository responseRepository;

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

    @CheckSession @RequestMapping(path="/close", method= RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> closeSurvey(@RequestBody Survey survey) {
        System.out.println("-----------");

        Survey newSurvey = surveyService.closeSurvey(survey);

        return new ResponseEntity<>(newSurvey, HttpStatus.OK);
    }

    @CheckSession @RequestMapping(path="/surveylisting", method= RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> surveyListing(@RequestBody User user) {
        System.out.println("-----------");

        System.out.println("inside survey listing controller");
        SurveyListing surveyListing = surveyService.getSurveyListing(user);

        return new ResponseEntity<>(surveyListing, HttpStatus.OK);
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
    public ResponseEntity<?> validateSurvey(@RequestBody Survey survey) {
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
        Map<Long, Integer> jsonResponse1;
        System.out.println("-----------");
        System.out.println(survey.getSurveyid());
        // Survey newSurvey = surveyService.createSurvey(survey);

        SurveyEntity surveyEntity = surveyRepository.findBySurveyid(survey.getSurveyid());
        System.out.println("created on "+surveyEntity.getCreatedon() + " " +"valid untill " + surveyEntity.getValidity());

        Set<QuestionsEntity> questionEntityForSurvey = questionRepository.findBySurveyEntity(surveyEntity);

        jsonResponse1 = new HashMap<Long, Integer>();

        for (QuestionsEntity questionsEntity : questionEntityForSurvey){
            int count= 0;
              Set<surveyape.entity.ResponseEntity> responseEntityForSurvey = responseRepository.findByQuestionsEntity(questionsEntity);
            for( surveyape.entity.ResponseEntity questionResponseEntityForSurvey: responseEntityForSurvey){
                questionResponseEntityForSurvey.getQuestionsEntity();
                count++;
            }

            jsonResponse1.put(questionsEntity.getQuestionid(), count);
        }
        Map.Entry<Long, Integer> maxEntry = null;

        for (Map.Entry<Long, Integer> entry : jsonResponse1.entrySet()) {

            if (maxEntry == null
                    || entry.getValue().compareTo(maxEntry.getValue()) > 0) {
                maxEntry = entry;
            }
        }
        System.out.println("No of participants: "+maxEntry.getValue());

        //no of submissions

        return new ResponseEntity<>(jsonResponse1, HttpStatus.OK);
    }

    @RequestMapping(path="/fetchQuestions", method= RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> fetchQuestions(@RequestBody Survey survey) {

        if(surveyService.findSurvey(survey) == null) {
            jsonResponse = new HashMap<>();
            jsonResponse.put("message", "Survey is not present!");
            return new ResponseEntity<>(jsonResponse, HttpStatus.NOT_FOUND);
        }

        if(!surveyService.isPublished(survey)) {
            jsonResponse = new HashMap<>();
            jsonResponse.put("message", "Survey is not published yet!");
            return new ResponseEntity<>(jsonResponse, HttpStatus.NOT_FOUND);
        }

        if(!surveyService.isValid(survey)) {
            jsonResponse = new HashMap<>();
            jsonResponse.put("message", "Survey is not valid anymore!");
            return new ResponseEntity<>(jsonResponse, HttpStatus.NOT_FOUND);
        }

        if(surveyService.isSurveyClosed(survey)) {
            jsonResponse = new HashMap<>();
            jsonResponse.put("message", "Survey is already closed!");
            return new ResponseEntity<>(jsonResponse, HttpStatus.NOT_FOUND);
        }

        Map<String, Object> surveyQuestions = surveyService.fetchSurveyQuestions(survey);
        return new ResponseEntity<>(surveyQuestions, HttpStatus.NOT_FOUND);
    }

}
