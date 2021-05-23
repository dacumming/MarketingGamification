package marketing.controllers;

import java.io.IOException;
import javax.ejb.EJB;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringEscapeUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import marketing.services.UserService;

@WebServlet("/RegisterUser")
public class RegisterUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private TemplateEngine templateEngine;
	@EJB(name = "marketing.services/UserService")
	private UserService uService;

    public RegisterUser() {
        super();
    }

	
    public void init() throws ServletException {
		ServletContext servletContext = getServletContext();
		ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(servletContext);
		templateResolver.setTemplateMode(TemplateMode.HTML);
		this.templateEngine = new TemplateEngine();
		this.templateEngine.setTemplateResolver(templateResolver);
		templateResolver.setSuffix(".html");
	}
    

    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
				
		String path = "/WEB-INF/userregistered.html";
		ServletContext servletContext = getServletContext();
		final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
		templateEngine.process(path, ctx, response.getWriter());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		doGet(request, response);
		// Get and parse all parameters from request
		boolean isBadRequest = false;
		String usr = null;
		String pass = null;
		String email = null;
		try {
			usr = StringEscapeUtils.escapeJava(request.getParameter("nusername"));
			pass = StringEscapeUtils.escapeJava(request.getParameter("npwd"));
			email = StringEscapeUtils.escapeJava(request.getParameter("nemail"));
			isBadRequest = usr.isEmpty() || pass.isEmpty() || email.isEmpty();
			
		} catch (NumberFormatException | NullPointerException e) {
			isBadRequest = true;
			e.printStackTrace();
		}
		if (isBadRequest) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Incorrect or missing param values");
			return;
		}

		try {
			uService.createUser(usr, pass, email);
		} catch (Exception e) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Not possible to create user");
			return;
		}


	}
}	
