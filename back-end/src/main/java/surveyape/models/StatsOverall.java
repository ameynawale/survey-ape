package surveyape.models;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Set;

public class StatsOverall {

    private String startTime;
    private double rate;
    private Set<StatsQuestions> questions;
    private long noOfParticipants;
    private String endTime;

    public String getStartTime () { return startTime; }
    @JsonInclude(JsonInclude.Include.NON_EMPTY) public void setStartTime (String startTime) { this.startTime = startTime; }

    public double getRate () { return rate; }
    @JsonInclude(JsonInclude.Include.NON_EMPTY) public void setRate (double rate) { this.rate = rate; }

    public Set<StatsQuestions> getQuestions () { return questions; }
    @JsonInclude(JsonInclude.Include.NON_EMPTY)     public void setQuestions (Set<StatsQuestions> questions) { this.questions = questions; }

    public long getNoOfParticipants () { return noOfParticipants; }
    @JsonInclude(JsonInclude.Include.NON_EMPTY)     public void setNoOfParticipants (long noOfParticipants) { this.noOfParticipants = noOfParticipants; }

    public String getEndTime () { return endTime; }
    @JsonInclude(JsonInclude.Include.NON_EMPTY)     public void setEndTime (String endTime) { this.endTime = endTime; }
}
