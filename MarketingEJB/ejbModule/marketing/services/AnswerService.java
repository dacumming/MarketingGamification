package marketing.services;
import java.util.Collections;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import javax.persistence.TypedQuery;
import marketing.entities.Questionnaire;
import marketing.entities.Answer;


@Stateless
public class AnswerService {
	@PersistenceContext(unitName = "MarketingEJB")
	private EntityManager em;
	
<<<<<<< Updated upstream
	
=======
	public void createAnswer(Answer answer, Questionnaire questionnaire) {
		answer.setQuestionnaire(questionnaire);
		em.persist(answer);
	}
>>>>>>> Stashed changes
	  public List<Answer> findAnswerFromQuestionnaire(List<Questionnaire> qa_list) { 
		  TypedQuery<Answer> query = em.createQuery("Select a from Answer a where a.questionnaire in :qalist order by a.questionnaire.date", Answer.class);
		  List<Answer> answers = null;
		  if (qa_list==null || qa_list.isEmpty()) {
		  answers = query.setParameter("qalist", Collections.singleton(null)).getResultList(); 
		  } else {
		  answers = query.setParameter("qalist", qa_list).getResultList(); 
		  }
		  
		  return answers;
	  }
}
