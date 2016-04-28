package edu.ycp.cs320.lab03.servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.ycp.cs320.lab03.controller.AddNumbersController;
import edu.ycp.cs320.lab03.controller.Patron;
import edu.ycp.cs320.lab03.controller.ProjectController;
import edu.ycp.cs320.lab03.controller.Restaurant;
import edu.ycp.cs320.lab03.controller.User;
import edu.ycp.cs320.lab03.queries.matchUsernameWithPassword;


public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private matchUsernameWithPassword match = null;
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.getRequestDispatcher("/_view/Login.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		// Decode form parameters and dispatch to controller
		String errorMessage = null;
		String username = null;
		String password = null;
			username = req.getParameter("username");
			password = req.getParameter("password");
			match = new matchUsernameWithPassword();
			ArrayList<User> user = null;
			user = match.matchUser(username);
			if(user.size()>0){
				User u = user.get(0);
				ProjectController controller = new ProjectController();
				//if user is authenticated, call homepage
				if(controller.authenticate(u, password)){
					req.getSession().setAttribute("username", username);
					resp.sendRedirect(req.getContextPath() + "/Homepage");
					//				User user = null;
					//				user.logIn(u, p);

				}
				else{
					errorMessage = "Incorrect Username or Password";
					req.setAttribute("errorMessage", errorMessage);
					req.getRequestDispatcher("/_view/Login.jsp").forward(req, resp);
				}
			}
			//otherwise, print an error message
			else{
				errorMessage = "Incorrect Username or Password";
				req.setAttribute("errorMessage", errorMessage);
				req.getRequestDispatcher("/_view/Login.jsp").forward(req, resp);
			}
	}

}
