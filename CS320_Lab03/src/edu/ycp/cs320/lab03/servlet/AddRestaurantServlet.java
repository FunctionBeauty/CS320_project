package edu.ycp.cs320.lab03.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.ycp.cs320.lab03.controllers.AddRestaurant;
import edu.ycp.cs320.lab03.controllers.AddUserToDatabase;



public class AddRestaurantServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private AddRestaurant restAdd = null;
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String user = (String) req.getSession().getAttribute("username");
		if (user == null) {
			// user is not logged in, or the session expired
			resp.sendRedirect(req.getContextPath() + "/Login");
			return;
		}
		
		req.getRequestDispatcher("/_view/AddRest.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		//initialize variables
		String restName = null;
		String street = null;
		String city = null;
		String zipcode = null;
		int ownerId = (int)req.getSession().getAttribute("userID");
		//get values from the jsp
		restName = req.getParameter("restName");
		street = req.getParameter("street");
		city = req.getParameter("city");
		zipcode = req.getParameter("zipcode");
		
		//Create add user instance
		restAdd = new AddRestaurant();
		//Add the restaurant
		if(restName!=null&&street!=null&&city!=null&&zipcode!=null){
			restAdd = new AddRestaurant();
			restAdd.AddRestaurant(ownerId, restName, street, city, zipcode);
			resp.sendRedirect(req.getContextPath() + "/OwnerPage");
		}
		//Redirect to login page
		else{
			req.getRequestDispatcher("/_view/AddRest.jsp").forward(req, resp);
		}
	}
}

