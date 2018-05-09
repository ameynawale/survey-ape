package surveyape.services;

import surveyape.models.Survey;
import surveyape.models.SurveyListing;
import surveyape.models.User;

public interface SurveyService {

    Survey createSurvey(Survey survey);
    Survey publishSurvey(Survey survey);
    Survey closeSurvey(Survey survey);
    SurveyListing getSurveyListing(User user);

    String isInvitedOrHasCompleted(String email, String surveyId);

    Boolean isPublished(Survey survey);

    Boolean isValid(Survey survey);
}
