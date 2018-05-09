package surveyape.models;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Map;

public class Question {
    private int questionid;
    private String question;
    private String surveyid;
    private String questiontype;
    private Map<String, Object> options;

    public Question(){}

    public Question(int questionid, String question, String surveyid, String questiontype) {
        this.questionid = questionid;
        this.question = question;
        this.surveyid = surveyid;
        this.questiontype = questiontype;
    }

    public int getQuestionid() {
        return questionid;
    }
    @JsonInclude(JsonInclude.Include.NON_EMPTY) public void setQuestionid(int questionid) { this.questionid = questionid; }

    public String getQuestion() {
        return question;
    }
    @JsonInclude(JsonInclude.Include.NON_EMPTY) public void setQuestion(String question) {
        this.question = question;
    }

    public String getSurveyid() {
        return surveyid;
    }
    @JsonInclude(JsonInclude.Include.NON_EMPTY) public void setSurveyid(String surveyid) {
        this.surveyid = surveyid;
    }

    public String getQuestiontype() {
        return questiontype;
    }
    @JsonInclude(JsonInclude.Include.NON_EMPTY) public void setQuestiontype(String questiontype) {this.questiontype = questiontype; }

    public Map<String, Object> getOptions() { return options; }
    @JsonInclude(JsonInclude.Include.NON_EMPTY) public void setOptions(Map<String, Object> options) { this.options = options; }
}
