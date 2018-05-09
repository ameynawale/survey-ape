package surveyape.models;

import java.util.Set;

public class UniqueSurveyListing {
    private Set<Survey> unique;
    public UniqueSurveyListing(Set<Survey> unique) {
        this.unique = unique;
    }

    public Set<Survey> getUnique() {
        return unique;
    }

    public void setUnique(Set<Survey> unique) {
        this.unique = unique;
    }




}
