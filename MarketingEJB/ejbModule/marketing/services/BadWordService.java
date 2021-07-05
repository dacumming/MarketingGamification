package marketing.services;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import marketing.exceptions.BadWordException;


@Stateless
public class BadWordService {
	@PersistenceContext(unitName = "MarketingEJB")
	private EntityManager em;

	public BadWordService() {
	}
	

	

	
	public List<String> findAllBadWords() throws BadWordException {
		List<String> badwords = null;
		try {
			badwords = em.createNamedQuery("BadWord.findAll", String.class).getResultList();

		} catch (PersistenceException e) {
			throw new BadWordException("Cannot load bad words");

		}
		return badwords;
	}
	

	

}