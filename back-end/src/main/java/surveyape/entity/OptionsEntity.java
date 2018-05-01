package surveyape.entity;

import javax.persistence.*;

/**
 * This OptionsEntity mapped to the options table in the database.
 * @author Suhas Hunsimar
 */
@Entity
@Table(name = "options")
@Cacheable(false)
public class OptionsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int 	optionid;
        private int     questionid;
    @Column(length=1000)
        private String 	options;

    public OptionsEntity() {}

    public OptionsEntity(int questionid, String options) {
        this.questionid = questionid;
        this.options = options;
    }

    public int getOptionid() { return optionid; }
    public void setOptionid(int optionid) { this.optionid = optionid; }

    public int getQuestionid() { return questionid; }
    public void setQuestionid(int questionid) { this.questionid = questionid; }

    public String getOption() { return options; }
    public void setOption(String options) { this.options = options; }
}
