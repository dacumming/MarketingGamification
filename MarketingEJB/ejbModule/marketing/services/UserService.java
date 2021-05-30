package marketing.services;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import marketing.entities.User;

import javax.persistence.NonUniqueResultException;
import marketing.entities.UserLogin;
import marketing.exceptions.*;

import java.util.List;

@Stateless
public class UserService {
	@PersistenceContext(unitName = "MarketingEJB")
	private EntityManager em;
	
	public void createUser(String nusername, String npwd, String nemail) {
		User user = new User(nusername, npwd, nemail);
		
		em.persist(user); // makes also user object managed via cascading
	}
	
	public int findUserByUser(String usr, String email) throws UsernameException {
		List<User> users = null;
		try {
			users = em.createNamedQuery("User.checkUsernames", User.class).setParameter(1, usr).setParameter(2, email).getResultList();
			//System.out.println(users);
			if (users.isEmpty()) {
				return 0;
			} else {
				return 1;
			}
		}catch (PersistenceException e) {
			throw new UsernameException("Username already exists");
		}
	}

	public User checkCredentials(String usrn, String pwd) throws CredentialsException, NonUniqueResultException {
		List<User> uList = null;
		try {
			uList = em.createNamedQuery("User.checkCredentials", User.class).setParameter(1, usrn).setParameter(2, pwd)
					.getResultList();
		} catch (PersistenceException e) {
			throw new CredentialsException("Could not verify credentals");
		}
		if (uList.isEmpty()) {
			return null;
		}
		else if (uList.size() == 1) {
			User loggedUser = uList.get(0);
			UserLogin userlogin = new UserLogin(loggedUser);
			em.persist(loggedUser);
			loggedUser.addUserLogin(userlogin);
			return loggedUser;
		}
		throw new NonUniqueResultException("More than one user registered with same credentials");

	}

}