package marketing.services;
import java.util.Collections;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import javax.persistence.TypedQuery;
import marketing.entities.Questionnaire;
import marketing.entities.User;
import marketing.entities.Answer;
import marketing.entities.Question;


@Stateless
public class AnswerService {
	@PersistenceContext(unitName = "MarketingEJB")
	private EntityManager em;
	
	public void createAnswer(String answer_text, String question_text, Questionnaire questionnaire, Question question) {
		Answer answer = new Answer(answer_text, question_text, question, questionnaire);
		
		em.persist(answer); // makes also user object managed via cascading
	}
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
