package surveyape.servicesImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.io.FileWriter;
import java.io.IOException;

import surveyape.converters.Convertors;
import surveyape.entity.*;
import surveyape.exceptions.HttpBadRequestException;
import surveyape.exceptions.InternalServerException;
import surveyape.models.*;
import surveyape.respositories.*;
import surveyape.services.MailService;
import surveyape.services.QRService;
import surveyape.services.SurveyService;

import java.util.HashSet;
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

    @Autowired
    private ResponseRepository responseRepository;

    @Value("${aws.url}")
    String appURL;

    public Survey createSurvey(Survey survey) {

        String sessionEmail = Convertors.fetchSessionEmail();
        UserEntity userEntity = userRepository.findByEmail(sessionEmail);
        SurveyEntity surveyEntity;
        surveyEntity = new SurveyEntity(survey.getSurveyname(), survey.getSurveytype(), survey.getValidity(), String.valueOf(java.time.LocalDate.now()), survey.getIspublished(), survey.getIsclosed(), userEntity);

        SurveyEntity s = surveyRepository.save(surveyEntity);
        if (survey.getSurveytype().equals("closed")) {
            for (Invitees invitees : survey.getInvitees()) {
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

    public ResponseEntity<byte[]> getQRCode(String text) {
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
        if (surveyEntity == null) {
            Survey r = new Survey();
            return r;
        }
        surveyEntity.setIspublished(1);
        SurveyEntity s = surveyRepository.save(surveyEntity);
        System.out.println("session email" + sessionEmail);
        System.out.println("survey type/" + surveyEntity.getSurveytype() + "/");
        String URL = appURL + "/" + surveyEntity.getSurveytype() + "?surveyid=" + survey.getSurveyid();
        System.out.println(URL);
        surveyEntity.setURL(URL);
        SurveyEntity Q = surveyRepository.save(surveyEntity);
        //if general

        if ((surveyEntity.getSurveytype()).equalsIgnoreCase("general")) {

            ResponseEntity<byte[]> QRCode = this.getQRCode(URL);
            System.out.println(QRCode);
            mailService.sendpublishMailGeneral(URL, sessionEmail);
        } else if ((surveyEntity.getSurveytype()).equalsIgnoreCase("closed")) {
            System.out.println("closed type");
            Set<InviteesEntity> set = inviteeRepository.findBySurveyEntity(surveyEntity);
            for (InviteesEntity invitees : set) {
                String email = invitees.getEmail();
                System.out.println(email);
                mailService.sendpublishMailClosed(URL, email);
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

    /*public Survey unpublishSurvey(Survey survey) {
        System.out.println(survey.getSurveytype());
        String sessionEmail = Convertors.fetchSessionEmail();
        UserEntity userEntity = userRepository.findByEmail(sessionEmail);
        SurveyEntity surveyEntity = surveyRepository.findBySurveyid(survey.getSurveyid());
        if (surveyEntity == null) {
            Survey r = new Survey();
            return r;
        }
        surveyEntity.setIspublished(0);
        SurveyEntity s = surveyRepository.save(surveyEntity);
        System.out.println("session email" + sessionEmail);
        System.out.println("survey type/" + surveyEntity.getSurveytype() + "/");
        String URL = appURL + "/" + surveyEntity.getSurveytype() + "?surveyid=" + survey.getSurveyid();
        System.out.println(URL);
        surveyEntity.setURL(URL);
        SurveyEntity Q = surveyRepository.save(surveyEntity);
        //if general

        if ((surveyEntity.getSurveytype()).equalsIgnoreCase("general")) {

            ResponseEntity<byte[]> QRCode = this.getQRCode(URL);
            System.out.println(QRCode);
            mailService.sendpublishMailGeneral(URL, sessionEmail);
        } else if ((surveyEntity.getSurveytype()).equalsIgnoreCase("closed")) {
            System.out.println("closed type");
            Set<InviteesEntity> set = inviteeRepository.findBySurveyEntity(surveyEntity);
            for (InviteesEntity invitees : set) {
                String email = invitees.getEmail();
                System.out.println(email);
                mailService.sendpublishMailClosed(URL, email);
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
*/

    public Object unpublishSurvey(Survey survey) {
        System.out.println(survey.getSurveytype());
        String sessionEmail = Convertors.fetchSessionEmail();
        UserEntity userEntity = userRepository.findByEmail(sessionEmail);
        SurveyEntity surveyEntity = surveyRepository.findBySurveyid(survey.getSurveyid());
        if (surveyEntity == null) {
            Survey r = new Survey();
            return r;
        }

        /*Survey survey1 = new Survey();
        survey1.setSurveyid(survey.getSurveyid());
        StatsOverall stats = fetchStats(survey1);
        if(stats.getNoOfParticipants() > 0)
        {
            return new BadRequest("400","Survey cannot be unpublished as it has at least one response");
        }*/
        for(QuestionsEntity questionsEntity: surveyEntity.getQuestions())
        {
            if(responseRepository.findByQuestionsEntity(questionsEntity).size() > 0)
                return new BadRequest("400","Survey cannot be unpublished as it has at least one response");
        }
        surveyEntity.setIspublished(0);
        SurveyEntity s = surveyRepository.save(surveyEntity);

        Survey r = new Survey();
        r.setSurveyid(s.getSurveyid());
        r.setSurveyname(s.getSurveyname());
        r.setIspublished(s.getIspublished());
        r.setSurveytype(s.getSurveytype());
        r.setURL(s.getURL());
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

    public Questions getQuestions(Survey survey) {
        SurveyEntity surveyEntity = new SurveyEntity();
        surveyEntity = surveyRepository.findBySurveyid(survey.getSurveyid());
        Set<QuestionsEntity> questionsEntities = surveyEntity.getQuestions();
        Set<Question> questions = new HashSet<Question>();

        for (QuestionsEntity questionsEntity : questionsEntities) {
            questions.add(Convertors.mapQuestionsEntityToQuestion(questionsEntity));
        }

        return new Questions(questions);
    }

    public UniqueSurveyListing getUniqueSurveyListing() {
        Set<SurveyEntity> uniqueSurveyEntities = new HashSet<SurveyEntity>();

        uniqueSurveyEntities = surveyRepository.findAllBySurveytype("unique");


        Set<Survey> uniqueSurvey = new HashSet<Survey>();


        for (SurveyEntity uniqueSurveyEntity : uniqueSurveyEntities) {
            uniqueSurvey.add(Convertors.mapSurveyEntityToSurvey(uniqueSurveyEntity));
        }

        UniqueSurveyListing uniqueSurveyListing = new UniqueSurveyListing(uniqueSurvey);

        return uniqueSurveyListing;
    }

    public SurveyListing getSurveyListing(User user) {
        Set<SurveyEntity> surveyEntitiesCreatedByMe = new HashSet<SurveyEntity>();
        Set<SurveyEntity> surveyEntitiesSharedWithMe = new HashSet<SurveyEntity>();

        UserEntity userEntity = new UserEntity();
        userEntity.setUserid(user.getUserid());
        surveyEntitiesCreatedByMe = surveyRepository.findAllByUserEntity(userEntity);

        Set<InviteesEntity> inviteesEntities = inviteeRepository.findAllByEmail(user.getEmail());

        for (InviteesEntity inviteesEntity : inviteesEntities) {
            surveyEntitiesSharedWithMe.add(surveyRepository.findBySurveyid(inviteesEntity.getSurveyEntity().getSurveyid()));
        }

        Set<Survey> surveysCreatedByMe = new HashSet<Survey>();
        Set<Survey> surveysSharedWithMe = new HashSet<Survey>();

        for (SurveyEntity surveyEntity : surveyEntitiesCreatedByMe) {
            surveysCreatedByMe.add(Convertors.mapSurveyEntityToSurvey(surveyEntity));
        }

        for (SurveyEntity surveyEntity : surveyEntitiesSharedWithMe) {
            if (surveyEntity.getIspublished() == 1)
                surveysSharedWithMe.add(Convertors.mapSurveyEntityToSurvey(surveyEntity));
        }

        SurveyListing surveyListing = new SurveyListing(surveysCreatedByMe, surveysSharedWithMe);

        return surveyListing;
    }

    @Override
    public String isClosedSurveyInvitedOrCompleted(String email, String surveyid) {

        SurveyEntity fetchedSurveyEntity = surveyRepository.findBySurveyid(surveyid);

        if (fetchedSurveyEntity == null) return "SURVEY_NOT_FOUND";
        InviteesEntity foundInvitee = fetchedSurveyEntity
                .getInvitees()  // Fetch Invitees
                .stream()       // Convert to stream
                .filter(inviteesEntity -> email.equals(inviteesEntity.getEmail())) // We want invited user only
                .findAny()      // If 'findAny' then return found and the entity
                .orElse(null);  // If not found, return null
        if (foundInvitee != null) {
            System.out.println("--> " + surveyid);
            System.out.println("--> " + email);
            UserSurveyEntity fetchUserSurveyEntity = userSurveyRepository.findBySurveyidAndEmail(surveyid, email);

            if (fetchUserSurveyEntity == null) return "INVITED";

            if (fetchUserSurveyEntity.getHascompleted() != 0) {
                return "HAS_COMPLETED";
            } else {
                return "USER_CAN_TAKE_SURVEY";
            }

        } else {
            return "NOT_INVITED";
        }
    }

    public Boolean isPublished(Survey survey) {
        String id = survey.getSurveyid();
        SurveyEntity surveyEntity = surveyRepository.findBySurveyid(id);
        if (surveyEntity.getIspublished() == 1) {
            return true;
        } else
            return false;
    }

    public Boolean isValid(Survey survey) {
        String id = survey.getSurveyid();
        System.out.println(id);
        SurveyEntity surveyEntity = surveyRepository.findBySurveyid(id);

        if (surveyEntity == null) return false;

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
        System.out.println("---> " + survey.getSurveyid());
        SurveyEntity surveyEntity = surveyRepository.findBySurveyid(survey.getSurveyid());
        return surveyEntity != null ? Convertors.mapSurveyEntityToSurvey(surveyEntity) : null;
    }

    @Override
    public Map<String, Object> fetchSurveyQuestions(Survey survey) {
        String email = Convertors.fetchSessionEmail() != null ? Convertors.fetchSessionEmail() : survey.getEmail();
        return Convertors.mapSurveyEntityToSurveyQuestions(surveyRepository.findBySurveyid(survey.getSurveyid()), email);
    }

    @Override
    public Boolean isUniqueSurveyAlreadyCompleted(String email, String surveyid) {
        return (userSurveyRepository.findBySurveyidAndEmail(surveyid, email).getHascompleted() == 1);
    }

    @Override
    public Boolean finishClosedUniqueSurveys(Survey survey, String email) {
        UserSurveyEntity userSurveyEntity = userSurveyRepository.findBySurveyidAndEmail(survey.getSurveyid(), email);


        if (userSurveyEntity == null) {
            UserSurveyEntity userSurveyEntity1 = new UserSurveyEntity(email, survey.getSurveyid(), 1);
            userSurveyRepository.save(userSurveyEntity1);
            mailService.sendSuccessMailGeneral(email);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public StatsOverall fetchStats(Survey survey) {


        SurveyEntity surveyEntity = surveyRepository.findBySurveyid(survey.getSurveyid());
        StatsOverall statsOverall = new StatsOverall();
        Set<StatsQuestions> statsQuestionsSet = new HashSet<>();
        long numberOfParticipants = 0;
        double rate = 0d;

        switch (surveyEntity.getSurveytype()) {

            case "closed":

                numberOfParticipants = surveyEntity.getInvitees()  // Fetch All Invitees
                        .stream()  // Convert to stream
                        .filter(inviteesEntity -> (userSurveyRepository.findBySurveyidAndEmail(surveyEntity.getSurveyid(), inviteesEntity.getEmail()).getHascompleted() == 1))
                        .count();  // Filter has completed invitees and Count the invitees

                if (numberOfParticipants < 2)
                    throw new HttpBadRequestException("Survey has fewer than two participants");
                rate = (((double) numberOfParticipants / surveyEntity.getInvitees().size()) * 100);
                rate = (Math.round(rate * 100.0) / 100.0);

                for (QuestionsEntity questionsEntity : surveyEntity.getQuestions()) {

                    StatsQuestions statsQuestions = new StatsQuestions();
                    long choiceDistribution = 0;

                    statsQuestions.setQuestionid      ( questionsEntity.getQuestionid() );
                    statsQuestions.setQuestiontype  ( questionsEntity.getQuestiontype() );
                    statsQuestions.setQuestion        ( questionsEntity.getQuestion() );

                    Set<StatsChoices> statsChoicesSet = new HashSet<>();

                    for(OptionsEntity optionsEntity : questionsEntity.getOptions()) {
                        
                        StatsChoices statsChoices = new StatsChoices();
                        statsChoices.setOption  ( optionsEntity.getOptions() );
                        choiceDistribution = responseRepository.countByQuestionsEntityAndOptionid(questionsEntity, optionsEntity.getOptionid());

                        statsChoices.setChoiceDistribution( choiceDistribution );

                        statsChoicesSet.add(statsChoices);
                    }
                    statsQuestionsSet.add(statsQuestions);
                 }

                break;

            case "open":

                break;

            case "unique":

                break;
            default:
                throw new InternalServerException("Internal Server Error");
        }

        statsOverall.setStartTime(surveyEntity.getCreatedon());
        statsOverall.setEndTime(surveyEntity.getValidity());
        statsOverall.setNoOfParticipants(numberOfParticipants);
        statsOverall.setRate(rate);
        statsOverall.setQuestions(statsQuestionsSet);

        return statsOverall;
    }

    @Override
    public String exportSurvey(String surveyid, String filename) {

        System.out.println("surveyid " + surveyid);
        System.out.println("filename " + filename);

        SurveyEntity surveyEntity = surveyRepository.findBySurveyid(surveyid);

//        JSONObject obj = new JSONObject();

//        String email = Convertors.fetchSessionEmail() != null ? Convertors.fetchSessionEmail();
        Survey survey = new Survey();
        survey.setSurveyid(surveyid);

        Map<String, Object> obj = fetchSurveyQuestions(survey);
        /*obj.put("surveyname", surveyEntity.getSurveyname());
        obj.put("surveytype", surveyEntity.getSurveytype());
        obj.put("validity", surveyEntity.getValidity());*/

        JSONObject obj1 = new JSONObject();
        for(Map.Entry<String, Object> entry: obj.entrySet())
        {
            if(entry.getKey().equals("surveyquestions"))
            {
                Set<Question> questionsSet = (Set<Question>) entry.getValue();
                JSONArray questionsarray = new JSONArray();
                for(Question question: questionsSet)
                {
                    JSONObject questionjson = new JSONObject();
                    questionjson.put("question",question.getQuestion());
                    questionjson.put("questiontype",question.getQuestiontype());

                    JSONArray options = new JSONArray();
                    for(Option option: question.getOptions())
                    {
                        options.add(option.getOptions());
                    }
                    questionjson.put("options", options);

                    questionsarray.add(questionjson);
                }
                obj1.put(entry.getKey(),questionsarray);
            }
            else if(!entry.getKey().equals("surveyid")){
                obj1.put(entry.getKey(),entry.getValue());
            }
        }

        String SurveyFiles = System.getProperty("user.dir")+"\\src\\main\\resources\\SurveyFiles";
        String filepath = SurveyFiles + "\\" + surveyid + ".txt";
        try (FileWriter file = new FileWriter(filepath)) {

            file.write(obj1.toJSONString());
            file.flush();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            return filepath;
        }
    }
}
