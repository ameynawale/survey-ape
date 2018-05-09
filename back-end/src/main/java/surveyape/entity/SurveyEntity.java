package surveyape.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "surveys")
@Cacheable(false)
public class SurveyEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "surveyid", updatable = false, nullable = false)
    private String surveyid;

    private String surveyname;

    @Column(length=50)
    private String surveytype;
    private String validity;
    private String createdon;

    @Column(columnDefinition="tinyint(1) default 0")
    private int ispublished;

    @Column(columnDefinition="tinyint(1) default 0")
    private int isclosed;


    @OneToMany(mappedBy = "surveyEntity", cascade=CascadeType.ALL)
    private Set<InviteesEntity> invitees;

    @OneToMany(mappedBy = "surveyEntity", cascade=CascadeType.ALL)
    private Set<QuestionsEntity> questions;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name = "userid") // ownerid
    private UserEntity userEntity;

    public SurveyEntity(String surveyname, String surveytype, String validity, String createdon, int ispublished, Set<InviteesEntity> invitees, UserEntity userEntity) {
        this.surveyname = surveyname;
        this.surveytype = surveytype;
        this.validity = validity;
        this.createdon = createdon;
        this.ispublished = ispublished;
        this.invitees = invitees;
//        this.questions = questions;
        this.userEntity = userEntity;
    }

    public SurveyEntity(String surveyname, String surveytype, String validity, String createdon, int ispublished, int isclosed, UserEntity userEntity) {
        this.surveyname = surveyname;
        this.surveytype = surveytype;
        this.validity = validity;
        this.createdon = createdon;
        this.ispublished = ispublished;
        this.isclosed = isclosed;
        this.userEntity = userEntity;
    }

    public SurveyEntity() { }

    public SurveyEntity(String surveyname, String surveytype, String validity, int ispublished, UserEntity userEntity) {
        this.surveyname = surveyname;
        this.surveytype = surveytype;
        this.validity = validity;
        this.ispublished = ispublished;
        this.userEntity = userEntity;
    }

    public SurveyEntity(String surveyname, String surveytype, String validity,
                        int ispublished, Set<InviteesEntity> invitees) {
        this.surveyname = surveyname;
        this.surveytype = surveytype;
        this.validity = validity;
        this.ispublished = ispublished;
        this.invitees = invitees;
    }

    public int getIsclosed() {
        return isclosed;
    }

    public void setIsclosed(int isclosed) {
        this.isclosed = isclosed;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }


    public String getSurveyid() {
        return surveyid;
    }
    public void setSurveyid(String surveyid) {
        this.surveyid = surveyid;
    }

    public String getSurveyname() {
        return surveyname;
    }
    public void setSurveyname(String surveyname) {
        this.surveyname = surveyname;
    }

    public String getSurveytype() {
        return surveytype;
    }
    public void setSurveytype(String surveytype) {
        this.surveytype = surveytype;
    }

    public String getValidity() {
        return validity;
    }
    public void setValidity(String validity) {
        this.validity = validity;
    }

    public int getIspublished() {
        return ispublished;
    }
    public void setIspublished(int ispublished) {
        this.ispublished = ispublished;
    }

    public Set<InviteesEntity> getInvitees() {
        return invitees;
    }
    public void setInvitees(Set<InviteesEntity> invitees) {
        this.invitees = invitees;
    }

    public String getCreatedon() { return createdon; }
    public void setCreatedon(String createdon) { this.createdon = createdon; }

    public Set<QuestionsEntity> getQuestions() { return questions; }
    public void setQuestions(Set<QuestionsEntity> questions) { this.questions = questions; }
}
