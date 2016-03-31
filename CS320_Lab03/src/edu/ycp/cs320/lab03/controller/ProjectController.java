package edu.ycp.cs320.lab03.controller;

import java.util.ArrayList;

public class ProjectController {

	User u;
	
	public ArrayList<Restaurant> searchRestaurants(String searchVal, String params){
		Search search = new Search();
		ArrayList<Restaurant> searchResults = new ArrayList<Restaurant>();
		if(params.equals("name")){
			searchResults = search.searchRestaurantsByName(searchVal);
		}else if(params.equals("city")){
			searchResults = search.searchRestaurantsByCity(searchVal);
		}
		return searchResults;
	}




	
	public boolean authenticate(String u, String p)
	{
		boolean real = false;
		if(u.equals("meeps") && p.equals("peeps")){

			real = true;
		}
		
		return real;
	}

	public Order makeOrder(Restaurant rest, String itemToOrder, boolean saveOrder){
		Order o;
		if(!u.isOwner()){
			Patron p = new Patron(u.getUserName(), u.getPassWord());
			o = p.createOrder(rest, itemToOrder, saveOrder);
			rest.recieveOrder(o);
		}
		else{
			return null;
		}
		return o;		
	}
	
	public void writeReview(Restaurant rest, String title, String review, int rating){
		Review rev = new Review(title, review, rating);
		rest.addReview(rev);
	}
	//these integers will be dependent upon a user choosing a review from a list on the website
	public Review readReview(Restaurant rest, int i){
		return rest.readReview(i);
	}

}
