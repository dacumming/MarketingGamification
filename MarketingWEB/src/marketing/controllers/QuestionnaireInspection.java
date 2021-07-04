package marketing.controllers;


import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringEscapeUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import marketing.entities.*;
import marketing.services.ProductService;
import marketing.services.QuestionnaireService;


@WebServlet("/QuestionnaireInspection")
public class QuestionnaireInspection extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private TemplateEngine templateEngine;
	@EJB(name = "marketing.services/ProductService")
	private ProductService pService;
	@EJB(name = "marketing.services/QuestionnaireService")
	private QuestionnaireService qaService;


    public QuestionnaireInspection() {
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
		HttpSession session = request.getSession();
		if (session.isNew() || session.getAttribute("user") == null) {
			String loginpath = getServletContext().getContextPath() + "/index.html";
			response.sendRedirect(loginpath);
			return;
		}
		String[] separated = null;
		int prodId = 0;
		Date date_aux = null;
		Date qdate = null;
		List<Questionnaire> q_dates = null;
		String user_selection = null;
		List<Questionnaire> s_qa = null;
		List<Questionnaire> c_qa = null;
		Calendar c = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
		List<String> information = new ArrayList<String>();
		List<Integer> isheader = new ArrayList<Integer>();
		String dt = null;
		try {
			q_dates = qaService.findQuestionnaireDates();
			user_selection = StringEscapeUtils.escapeJava(request.getParameter("qaStr"));
			separated = user_selection.split(",");
			date_aux = sdf.parse(separated[0]);
			c.setTime(date_aux);
			//c.add(Calendar.DATE, 1);
			dt = sdf.format(c.getTime());
			qdate=sdf.parse(dt); 
			prodId = Integer.parseInt(separated[1]);
			s_qa = qaService.findQuestionnairesByDateProductIsCanceled(qdate, prodId, 0);
			c_qa = qaService.findQuestionnairesByDateProductIsCanceled(qdate, prodId, 1);
			for (int i = 0; i < s_qa.size(); i++) {
				Questionnaire questionnaire =  s_qa.get(i);
				information.add(questionnaire.getUser().getUsername());
				isheader.add(1);
				List<Answer> answers = questionnaire.getAnswers();
				for (int j = 0; j < answers.size(); j++) {
					Answer answer = answers.get(j);
					information.add("Q: "+answer.getQuestionText());
					isheader.add(0);
					information.add("A: "+answer.getAnswer());
					isheader.add(0);
				}
				
				UserData userdata = questionnaire.getUserData();
				information.add("Q: Sex");
				isheader.add(0);
				information.add("A: "+userdata.getAnswer1());
				isheader.add(0);
				information.add("Q: Age");
				isheader.add(0);
				information.add("A: "+userdata.getAnswer2());
				isheader.add(0);
				information.add("Q: Expertise Level");
				isheader.add(0);
				information.add("A: "+userdata.getAnswer3());
				isheader.add(0);

			}
			
		} catch (ParseException e) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Not possible to get data");
			return;
		}
		
		// return the user to the right view
		
		ServletContext servletContext = getServletContext();
		final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
		ctx.setVariable("q_dates", q_dates);
		ctx.setVariable("selected_date", date_aux);
		ctx.setVariable("selected_product_id", prodId);
		ctx.setVariable("selected_product_name", pService.findProductById(prodId).getName());
		ctx.setVariable("information", information);
		ctx.setVariable("isheader", isheader);
		ctx.setVariable("s_qa", s_qa);
		ctx.setVariable("c_qa", c_qa);
		String path = "/WEB-INF/AdminInspection.html";
		templateEngine.process(path, ctx, response.getWriter());
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		doGet(request, response);
	}

}
