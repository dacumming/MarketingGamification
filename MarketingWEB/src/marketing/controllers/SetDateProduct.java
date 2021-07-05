package marketing.controllers;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import marketing.entities.Product;
import marketing.services.ProductService;

@WebServlet("/SetDateProduct")
public class SetDateProduct extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@EJB(name = "marketing.services/ProductService")
	private ProductService pService;

    public SetDateProduct() {
        super();
    }

    public void init() throws ServletException {
	}
    
    private Date getYesterday() {
		return new Date(System.currentTimeMillis() - 24 * 60 * 60 * 1000);
	}
    
    
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		if (session.isNew() || session.getAttribute("user") == null) {
			String loginpath = getServletContext().getContextPath() + "/index.html";
			response.sendRedirect(loginpath);
			return;
		}
		boolean isBadRequest = false;
		Date date = null;
		Integer productId= null;
		try {

			productId =Integer.parseInt(request.getParameter("productId"));
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			date = (Date) sdf.parse(request.getParameter("date"));
			isBadRequest = getYesterday().after(date);
			
		} catch (NumberFormatException | NullPointerException | ParseException e) {
			isBadRequest = true;
			e.printStackTrace();
		}
		if (isBadRequest) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Incorrect or missing param values");
			return;
		}

		Product product = pService.findProductById(productId);
		List<Product> product_same_date = pService.findProductByDate(date);
		Date oldDate = product.getDate();
		
		try {
			if (product.getDate() != null) {
				if (product.getDate() != date) {
					product.setDate(date);
					pService.updateDate(product);
				}
			}
			else {
				product.setDate(date);
				pService.updateDate(product);
			}
			if (product_same_date.size() > 0) {
				for (int i = 0; i < product_same_date.size(); i++) {
					Product current_product_same_date = product_same_date.get(i);
					current_product_same_date.setDate(null);
					pService.updateDate(current_product_same_date);
				}
				
			}
		} catch (Exception e) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Not possible to update date");
			product.setDate(oldDate);
			return;
		}
		

		String ctxpath = getServletContext().getContextPath();
		String path = ctxpath + "/AdminCreation";
		response.sendRedirect(path);
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		doGet(request, response);
	}

}
