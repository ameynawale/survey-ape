package surveyape.models;
import java.util.List;

public class Question {
    private int questionid;
    private String question;
    private String surveyid;
    private String questiontype;

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

    public void setQuestionid(int questionid) {
        this.questionid = questionid;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getSurveyid() {
        return surveyid;
    }

    public void setSurveyid(String surveyid) {
        this.surveyid = surveyid;
    }

    public String getQuestiontype() {
        return questiontype;
    }

    public void setQuestiontype(String questiontype) {
        this.questiontype = questiontype;
    }
}
