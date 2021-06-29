package marketing.services;

import java.util.Collections;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import javax.persistence.TypedQuery;

import marketing.entities.Answer;
import marketing.entities.Questionnaire;
import marketing.entities.UserData;


@Stateless
public class UserDataService {
	@PersistenceContext(unitName = "MarketingEJB")
	private EntityManager em;
	
	public void createUserData(UserData userdata, Questionnaire questionnaire) {
		userdata.setQuestionnaire(questionnaire);
		em.persist(userdata);
	}
	
	  public List<UserData> findUserDataFromQuestionnaire(List<Questionnaire> qa_list) { 
		  TypedQuery<UserData> query = em.createQuery("Select ud from UserData ud where ud.questionnaire in :qalist order by ud.questionnaire.date", UserData.class);
		  List<UserData> userdata = null;
		  if (qa_list==null || qa_list.isEmpty()) {
		  userdata = query.setParameter("qalist", Collections.singleton(null)).getResultList(); 
		  } else {
		  userdata = query.setParameter("qalist", qa_list).getResultList(); 
		  }
		  
		  return userdata;
	  }
}
