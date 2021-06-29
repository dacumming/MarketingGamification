package marketing.controllers;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
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

import marketing.entities.Answer;
import marketing.entities.Product;
import marketing.entities.Questionnaire;
import marketing.entities.User;
import marketing.entities.Question;
import marketing.entities.UserData;
import marketing.services.ProductService;
import marketing.services.QuestionService;
import marketing.services.AnswerService;
import marketing.services.UserDataService;
import marketing.services.QuestionnaireService;

/**
 * Servlet implementation class CreateQuestionnaire
 */
@WebServlet("/CreateQuestionnaire")
public class CreateQuestionnaire extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private TemplateEngine templateEngine;
	@EJB(name = "marketing.services/ProductService")
	private ProductService pService;
	@EJB(name = "marketing.services/QuestionService")
	private QuestionService qService;
	@EJB(name = "marketing.services/AnswerService")
	private AnswerService aService;
	@EJB(name = "marketing.services/UserDataService")
	private UserDataService udService;
	@EJB(name = "marketing.services/QuestionnaireService")
	private QuestionnaireService qaService;

    public CreateQuestionnaire() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    public void init() throws ServletException {
		ServletContext servletContext = getServletContext();
		ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(servletContext);
		templateResolver.setTemplateMode(TemplateMode.HTML);
		this.templateEngine = new TemplateEngine();
		this.templateEngine.setTemplateResolver(templateResolver);
		templateResolver.setSuffix(".html");
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		if (session.isNew() || session.getAttribute("user") == null) {
			String loginpath = getServletContext().getContextPath() + "/index.html";
			response.sendRedirect(loginpath);
			return;
		}
		List<Product> products = pService.findProductOfTheDay();
		Product product_of_the_day = products.get(0);
		List<Question> questions =product_of_the_day.getQuestions();
		User user = (User)session.getAttribute("user");
		
		boolean isBadRequest = false;
		String sex = null;
		String age = null;
		String exp = null;
		Questionnaire questionnaire = new Questionnaire(user, 0, product_of_the_day);
		try {
			qaService.createQuestionnaire(questionnaire);
			sex = StringEscapeUtils.escapeJava(request.getParameter("sex"));
			age = StringEscapeUtils.escapeJava(request.getParameter("age"));
			exp = StringEscapeUtils.escapeJava(request.getParameter("exp"));
			UserData userdata = new UserData(sex, age, exp);
			udService.createUserData(userdata, questionnaire);
			for (int i = 0; i < questions.size(); i++) {
				String answertext = StringEscapeUtils.escapeJava(request.getParameter("question_"+String.valueOf(questions.get(i).getId())));
				Answer answer =  new Answer(answertext, questions.get(i).getQuestion(), questions.get(i));
				aService.createAnswer(answer, questionnaire);
			}

			
			
		} catch (NumberFormatException | NullPointerException e) {
			isBadRequest = true;
			e.printStackTrace();
		}
		if (isBadRequest) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Incorrect or missing param values");
			return;
		}
		
//		try {
//			qService.createQuestion(question, productId);
//		} catch (Exception e) {
//			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Not possible to create question");
//			return;
//		}
		
		
		
		
		
		
		
		ServletContext servletContext = getServletContext();
		final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
		String path = "/WEB-INF/Feedback.html";
		templateEngine.process(path, ctx, response.getWriter());
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
		
	}

}
