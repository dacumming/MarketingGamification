package marketing.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "review", schema = "marketingdb")

public class Review implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Temporal(TemporalType.DATE)
	private Date date;

	private String review;


	// Bi-directional many-to-one association to User. Review is the owner
	// entity
	@ManyToOne
	@JoinColumn(name = "user")
	private User user;

	// Bi-directional many-to-one association to Question. Review is the owner
	// entity
	@ManyToOne
	@JoinColumn(name = "question")
	private Question question;
	
	// Bi-directional many-to-one association to Product. Review is the owner
	// entity
	@ManyToOne
	@JoinColumn(name = "product")
	private Product product;

	public Review() {
	}

	public Review(Date date, String review, Question question, User user, Product product) {
		this.date = date;
		this.review = review;
		this.question = question;
		this.user = user;
		this.product = product;
	}


	
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getReview() {
		return this.review;
	}

	public void setReview(String review) {
		this.review = review;
	}
	

	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Question getQuestion() {
		return this.question;
	}

	public void setQuestion(Question question) {
		this.question = question;
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