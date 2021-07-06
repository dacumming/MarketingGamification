package marketing.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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
import marketing.exceptions.BadWordException;
import marketing.exceptions.UpdateIsBannedException;
import marketing.services.BadWordService;
import marketing.services.ProductService;
import marketing.services.QuestionnaireService;
import marketing.services.UserService;

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
	@EJB(name = "marketing.services/BadWordService")
	private BadWordService bwService;
	@EJB(name = "marketing.services/UserService")
	private UserService uService;

    public CreateQuestionnaire() {
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

	boolean checkBadWords(List<String> answertexts, List<String> badwords) {
	
	for (int i=0; i< answertexts.size(); i++) {
		for (int j=0; j< badwords.size(); j++) {
			if (answertexts.get(i).toUpperCase(Locale.forLanguageTag("en")).contains(badwords.get(j).toUpperCase(Locale.forLanguageTag("en")))) {
				return true;
				
				
			}
			
		}
		
	}
	return false;
	}
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
		boolean areThereBadWords = false;
		
		if(submitOption.equals("Submit")){
			List<Answer> answers = new ArrayList<Answer>();
			List<String> answertexts = new ArrayList<String>();
			List<Question> questions =product_of_the_day.getQuestions();		
			String sex = null;
			String age = null;
			String exp = null;
			
			try {
				
				List<String> badwords = bwService.findAllBadWords();
				for (int i = 0; i < questions.size(); i++) {
					String answertext = StringEscapeUtils.escapeJava(request.getParameter("question_"+String.valueOf(questions.get(i).getId())));
					answertexts.add(answertext);
				}
				areThereBadWords = checkBadWords(answertexts, badwords);
				if (areThereBadWords) { 
					uService.updateIsBanned(user);
					session.invalidate();
					String path = "/WEB-INF/YouAreBanned.html";
					ServletContext servletContext = getServletContext();
					final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
					templateEngine.process(path, ctx, response.getWriter());
				}
				 
				sex = StringEscapeUtils.escapeJava(request.getParameter("sex"));
				age = StringEscapeUtils.escapeJava(request.getParameter("age"));
				exp = StringEscapeUtils.escapeJava(request.getParameter("exp"));
				UserData userdata = new UserData(sex, age, exp);
				for (int i = 0; i < questions.size(); i++) {
					Answer answer =  new Answer(answertexts.get(i), questions.get(i).getQuestion());
					answers.add(answer);
				}
				Questionnaire questionnaire = new Questionnaire(user, 0, product_of_the_day, answers, userdata);
				for (int i = 0; i < answers.size(); i++) {
					answers.get(i).setQuestionnaire(questionnaire);
				}
				userdata.setQuestionnaire(questionnaire);
				if (areThereBadWords) {
					
				} else {
					qaService.createQuestionnaire(questionnaire);
				}
				
			} catch (NumberFormatException | NullPointerException e) {
				isBadRequest = true;
				e.printStackTrace();

			} catch (BadWordException e) {
				e.printStackTrace();
			} catch (UpdateIsBannedException e) {
				// TODO Auto-generated catch block
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
		
		if (!areThereBadWords) {
		String path = "/WEB-INF/Feedback.html";
		ServletContext servletContext = getServletContext();
		final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
		ctx.setVariable("feedback", feedback);
		templateEngine.process(path, ctx, response.getWriter());
		}
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
		
	}

}
