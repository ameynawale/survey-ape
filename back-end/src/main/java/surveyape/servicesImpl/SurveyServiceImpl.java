package surveyape.servicesImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import surveyape.converters.Convertors;
import surveyape.entity.*;
import surveyape.models.Invitees;
import surveyape.models.Survey;
import surveyape.models.SurveyListing;
import surveyape.models.User;
import surveyape.respositories.InviteeRepository;
import surveyape.respositories.SurveyRepository;
import surveyape.respositories.UserRepository;
import surveyape.respositories.UserSurveyRepository;
import surveyape.services.SurveyService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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
            surveyEntity = new SurveyEntity(survey.getSurveyname(), survey.getSurveytype(), survey.getValidity(), String.valueOf(java.time.LocalDate.now()) , survey.getIspublished(), survey.getIsclosed(), userEntity);

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

    public Survey closeSurvey(Survey survey) {

        String sessionEmail = Convertors.fetchSessionEmail();
        UserEntity userEntity = userRepository.findByEmail(sessionEmail);
        SurveyEntity surveyEntity = surveyRepository.findBySurveyid(survey.getSurveyid());
        surveyEntity.setIsclosed(1);

        SurveyEntity s = surveyRepository.save(surveyEntity);

        Survey r = new Survey();
        r.setSurveyid(s.getSurveyid());
        r.setSurveyname(s.getSurveyname());
        r.setIspublished(s.getIspublished());
        r.setSurveytype(s.getSurveytype());

        return r;
    }


    public SurveyListing getSurveyListing(User user)
    {
        Set<SurveyEntity> surveyEntitiesCreatedByMe = new HashSet<SurveyEntity>();
        Set<SurveyEntity> surveyEntitiesSharedWithMe = new HashSet<SurveyEntity>();

        UserEntity userEntity = new UserEntity();
        userEntity.setUserid(user.getUserid());
        surveyEntitiesCreatedByMe = surveyRepository.findAllByUserEntity(userEntity);

        Set<InviteesEntity> inviteesEntities = inviteeRepository.findAllByEmail(user.getEmail());

        for(InviteesEntity inviteesEntity: inviteesEntities)
        {
            surveyEntitiesSharedWithMe.add(surveyRepository.findBySurveyid(inviteesEntity.getSurveyEntity().getSurveyid()));
        }

        Set<Survey> surveysCreatedByMe = new HashSet<Survey>();
        Set<Survey> surveysSharedWithMe = new HashSet<Survey>();

        for(SurveyEntity surveyEntity: surveyEntitiesCreatedByMe)
        {
            surveysCreatedByMe.add(Convertors.mapSurveyEntityToSurvey(surveyEntity));
        }

        for(SurveyEntity surveyEntity: surveyEntitiesSharedWithMe)
        {
            surveysSharedWithMe.add(Convertors.mapSurveyEntityToSurvey(surveyEntity));
        }

        SurveyListing surveyListing = new SurveyListing(surveysCreatedByMe,surveysSharedWithMe);

        return surveyListing;
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
    public Boolean isPublished(Survey survey){
        String id =   survey.getSurveyid();
        SurveyEntity surveyEntity = surveyRepository.findBySurveyid(id) ;
        if(surveyEntity.getIspublished()==1)
        {
            return true;
        }
        else
            return false;
    }

    public Boolean isValid(Survey survey){
        String id =   survey.getSurveyid();
        System.out.println(id);
        SurveyEntity surveyEntity = surveyRepository.findBySurveyid(id);
        System.out.println(surveyEntity.getValidity());
        System.out.println(surveyEntity.getValidity());
        Calendar cal = Calendar.getInstance();
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            System.out.println(surveyEntity.getValidity());
            Date validTime = dateFormat.parse(surveyEntity.getValidity());
            Date currentTime = cal.getTime();
            /*cal1.setTime(validTime);
            cal2.setTime(currentTime);
            System.out.println(validTime);
            System.out.println(currentTime);*/
            System.out.println("date1 : " + dateFormat.format(validTime));
            System.out.println("date2 : " + dateFormat.format(currentTime));

            System.out.println(cal1.compareTo(cal2));
            System.out.println(cal2.before(cal1));
            if (currentTime.before(validTime)) {
                return true;
            }

        } catch (ParseException e) {              // Insert this block.
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
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
