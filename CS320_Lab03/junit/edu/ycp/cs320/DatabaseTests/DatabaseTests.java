package edu.ycp.cs320.DatabaseTests;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.ycp.cs320.lab03.DBpersist.DatabaseProvider;
import edu.ycp.cs320.lab03.DBpersist.DerbyDatabase;
import edu.ycp.cs320.lab03.DBpersist.IDatabase;
import edu.ycp.cs320.lab03.model.Favorites;
import edu.ycp.cs320.lab03.model.Menu;
import edu.ycp.cs320.lab03.model.Order;
import edu.ycp.cs320.lab03.model.Owner;
import edu.ycp.cs320.lab03.model.Patron;
import edu.ycp.cs320.lab03.model.Restaurant;
import edu.ycp.cs320.lab03.model.User;

public class DatabaseTests {
	private IDatabase db = null;

	List<Restaurant> restList = null;
	List<User> userList = null;
	List<User> users = null; 
	ArrayList<Menu> MenuList = null;
	ArrayList<Order> OrderList = null;
	List<Favorites> favList = null;
	List<Menu> menu = null;
	Menu m = null;
	List<Order> orderList = null;
	Order o = null;


	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		// creating DB instance here
		DatabaseProvider.setInstance(new DerbyDatabase());	
		db = DatabaseProvider.getInstance();

	}



	@After
	public void tearDown() throws Exception {
	}

	/*
		@Test
		public void testLoadUser() {

		}

		@Test
		public void testLoadOwner() {

		}

		@Test
		public void testLoadRestaurant() {

		}

		@Test
		public void testLoadPatron() {

		}
	 */

	@Test
	public void getListOfRestaurantsByCity() throws Exception {
		System.out.println("\n*** Testing getListOfRestaurantsByCity ***");
		String city = "Boston TX"; 

		restList = db.getListOfRestaurantsByCity(city);

		if (restList.isEmpty()) {
			System.out.println("No book found in library with title <" + city + ">");
			fail("No book with title <" + city + "> returned from Library DB");
		}

		else {			
			ArrayList<Restaurant> rests = new ArrayList<Restaurant>();
			for (Restaurant rest : restList) {
				Restaurant restAdd = rest;
				rests.add(restAdd);
				System.out.println(rest.getName() + "," + rest.getAddress() + ", " + rest.getCity() + "," + rest.getZipCode());
			}			
		}
	}
	@Test
	public void getRestByName() throws Exception{
		System.out.println("\n*** Testing getRestByName ***");
		String name = "Bakers Donuts"; 

		restList = db.getRestByName(name);

		if (restList.isEmpty()) {
			System.out.println("No book found in library with title <" + name + ">");
			fail("No book with title <" + name + "> returned from Library DB");
		}

		else {			
			ArrayList<Restaurant> rests = new ArrayList<Restaurant>();
			for (Restaurant rest : restList) {
				Restaurant restAdd = rest;
				rests.add(restAdd);
				System.out.println(rest.getName() + "," + rest.getAddress() + ", " + rest.getCity() + "," + rest.getZipCode());
			}			
		}
	}
	@Test
	public void getListOfRestaurantsByOwner() throws Exception{
		System.out.println("\n*** Testing getListOfRestaurantsByOwner ***");
		String username = "theExpress"; 

		restList = db.getListOfRestaurantsByOwner(username);
		// NOTE: this is a simple test to check if no results were found in the DB
		if (restList.isEmpty()) {
			System.out.println("No book found in library with title <" + username + ">");
			fail("No book with title <" + username + "> returned from Library DB");
		}

		else {			
			ArrayList<Restaurant> rests = new ArrayList<Restaurant>();
			for (Restaurant rest : restList) {
				Restaurant restAdd = rest;
				rests.add(restAdd);
				System.out.println(rest.getName() + "," + rest.getAddress() + ", " + rest.getCity() + "," + rest.getZipCode());
			}			
		}
	}
	@Test
	public void addUserToDatabase() throws Exception{
		System.out.println("\n*** Testing addUserToDataBase ***");

		String name = "JimNacho56";
		String pswd = "theBest";
		String last = "Nacho";
		String first = "Jim";
		String email = "jNacho@aol.com";
		String type = "patron";


		users = db.addUserToDatabase(name, pswd, email, type, first, last);

		if (users.size() > 0)
		{

			userList = db.getAccountInfo(name);

			if (userList.isEmpty()) {
				System.out.println("No books found for author <" + last + ">");
				fail("Failed to insert new book <" + name + "> into Library DB");
			}

			else {
				System.out.println("New user (username: " + name + ") successfully added to users table: <" + first + ">");


				List<User> users = db.DeleteUserFromDatabase(name, pswd);				
			}
		}
	}

	@Test
	public void getAccountInfoTest() throws Exception {
		String Username1 = "theDonald";
		//		String Username2 = "theExpress";
		//		String Username3 = "userGuy";
		//		String Username4 = "anotherUser";
		List<User> listofUsers = new ArrayList<User>(); 

		listofUsers = db.getAccountInfo(Username1);

		if(listofUsers.size() < 0) {
			System.out.println("There are no users to retrieve information for");
			fail("We need more users!");
		}
		else {
			userList = new ArrayList<User>(); 
			for(User u: listofUsers){
				userList.add(u);
				System.out.println(u.getUserName() + "," + u.getUserId()+ ","+ u.getEmail() + "," + u.getFirstName() + "," + u.getLastName());
			}
		}
	}



	@Test 
	public void MatchUsersWithPassword() throws Exception {
		System.out.println("\n*** Testing MatchUsersWithPassword ***"); 

		String userPassword = "theDonald"; 
		List<User> userList = db.matchUsernameWithPassword(userPassword); 

		if(userList.isEmpty()){
			System.out.println("No users matched to these passwords");
			fail("No users returned from the database");
		}
		else {
			users = new ArrayList<User>(); 
			for(User u : userList) {
				users.add(u); 
				System.out.println(u.getUserName() + ", " + u.getFirstName() + ", " + u.getLastName());
			}
		}
	}

	@Test
	public void deleteUserFromDatabase() throws Exception {
		System.out.println("\n*** Testing deleteUserFromDatabase ***");

		String name = "JimNacho56";
		String pswd = "theBest";
		String last = "Nacho";
		String first = "Jim";
		String email = "jNacho@aol.com";
		String type = "patron";


		users = db.addUserToDatabase(name, pswd, email, type, first, last);

		if (users.size() > 0) {

			List<User> removedUser = db.DeleteUserFromDatabase(name, pswd);

			if (removedUser.isEmpty()) {
				System.out.println("Failed to remove User(s) for book with username <" + name + ">");
				fail("No User(s) removed from DB for  username <" + name + ">");
			}
			else {
				System.out.println("User <" + removedUser.get(0).getFirstName() + ", " + removedUser.get(0).getLastName() + "> removed from User DB");
			}					


			userList = db.getAccountInfo(name);

			if (userList.isEmpty()) {
				System.out.println("User with name <" + name + "> were removed from the Users DB");
			}
			else {
				fail("User with name <" + name + "> remains in Library DB after delete");			
			}
		}
		else {
			System.out.println("Failed to insert new User (ID: " + name + ") into User table: <" + name + ">");
			fail("Failed to insert new user <" + name + "> into Users DB");			
		}
	} 


	@Test
	public void ChangeUserNameTest() {

		System.out.println("\n*** Testing ChangerUsername ***");
		String name = "JimNacho56";
		String pswd = "theBest";
		String last = "Nacho";
		String first = "Jim";
		String email = "jNacho@aol.com";
		String type = "patron";
		String newName = "JNach";

		// insert new book (and possibly new author) into DB
		users = db.addUserToDatabase(name, pswd, email, type, first, last);

		users = db.changeUsername(name, newName, pswd);
		if (users.size() > 0) {
			userList = db.getAccountInfo(newName);
			if (userList.isEmpty()) {
				System.out.println("New username <" + newName + "> not found in table");
				fail("Failed to change userName <" + name + "> to <" + newName + "> in user DB");
			}
			else {
				System.out.println("Changed username (username: " + name + ") successfully changed name to: <" + newName + ">");

				// 
				List<User> users = db.DeleteUserFromDatabase(newName, pswd);				
			}
		}

	}

	@Test
	public void addItemToMenuTest() throws Exception {
		System.out.println("\n*** Testing addUserToDataBase ***");

		String item = "burritos";
		String price = "3.50";
		String restName = "Bakers Donuts";


		menu = db.addItemToMenu(item, price, restName);


		if (menu.size() > 0)
		{

			m = db.getPriceOfMenuItem(item);

			if (m == null) {
				System.out.println("No items called <" + item + ">");
				fail("Failed to insert new book <" + item + "> into menu DB");
			}

			else {
				System.out.println("New item (item: " + item + ") successfully added to menu table: <" + price + ">");


				Menu delMenu = db.deleteFromMenu(item);				
			}
		}

	}

	@Test
	public void getMenuByRestName() throws Exception {
		System.out.println("\n*** Testing getmenuByRestName ***");
		String rest = "Bakers Donuts"; 

		menu = db.menuByRestName(rest);
		// NOTE: this is a simple test to check if no results were found in the DB
		if (menu.isEmpty()) {
			System.out.println("No menu found for <" + rest + ">");
			fail("No menu for <" + rest + "> returned from Library DB");
		}

		else {			
			System.out.println("Menu found for (rest: " + rest);
		}
		//test getting price off menu
		assertEquals(menu.get(0).getPrice(), "1.00");


	}


	@Test
	public void getOrderTest() throws Exception {
		System.out.println("*****Testing getting orders*****");
		String name = "cDier";
		String pswd = "dorff";
		String last = "old";
		String first = "man";
		String email = "oldie@aol.com";
		String type = "patron";
		// insert new book (and possibly new author) into DB
		users = db.addUserToDatabase(name, pswd, email, type, first, last);
		this.o = new Order(); //generates order consistent number;
		int patId = users.get(0).getUserId();
		System.out.println(users.get(0).getUserId());
		String rest = "Bakers Donuts";
		//get ordernum from order object
		int orderNum = o.getorderNumber();
		String item = "Hot Dog";
		int quantity = 1;
		String price = "1.00";
		String status = "pending";
		this.orderList = db.ceateOrderInTable(patId, rest, orderNum, item, quantity, price, status);

		if(orderList.isEmpty()){
			System.out.println("No order was created for patron with id <" + patId + ">");
			fail("Failed to insert new order for patron <" + patId + "> into Favorites DB");
		}
		else{
			System.out.println("New order for patron (id: " + patId + ") successfully added to orders table: <" + orderList + ">");
		}
		//test get by confirmation number
		List<Order> orderNumChecker = new ArrayList<Order>();
		orderNumChecker = db.getOrderByConfirmationNumber(orderNum);
		assertEquals(0, orderList.get(0).getorderNumber() - orderNumChecker.get(0).getorderNumber());
		
		//Test change order Status
		String newStatus = "In Progress";
		orderList = db.updateOrderStatus(newStatus, orderNum);
			if(orderList.isEmpty()){
				System.out.println("No order was updated for orderNumber <" + orderNum + ">");
				fail("Failed to update new order for patron <" + orderNum + "> into Favorites DB");
			}
			else{
				System.out.println("New order for orderNumber (id: " + orderNum + ") successfully updated in orders table: <" + orderList.get(0).getStatus() + ">");
			}
		//test get by pat Id
		System.out.println(users.get(0).getUsername());
		List<Order> orderByPatIdChecker = null;
		orderByPatIdChecker = db.getOrderByPatronUname(users.get(0).getUsername());
		if(orderByPatIdChecker.isEmpty()){
			System.out.println("No order was created for patron with id <" + patId + ">");
			fail("Failed to retrieve order from <" + patId + "> from orders DB");
		}
		else{
			System.out.println("New order for patron (id: " + patId + ") successfully retrieved from orders table: <" + orderList + ">");
		}
		//test getting orders by rest Name
		List<Order> ordersByRestName = null;
		ordersByRestName = db.getOrdersByRestaurant(rest);
		if(ordersByRestName.isEmpty()){
			System.out.println("No order was created for patron with id <" + patId + ">");
			fail("Failed to retrieve order from <" + patId + "> from orders DB");
		}
		else{
			System.out.println("New order for rest (id: " + rest + ") successfully retrieved from orders table: <" + orderList + ">");
		}

		List<User> DelUsers = db.DeleteUserFromDatabase(name, pswd);
	}




	@Test
	public void addToFavoriteRests() {
		System.out.println("\n*** Testing addToFavoriteRests ***");

		String name = "JimNacho56";
		String pswd = "theBest";
		String last = "Nacho";
		String first = "Jim";
		String email = "jNacho@aol.com";
		String type = "patron";
		String rest = "Trumps Steaks";
		// insert new book (and possibly new author) into DB
		users = db.addUserToDatabase(name, pswd, email, type, first, last);
		favList = db.addToFavoriteRests(rest, users.get(0).getUserId());
		// check the return value - should be a book_id > 0
		if (favList.isEmpty()) {
			System.out.println("No rests found in Favorites <" + rest + ">");
			fail("Failed to insert new rest <" + rest + "> into Favorites DB");
		}
		// otherwise, the test was successful.  Now remove the user just inserted to return the DB
		// to it's original state
		else {
			System.out.println("New rest (rest: " + rest + ") successfully added to rest table: <" + rest + ">");

		}
	}

	@Test
	public void deleteMenuItemTest(){
		System.out.println("\n*** Testing addUserToDataBase ***");

		String item = "burritos";
		String price = "3.50";
		String restName = "Bakers Donuts";


		menu = db.addItemToMenu(item, price, restName);


		if (menu.size() > 0){
			Menu delMenu = db.deleteFromMenu(item);
			if(delMenu == null){
				System.out.println("Failed to remove item with name <" + item + ">");
				fail("No items removed from DB for  name <" + item + ">");
			}
			else {
				System.out.println("Item <" + item + "> removed from Menu DB");
			}					

			m = db.getPriceOfMenuItem(item);

			if (m.equals("3.50")) {
				fail("User with name <" + item + "> remains in Library DB after delete");

			}
			else {
				System.out.println("User with name <" + item + "> were removed from the Users DB");		
			}
		}
		else {
			System.out.println("Failed to insert new User (ID: " + item + ") into User table: <" + item + ">");
			fail("Failed to insert new user <" + item + "> into Users DB");			
		}
	}
}



