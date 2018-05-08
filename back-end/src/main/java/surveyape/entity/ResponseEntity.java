package surveyape.entity;

import javax.persistence.*;

@Entity
@Table(name = "responses")
@Cacheable(false)
public class ResponseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dummyid;
    private String email;

    @Column(length=1000)
    private String response;

    private Long optionid;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name = "userid")
    private UserEntity userEntity;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name = "questionid")
    private QuestionsEntity questionsEntity;

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getResponse() { return response; }
    public void setResponse(String response) { this.response = response; }

    public Long getOptionid() { return optionid; }
    public void setOptionid(Long optionid) { this.optionid = optionid; }

    public UserEntity getUserEntity() { return userEntity; }
    public void setUserEntity(UserEntity userEntity) { this.userEntity = userEntity; }

    public QuestionsEntity getQuestionsEntity() { return questionsEntity; }
    public void setQuestionsEntity(QuestionsEntity questionsEntity) { this.questionsEntity = questionsEntity; }
}

