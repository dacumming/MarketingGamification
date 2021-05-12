package marketing.services;


import java.util.List;
import java.util.Date;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import marketing.exceptions.ProductException;

import javax.persistence.NonUniqueResultException;

import marketing.entities.Product;
import marketing.entities.User;

@Stateless
public class ProductService {
	@PersistenceContext(unitName = "MarketingEJB")
	private EntityManager em;

	public ProductService() {
	}

	// If a mission is deleted by a concurrent transaction this method retrieves it
	// from the cache. If a mission is deleted by the JPA application, the
	// persistence context evicts it, this method no longer
	// retrieves it, and relationship sorting by the client works
	

	// If a mission is deleted by a concurrent transaction this method
	// bypasses the cache and sees the correct list. Sorting is done by the query
	
	
	public List<Product> findProductOfTheDay() {
		List<Product> products = em
				.createQuery("Select p from Product p where p.date = current_date", Product.class)
				.getResultList();
		System.out.println(products);
		return products;
	}
	
	public List<Product> findAllProducts() throws ProductException {
		List<Product> products = null;
		try {
			products = em.createNamedQuery("Product.findAll", Product.class).getResultList();

		} catch (PersistenceException e) {
			throw new ProductException("Cannot load products");

		}
		return products;
	}
	

}
