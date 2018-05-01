package surveyape.entity;

import javax.persistence.*;

@Entity
@Table(name = "responses")
@Cacheable(false)
public class ResponseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int dummyid;
    private int userid;
    private int questionid;
    private String email;
    @Column(length=1000)
    private String response;

    public ResponseEntity(int userid, int questionid, String email, String response) {
        this.userid = userid;
        this.questionid = questionid;
        this.email = email;
        this.response = response;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public int getQuestionid() {
        return questionid;
    }

    public void setQuestionid(int questionid) {
        this.questionid = questionid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}

