package edu.ycp.cs320.lab03.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;

import edu.ycp.cs320.lab03.controller.AddNumbersController;
import edu.ycp.cs320.lab03.controller.ProjectController;
import edu.ycp.cs320.lab03.controller.User;

public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

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
		
		
		try {
			
			User u = null;
			u.setUserName(req.getParameter("username"));
			
			u.setPassWord(req.getParameter("password"));


			if (u.getUserName() == null || u.getUserName() == null) {
				errorMessage = "Please enter a username/pswd combo";
				
			} else {
				ProjectController controller = new ProjectController();
				controller.LogIn(u, u.getUserName());
				
			}
		} catch (NumberFormatException e) {
			errorMessage = "Invalid double";
		}
		
		// Add parameters as request attributes
		req.setAttribute("username", req.getParameter("username"));
		req.setAttribute("password", req.getParameter("password"));
		
		// Add result objects as request attributes
		req.setAttribute("errorMessage", errorMessage);
		
		
		// Forward to view to render the result HTML document
		RequestDispatcher rd = req.getRequestDispatcher("Homepage");
		rd.forward(req, resp);
		
	}

}
