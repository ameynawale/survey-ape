package surveyape.models;

import com.fasterxml.jackson.annotation.JsonInclude;

public class StatsChoices {

    private Long choiceDistribution;
    private String option;

    public Long getChoiceDistribution() { return choiceDistribution; }
    @JsonInclude(JsonInclude.Include.NON_EMPTY) public void setChoiceDistribution(Long choiceDistribution) { this.choiceDistribution = choiceDistribution; }

    public String getOption () { return option; }
    @JsonInclude(JsonInclude.Include.NON_EMPTY) public void setOption (String option) { this.option = option; }
}
