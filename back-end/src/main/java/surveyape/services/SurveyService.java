package surveyape.services;

import surveyape.models.Survey;

public interface SurveyService {

    Survey createSurvey(Survey survey);
    Survey publishSurvey(Survey survey);

    String isInvitedOrHasCompleted(String email, String surveyId);
}
