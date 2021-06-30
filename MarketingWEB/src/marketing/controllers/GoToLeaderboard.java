package marketing.controllers;

import java.io.IOException;
import java.text.SimpleDateFormat;
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

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import marketing.entities.*;
import marketing.services.AnswerService;
import marketing.services.ProductService;
import marketing.services.QuestionService;
import marketing.services.QuestionnaireService;
import marketing.services.UserDataService;
import marketing.services.UserService;

/**
 * Servlet implementation class GoToLeaderboard
 */
@WebServlet("/Leaderboard")
public class GoToLeaderboard extends HttpServlet {
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
	@EJB(name = "marketing.services/UserService")
	private UserService uService;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GoToLeaderboard() {
        super();
        // TODO Auto-generated constructor stub
    }
    

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		HttpSession session = request.getSession();
		if (session.isNew() || session.getAttribute("user") == null) {
			String loginpath = getServletContext().getContextPath() + "/index.html";
			response.sendRedirect(loginpath);
			return;
		}
		
		Questionnaire questionnaire = null;
        User currentUser = null;
        int points = 0;
        boolean isBadRequest = false;
        String [][] leaderboard = null;
        List<Questionnaire> questionnaires_of_the_day = null;
        try {
			List<Product> products = pService.findProductOfTheDay();
			Product product_of_the_day = products.get(0);
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyy");
	        Date date = new Date();
	        String currentDate = dateFormat.format(date);
	        int prodId = product_of_the_day.getId();
	        System.out.println(date);
	        System.out.println(currentDate);
	        System.out.println(prodId);
	        System.out.println("***************************");
	        questionnaires_of_the_day = qaService.findQuestionnairesByDateProduct(date, prodId);
	        
	        leaderboard = new String[questionnaires_of_the_day.size()][2];
	        
	        for (int i = 0; i < questionnaires_of_the_day.size(); i++) {
	        	questionnaire = questionnaires_of_the_day.get(i);
	        	currentUser = questionnaire.getUser();
	        	//leaderboard[i][1] = uService.getUserById(questionnaire.getUser());
	        	points = currentUser.getPoints();
	        	
	        	if(i>0 && Integer.parseInt(leaderboard[i-1][2])<points) {
	        		leaderboard[i][1] = leaderboard[i-1][1];
	        		leaderboard[i][2] = leaderboard[i-1][2];
	        		leaderboard[i-1][1] = currentUser.getUsername();
	        		leaderboard[i-1][2] = Integer.toString(points);
	        		
	        	}else {
		        	leaderboard[i][1] = currentUser.getUsername();	        	
		        	leaderboard[i][2] = Integer.toString(points);
	        	}
	        }
        }catch (NumberFormatException | NullPointerException e) {
			isBadRequest = true;
			e.printStackTrace();
		}
	
        if (isBadRequest) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Incorrect or missing param values");
			return;
		}
        
        String path = "/WEB-INF/Leaderboard.html";
		ServletContext servletContext = getServletContext();
		final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
		ctx.setVariable("leaderboard", leaderboard);
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
