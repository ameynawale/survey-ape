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
    private Long optionid;
    @Column(length = 1000)
    private String options;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name = "questionid")
    private QuestionsEntity questionsEntity;

    public OptionsEntity() {}

    public OptionsEntity(String options) {
        this.options = options;
    }

    public Long getOptionid() { return optionid; }
    public void setOptionid(Long optionid) { this.optionid = optionid; }


    public String getOption() { return options; }
    public void setOption(String options) { this.options = options; }
}
