package surveyape.models;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Set;

public class StatsQuestions {

    private Long questionid;
    private String questiontype;
    private String question;
    private Set<StatsChoices> choices;

    public Long getQuestionid() { return questionid; }
    @JsonInclude(JsonInclude.Include.NON_EMPTY) public void setQuestionid(Long questionid) { this.questionid = questionid; }

    public Set<StatsChoices> getChoices () { return choices; }
    public void setChoices (Set<StatsChoices> choices) { this.choices = choices; }

    public String getQuestiontype() { return questiontype; }
    @JsonInclude(JsonInclude.Include.NON_EMPTY) public void setQuestiontype(String questiontype) { this.questiontype = questiontype; }

    public String getQuestion() { return question; }
    @JsonInclude(JsonInclude.Include.NON_EMPTY) public void setQuestion(String question) { this.question = question; }
}
