//package edu.ycp.cs320.lab03.controllers;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import edu.ycp.cs320.lab03.DBpersist.DatabaseProvider;
//import edu.ycp.cs320.lab03.DBpersist.DerbyDatabase;
//import edu.ycp.cs320.lab03.DBpersist.IDatabase;
//import edu.ycp.cs320.lab03.model.Order;
//
//
//public class PastOrdersByPatron {
//	private IDatabase db = null;
//	public PastOrdersByPatron() {
//		
//		// Create the default IDatabase instance
//		DatabaseProvider.setInstance(new DerbyDatabase());
//		db = DatabaseProvider.getInstance();
//	}
//		public ArrayList<Order> orderByPatronId(String pat){
//			List<Order> orderList = db.getOrderByPatronUname(pat);
//			ArrayList<Order> orders = null;
//			
//			if (orderList.isEmpty()) {
//				System.out.println("No past orders");
//				return null;
//			}
//			else {
//				orders = new ArrayList<Order>();
//				for (Order order : orderList) {
//					orders.add(order);
//				}			
//			}
//			
//			
//			return orders;
//	}
//}
