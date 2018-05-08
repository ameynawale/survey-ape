package surveyape.services;

import surveyape.models.Survey;

public interface SurveyService {

    Survey createSurvey(Survey survey);

    String isInvitedOrHasCompleted(String email, String surveyId);

    Boolean isPublished(Survey survey);

    Boolean isValid(Survey survey);
}
