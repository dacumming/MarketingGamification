package marketing.controllers;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

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
        ArrayList<String[]> leaderboard;
        leaderboard = new ArrayList<String[]>();
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
	        Collections.sort(questionnaires_of_the_day, new Comparator<Questionnaire>() {
	            @Override
	            public int compare(Questionnaire q1, Questionnaire q2) {
	            	User currentUser1 = q1.getUser();
		        	int points1 = currentUser1.getPoints();
		        	User currentUser2 = q2.getUser();
		        	int points2 = currentUser2.getPoints();
		        	
	                return points1 - points2;
	            }
	        }); 
	        
	        for (int i = 0; i < questionnaires_of_the_day.size(); i++) {
	        	String[] row = new String[2];
	        	questionnaire = questionnaires_of_the_day.get(i);
	        	currentUser = questionnaire.getUser();
	        	if (!existingUser(leaderboard, currentUser)) {
	        		points = currentUser.getPoints();
		        	row[0] = currentUser.getUsername();	        	
		        	row[1] = Integer.toString(points);
		        	leaderboard.add(row);
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
	
	boolean existingUser(ArrayList<String[]> leaderboard, User currentUser) {
		boolean exists = false;
		int size = leaderboard.size();
		String username = currentUser.getUsername();
		for (int i = 0; i < size; i++) {
			if (leaderboard.get(i)[0].equals(username)) {
				exists = true;
				return exists;
			}
		}
			
		
		return exists;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	} 

}
