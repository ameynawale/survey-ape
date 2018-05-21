package surveyape.models;

import com.fasterxml.jackson.annotation.JsonInclude;

public class StatsChoices {

    private Long choiceDistribution;
    private Double choiceResponseRate;
    private String option;
    private String textResponses;

    public Long getChoiceDistribution() { return choiceDistribution; }
    @JsonInclude(JsonInclude.Include.NON_EMPTY) public void setChoiceDistribution(Long choiceDistribution) { this.choiceDistribution = choiceDistribution; }

    public Double getChoiceResponseRate() { return choiceResponseRate; }
    @JsonInclude(JsonInclude.Include.NON_EMPTY) public void setChoiceResponseRate(Double choiceResponseRate) { this.choiceResponseRate = choiceResponseRate; }

    public String getOption () { return option; }
    @JsonInclude(JsonInclude.Include.NON_EMPTY) public void setOption (String option) { this.option = option; }

    public String getTextResponses () { return textResponses; }
    @JsonInclude(JsonInclude.Include.NON_EMPTY) public void setTextResponses (String textResponses) { this.textResponses = textResponses; }
}
