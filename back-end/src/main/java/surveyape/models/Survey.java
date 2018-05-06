package surveyape.models;
import java.util.List;

public class Survey {
    private int surveyid;
    private String surveyname;
    private int ownerid;
    private String surveytype;
    private String validity;
    private int ispublished;
    private List<Invitees> invitees;

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

    public List<Invitees> getInvitees() {
        return invitees;
    }

    public void setInvitees(List<Invitees> invitees) {
        this.invitees = invitees;
    }
}
