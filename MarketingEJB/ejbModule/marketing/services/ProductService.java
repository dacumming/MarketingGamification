package marketing.services;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;

import marketing.exceptions.UpdateDateException;
import marketing.exceptions.ProductException;

import marketing.entities.Product;

@Stateless
public class ProductService {
	@PersistenceContext(unitName = "MarketingEJB")
	private EntityManager em;

	public ProductService() {
	}
	
	public Product findProductById(int productId) {
		Product product = em.find(Product.class, productId);
		return product;
	}
	
	public List<Product> findProductOfTheDay() {
		List<Product> products = em
				.createQuery("Select p from Product p where p.date = current_date", Product.class)
				.getResultList();

		return products;
	}
	
	public List<Product> findProductByDate(Date product_date) {
		TypedQuery<Product> query = em
				.createQuery("Select p from Product p where p.date=:product_date", Product.class);
		List<Product> products = query
				.setParameter("product_date", product_date, TemporalType.DATE)
				.getResultList();

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
	
	public void updateDate(Product p) throws UpdateDateException {
		try {
			em.merge(p);
		} catch (PersistenceException e) {
			throw new UpdateDateException("Could not change profile");
		}
	}
	

}
