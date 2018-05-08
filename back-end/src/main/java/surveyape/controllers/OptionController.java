package surveyape.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import surveyape.entity.OptionsEntity;
import surveyape.models.Option;
import surveyape.models.Question;
import surveyape.models.Survey;
import surveyape.respositories.OptionRepository;
import surveyape.services.QuestionService;
import surveyape.services.SurveyService;

/**
 * SurveyController a RestController class which manages the Surveys.
 *
 * @author Ameya Nawale
 *
 */
@RestController
@RequestMapping("/option")
@CrossOrigin(origins = "http://localhost:3000")
public class OptionController {

    @Autowired
    private OptionRepository optionRepository;

    @RequestMapping(path="/create", method= RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createOption(@RequestBody Option option) {
        System.out.println("-----------");

        System.out.println("option" + option.getOptions());
        System.out.println("option" + option.getOptionid());
        System.out.println("option" + option.getQuestionid());
        OptionsEntity optionsEntity = new OptionsEntity();
        optionsEntity.setOption(option.getOptions());
        optionsEntity.setOptionid(option.getOptionid());
        optionsEntity.setQuestionid(option.getQuestionid());
        OptionsEntity optionsEntity1 = optionRepository.save(optionsEntity);
        return new ResponseEntity<>(optionsEntity1, HttpStatus.CREATED);
    }
}
