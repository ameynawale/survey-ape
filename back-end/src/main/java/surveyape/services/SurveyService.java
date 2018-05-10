package surveyape.services;

import surveyape.models.*;
import java.util.Set;
import org.springframework.http.ResponseEntity;
import surveyape.models.Survey;
import surveyape.models.SurveyListing;
import surveyape.models.UniqueSurveyListing;
import surveyape.models.User;

import java.util.Map;

public interface SurveyService {

    Survey createSurvey(Survey survey);
    Survey publishSurvey(Survey survey);
    Survey closeSurvey(Survey survey);
    Questions getQuestions(Survey survey);
    SurveyListing getSurveyListing(User user);
    UniqueSurveyListing getUniqueSurveyListing(); // return the list of unique surveys
    public ResponseEntity<byte[]> getQRCode(String text);

    String isClosedSurveyInvitedOrCompleted(String email, String surveyId);

    Boolean isPublished(Survey survey);

    Boolean isValid(Survey survey);

    Boolean isSurveyClosed(Survey survey);

    Survey findSurvey(Survey survey);

    Map<String, Object> fetchSurveyQuestions(Survey survey);

    Boolean isUniqueSurveyAlreadyCompleted(String email, String surveyId);

    Map<String, Object> fetchStats(Survey survey);

    Boolean finishClosedUniqueSurveys(Survey survey, String email);
}
