package surveyape.entity;

import javax.persistence.*;

/**
 * This InviteesEntity mapped to the invitees table in the database.
 * @author Suhas Hunsimar
 */
@Entity
@Table(name = "invitees")
@Cacheable(false)
public class InviteesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long 	inviteeid;
    private String 	email;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name = "surveyid")
    private SurveyEntity surveyEntity;

    public InviteesEntity() {}

    public InviteesEntity(String email) {
        this.email = email;
    }

    public Long getInviteeid() { return inviteeid; }
    public void setInviteeid(Long inviteeid) { this.inviteeid = inviteeid; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public SurveyEntity getSurveyEntity() { return surveyEntity; }
    public void setSurveyEntity(SurveyEntity surveyEntity) { this.surveyEntity = surveyEntity; }
}
