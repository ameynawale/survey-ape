package surveyape.entity;

import javax.persistence.*;

/**
 * This UserEntity mapped to the users table in the database.
 * @author Suhas Hunsimar
 */
@Entity
@Table(name = "users")
@Cacheable(false)
public class UserEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
		private int 	userid;
	@Column(length=50)
		private String 	firstname;
	@Column(length=50)
		private String 	lastname;
	@Column(unique=true)
		private String 	email;
	@Column(length=100)
		private String 	password;
	@Column(columnDefinition="tinyint(1) default 0")
		private int 	isactivated;
		private String 	code;


	public UserEntity() {};

	public UserEntity(String firstname, String lastname, String email, String password, int isactivated, String code) {
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.password = password;
		this.isactivated = isactivated;
		this.code = code;
	}

	public int getUserid() { return userid; }
	public void setUserid(int userid) { this.userid = userid; }

	public String getFirstname() { return firstname; }
	public void setFirstname(String firstname) { this.firstname = firstname; }

	public String getLastname() { return lastname; }
	public void setLastname(String lastname) { this.lastname = lastname; }

	public String getEmail() { return email; }
	public void setEmail(String email) { this.email = email; }

	public String getPassword() { return password; }
	public void setPassword(String password) { this.password = password; }

	public int getIsactivated() { return isactivated; }
	public void setIsactivated(int isactivated) { this.isactivated = isactivated; }

	public String getCode() { return code; }
	public void setCode(String code) { this.code = code; }
}
