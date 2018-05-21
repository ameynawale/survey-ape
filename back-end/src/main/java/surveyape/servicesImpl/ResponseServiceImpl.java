package surveyape.servicesImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import surveyape.entity.QuestionsEntity;
import surveyape.entity.ResponseEntity;
import surveyape.entity.UserEntity;
import surveyape.models.OpenResponses;
import surveyape.models.OpenSurveyResponse;
import surveyape.models.Response;
import surveyape.respositories.QuestionRepository;
import surveyape.respositories.ResponseRepository;
import surveyape.respositories.UserRepository;
import surveyape.services.MailService;
import surveyape.services.ResponseService;

@Service
public class ResponseServiceImpl implements ResponseService {

    @Autowired
    private ResponseRepository responseRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MailService mailService;

    public Response saveSurveyResponse(Response response, String email){

        ResponseEntity responseEntity = new ResponseEntity();
        QuestionsEntity question = questionRepository.findById(Long.parseLong(response.getQuestionid()));
        ResponseEntity resObject = responseRepository.findByEmailAndAndQuestionsEntity(response.getEmail(),question);
        if(resObject == null){
            if(email != null){
                UserEntity user = userRepository.findByEmail(email);
                responseEntity.setUserEntity(user);
            } else{
                responseEntity.setUserEntity(null);
            }

            if(response.getOptionid() == null){
                responseEntity.setOptionid(null);
            } else{
                responseEntity.setOptionid(Long.parseLong(response.getOptionid()));
            }
            responseEntity.setEmail(response.getEmail());
            responseEntity.setResponse(response.getResponse());
            responseEntity.setQuestionsEntity(question);
            ResponseEntity res = responseRepository.save(responseEntity);
            Response r = new Response();
            r.setEmail(res.getEmail());
            r.setResponse(res.getResponse());
            return r;
        } else{
            if(response.getQuestionType().equals("unCheck")){

                //delete the object
                responseRepository.delete(resObject);
                return null;
//                resObject.setResponse(resObject.getResponse() + "," + response.getResponse());
            } else{
                if(email != null){
                    UserEntity user = userRepository.findByEmail(email);
                    resObject.setUserEntity(user);
                } else{
                    resObject.setUserEntity(null);
                }
                resObject.setDummyid(resObject.getDummyid());
                resObject.setResponse(response.getResponse());
                ResponseEntity res = responseRepository.save(responseEntity);
                Response r = new Response();
                r.setEmail(res.getEmail());
                r.setResponse(res.getResponse());
                return r;
            }
        }
    }

    public Response saveOpenSurveyResponse(OpenSurveyResponse openS){
        Response r = new Response();
        ResponseEntity res = null;
        for(OpenResponses open: openS.getOpenResponses()){
            ResponseEntity responseEntity = new ResponseEntity();

            if(open.getOptionid() == null){
                responseEntity.setOptionid(null);
            } else{
                responseEntity.setOptionid(Long.parseLong(open.getOptionid()));
            }

            responseEntity.setResponse(open.getResponse());

            QuestionsEntity question = questionRepository.findById(Long.parseLong(open.getQuestionid()));
            responseEntity.setQuestionsEntity(question);

            res = responseRepository.save(responseEntity);
        }
//        r.setEmail(res.getEmail());
        r.setType("General");
//        r.setResponse(res.getResponse());

        if(openS.getSendEmail() != null ){
            mailService.sendSuccessMailGeneral(openS.getSendEmail());
        }
        return r;
    }

    public Response saveCheckboxResponses(Response response, String email){
        ResponseEntity responseEntity = new ResponseEntity();
        QuestionsEntity question = questionRepository.findById(Long.parseLong(response.getQuestionid()));
        ResponseEntity resObject = responseRepository.findByEmailAndAndQuestionsEntityAndOptionid(
                response.getEmail(),question, Long.parseLong(response.getOptionid()));
        if(resObject == null){
            if(email != null){
                UserEntity user = userRepository.findByEmail(email);
                responseEntity.setUserEntity(user);
            } else{
                responseEntity.setUserEntity(null);
            }
            responseEntity.setEmail(response.getEmail());
            responseEntity.setOptionid(Long.parseLong(response.getOptionid()));
            responseEntity.setResponse(response.getResponse());
            responseEntity.setQuestionsEntity(question);
            ResponseEntity res = responseRepository.save(responseEntity);
            Response r = new Response();
            r.setEmail(res.getEmail());
            r.setResponse(res.getResponse());
            return r;
        } else{
            if(response.getQuestionType().equals("unCheck")){
                //delete the object
                responseRepository.delete(resObject);
                return null;
            } else{
                return null;
            }
        }
    }
}
