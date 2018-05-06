package surveyape.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "surveys")
@Cacheable(false)
public class SurveyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int surveyid;
    private String surveyname;
    private int ownerid;
    @Column(length=50)
    private String surveytype;
    private String validity;
    @Column(columnDefinition="tinyint(1) default 0")
    private int ispublished;

//    @OneToMany(fetch=FetchType.EAGER)
    @OneToMany(mappedBy = "surveyid")
    private List<InviteesEntity> invitees;

    public SurveyEntity(String surveyname, int ownerid, String surveytype, String validity, int ispublished) {
        this.surveyname = surveyname;
        this.ownerid = ownerid;
        this.surveytype = surveytype;
        this.validity = validity;
        this.ispublished = ispublished;
    }

    public SurveyEntity(String surveyname, int ownerid, String surveytype, String validity,
                        int ispublished, List<InviteesEntity> invitees) {
        this.surveyname = surveyname;
        this.ownerid = ownerid;
        this.surveytype = surveytype;
        this.validity = validity;
        this.ispublished = ispublished;
        this.invitees = invitees;
    }

    public int getSurveyid() {
        return surveyid;
    }

    public void setSurveyid(int surveyid) {
        this.surveyid = surveyid;
    }

    public String getSurveyname() {
        return surveyname;
    }

    public void setSurveyname(String surveyname) {
        this.surveyname = surveyname;
    }

    public int getOwnerid() {
        return ownerid;
    }

    public void setOwnerid(int ownerid) {
        this.ownerid = ownerid;
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

    public List<InviteesEntity> getInvitees() {
        return invitees;
    }

    public void setInvitees(List<InviteesEntity> invitees) {
        this.invitees = invitees;
    }
}
