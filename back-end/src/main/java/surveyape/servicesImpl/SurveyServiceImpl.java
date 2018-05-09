package surveyape.servicesImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import surveyape.converters.Convertors;
import surveyape.entity.*;
import surveyape.models.Invitees;
import surveyape.models.Survey;
import surveyape.respositories.InviteeRepository;
import surveyape.respositories.SurveyRepository;
import surveyape.respositories.UserRepository;
import surveyape.respositories.UserSurveyRepository;
import surveyape.services.SurveyService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class SurveyServiceImpl implements SurveyService {

    @Autowired
    private SurveyRepository surveyRepository;

    @Autowired
    private UserSurveyRepository userSurveyRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private InviteeRepository inviteeRepository;


    public Survey createSurvey(Survey survey) {

        String sessionEmail = Convertors.fetchSessionEmail();
        UserEntity userEntity = userRepository.findByEmail(sessionEmail);
        SurveyEntity surveyEntity;
            surveyEntity = new SurveyEntity(survey.getSurveyname(), survey.getSurveytype(), survey.getValidity(), String.valueOf(java.time.LocalDate.now()) , survey.getIspublished(), userEntity);


        SurveyEntity s = surveyRepository.save(surveyEntity);
            if(survey.getSurveytype().equals("closed"))
            {
                for(Invitees invitees: survey.getInvitees())
                {
                    InviteesEntity inviteesEntity = new InviteesEntity(invitees.getEmail(), s);
                    inviteeRepository.save(inviteesEntity);
                }
            }

        Survey r = new Survey();
        r.setSurveyid(s.getSurveyid());
        r.setSurveyname(s.getSurveyname());
        r.setIspublished(s.getIspublished());
        r.setSurveytype(s.getSurveytype());

        return r;
    }

    public Survey publishSurvey(Survey survey) {

        String sessionEmail = Convertors.fetchSessionEmail();
        UserEntity userEntity = userRepository.findByEmail(sessionEmail);
        SurveyEntity surveyEntity = surveyRepository.findBySurveyid(survey.getSurveyid());
        surveyEntity.setIspublished(1);

        SurveyEntity s = surveyRepository.save(surveyEntity);

        Survey r = new Survey();
        r.setSurveyid(s.getSurveyid());
        r.setSurveyname(s.getSurveyname());
        r.setIspublished(s.getIspublished());
        r.setSurveytype(s.getSurveytype());

        return r;
    }

    @Override
    public String isInvitedOrHasCompleted(String email, String surveyid) {

        SurveyEntity fetchedSurveyEntity = surveyRepository.findBySurveyid(surveyid);

        if(fetchedSurveyEntity == null) return "SURVEY_NOT_FOUND";
        InviteesEntity foundInvitee = fetchedSurveyEntity
                                                    .getInvitees()  // Fetch Invitees
                                                    .stream()       // Convert to stream
                                                    .filter(inviteesEntity -> email.equals(inviteesEntity.getEmail())) // We want invited user only
                                                    .findAny()      // If 'findAny' then return found and the entity
                                                    .orElse(null);  // If not found, return null
        if(foundInvitee != null) {

            UserSurveyEntity fetchUserSurveyEntity = userSurveyRepository.findBySurveyidAndEmail(surveyid, email);

            if(fetchUserSurveyEntity.getHascompleted() != 0) {
                return "HAS_COMPLETED";
            } else {
                return "USER_CAN_TAKE_SURVEY";
            }

        } else {
            return "NOT_INVITED";
        }
    }

    /*public Survey createSurvey(Survey survey){
        List<InviteesEntity> inviteesEntity = new ArrayList<>();
//        List<Invitees> invitees = new ArrayList<>();

        for(Invitees invitee : survey.getInvitees()){
            InviteesEntity i = new InviteesEntity();
            i.setEmail(invitee.getEmail());
            inviteesEntity.add(i);
        }


        SurveyEntity surveyEntity = new SurveyEntity(survey.getSurveyname(), survey.getOwnerid(), survey.getSurveytype(),
                survey.getValidity(), survey.getIspublished());
        SurveyEntity newSurvey = surveyRepository.save(surveyEntity);

        for(Invitees invitee : survey.getInvitees()){
            InviteesEntity i = new InviteesEntity();
            i.setSurveyid(newSurvey.getSurveyid());
            i.setEmail(invitee.getEmail());
            inviteeRespository.save(i);
        }



        return getSurvey(newSurvey.getSurveyid());

//        return s;
    }*/

//    public Survey createSurvey(Survey survey){
//        SurveyEntity surveyEntity = new SurveyEntity(survey.getSurveyname(), 7, survey.getSurveyname(), survey.getValidity(),
//        0, null);
//        return Convertors.mapSurveyEntityToSurvey(surveyRepository.save(surveyEntity));
//    }

//    public Survey getSurvey(int surveyId){
//
//        List<Invitees> invitees = new ArrayList<>();
//        Survey s = new Survey();
//
//        SurveyEntity newSurvey = surveyRepository.findBySurveyid(surveyId);
//
//        s.setSurveyid(newSurvey.getSurveyid());
//        s.setSurveyname(newSurvey.getSurveyname());
//        s.setOwnerid(newSurvey.getOwnerid());
//        s.setIspublished(newSurvey.getIspublished());
//        s.setSurveytype(newSurvey.getSurveytype());
//        s.setValidity(newSurvey.getValidity());
//
//        for(InviteesEntity invitee : newSurvey.getInvitees()){
//            Invitees i = new Invitees();
//            i.setSurveyid(invitee.getSurveyid());
//            i.setEmail(invitee.getEmail());
//            invitees.add(i);
//        }
//        s.setInvitees(invitees);
//
//        return s;
//    }
}
