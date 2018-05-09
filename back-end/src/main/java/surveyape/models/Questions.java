package surveyape.models;

import java.util.Set;

public class Questions {
    private Set<Question> questions;

    public Questions(Set<Question> questions) {
        this.questions = questions;
    }

    public Set<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(Set<Question> questions) {
        this.questions = questions;
    }
}
