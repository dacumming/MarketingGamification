package marketing.services;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;


import marketing.entities.Questionnaire;
import marketing.entities.User;
import marketing.entities.Product;
import marketing.exceptions.BadQuestionnaireDelete;

@Stateless
public class QuestionnaireService {
	@PersistenceContext(unitName = "MarketingEJB")
	private EntityManager em;
	

	public QuestionnaireService() {
		
	}
	
	private Date getToday() {
		return new Date(System.currentTimeMillis());
	}
	
	public Questionnaire findQuestionnaireById(int questionnaireId) {
		Questionnaire questionnaire = em.find(Questionnaire.class, questionnaireId);
		return questionnaire;
	}
	
	public List<Questionnaire> findQuestionnaireDates() {
		List<Questionnaire> q_dates = em
				.createQuery("Select distinct q.date, q.product from Questionnaire q order by q.date, q.product.id", Questionnaire.class)
				.getResultList();
		return q_dates;
	}
	
	public List<Questionnaire> findQuestionnairesByDateProductIsCanceled(Date q_date, int productId, int isCanceled) {
		TypedQuery<Questionnaire> query = em
				.createQuery("Select qa from Questionnaire qa where qa.date=:qdate and qa.product.id=:prodId and qa.iscanceled=:iscanc", Questionnaire.class);
		List<Questionnaire> questionnaires = query
				.setParameter("qdate", q_date, TemporalType.DATE)
				.setParameter("prodId", productId)
				.setParameter("iscanc", isCanceled)
				.getResultList();
		

		return questionnaires;
	}
	
	public List<Questionnaire> findQuestionnairesByDateProduct(Date q_date, int productId) {
		TypedQuery<Questionnaire> query = em
				.createQuery("Select qa from Questionnaire qa where qa.date=:qdate and qa.product.id=:prodId", Questionnaire.class);
		List<Questionnaire> questionnaires = query
				.setParameter("qdate", q_date, TemporalType.DATE)
				.setParameter("prodId", productId)
				.getResultList();
		

		return questionnaires;
	}
	
	public List<Questionnaire> findQuestionnairesByDateProductUser(Date q_date, int productId, int userId) {
		//em.getEntityManagerFactory().getCache().evictAll();
		User user = em.find(User.class, userId);
		em.flush();
		em.refresh(user);
		TypedQuery<Questionnaire> query = em
				.createQuery("Select qa from Questionnaire qa where qa.date=:qdate and qa.product.id=:prodId and qa.user.id=:userId", Questionnaire.class);
		List<Questionnaire> questionnaires = query
				.setParameter("qdate", q_date, TemporalType.DATE)
				.setParameter("prodId", productId)
				.setParameter("userId", userId)
				.getResultList();
		

		return questionnaires;
	}
	
	public void createQuestionnaire(Questionnaire questionnaire) {	
		
		em.persist(questionnaire);
		
	}
	 
	
	public void deleteQuestionnaire(int qaId) throws BadQuestionnaireDelete {
		Questionnaire questionnaire = em.find(Questionnaire.class, qaId);
		User user = questionnaire.getUser();
		Product product = questionnaire.getProduct();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		if (format.format(questionnaire.getDate()).equals(format.format(getToday()))) {
			throw new BadQuestionnaireDelete("Deletion should be possible only for a date preceding the current date.");
		}
		user.removeQuestionnaire(questionnaire);
		product.removeQuestionnaire(questionnaire);
		em.remove(questionnaire);
	}
	

}