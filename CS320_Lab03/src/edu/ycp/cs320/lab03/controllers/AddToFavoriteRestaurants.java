package edu.ycp.cs320.lab03.controllers;

import edu.ycp.cs320.lab03.DBpersist.DatabaseProvider;
import edu.ycp.cs320.lab03.DBpersist.DerbyDatabase;
import edu.ycp.cs320.lab03.DBpersist.IDatabase;

public class AddToFavoriteRestaurants {
	private IDatabase db = null;
	public AddToFavoriteRestaurants() {
		
		// Create the default IDatabase instance
		DatabaseProvider.setInstance(new DerbyDatabase());
		db = DatabaseProvider.getInstance();
	
	}
	public void AddUser(String rest, Integer userId){
		
		db.addToFavoriteRests(rest, userId);
	}
}
