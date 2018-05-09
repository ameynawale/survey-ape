package surveyape.services;

import surveyape.models.*;

import java.util.Set;

public interface SurveyService {

    Survey createSurvey(Survey survey);
    Survey publishSurvey(Survey survey);
    Survey closeSurvey(Survey survey);
    Questions getQuestions(Survey survey);
    SurveyListing getSurveyListing(User user);

    String isInvitedOrHasCompleted(String email, String surveyId);

    Boolean isPublished(Survey survey);

    Boolean isValid(Survey survey);
}
