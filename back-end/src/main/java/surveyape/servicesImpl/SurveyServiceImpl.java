package surveyape.servicesImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import surveyape.converters.Convertors;
import surveyape.entity.InviteesEntity;
import surveyape.entity.SurveyEntity;
import surveyape.entity.UserEntity;
import surveyape.models.Invitees;
import surveyape.models.Survey;
import surveyape.respositories.InviteeRepository;
import surveyape.respositories.SurveyRepository;
import surveyape.respositories.UserRepository;
import surveyape.services.SurveyService;

import java.util.ArrayList;
import java.util.List;

@Service
public class SurveyServiceImpl implements SurveyService {

    @Autowired
    private SurveyRepository surveyRepository;
    @Autowired
    private InviteeRepository inviteeRespository;

    @Autowired
    private UserRepository userRepository;


    public Survey createSurvey(Survey survey) {

        String sessionEmail = Convertors.fetchSessionEmail();
        UserEntity userEntity = userRepository.findByEmail(sessionEmail);

        SurveyEntity s = new SurveyEntity(survey.getSurveyname(), survey.getSurveytype(), survey.getValidity(), survey.getIspublished(), userEntity);

        s = surveyRepository.save(s);

        Survey r = new Survey();
        r.setSurveyid(s.getSurveyid());
        r.setSurveyname(s.getSurveyname());
        r.setIspublished(s.getIspublished());
        r.setSurveytype(s.getSurveytype());

        return r;
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
