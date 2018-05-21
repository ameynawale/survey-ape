package surveyape;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import surveyape.controllers.OptionController;
import surveyape.controllers.SurveyController;
import surveyape.controllers.UserController;
import surveyape.entity.OptionsEntity;
import surveyape.entity.QuestionsEntity;
import surveyape.models.Options;
import surveyape.models.Question;
import surveyape.models.Survey;
import surveyape.models.User;
import surveyape.respositories.OptionRepository;
import surveyape.respositories.SurveyRepository;
import surveyape.respositories.UserSurveyRepository;
import surveyape.services.MailService;
import surveyape.services.QuestionService;
import surveyape.services.SurveyService;
import surveyape.services.UserService;

@ContextConfiguration
@RunWith(SpringRunner.class)
@WebMvcTest(value = { UserController.class, SurveyController.class, OptionController.class}, secure = false)
public class ControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private MailService mailService;

    @MockBean
    private SurveyService surveyService;

    @MockBean
    private SurveyRepository surveyRepository;

    @MockBean
    private UserSurveyRepository userSurveyRepository;

    @MockBean
    private OptionRepository optionRepository;

    @MockBean
    private QuestionService questionService;

    // USER Tests

    public User getUser() {
        User mockUser = new User();
        mockUser.setFirstname("first");
        mockUser.setLastname("last");
        mockUser.setEmail("f@l.com");
        mockUser.setIsactivated(1);
        return mockUser;
    }
    @Test
    public void loginUserTest() throws Exception {

        Mockito.when(userService.fetchUniqueUser(Mockito.any(User.class))).thenReturn("NOT_AVAILABLE");
        Mockito.when(userService.login(Mockito.any(User.class))).thenReturn(getUser());

        String userJson = "{\"email\":\"f@l.com\",\"isactivated\":1}";
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/users/login")
                .accept(MediaType.APPLICATION_JSON)
                .content(userJson)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        String expected = "{\"firstname\":\"first\",\"lastname\":\"last\",\"email\":\"f@l.com\"}";
        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
    }

    public User getNewUser() {
        User mockUser = new User();
        mockUser.setFirstname("first100");
        mockUser.setLastname("last100");
        mockUser.setEmail("f@l");
        mockUser.setIsactivated(0);
        return mockUser;
    }
    @Test
    public void registerUserTest() throws Exception {

        Mockito.when(userService.getUser(Mockito.anyString())).thenReturn(null);
        Mockito.when(userService.addUser(Mockito.any(User.class))).thenReturn(getNewUser());

        String userJson = "{\"firstname\":\"first100\",\"lastname\":\"last100\",\"email\":\"f@l\",\"password\":\"123123\"}";
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/users/signup")
                .accept(MediaType.APPLICATION_JSON)
                .content(userJson)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();



        String expected = "{\"firstname\":\"first100\",\"lastname\":\"last100\",\"isactivated\":0}";
        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
    }


    // Survey TESTS

    public Survey getNewSurvey() {
        Survey r = new Survey();
        r.setSurveyid("abcdabcd");
        r.setSurveyname("Test Survey");
        r.setIspublished(0);
        r.setSurveytype("general");

        return r;
    }

    @Test
    public void createSurvey() throws Exception {

        Mockito.when(surveyService.createSurvey(Mockito.any(Survey.class))).thenReturn(getNewSurvey());

        String surveyJson = "{\"surveyname\":\"abcdabcd\",\"surveytype\":\"general\",\"validity\":\"2018-10-10\",\"ispublished\":0}";
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/survey/create")
                .sessionAttr("email", "f@l.com")
                .accept(MediaType.APPLICATION_JSON)
                .content(surveyJson)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        String expected = "{\"surveyname\":\"Test Survey\"}";
        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
    }

    // Options Test

    public OptionsEntity getNewOption() {
        OptionsEntity optionsEntity = new OptionsEntity();
        optionsEntity.setOptionid(1l);
        optionsEntity.setOptions("What is Spring Framework");

        QuestionsEntity questionsEntity = new QuestionsEntity();
        questionsEntity.setQuestionid(1l);

        optionsEntity.setQuestionsEntity(questionsEntity);
        return optionsEntity;
    };


    @Test
    public void createOption() throws Exception {

        Mockito.when(optionRepository.save(Mockito.any(OptionsEntity.class))).thenReturn(getNewOption());
        Mockito.when(userService.login(Mockito.any(User.class))).thenReturn(getUser());

        String optionsJson = "{\"options\":\"What is Spring Framework\",\"questionid\":1}";
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/option/create")
                .accept(MediaType.APPLICATION_JSON)
                .content(optionsJson)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        String expected = "{\"optionid\":1,\"options\":\"What is Spring Framework\",\"questionid\":1}";
        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
    }


    @Test
    public void validateSurveys() throws Exception {

        Mockito.when(surveyService.isPublished(Mockito.any(Survey.class))).thenReturn(true);
        Mockito.when(surveyService.isValid(Mockito.any(Survey.class))).thenReturn(false);

        String surveyJson = "{\"surveyname\":\"abcdabcd\",\"surveytype\":\"general\",\"validity\":\"2018-10-10\",\"ispublished\":0}";
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/survey/validateSurvey")
                .accept(MediaType.APPLICATION_JSON)
                .content(surveyJson)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        System.out.println("------>" + result.getResponse().getContentAsString());
        String expected = "400";
        JSONAssert.assertEquals(expected, Integer.toString(result.getResponse().getStatus()), false);
    }
}

