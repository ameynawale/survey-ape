package surveyape.models;

import com.fasterxml.jackson.annotation.JsonInclude;

public class Option {
    private int optionid;
    private String options;
    private int questionid;

    public Option() {}

    public Option(int optionid, String options, int questionid) {
        this.optionid = optionid;
        this.options = options;
        this.questionid = questionid;
    }

    public int getOptionid() {
        return optionid;
    }
    @JsonInclude(JsonInclude.Include.NON_EMPTY) public void setOptionid(int optionid) {
        this.optionid = optionid;
    }

    public String getOptions() {
        return options;
    }
    @JsonInclude(JsonInclude.Include.NON_EMPTY) public void setOptions(String options) {
        this.options = options;
    }

    public int getQuestionid() {
        return questionid;
    }
    @JsonInclude(JsonInclude.Include.NON_EMPTY) public void setQuestionid(int questionid) { this.questionid = questionid; }
}
