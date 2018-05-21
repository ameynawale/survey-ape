package surveyape.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import surveyape.converters.Convertors;
import surveyape.entity.OptionsEntity;
import surveyape.entity.QuestionsEntity;
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
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = {"*"})
public class OptionController {

    @Autowired
    private OptionRepository optionRepository;

    @RequestMapping(path="/create", method= RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = {"*"})
    public ResponseEntity<?> createOption(@RequestBody Option option) {

        OptionsEntity optionsEntity = new OptionsEntity();
        optionsEntity.setOptions(option.getOptions());
        if(option.getOptionid() != null) {
            optionsEntity.setOptionid(Long.parseLong(String.valueOf(option.getOptionid())));
        }
        QuestionsEntity questionsEntity = new QuestionsEntity();
        questionsEntity.setQuestionid(Long.parseLong(String.valueOf(option.getQuestionid())));
        optionsEntity.setQuestionsEntity(questionsEntity);
        OptionsEntity optionsEntity1 = optionRepository.save(optionsEntity);

        return new ResponseEntity<>(Convertors.mapOptionEntityToOption(optionsEntity1), HttpStatus.CREATED);
    }
}
