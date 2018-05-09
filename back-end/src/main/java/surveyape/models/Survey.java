package surveyape.models;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;
import java.util.Set;

public class Survey {
    private String surveyid;
    private String surveyname;
    private int ownerid;
    private String surveytype;
    private String validity;
    private int ispublished;

    public Survey(String surveyid, String surveyname, int ownerid, String surveytype, String validity, int ispublished, int isclosed, String email, Set<Invitees> invitees, String URL) {
        this.surveyid = surveyid;
        this.surveyname = surveyname;
        this.ownerid = ownerid;
        this.surveytype = surveytype;
        this.validity = validity;
        this.ispublished = ispublished;
        this.isclosed = isclosed;
        this.email = email;
        this.invitees = invitees;
        this.URL = URL;
    }

    private int isclosed;
    private String email;
    private Set<Invitees> invitees;

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    private String URL;

    public Survey(){}

    public Survey(String surveyid, String surveyname, String surveytype, String validity, int ispublished, int isclosed, Set<Invitees> invitees) {
        this.surveyid = surveyid;
        this.surveyname = surveyname;
        this.surveytype = surveytype;
        this.validity = validity;
        this.ispublished = ispublished;
        this.isclosed = isclosed;
        this.invitees = invitees;
    }

    public int getIsclosed() {
        return isclosed;
    }

    public void setIsclosed(int isclosed) {
        this.isclosed = isclosed;
    }

    public String getSurveyid() {
        return surveyid;
    }
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public void setSurveyid(String surveyid) { this.surveyid = surveyid; }

    public String getSurveyname() {
        return surveyname;
    }
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public void setSurveyname(String surveyname) {
        this.surveyname = surveyname;
    }

    public int getOwnerid() {
        return ownerid;
    }
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public void setOwnerid(int ownerid) {
        this.ownerid = ownerid;
    }

    public String getSurveytype() {
        return surveytype;
    }
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public void setSurveytype(String surveytype) {
        this.surveytype = surveytype;
    }

    public String getValidity() {
        return validity;
    }
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public void setValidity(String validity) {
        this.validity = validity;
    }

    public int getIspublished() {
        return ispublished;
    }
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public void setIspublished(int ispublished) {
        this.ispublished = ispublished;
    }

    public Set<Invitees> getInvitees() {
        return invitees;
    }
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public void setInvitees(Set<Invitees> invitees) {
        this.invitees = invitees;
    }

    public String getEmail() { return email; }
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public void setEmail(String email) { this.email = email; }
}
