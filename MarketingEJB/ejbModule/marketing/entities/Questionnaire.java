package marketing.entities;


import java.io.Serializable;

import javax.persistence.*;


import java.util.Date;
import java.util.List;

@Entity
@Table(name = "questionnaire", schema = "marketingdb")

public class Questionnaire implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	
	@Temporal(TemporalType.DATE)
	private Date date;
	
	private int iscanceled;
	
	@ManyToOne
	@JoinColumn(name = "user")
	private User user;
	
	@ManyToOne
	@JoinColumn(name = "product")
	private Product product;
	
	@OneToOne(fetch = FetchType.EAGER, mappedBy = "questionnaire", cascade = CascadeType.ALL, orphanRemoval = true)
	private UserData userdata;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "questionnaire", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Answer> answers;

	public Questionnaire() {
	}

	public Questionnaire(User user, int iscanceled, Product product) {
		this.date = new Date();
		this.user = user;
		this.iscanceled = iscanceled;
		this.product = product;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	public int getIsCanceled() {
		return this.iscanceled;
	}

	public void setIsCanceled(int iscanceled) {
		this.iscanceled = iscanceled;
	}
	
	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public Product getProduct() {
		return this.product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
	
	public UserData getUserData() {
		return this.userdata;
	}

	public void setUserData(UserData userdata) {
		this.userdata = userdata;
	}
	
	public List<Answer> getAnswers() {
		return this.answers;
	}

	public void addAnswer(Answer answer) {
		getAnswers().add(answer);
		answer.setQuestionnaire(this);
		// aligns both sides of the relationship
		// if mission is new, invoking persist() on reporter cascades also to mission
	}

	public void removeAnswer(Answer answer) {
		getAnswers().remove(answer);
	}

}