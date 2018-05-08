package surveyape.entity;

import javax.persistence.*;

@Entity
@Table(name = "usersurvey")
@Cacheable(false)
public class UserSurveyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dummyid;
    private String email;
    private String surveyid;
    @Column(columnDefinition="tinyint(1) default 0")
    private int hascompleted;

    public UserSurveyEntity() {}

    public UserSurveyEntity(String email, String surveyid, int hascompleted) {
        this.email = email;
        this.surveyid = surveyid;
        this.hascompleted = hascompleted;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getSurveyid() {
        return surveyid;
    }
    public void setSurveyid(String surveyid) {
        this.surveyid = surveyid;
    }

    public int getHascompleted() {
        return hascompleted;
    }
    public void setHascompleted(int hascompleted) {
        this.hascompleted = hascompleted;
    }
}
