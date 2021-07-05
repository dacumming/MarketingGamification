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
@NamedQuery(name = "Product.findAll", query = "SELECT p FROM Product p")

public class Product implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String name;
	
	private String brand;
	
	@Temporal(TemporalType.DATE)
	private Date date;

	private String image;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "product")
	private List<Questionnaire> questionnaires;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "product", cascade = CascadeType.PERSIST)
	private List<Question> questions;
	

	public Product() {
	}

	public Product(String n, String b, Date d, String i) {
		this.name = n;
		this.brand = b;
		this.date = d;
		this.image = i;
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

	public String getImage() {
		return this.image;
	}

	public void setImage(String image) {
		this.image = image;
	}
	
	public List<Questionnaire> getQuestionnaires() {
		return this.questionnaires;
	}

	public void addQuestionnaire(Questionnaire questionnaire) {
		getQuestionnaires().add(questionnaire);
		questionnaire.setProduct(this);
	}

	public void removeQuestionnaire(Questionnaire questionnaire) {
		getQuestionnaires().remove(questionnaire);
	}
	
	public List<Question> getQuestions() {
		return this.questions;
	}

	public void addQuestion(Question question) {
		getQuestions().add(question);
		question.setProduct(this);
	}

	public void removeQuestion(Question question) {
		getQuestions().remove(question);
	}

}