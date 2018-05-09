package surveyape.services;

import org.springframework.http.ResponseEntity;
import surveyape.models.Survey;
import surveyape.models.SurveyListing;
import surveyape.models.UniqueSurveyListing;
import surveyape.models.User;

public interface SurveyService {

    Survey createSurvey(Survey survey);
    Survey publishSurvey(Survey survey);
    Survey closeSurvey(Survey survey);
    SurveyListing getSurveyListing(User user);
    UniqueSurveyListing getUniqueSurveyListing(); // return the list of unique surveys
    public ResponseEntity<byte[]> getQRCode(String text);

    String isInvitedOrHasCompleted(String email, String surveyId);

    Boolean isPublished(Survey survey);

    Boolean isValid(Survey survey);
}
