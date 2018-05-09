package surveyape.servicesImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import surveyape.controllers.QRController;
import surveyape.converters.Convertors;
import surveyape.entity.*;
import surveyape.models.*;
import surveyape.respositories.InviteeRepository;
import surveyape.respositories.SurveyRepository;
import surveyape.respositories.UserRepository;
import surveyape.respositories.UserSurveyRepository;
import surveyape.services.MailService;
import surveyape.services.QRService;
import surveyape.services.SurveyService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static surveyape.controllers.QRController.QRCODE_ENDPOINT;
import static surveyape.controllers.QRController.THIRTY_MINUTES;

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
    @Autowired
    private QRService imageService;
    @Autowired
    private MailService mailService;


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
    @Scheduled(fixedRate = THIRTY_MINUTES)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = QRCODE_ENDPOINT)
    public void deleteAllCachedImages() {
        imageService.purgeCache();
    }

    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)

    public class InternalServerError extends RuntimeException {

        private static final long serialVersionUID = 1L;

        public InternalServerError(final String message, final Throwable cause) {
            super(message);
        }

    }
    public ResponseEntity<byte[]> getQRCode( String text) {
        try {
            System.out.println("Get QRcode");
            return ResponseEntity.ok().cacheControl(CacheControl.maxAge(5, TimeUnit.SECONDS))
                    .body(imageService.generateQRCodeAsync(text, 256, 256).get());
        } catch (Exception ex) {
            throw new InternalServerError("Error while generating QR code image.", ex);
        }

    }

    public Survey publishSurvey(Survey survey) {
        System.out.println(survey.getSurveytype());
        String sessionEmail = Convertors.fetchSessionEmail();
        UserEntity userEntity = userRepository.findByEmail(sessionEmail);
        SurveyEntity surveyEntity = surveyRepository.findBySurveyid(survey.getSurveyid());
        if(surveyEntity == null){
            Survey r = new Survey();
            return r;
        }
        surveyEntity.setIspublished(1);
        SurveyEntity s = surveyRepository.save(surveyEntity);
        System.out.println("session email"+sessionEmail);
        System.out.println("survey type/"+surveyEntity.getSurveytype()+"/");
        String URL = "http://localhost:8080/survey" +"/" +surveyEntity.getSurveytype()+ "?surveyid=" +survey.getSurveyid();
        System.out.println(URL);
        surveyEntity.setURL(URL);
        SurveyEntity Q= surveyRepository.save(surveyEntity);
        //if general

        if((surveyEntity.getSurveytype()).equalsIgnoreCase("general") ){

            ResponseEntity<byte[]> QRCode = this.getQRCode(URL);
            System.out.println(QRCode);
            mailService.sendpublishMailGeneral(URL,sessionEmail);
        }
        else if((surveyEntity.getSurveytype()).equalsIgnoreCase("closed")){
            System.out.println("closed type");
            Set<InviteesEntity> set = inviteeRepository.findBySurveyEntity(surveyEntity);
            for(InviteesEntity invitees: set)
            {
                String email = invitees.getEmail();
                System.out.println(email);
                mailService.sendpublishMailGeneral(URL,email);
            }

        }


        Survey r = new Survey();
        r.setSurveyid(Q.getSurveyid());
        r.setSurveyname(Q.getSurveyname());
        r.setIspublished(Q.getIspublished());
        r.setSurveytype(Q.getSurveytype());
        r.setURL(Q.getURL());
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

    public Questions getQuestions(Survey survey)
    {
        SurveyEntity surveyEntity = new SurveyEntity();
        surveyEntity = surveyRepository.findBySurveyid(survey.getSurveyid());
        Set<QuestionsEntity> questionsEntities = surveyEntity.getQuestions();
        Set<Question> questions = new HashSet<Question>();

        for(QuestionsEntity questionsEntity: questionsEntities)
        {
            questions.add(Convertors.mapQuestionsEntityToQuestion(questionsEntity));
        }

        return new Questions(questions);
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
            if(surveyEntity.getIspublished() == 1)
                surveysSharedWithMe.add(Convertors.mapSurveyEntityToSurvey(surveyEntity));
        }

        SurveyListing surveyListing = new SurveyListing(surveysCreatedByMe,surveysSharedWithMe);

        return surveyListing;
    }

    @Override
    public String isClosedSurveyInvitedOrCompleted(String email, String surveyid)  {

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

        if(surveyEntity == null) return false;

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

    @Override
    public Boolean isSurveyClosed(Survey survey) {
        return (surveyRepository.findBySurveyid(survey.getSurveyid()).getIsclosed() == 1);
    }

    @Override
    public Survey findSurvey(Survey survey) {
        SurveyEntity surveyEntity = surveyRepository.findBySurveyid(survey.getSurveyid());
        return surveyEntity != null ? Convertors.mapSurveyEntityToSurvey(surveyEntity) : null;
    }

    @Override
    public Map<String, Object> fetchSurveyQuestions(Survey survey) {
        return Convertors.mapSurveyEntityToSurveyQuestions(surveyRepository.findBySurveyid(survey.getSurveyid()));
    }

    @Override
    public Boolean isUniqueSurveyAlreadyCompleted(String email, String surveyid) {
        return (userSurveyRepository.findBySurveyidAndEmail(surveyid, email).getHascompleted() == 1);
    }

    @Override
    public Boolean finishClosedUniqueSurveys(Survey survey, String email) {
        UserSurveyEntity userSurveyEntity = userSurveyRepository.findBySurveyidAndEmail(survey.getSurveyid(), email);
        if(userSurveyEntity.getHascompleted() == 0) {
            userSurveyEntity.setHascompleted(1);
            userSurveyRepository.save(userSurveyEntity);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Map<String, Object> fetchStats(Survey survey) {

//
//        SurveyEntity surveyEntity = surveyRepository.findBySurveyid(survey.getSurveyid());
//
//        switch (surveyEntity.getSurveytype()) {
//
//            case "closed":
//
////                double total_price  =  projectEntity.getBids()
////                        .stream()
////                        .filter(bidsEntity -> bidsEntity.getBid_price() != null)
////                        .mapToDouble(BidsEntity::getBid_price)
////                        .sum();
//                long numberOfParticipants = surveyEntity.getInvitees()  // Fetch All Invitees
//                                                        .stream()       // Convert to stream
//                                                        .filter(inviteesEntity -> (userSurveyRepository.findBySurveyidAndEmail(surveyEntity.getSurveyid(),inviteesEntity.getEmail()).getHascompleted() == 1) )
//                                                        .count();       // Count the invitees
//
//
//                break;
//
//            case "open":
//
//                break;
//
//            case "unique":
//
//                break;
//            default:
//                throw new InternalServerException("Internal Server Error");
//        }
//
//
//
//        Map<String, Object> statsResponse = new HashMap<>();
//
//        statsResponse.put("startTime", surveyEntity.getCreatedon());
//        statsResponse.put("endTime", surveyEntity.getValidity());
//


        return null;
    }
}
