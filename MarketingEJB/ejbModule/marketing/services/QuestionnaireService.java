package marketing.services;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;

import marketing.entities.Questionnaire;
import marketing.exceptions.ProductException;
import marketing.exceptions.UpdateDateException;

@Stateless
public class QuestionnaireService {
	@PersistenceContext(unitName = "MarketingEJB")
	private EntityManager em;

	public QuestionnaireService() {
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
	
	public List<Questionnaire> findQuestionnairesByDateProduct(Date q_date, int productId, int isCanceled) {
		TypedQuery<Questionnaire> query = em
				.createQuery("Select qa from Questionnaire qa where qa.date=:qdate and qa.product.id=:prodId and qa.iscanceled=:iscanc", Questionnaire.class);
		List<Questionnaire> questionnaires = query
				.setParameter("qdate", q_date, TemporalType.DATE)
				.setParameter("prodId", productId)
				.setParameter("iscanc", isCanceled)
				.getResultList();
		

		return questionnaires;
	}
	

}