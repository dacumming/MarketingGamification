package marketing.entities;

import java.io.Serializable;

import javax.persistence.*;
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


}