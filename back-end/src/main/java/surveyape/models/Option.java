package surveyape.models;

import com.fasterxml.jackson.annotation.JsonInclude;

public class Option {
    private Integer optionid;
    private String options;
    private Integer questionid;

    public Option() {}

    public Option(int optionid, String options, int questionid) {
        this.optionid = optionid;
        this.options = options;
        this.questionid = questionid;
    }

    public Integer getOptionid() {
        return optionid;
    }
    @JsonInclude(JsonInclude.Include.NON_EMPTY) public void setOptionid(Integer optionid) {
        this.optionid = optionid;
    }

    public String getOptions() {
        return options;
    }
    @JsonInclude(JsonInclude.Include.NON_EMPTY) public void setOptions(String options) {
        this.options = options;
    }

    public Integer getQuestionid() {
        return questionid;
    }
    @JsonInclude(JsonInclude.Include.NON_EMPTY) public void setQuestionid(Integer questionid) { this.questionid = questionid; }
}
