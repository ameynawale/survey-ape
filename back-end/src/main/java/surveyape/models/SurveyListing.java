package surveyape.models;

import java.util.Set;

public class SurveyListing {
    private Set<Survey> createdbyme;
    private Set<Survey> sharedwithme;

    public SurveyListing(Set<Survey> createdbyme, Set<Survey> sharedwithme) {
        this.createdbyme = createdbyme;
        this.sharedwithme = sharedwithme;
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
