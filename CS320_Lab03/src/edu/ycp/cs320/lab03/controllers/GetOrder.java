package edu.ycp.cs320.lab03.controllers;

import java.util.ArrayList;
import java.util.List;

import edu.ycp.cs320.lab03.DBpersist.DatabaseProvider;
import edu.ycp.cs320.lab03.DBpersist.DerbyDatabase;
import edu.ycp.cs320.lab03.DBpersist.IDatabase;
import edu.ycp.cs320.lab03.model.Order;


public class GetOrder {
	private IDatabase db = null;
	public GetOrder() {
		
		// Create the default IDatabase instance
		DatabaseProvider.setInstance(new DerbyDatabase());
		db = DatabaseProvider.getInstance();
	}
		public ArrayList<Order> orderByNum(Integer orderNumber){
			List<Order> orderList = db.getOrderByConfirmationNumber(orderNumber);
			ArrayList<Order> orders = null;
			
			if (orderList.isEmpty()) {
				System.out.println("No Orders found");
				return null;
			}
			else {
				orders = new ArrayList<Order>();
				for (Order order : orderList) {
					orders.add(order);
				}			
			}
			
			
			return orders;
	}
		public ArrayList<Order> orderByUser(int userId){
			List<Order> orderList = db.getOrderByPatronUname(userId);
			ArrayList<Order> orders = null;
			
			if (orderList.isEmpty()) {
				System.out.println("No Orders found");
				return null;
			}
			else {
				orders = new ArrayList<Order>();
				for (Order order : orderList) {
					orders.add(order);
				}			
			}
			
			
			return orders;
	}
}
