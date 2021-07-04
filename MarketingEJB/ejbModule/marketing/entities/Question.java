package marketing.entities;

import java.io.Serializable;

import javax.persistence.*;
import java.util.List;
import java.lang.String;

/**
 * The persistent class for the projects database table.
 * 
 */

@Entity
@Table(name = "questions", schema="marketingdb")

public class Question implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String question;

	// Bidirectional one-to-many association to Review
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "question")
	private List<Answer> reviews;
	
	// Bi-directional many-to-one association to Product. Question is the owner
	// entity
	@ManyToOne
	@JoinColumn(name = "product")
	private Product product;
	
	public Question() {
	}

	public Question(String question, Product product) {
		this.question = question;
		this.product = product;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getQuestion() {
		return this.question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public Product getProduct() {
		return this.product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public List<Answer> getReviews() {
		return this.reviews;
	}

	public void addReview(Answer review) {
		getReviews().add(review);
		review.setQuestion(this);
		// aligns both sides of the relationship
		// if mission is new, invoking persist() on reporter cascades also to mission
	}

	public void removeReview(Answer review) {
		getReviews().remove(review);
	}

}