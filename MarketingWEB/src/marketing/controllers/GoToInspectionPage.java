package marketing.controllers;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import marketing.entities.Product;
import marketing.entities.User;
import marketing.entities.Questionnaire;
import marketing.services.QuestionnaireService;
import marketing.services.UserService;
import java.util.Date;

/**
 * Servlet implementation class GoToInspectionPage
 */
@WebServlet("/AdminInspection")
public class GoToInspectionPage extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private TemplateEngine templateEngine;
	@EJB(name = "marketing.services/QuestionnaireService")
	private QuestionnaireService qaService;
	@EJB(name = "marketing.services/UserService")
	private UserService uService;
	
    public GoToInspectionPage() {
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
		
		String loginpath = getServletContext().getContextPath() + "/index.html";
		HttpSession session = request.getSession();
		if (session.isNew() || session.getAttribute("user") == null) {
			response.sendRedirect(loginpath);
			return;
		}
		List<Questionnaire> q_dates = null;

		LocalDate localdate = LocalDate.of( 2021 , 5 , 16 );
		ZoneId defaultZoneId = ZoneId.systemDefault();
        Date qdate = Date.from(localdate.atStartOfDay(defaultZoneId).toInstant());
		try {
			q_dates = qaService.findQuestionnaireDates();
//			users = uService.findUsersByDateQuestionnaire(qdate);
			
		} catch (Exception e) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Not possible to get data");
			return;
		}
		
		String path = "/WEB-INF/AdminInspection.html";
		ServletContext servletContext = getServletContext();
		final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
		ctx.setVariable("q_dates", q_dates);

		templateEngine.process(path, ctx, response.getWriter());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		doGet(request, response);
	}

}