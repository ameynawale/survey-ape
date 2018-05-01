package surveyape.entity;

import javax.persistence.*;

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
        private int 	questionid;
        private int     surveyid;
    @Column(length=1000)
        private String 	question;
    @Column(length=1000)
        private String  questiontype;

    public QuestionsEntity() {}

    public QuestionsEntity(int surveyid, String question, String questiontype) {
        this.surveyid = surveyid;
        this.question = question;
        this.questiontype = questiontype;
    }

    public int getQuestionid() { return questionid; }
    public void setQuestionid(int questionid) { this.questionid = questionid; }

    public int getSurveyid() { return surveyid; }
    public void setSurveyid(int surveyid) { this.surveyid = surveyid; }

    public String getQuestion() { return question; }
    public void setQuestion(String question) { this.question = question; }

    public String getQuestiontype() { return questiontype; }
    public void setQuestiontype(String questiontype) { this.questiontype = questiontype; }
}
