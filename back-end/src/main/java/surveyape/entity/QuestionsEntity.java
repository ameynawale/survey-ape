package surveyape.entity;

import javax.persistence.*;
import java.util.Set;

/**
 * This QuestionsEntity mapped to the questions table in the database.
 * @author Suhas Hunsimar
 */
@Entity
@Table(name = "questions")
@Cacheable(false)
public class QuestionsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long questionid;

    @Column(length = 1000)
    private String question;

    @Column(length = 1000)
    private String questiontype;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name = "surveyid")
    private SurveyEntity surveyEntity;

    @OneToMany(mappedBy = "questionsEntity", cascade=CascadeType.ALL)
    private Set<OptionsEntity> options;

    @OneToMany(mappedBy = "questionsEntity", cascade=CascadeType.ALL)
    private Set<ResponseEntity> responses;

    public QuestionsEntity() {}

    public Set<OptionsEntity> getOptions() {
        return options;
    }

    public void setOptions(Set<OptionsEntity> options) {
        this.options = options;
    }

    public Set<ResponseEntity> getResponses() {
        return responses;
    }

    public void setResponses(Set<ResponseEntity> responses) {
        this.responses = responses;
    }

    public QuestionsEntity(String question, String questiontype) {
        this.question = question;
        this.questiontype = questiontype;
    }

    public Long getQuestionid() { return questionid; }
    public void setQuestionid(Long questionid) { this.questionid = questionid; }

    public String getQuestion() { return question; }
    public void setQuestion(String question) { this.question = question; }

    public String getQuestiontype() { return questiontype; }
    public void setQuestiontype(String questiontype) { this.questiontype = questiontype; }

    public SurveyEntity getSurveyEntity() { return surveyEntity; }
    public void setSurveyEntity(SurveyEntity surveyEntity) { this.surveyEntity = surveyEntity; }
}
