package surveyape.models;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Response DTO. 
 * @author Suhas Hunsimar
 */
@XmlRootElement(name = "Response") 
@XmlType(propOrder = {"code", "msg"})
public class Response {
	
	private String code;
	private String msg;
	
	public Response() {};
	
	public Response(String code, String msg) {
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
