package surveyape.servicesImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import surveyape.converters.Convertors;
import surveyape.entity.QuestionsEntity;
import surveyape.models.Question;
import surveyape.respositories.QuestionRepository;
import surveyape.services.QuestionService;

@Service
public class QuestionServiceImpl implements QuestionService{

    @Autowired
    private QuestionRepository questionRepository;

    public Question createQuestion(Question question)
    {
        QuestionsEntity questionsEntity = new QuestionsEntity();
        System.out.println("questionid" + question.getQuestionid());
        questionsEntity.setSurveyid(question.getSurveyid());
        questionsEntity.setQuestionid(question.getQuestionid());
        questionsEntity.setQuestion(question.getQuestion());
        questionsEntity.setQuestiontype(question.getQuestiontype());
        QuestionsEntity newquestionsEntity = questionRepository.save(questionsEntity);

//        System.out.println(newquestionsEntity.getQuestionid());

        return Convertors.mapQuestionsEntityToQuestion(newquestionsEntity);
    }
}
