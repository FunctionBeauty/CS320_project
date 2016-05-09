package edu.ycp.cs320.lab03.controllers;

import edu.ycp.cs320.lab03.DBpersist.DatabaseProvider;
import edu.ycp.cs320.lab03.DBpersist.DerbyDatabase;
import edu.ycp.cs320.lab03.DBpersist.IDatabase;

public class AddRestaurant {
	private IDatabase db = null;
	public AddRestaurant() {
		
		// Create the default IDatabase instance
		DatabaseProvider.setInstance(new DerbyDatabase());
		db = DatabaseProvider.getInstance();
	
	}
	public void AddRestaurant(int ownerId, String name, String address, String city, String zipCode){
		
		db.addRestaurantToDatabase(ownerId, name, address, city, zipCode);
	}
}
