package surveyape.converters;

import surveyape.entity.QuestionsEntity;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import surveyape.entity.SurveyEntity;
import surveyape.entity.UserEntity;
import surveyape.models.Invitees;
import surveyape.models.Question;
import surveyape.models.Survey;
import surveyape.models.User;

public class Convertors {

    public static User mapUserEntityToUser(UserEntity userEntity){
        User response = new User();
        response.setUserid(userEntity.getUserid());
        response.setFirstname(userEntity.getFirstname());
        response.setLastname(userEntity.getLastname());
        response.setEmail(userEntity.getEmail());
        response.setPassword(userEntity.getPassword());
        response.setIsactivated(userEntity.getIsactivated());
        response.setCode(userEntity.getCode());
        return response;
    }

    public static Survey mapSurveyEntityToSurvey(SurveyEntity surveyEntity){
        Survey survey = new Survey(surveyEntity.getSurveyid(),
                surveyEntity.getSurveyname(),
                surveyEntity.getSurveytype(),
                surveyEntity.getValidity(),
                surveyEntity.getIspublished(),
                null);

        return survey;
    }

    public static Question mapQuestionsEntityToQuestion(QuestionsEntity questionsEntity) {

        Question question = new Question(Integer.parseInt(String.valueOf(questionsEntity.getQuestionid())),
                questionsEntity.getQuestion(),
                Integer.parseInt(questionsEntity.getSurveyEntity().getSurveyid()),
                questionsEntity.getQuestiontype());
        return question;
    }

    public static String fetchSessionEmail() {
        return (String)((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession().getAttribute("email");
    }
}
