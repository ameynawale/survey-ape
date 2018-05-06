package surveyape.models;

public class Invitees {
    private int 	inviteeid;
    private int 	surveyid;
    private String 	email;

    public int getInviteeid() {
        return inviteeid;
    }

    public void setInviteeid(int inviteeid) {
        this.inviteeid = inviteeid;
    }

    public int getSurveyid() {
        return surveyid;
    }

    public void setSurveyid(int surveyid) {
        this.surveyid = surveyid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
