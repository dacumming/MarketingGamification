package marketing.services;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import marketing.entities.Question;
import marketing.entities.Product;

@Stateless
public class QuestionService {
	@PersistenceContext(unitName = "MarketingEJB")
	private EntityManager em;

	public QuestionService() {
	}
	
	public Question findQuestionById(int questionId) {
		Question question = em.find(Question.class, questionId);
		return question;
	}
	
	public void createQuestion(String questiontext, int productId) {
		Product product = em.find(Product.class, productId);
		Question question = new Question(questiontext, product);
		// for debugging: let's check if mission is managed
		System.out.println("Method createQuestion before product.addQuestion(question)");
		System.out.println("Is question object managed?  " + em.contains(question));

		product.addQuestion(question); // updates both sides of the relationship
		
		System.out.println("Method createQuestion AFTER product.addQuestion(question)");
		System.out.println("Is question object managed?  " + em.contains(question));

		
		em.persist(product); // makes also question object managed via cascading
		
		System.out.println("Method createQuestion after em.persist()");
		System.out.println("Is question object managed?  " + em.contains(question));

	}
	
	public List<Question> findProductQuestions(Product product) { 
		List<Question> questions = em
				.createQuery("SELECT q FROM Question q WHERE q.product = :prod", Question.class)
				.setParameter("prod", product)
				.getResultList();

		return questions;
	}

}