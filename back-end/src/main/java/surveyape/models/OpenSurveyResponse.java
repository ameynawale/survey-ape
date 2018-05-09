package surveyape.models;
import surveyape.models.OpenResponses;
public class OpenSurveyResponse
{
    private String sendemail;

    private OpenResponses[] openResponses;

    public String getSendEmail ()
    {
        return sendemail;
    }

    public void setSendEmail (String sendemail)
    {
        this.sendemail = sendemail;
    }

    public OpenResponses[] getOpenResponses ()
    {
        return openResponses;
    }

    public void setOpenresponses (OpenResponses[] openResponses)
    {
        this.openResponses = openResponses;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [email = "+sendemail+", openresponses = "+openResponses+"]";
    }
}
