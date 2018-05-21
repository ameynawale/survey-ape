package surveyape.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import surveyape.aspects.CheckSession;
import surveyape.models.Options;
import surveyape.models.Question;
import surveyape.models.Questions;
import surveyape.models.Survey;
import surveyape.services.QuestionService;
import surveyape.services.SurveyService;

/**
 * SurveyController a RestController class which manages the Surveys.
 *
 * @author Ameya Nawale
 *
 */
@RestController
@RequestMapping("/question")
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = {"*"})
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @RequestMapping(path="/create", method= RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = {"*"})
    public ResponseEntity<?> createQuestion(@RequestBody Question question) {
        System.out.println("-----------");

        Question newQuestion = questionService.createQuestion(question);
        return new ResponseEntity<>(newQuestion, HttpStatus.CREATED);
    }

    @CheckSession
    @RequestMapping(path="/getoptions", method= RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = {"*"})
    public ResponseEntity<?> getQuestions(@RequestBody Question question) {
        System.out.println("-----------");

        Options options = questionService.getOptions(question);

        return new ResponseEntity<>(options, HttpStatus.OK);
    }

}
