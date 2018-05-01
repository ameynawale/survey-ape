package surveyape.models;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * BadRequest DTO. 
 * @author Suhas Hunsimar
 */
@XmlRootElement(name = "BadRequest") 
@XmlType(propOrder = {"code", "msg"})
@JsonTypeName("BadRequest")
@JsonTypeInfo(include= JsonTypeInfo.As.WRAPPER_OBJECT,use= JsonTypeInfo.Id.NAME)
public class BadRequest {
	
	private String code;
	private String msg;
	
	public BadRequest() {};
	
	public BadRequest(String code, String msg) {
		super();
		this.code = code;
		this.msg = msg;
	}
	public String getCode() {
		return code;
	}
	@XmlElement  
	public void setCode(String code) {
		this.code = code;
	}
	
	public String getMsg() {
		return msg;
	}
	@XmlElement  
	public void setMsg(String msg) {
		this.msg = msg;
	}
}
