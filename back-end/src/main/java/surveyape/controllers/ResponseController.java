package surveyape.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import surveyape.converters.Convertors;
import surveyape.models.Response;
import surveyape.services.ResponseService;

import java.util.HashMap;
import java.util.Map;


@RequestMapping("/response")
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = {"*"})
@RestController
public class ResponseController {

    @Autowired
    ResponseService responseService;

    private Map<String, String> jsonResponse = null;

    @RequestMapping(path="/saveSurveyResponse", method= RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> saveSurveyResponse(@RequestBody Response response) {

        Response res = null;
        String sessionEmail = Convertors.fetchSessionEmail();
//        String sessionUserId = Convertors.fetchSessionUserId();

        if (response.getType().equals("close") || response.getType().equals("unique")) {
            if(sessionEmail != null){
                res = responseService.saveSurveyResponse(response, sessionEmail);
                return new ResponseEntity<>(res,HttpStatus.OK);
            } else{
                res = responseService.saveSurveyResponse(response, null);
                return new ResponseEntity<>(res,HttpStatus.OK);
            }
        } else if(response.getType().equals("general")) {
                res = responseService.saveSurveyResponse(response, null);
                return new ResponseEntity<>(res,HttpStatus.OK);
        } else{
            jsonResponse = new HashMap<>();
            jsonResponse.put("message", "Invalid details provided.");
            return new ResponseEntity<>(jsonResponse, HttpStatus.BAD_REQUEST);
        }
    }
}
