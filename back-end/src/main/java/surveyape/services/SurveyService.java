package surveyape.services;

import org.springframework.http.ResponseEntity;
import surveyape.models.Survey;
import surveyape.models.SurveyListing;
import surveyape.models.User;

import java.util.Map;

public interface SurveyService {

    Survey createSurvey(Survey survey);
    Survey publishSurvey(Survey survey);
    Survey closeSurvey(Survey survey);
    SurveyListing getSurveyListing(User user);
    public ResponseEntity<byte[]> getQRCode(String text);

    String isInvitedOrHasCompleted(String email, String surveyId);

    Boolean isPublished(Survey survey);

    Boolean isValid(Survey survey);

    Boolean isSurveyClosed(Survey survey);

    Survey findSurvey(Survey survey);

    Map<String, Object> fetchSurveyQuestions(Survey survey);
}
