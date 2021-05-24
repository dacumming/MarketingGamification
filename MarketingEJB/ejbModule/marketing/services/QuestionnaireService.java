package marketing.services;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

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
				.createQuery("Select distinct q.date from Questionnaire q order by q.date", Questionnaire.class)
				.getResultList();

		return q_dates;
	}
	

}