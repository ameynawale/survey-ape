package surveyape.services;

import surveyape.models.OpenSurveyResponse;
import surveyape.models.Response;

public interface ResponseService {

    Response saveSurveyResponse(Response response, String email);

    Response saveOpenSurveyResponse(OpenSurveyResponse openS);

    Response saveCheckboxResponses(Response res, String email);
}
