package surveyape.converters;

import surveyape.entity.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import surveyape.models.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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
                surveyEntity.getIsclosed(),
                null);

        return survey;
    }

    public static Question mapQuestionsEntityToQuestion(QuestionsEntity questionsEntity) {

        Question question = new Question(Integer.parseInt(String.valueOf(questionsEntity.getQuestionid())),
                questionsEntity.getQuestion(),
                questionsEntity.getSurveyEntity().getSurveyid(),
                questionsEntity.getQuestiontype());
        return question;
    }

    public static Option mapOptionEntityToOption(OptionsEntity optionsEntity)
    {
        Option option = new Option(Integer.parseInt(String.valueOf(optionsEntity.getOptionid())), optionsEntity.getOptions(), Integer.parseInt(String.valueOf(optionsEntity.getQuestionsEntity().getQuestionid())));
        return option;
    }

    /*public static Invitees mapInviteesEntityToInvitees(InviteesEntity inviteesEntity)
    {

    }*/

    public static String fetchSessionEmail() {
        return (String)((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession().getAttribute("email");
    }

    public static Map<String, Object> mapSurveyEntityToSurveyQuestions(SurveyEntity surveyEntity) {

        Set<Question> questionsList = new HashSet<>();

        surveyEntity.getQuestions()
                    .stream()
                    .forEach(questionsEntity -> {
                        Question question = new Question();

                        question.setQuestionid  ( questionsEntity.getQuestionid().intValue() );
                        question.setQuestiontype( questionsEntity.getQuestiontype() );
                        question.setQuestion    ( questionsEntity.getQuestion() );

                        Map<String, Object> options = new HashMap<>();
                        options.put("options", fetchOptions(questionsEntity));

                        question.setOptions(options);
                        questionsList.add(question);
                    });

        Map<String, Object> surveyQuestionsResponse = new HashMap<>();

        surveyQuestionsResponse.put("surveyid", surveyEntity.getSurveyid());
        surveyQuestionsResponse.put("surveyname", surveyEntity.getSurveyname());
        surveyQuestionsResponse.put("surveyquestions", questionsList);

        return surveyQuestionsResponse;
    }

    private static Set<Option> fetchOptions(QuestionsEntity questionsEntity) {

        Set<Option> optionsSet = new HashSet<>();

        questionsEntity.getOptions()
                       .stream()
                       .forEach(optionsEntity -> {
                           Option option = new Option();
                           option.setOptionid( optionsEntity.getOptionid().intValue() );
                           option.setOptions ( optionsEntity.getOptions() );
                           optionsSet.add(option);
                       });

        return  optionsSet;
    }

}
