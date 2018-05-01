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
        private int 	inviteeid;
        private int 	surveyid;
        private String 	email;

    public InviteesEntity() {}

    public InviteesEntity(int surveyid, String email) {
        this.surveyid = surveyid;
        this.email = email;
    }

    public int getInviteeid() { return inviteeid; }
    public void setInviteeid(int inviteeid) { this.inviteeid = inviteeid; }

    public int getSurveyid() { return surveyid; }
    public void setSurveyid(int surveyid) { this.surveyid = surveyid; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
