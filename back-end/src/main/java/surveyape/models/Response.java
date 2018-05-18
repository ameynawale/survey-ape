package surveyape.models;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Response DTO. 
 * @author Manali Jain
 */

public class Response {

	private String type;
	private String email;
	private String response;
	private String questionid;
	private String optionid;
	private String questionType;

	public Response(){ };

	public Response(String type, String email, String response, String questionid, String optionid, String questionType) {
		this.type = type;
		this.email = email;
		this.response = response;
		this.questionid = questionid;
		this.optionid = optionid;
		this.questionType = questionType;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

	public String getQuestionid() {
		return questionid;
	}

	public void setQuestionid(String questionid) {
		this.questionid = questionid;
	}

	public String getOptionid() {
		return optionid;
	}

	public void setOptionid(String optionid) {
		this.optionid = optionid;
	}

	public String getQuestionType() {
		return questionType;
	}

	public void setQuestionType(String questionType) {
		this.questionType = questionType;
	}
}
