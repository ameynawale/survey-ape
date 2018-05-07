package surveyape.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import surveyape.models.Survey;
import surveyape.services.SurveyService;

/**
 * SurveyController a RestController class which manages the Surveys.
 *
 * @author Manali Jain
 *
 */
@RestController
@RequestMapping("/survey")
@CrossOrigin(origins = "http://localhost:3000")
public class SurveyController {

    @Autowired
    private SurveyService surveyService;

    @RequestMapping(path="/create", method= RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createSurvey(@RequestBody Survey survey) {
        System.out.println("-----------");

        Survey newSurvey = surveyService.createSurvey(survey);

        return new ResponseEntity<>(newSurvey, HttpStatus.OK);
    }
}
