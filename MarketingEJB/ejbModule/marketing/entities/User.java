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
@NamedQuery(name = "User.checkCredentials", query = "SELECT r FROM User r  WHERE r.username = ?1 and r.password = ?2")

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

	// Bidirectional many-to-one association to Mission
	/*
	 * Fetch type EAGER allows resorting the relationship list content also in the
	 * client Web servlet after the creation of a new mission. If you leave the
	 * default LAZY policy, the relationship is sorted only at the first access but
	 * then adding a new mission does not trigger the reloading of data from the
	 * database and thus the sort method in the client does not actually re-sort the
	 * list of missions. MERGE is not cascaded because we will modify and merge only
	 * username and surname attributes of the user and do not want to cascade
	 * detached changes to relationship.
	 */
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL)
	private List<Questionnaire> questionnaires;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL)
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
		// aligns both sides of the relationship
		// if mission is new, invoking persist() on reporter cascades also to mission
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
		// aligns both sides of the relationship
		// if mission is new, invoking persist() on reporter cascades also to mission
	}

	public void removeUserLogin(UserLogin userlogin) {
		getUserLogins().remove(userlogin);
	}

}