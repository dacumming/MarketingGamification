package marketing.entities;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;
import java.util.List;

/**
 * The persistent class for the mission database table.
 * 
 */
@Entity
@Table(name = "products", schema = "marketingdb")

public class Product implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String name;
	
	private String brand;
	
	@Temporal(TemporalType.DATE)
	private Date date;

	
	// Bidirectional one-to-many association to Review
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "product", cascade = CascadeType.ALL)
	private List<Review> reviews;
	
	// Bidirectional one-to-many association to Question
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "product", cascade = CascadeType.ALL)
	private List<Question> questions;
	

	public Product() {
	}

	public Product(String n, String b, Date d) {
		this.name = n;
		this.brand = b;
		this.date = d;
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
	
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBrand() {
		return this.brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}


	public List<Review> getReviews() {
		return this.reviews;
	}

	public void addReview(Review review) {
		getReviews().add(review);
		review.setProduct(this);
		// aligns both sides of the relationship
		// if mission is new, invoking persist() on reporter cascades also to mission
	}

	public void removeReview(Review review) {
		getReviews().remove(review);
	}
	
	public List<Question> getQuestions() {
		return this.questions;
	}

	public void addQuestion(Question question) {
		getQuestions().add(question);
		question.setProduct(this);
		// aligns both sides of the relationship
		// if mission is new, invoking persist() on reporter cascades also to mission
	}

	public void removeQuestion(Question question) {
		getQuestions().remove(question);
	}

}