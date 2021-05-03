package marketing.entities;


import java.io.Serializable;
import javax.persistence.*;


@Entity
@Table(name = "questionnaire", schema = "marketingdb")

public class Questionnaire implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String answer1;

	private String answer2;
	
	private String answer3;
	
	// Bi-directional many-to-one association to User. Questionnaire is the owner
	// entity
	@ManyToOne
	@JoinColumn(name = "user")
	private User user;
	
	// Bi-directional many-to-one association to Product. Questionnaire is the owner
	// entity
	@ManyToOne
	@JoinColumn(name = "product")
	private Product product;

	public Questionnaire() {
	}

	public Questionnaire(String answer1, String answer2, String answer3, User user, Product product) {
		this.answer1 = answer1;
		this.answer2 = answer2;
		this.answer3 = answer3;
		this.user = user;
		this.product = product;
	}


	
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAnswer1() {
		return this.answer1;
	}

	public void setAnswer1(String answer1) {
		this.answer1 = answer1;
	}
	
	public String getAnswer2() {
		return this.answer2;
	}

	public void setAnswer2(String answer2) {
		this.answer2 = answer2;
	}
	
	public String getAnswer3() {
		return this.answer3;
	}

	public void setAnswer3(String answer3) {
		this.answer3 = answer3;
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

}