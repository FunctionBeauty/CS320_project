package edu.ycp.cs320.lab03.DBpersist;

import java.util.List;

import edu.ycp.cs320.lab03.model.Favorites;
import edu.ycp.cs320.lab03.model.Menu;
import edu.ycp.cs320.lab03.model.Order;
import edu.ycp.cs320.lab03.model.Restaurant;
import edu.ycp.cs320.lab03.model.User;



public interface IDatabase {

	List<Restaurant> getListOfRestaurantsByCity(String name);
	
	List<Restaurant> getRestByName(String name);

	List<User> matchUsernameWithPassword(String name);
	
    List<User> getAccountInfo(String name);

	List<User> addUserToDatabase(String name, String pswd, String email, String type, String first, String last);

	List<User> DeleteUserFromDatabase(String name, String pswd);

	List<User> changeUsername(String name, String newName, String pswd);

	List<Menu> addItemToMenu(String item, String newItemPrice, String rest_id);

	List<Restaurant> getListOfRestaurantsByOwner(String username);
	
	List<Menu> menuByRestName(String rest);

	Menu getPriceOfMenuItem(String item);

	List<Order> ceateOrderInTable(int patId, String rest, int orderNum, String item, int quantity, String price, String status);

	List<Order> getOrderByConfirmationNumber(Integer orderNumber);

	Menu deleteFromMenu(String item);

	List<Order> getOrdersByRestaurant(String rest);
	
	List<Order> updateOrderStatus(String status, int orderNum);

	List<Favorites> addToFavoriteRests(String rest, Integer userId);

	List<Favorites> getFromFavorites(Integer userId);

	List<Order> getOrderByPatronUname(int patId);

	List<Restaurant> addRestaurantToDatabase(int ownerId, String name, String address, String city, String zipCode);
	
}