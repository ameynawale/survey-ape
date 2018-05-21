package surveyape.controllers;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import surveyape.aspects.CheckSession;
import surveyape.converters.Convertors;
import surveyape.entity.SurveyEntity;
import surveyape.entity.UserSurveyEntity;
import surveyape.exceptions.HttpBadRequestException;
import surveyape.exceptions.HttpNotFoundException;
import surveyape.exceptions.HttpUnAuthorizedException;
import surveyape.models.*;
import surveyape.respositories.SurveyRepository;
import surveyape.respositories.UserSurveyRepository;
import surveyape.services.MailService;
import surveyape.services.SurveyService;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

/**
 * SurveyController a RestController class which manages the Surveys.
 *
 * @author Manali Jain
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
    private UserSurveyRepository userSurveyRepository;
    @Autowired
    private MailService mailService;

    private Map<String, String> jsonResponse = null;

    @CheckSession
    @RequestMapping(path = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createSurvey(@RequestBody Survey survey) {
        System.out.println("-----------");

        Survey newSurvey = surveyService.createSurvey(survey);

        return new ResponseEntity<>(newSurvey, HttpStatus.CREATED);
    }

    @CheckSession
    @RequestMapping(
            path = "/publish",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> publishSurvey(@RequestBody Survey survey) {
        System.out.println("-----------");

        System.out.println("publish method");


        Survey newSurvey = surveyService.publishSurvey(survey);

        return new ResponseEntity<>(newSurvey, HttpStatus.OK);
    }

    @CheckSession
    @RequestMapping(
            path = "/unpublish",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> unpublishSurvey(@RequestBody Survey survey) {
        System.out.println("-----------");

        System.out.println("unpublish");


        Object object = surveyService.unpublishSurvey(survey);

        if(object instanceof BadRequest)
            return new ResponseEntity<>(object, HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(object, HttpStatus.OK);
    }


    @CheckSession
    @RequestMapping(
            path = "/close",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> closeSurvey(@RequestBody Survey survey) {
        System.out.println("-----------");

        Survey newSurvey = surveyService.closeSurvey(survey);

        return new ResponseEntity<>(newSurvey, HttpStatus.OK);
    }

    @CheckSession
    @RequestMapping(
            path = "/getquestions",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> getQuestions(@RequestBody Survey survey) {
        System.out.println("-----------");

        Questions questions = surveyService.getQuestions(survey);

        return new ResponseEntity<>(questions, HttpStatus.OK);
    }

    @CheckSession
    @RequestMapping(
            path = "/surveylisting",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> surveyListing(@RequestBody User user) {
        System.out.println("-----------");

        System.out.println("inside survey listing controller");
        SurveyListing surveyListing = surveyService.getSurveyListing(user);

        return new ResponseEntity<>(surveyListing, HttpStatus.OK);
    }

    @RequestMapping(
            path = "/uniqueSurveylisting",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> uniqueSurveyListing() {
        System.out.println("-----------");

        System.out.println("inside unique survey listing controller");
        UniqueSurveyListing uniqueSurveyListing = surveyService.getUniqueSurveyListing();

        return new ResponseEntity<>(uniqueSurveyListing, HttpStatus.OK);
    }

    @RequestMapping(
            path = "/validateEmail",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> validateEmail(@RequestBody Survey requestSurvey) {

        Survey fetchedSurvey = surveyService.findSurvey(requestSurvey);

        if (fetchedSurvey == null)
            throw new HttpNotFoundException("Survey not found. Please enter correct URL");

        checkClosedUniqueSurveyIfCompletedOrClosed(fetchedSurvey, requestSurvey);

        jsonResponse = new HashMap<>();
        jsonResponse.put("message", "User can take the survey!");
        return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
    }

    @RequestMapping(
            path = "/validateUniqueEmail",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> validateUniqueEmail(@RequestBody Survey survey) {
        System.out.println("validateUniqueEmail");
        String email = survey.getEmail();
        System.out.println(email);
        System.out.println(survey.getSurveyid());
        SurveyEntity surveyEntity = surveyRepository.findBySurveyid(survey.getSurveyid());
        UserSurveyEntity userSurveyEntity =
                userSurveyRepository.findBySurveyidAndEmail(survey.getSurveyid(), email);
        //        survey.getInvitees().forEach(invitee -> System.out.println(invitee));
        jsonResponse = new HashMap<>();
        if (surveyEntity != null) {
            System.out.println("validateUniqueEmail");
            String URL = surveyEntity.getURL();
            System.out.println(URL);
            if (URL != null) {
                if (userSurveyEntity == null) {
                    System.out.println(URL);
                    //  if (userSurveyEntity.getHascompleted() == 0) {
                    mailService.sendUniqueMail(URL, email);
                    System.out.println("sendUniqueMail");
                    return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
                    //  } else {

                }
                System.out.println("You have already completed the survey");
                jsonResponse.put("message", "You have already completed the survey");
                return new ResponseEntity<>(jsonResponse, HttpStatus.ALREADY_REPORTED);
            } else {
                jsonResponse.put("message", "Survey not found. Please enter correct URL");
                return new ResponseEntity<>(jsonResponse, HttpStatus.NOT_FOUND);
            }
        }
        jsonResponse.put("message", "Invalid details provided.");
        return new ResponseEntity<>(jsonResponse, HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(
            path = "/validateSurvey",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> validateSurvey(@RequestBody Survey survey) {
        System.out.println("-----------");
        if (surveyService.isPublished(survey)) {
            if (surveyService.isValid(survey)) {
                System.out.println("the survey is published and is valid");
                return new ResponseEntity<>("the survey is published and is valid", HttpStatus.OK);
            } else {
                System.out.println("the survey is not valid");
                return new ResponseEntity<>("the survey is not valid", HttpStatus.BAD_REQUEST);
            }
        } else {
            System.out.println("the survey is not published");
            return new ResponseEntity<>("the survey is not published", HttpStatus.BAD_REQUEST);
        }
    }

    @CheckSession
    @RequestMapping(
            path = "/fetchStats",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> surveyStat(@RequestBody Survey survey) {

        if (surveyService.findSurvey(survey) == null)
            throw new HttpNotFoundException("Survey is not presents!");

        StatsOverall statsResponse = surveyService.fetchStats(survey);
        return new ResponseEntity<>(statsResponse, HttpStatus.OK);
    }

    @CheckSession
    @RequestMapping(
            path = "/export-survey",
            method = RequestMethod.GET
//            consumes = MediaType.APPLICATION_JSON_VALUE,
//            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> exportSurvey(@RequestParam(value = "surveyid") String surveyid,
                                                 @RequestParam(value = "filename") String filename)
    {

        JSONObject jsonObject = surveyService.exportSurvey(surveyid,"test");
        return new ResponseEntity<>(jsonObject, HttpStatus.OK);
//        System.out.println("surveyid " + surveyid);
//        System.out.println("filename " + filename);
//        surv
        /*String file = surveyService.exportSurvey(surveyid, filename);
        try{
            System.out.println("file in controller" + file);
            InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
            HttpHeaders headers = new HttpHeaders();
            headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
            headers.add("Pragma", "no-cache");
            headers.add("Expires", "0");
            headers.add("Content-Disposition", "attachment; filename=\"somefile.pdf\"");

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentLength(file.length())
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(resource);
        }
        catch (FileNotFoundException fnf)
        {
            return new ResponseEntity<>("the survey cannot be exported", HttpStatus.BAD_REQUEST);
        }*/
    }

    @RequestMapping(
            path = "/fetchQuestions",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> fetchQuestions(@RequestBody Survey requestSurvey) {

        System.out.println(requestSurvey.getSurveyid());
        System.out.println(requestSurvey.getEmail());

        Survey fetchedSurvey = surveyService.findSurvey(requestSurvey);

        checkPublishedValidClosed(fetchedSurvey);

        checkClosedUniqueSurveyIfCompletedOrClosed(fetchedSurvey, requestSurvey);

        Map<String, Object> surveyQuestions = surveyService.fetchSurveyQuestions(requestSurvey);
        return new ResponseEntity<>(surveyQuestions, HttpStatus.OK);
    }

    private void checkPublishedValidClosed(Survey fetchedSurvey) {

        if (surveyService.findSurvey(fetchedSurvey) == null)
            throw new HttpNotFoundException("Survey not found. Please enter correct URL");

        if (!surveyService.isPublished(fetchedSurvey))
            throw new HttpNotFoundException("Survey is not published yet!");

        if (!surveyService.isValid(fetchedSurvey))
            throw new HttpNotFoundException("Survey is not valid anymore!");

        if (surveyService.isSurveyClosed(fetchedSurvey))
            throw new HttpNotFoundException("Survey is already closed!");
    }

    private void checkClosedUniqueSurveyIfCompletedOrClosed(
            Survey fetchedSurvey, Survey requestSurvey) {

        if (fetchedSurvey.getSurveytype().equals("closed")) {
            String email =
                    Convertors.fetchSessionEmail() != null
                            ? Convertors.fetchSessionEmail()
                            : requestSurvey.getEmail();

            String check =
                    surveyService.isClosedSurveyInvitedOrCompleted(email, requestSurvey.getSurveyid());

            if(check.equals("HAS_COMPLETED")) throw new HttpBadRequestException("You have already completed the survey");
            if(check.equals("NOT_INVITED"))   throw new HttpUnAuthorizedException("You are not invited to take the survey. " +
                    "Please enter correct URL or check or maybe you are logged in the survey ape with other email id. Logout first and then try again ");
        }
        else if(fetchedSurvey.getSurveytype().equals("unique")) {

            String email =
                    Convertors.fetchSessionEmail() != null
                            ? Convertors.fetchSessionEmail()
                            : requestSurvey.getEmail();

            if (surveyService.isUniqueSurveyAlreadyCompleted(email, requestSurvey.getSurveyid()))
                throw new HttpBadRequestException("You have already completed the survey");
        }
    }

    @RequestMapping(
            path = "/finishClosedUniqueSurveys",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> finishClosedUniqueSurveys(@RequestBody Survey survey) {

        checkPublishedValidClosed(survey);

        String email =
                Convertors.fetchSessionEmail() != null ? Convertors.fetchSessionEmail() : survey.getEmail();

        if (surveyService.finishClosedUniqueSurveys(survey, email)) {
            jsonResponse = new HashMap<>();
            jsonResponse.put("message", "Survey submitted successfully.");
            return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
        } else {
            throw new HttpBadRequestException("You have already completed the survey.");
        }
    }
}
