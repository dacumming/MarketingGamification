package marketing.entities;

import java.io.Serializable;
import javax.persistence.*;


import java.util.List;

/**
 * The persistent class for the usertable database table.
 * 
 */
@Entity
@Table(name = "usertable", schema = "marketingdb")
@NamedQueries({
	@NamedQuery(name = "User.checkCredentials", query = "SELECT r FROM User r  WHERE r.username = ?1 and r.password = ?2"),
	@NamedQuery(name = "User.checkUsernames", query = "SELECT r FROM User r WHERE r.username = ?1 or r.email = ?2"),
})


public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String username;

	private String password;

	private String email;

	private int points;
	
	private int isbanned;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
	private List<Questionnaire> questionnaires;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade=CascadeType.PERSIST)
	private List<UserLogin> userlogins;
	
	public User() {
		
	}
	
	public User(String nusername, String npwd, String nemail) {
		this.username = nusername;
		this.password = npwd;
		this.email = nemail;
		this.points = 0;
		this.isbanned = 0;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public int getPoints() {
		return this.points;
	}

	public void setPoints(int points) {
		this.points = points;
	}
	
	public int getIsbanned() {
		return this.isbanned;
	}

	public void setIsbanned(int isbanned) {
		this.isbanned = isbanned;
	}
	
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public List<Questionnaire> getQuestionnaires() {
		return this.questionnaires;
	}

	public void addQuestionnaire(Questionnaire questionnaire) {
		getQuestionnaires().add(questionnaire);
		questionnaire.setUser(this);
	}

	public void removeQuestionnaire(Questionnaire questionnaire) {
		getQuestionnaires().remove(questionnaire);
	}
	
	public List<UserLogin> getUserLogins() {
		return this.userlogins;
	}

	public void addUserLogin(UserLogin userlogin) {
		getUserLogins().add(userlogin);
		userlogin.setUser(this);
	}

	public void removeUserLogin(UserLogin userlogin) {
		getUserLogins().remove(userlogin);
	}

}