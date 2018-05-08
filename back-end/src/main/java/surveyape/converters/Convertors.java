package surveyape.converters;

import surveyape.entity.QuestionsEntity;
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

        return response;
    }

    public static Survey mapSurveyEntityToSurvey(SurveyEntity surveyEntity){
        Survey survey = new Survey(surveyEntity.getSurveyid(),
                surveyEntity.getSurveyname(),
                surveyEntity.getOwnerid(),
                surveyEntity.getSurveytype(),
                surveyEntity.getValidity(),
                surveyEntity.getIspublished(),
                null);

        return survey;
    }

    public static Question mapQuestionsEntityToQuestion(QuestionsEntity questionsEntity){

        Question question = new Question(questionsEntity.getQuestionid(),
                                        questionsEntity.getQuestion(),
                                        questionsEntity.getSurveyid(),
                                        questionsEntity.getQuestiontype());
        return question;
    }
}
