package marketing.controllers;

import java.io.IOException;
import java.util.ArrayList;
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
		
		User user = (User)session.getAttribute("user");		
		List<Product> products = pService.findProductOfTheDay();
		Product product_of_the_day = products.get(0);
		String feedback = null;
		boolean isBadRequest = false;
		String submitOption = StringEscapeUtils.escapeJava(request.getParameter("Submit"));
		System.out.println(submitOption);
		if(submitOption.equals("Submit")){
		
			List<Answer> answers = new ArrayList<Answer>();
			List<Question> questions =product_of_the_day.getQuestions();		
			String sex = null;
			String age = null;
			String exp = null;
			
			try {
				
				
				sex = StringEscapeUtils.escapeJava(request.getParameter("sex"));
				age = StringEscapeUtils.escapeJava(request.getParameter("age"));
				exp = StringEscapeUtils.escapeJava(request.getParameter("exp"));
				UserData userdata = new UserData(sex, age, exp);
				for (int i = 0; i < questions.size(); i++) {
					String answertext = StringEscapeUtils.escapeJava(request.getParameter("question_"+String.valueOf(questions.get(i).getId())));
					Answer answer =  new Answer(answertext, questions.get(i).getQuestion(), questions.get(i));
					answers.add(answer);
				}
				Questionnaire questionnaire = new Questionnaire(user, 0, product_of_the_day, answers, userdata);
				for (int i = 0; i < answers.size(); i++) {
					answers.get(i).setQuestionnaire(questionnaire);
				}
				userdata.setQuestionnaire(questionnaire);
				
				qaService.createQuestionnaire(questionnaire);
				
				
			} catch (NumberFormatException | NullPointerException e) {
				isBadRequest = true;
				e.printStackTrace();
			}
			
			feedback = "Thanks for filling the questionnaire";
		
		}else {
			try {
				Questionnaire questionnaire = new Questionnaire(user, 1, product_of_the_day, null, null);
				qaService.createQuestionnaire(questionnaire);
			}catch (NumberFormatException | NullPointerException e) {
				isBadRequest = true;
				e.printStackTrace();
			}
			
			feedback = "Questionnaire cancelled";
		}
		
		if (isBadRequest) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Incorrect or missing param values");
			return;
		}
		String path = "/WEB-INF/Feedback.html";
		ServletContext servletContext = getServletContext();
		final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
		ctx.setVariable("feedback", feedback);
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
