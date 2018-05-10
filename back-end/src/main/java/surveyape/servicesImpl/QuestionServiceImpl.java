package surveyape.servicesImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import surveyape.converters.Convertors;
import surveyape.entity.OptionsEntity;
import surveyape.entity.QuestionsEntity;
import surveyape.entity.SurveyEntity;
import surveyape.models.Option;
import surveyape.models.Options;
import surveyape.models.Question;
import surveyape.respositories.OptionRepository;
import surveyape.respositories.QuestionRepository;
import surveyape.services.QuestionService;

import java.util.HashSet;
import java.util.Set;

@Service
public class QuestionServiceImpl implements QuestionService{

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private OptionRepository optionRepository;

    public Question createQuestion(Question question)
    {
        QuestionsEntity questionsEntity = new QuestionsEntity();
        System.out.println("questionid" + question.getQuestionid());
        SurveyEntity surveyEntity = new SurveyEntity();
        surveyEntity.setSurveyid(String.valueOf(question.getSurveyid()));
        questionsEntity.setSurveyEntity(surveyEntity);
        questionsEntity.setQuestionid(Long.parseLong(String.valueOf(question.getQuestionid())));
        questionsEntity.setQuestion(question.getQuestion());
        questionsEntity.setQuestiontype(question.getQuestiontype());
        QuestionsEntity newquestionsEntity = questionRepository.save(questionsEntity);

//        System.out.println(newquestionsEntity.getQuestionid());

        return Convertors.mapQuestionsEntityToQuestion(newquestionsEntity);
    }

    public Options getOptions(Question question)
    {
        QuestionsEntity questionsEntity = questionRepository.findById(question.getQuestionid());
        Set<OptionsEntity> optionsEntities = questionsEntity.getOptions();

        Set<Option> options = new HashSet<>();
        for(OptionsEntity optionsEntity: optionsEntities)
        {
            options.add(Convertors.mapOptionEntityToOption(optionsEntity));
        }

        Options options1 = new Options(options);

        return options1;
    }
}
