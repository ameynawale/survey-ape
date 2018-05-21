package surveyape.models;

import java.util.Set;

public class SurveyListing {
    private Set<Survey> createdbyme;
    private Set<Survey> sharedwithme;
    private Set<Survey> uniqueSurvey;

    public SurveyListing(Set<Survey> createdbyme, Set<Survey> sharedwithme, Set<Survey> uniqueSurvey) {
        this.createdbyme = createdbyme;
        this.sharedwithme = sharedwithme;
        this.uniqueSurvey = uniqueSurvey;
    }

    public Set<Survey> getUniqueSurvey() {
        return uniqueSurvey;
    }

    public void setUniqueSurvey(Set<Survey> uniqueSurvey) {
        this.uniqueSurvey = uniqueSurvey;
    }

    public Set<Survey> getCreatedbyme() {
        return createdbyme;
    }

    public void setCreatedbyme(Set<Survey> createdbyme) {
        this.createdbyme = createdbyme;
    }

    public Set<Survey> getSharedwithme() {
        return sharedwithme;
    }

    public void setSharedwithme(Set<Survey> sharedwithme) {
        this.sharedwithme = sharedwithme;
    }
}
