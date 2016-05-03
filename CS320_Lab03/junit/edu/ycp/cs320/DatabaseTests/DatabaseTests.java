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
		// NOTE: this is a simple test to check if no results were found in the DB
		if (restList.isEmpty()) {
			System.out.println("No book found in library with title <" + city + ">");
			fail("No book with title <" + city + "> returned from Library DB");
		}
		// NOTE: assembling the results into Author and Book lists so that they could be
		//       inspected for correct content - well-formed objects with correct content
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
		// NOTE: this is a simple test to check if no results were found in the DB
		if (restList.isEmpty()) {
			System.out.println("No book found in library with title <" + name + ">");
			fail("No book with title <" + name + "> returned from Library DB");
		}
		// NOTE: assembling the results into Author and Book lists so that they could be
		//       inspected for correct content - well-formed objects with correct content
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
		String username = "theDonald"; 
		
		restList = db.getListOfRestaurantsByOwner(username);
		// NOTE: this is a simple test to check if no results were found in the DB
		if (restList.isEmpty()) {
			System.out.println("No book found in library with title <" + username + ">");
			fail("No book with title <" + username + "> returned from Library DB");
		}
		// NOTE: assembling the results into Author and Book lists so that they could be
		//       inspected for correct content - well-formed objects with correct content
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
				
		// insert new book (and possibly new author) into DB
		users = db.addUserToDatabase(name, pswd, email, type, first, last);

		// check the return value - should be a book_id > 0
		if (users.size() > 0)
		{
			// try to retrieve the book and author from the DB
			// get the list of (Author, Book) pairs from DB
			userList = db.getAccountInfo(name);
			
			if (userList.isEmpty()) {
				System.out.println("No books found for author <" + last + ">");
				fail("Failed to insert new book <" + name + "> into Library DB");
			}
			// otherwise, the test was successful.  Now remove the book just inserted to return the DB
			// to it's original state, except for using an author_id and a book_id
			else {
				System.out.println("New user (username: " + name + ") successfully added to users table: <" + first + ">");
				
				// now delete Book (and its Author) from DB
				// leaving the DB in its previous state - except that an author_id, and a book_id have been used
				List<User> users = db.DeleteUserFromDatabase(name, pswd);				
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
				
		// insert new book (and possibly new author) into DB
		users = db.addUserToDatabase(name, pswd, email, type, first, last);
		// check to see that insertion was successful before proceeding
		if (users.size() > 0) {
			// now delete Book (and its Author) from DB
			List<User> removedUser = db.DeleteUserFromDatabase(name, pswd);
			
			if (removedUser.isEmpty()) {
				System.out.println("Failed to remove User(s) for book with username <" + name + ">");
				fail("No User(s) removed from DB for  username <" + name + ">");
			}
			else {
				System.out.println("User <" + removedUser.get(0).getFirstName() + ", " + removedUser.get(0).getLastName() + "> removed from User DB");
			}					
			
			// get the list of (Author, Book) pairs from DB
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
		//SetUp
		User TestUser = new User(); 
		TestUser.setUserName("Imma Boss");
		TestUser.setPassWord("password");

		String newName = "I am a Boss"; 

		//Why am I getting an unused error here. 
		List<User> userlist = db.addUserToDatabase(TestUser.getUserName(), TestUser.getPassWord(), TestUser.getEmail(), TestUser.getAccountType(), TestUser.getFirstName(), TestUser.getLastName());

		userlist = db.changeUsername(TestUser.getUserName(), newName, TestUser.getPassWord()); 


		assertEquals("I am a Boss", TestUser.getUserName());

		//Only print the message when the operation is successful. 
		if(TestUser.getUserName() == newName) {
			System.out.println("\n*** Changing Username ***");
		}
	}

	@Test
	public void addItemToMenuTest() throws Exception {
		//Set up
		Restaurant r = new Restaurant(); 
		Menu menu = new Menu(); 
		String item1 = "pizza";
		String item2 = "hotdog";
		String item3 = "Special Brownies"; 
		String price1 = "10.99";
		String price2 ="3.00";
		String price3 = "5.00";
		//These caused a null pointer exception in the test. 
		//r.getMenu().addToMenu(item1, 10.99);
		//r.getMenu().addToMenu(item2, 4.99);
		//r.getMenu().addToMenu(item3, 29.99);

		
		
		//I will work on fixing these tests tomorrow - 5/2/2016
		List<Menu> menulist = db.addItemToMenu(item1, menu.getItemPrice(item1), r.getRestID());
		menulist = db.addItemToMenu(item2, menu.getItemPrice(item2), r.getRestID());
		menulist = db.addItemToMenu(item3, menu.getItemPrice(item3), r.getRestID());

		//Test to make sure that the items were added. 
		assertEquals(10.99, menulist.get(0).getItemPrice(item1), 0.001);
		assertEquals(4.99, menulist.get(1).getItemPrice(item2), 0.001);
		assertEquals(29.99, menulist.get(2).getItemPrice(item3), 0.001);

		assertEquals(3, menulist.size());
		if(menulist.isEmpty()) {
			System.out.println("Error. No items were added to the menu");
			fail("Something has gone terribly wrong"); 
		}
		else {
			MenuList= new ArrayList<Menu>(); 
			for(Menu m: MenuList) {
				MenuList.add(m);
				System.out.println("Adding Items"); 
			}
		}


	}

	@Test
	public void getMenuByRestName() throws Exception {
		//Set up
		Restaurant Rest = new Restaurant(); 
		String desiredName = "PizzaPain"; 
		Rest.setName("PizzaPain");

		List<Menu> menulist = new ArrayList<Menu>(); 
		menulist = db.menuByRestName(desiredName);

		if(menulist.isEmpty()) {
			System.out.println("No menus to find");
			fail("Manuel! Go get the Menus!");
		}
		else {
			for(Menu M: MenuList) {
				MenuList.add(M); //this needs to be revised I know it is wrong. 
				System.out.println("Retrieving Menu");
			}
		}

	}

	//@Test
	public void getPriceOffMenuTest() throws Exception {
		//Set up
		Menu m = new Menu();
		String item1 = "Pizza"; 
		String item2 = "Hamburger";
		String item3 = "Hit in Head";

		m.addToMenu(item1, 7.99);
		m.addToMenu(item2, 6.99);
		m.addToMenu(item3, 0.99);

		List<Menu> menulist = new ArrayList<Menu>();

		//Operations and conditions 
		menulist = (List<Menu>) db.getPriceOfMenuItem(item1); 
		assertEquals(7.99, menulist.get(0).getItemPrice(item1), 0.001);

		menulist = (List<Menu>) db.getPriceOfMenuItem(item2);
		assertEquals(6.99, menulist.get(1).getItemPrice(item2), 0.001);

		menulist = (List<Menu>) db.getPriceOfMenuItem(item3);
		assertEquals(6.99, menulist.get(2).getItemPrice(item3), 0.001);

		if(menulist.isEmpty()){
			System.out.println("There is no menu present");
			fail("This has gotten rather silly"); 
		}
		//need another condition here for returns.	
	}

	//@Test
	public void createOrderInTableTest() {

		Order o = new Order();
		Order o2 = new Order();
		Order o3 = new Order();
		User p = new User(); 
		User p2= new User(); 
		Restaurant r = new Restaurant();  
		Restaurant r2 = new Restaurant();  

		List<Order> orderList = new ArrayList<Order>(); 
		
		//I will work on fixing these tests tomorrow - 5/2/2016
		orderList = db.ceateOrderInTable(p.getUserId(), o.getorderNumber(), r.getMenu().getItem(), r.getMenu().getItemPrice(r.getMenu().getItem()));
		assertEquals(1, orderList.size());
		orderList = db.ceateOrderInTable(p.getUserId(), o2.getorderNumber(), r.getMenu().getItem(), r.getMenu().getItemPrice(r.getMenu().getItem()));
		assertEquals(2, orderList.size());
		orderList = db.ceateOrderInTable(p2.getUserId(), o3.getorderNumber(), r2.getMenu().getItem(), r2.getMenu().getItemPrice(r2.getMenu().getItem()));
		assertEquals(1, orderList.size());

		if(orderList.isEmpty()) {
			System.out.println("Why is no one ordering anything!?");
			fail("Manuel!!");
		}
		else {
			for (Order O: OrderList){
				OrderList.add(O); 
				System.out.println("Creating Orders"); 
			}
		}


	}

	public void getAccountInfoTest() throws Exception {
		String Username1 = "theDonald";
		String Username2 = "theExpress";
		String Username3 = "userGuy";
		String Username4 = "anotherUser";
		List<User> listofUsers = new ArrayList<User>(); 
		
		listofUsers = db.getAccountInfo(Username1);
		assertEquals(1, listofUsers.size());
		
		listofUsers = db.getAccountInfo(Username2);
		assertEquals(2, listofUsers.size());
		
		listofUsers = db.getAccountInfo(Username3);
		assertEquals(3, listofUsers.size());
		
		listofUsers = db.getAccountInfo(Username4);
		assertEquals(4, listofUsers.size());
		
		if(listofUsers.isEmpty()) {
			System.out.println("There are no users to retrieve information for");
			fail("We need more users!");
		}
		else {
			userlist = new ArrayList<User>(); 
			for(User u: listofUsers){
				userlist.add(u);
				System.out.println(u.getUserName() + "," + u.getUserId()+ ","+ u.getEmail() + "," + u.getFirstName() + "," + u.getLastName());
			}
		}
	}



	public void getOrderByConfirmNumber() throws Exception {

		Integer ConfirmNumber = 1345;
		List<Order> orderList = new ArrayList<Order>(); 

		orderList = db.getOrderByConfirmationNumber(ConfirmNumber);
		assertEquals(1, orderList.size());

		if(orderList.isEmpty()) {
			System.out.println("There are no orders with this number");
			fail("Re enter the order number");
		}
		else{
			OrderList = new ArrayList<Order>(); 
			for(Order o: orderList) {
				OrderList.add(o);
				System.out.println("Adding orders to the database");
			}
		}
	}

	public void deleteFromMenuTest() {
		//Need to add something in order to remove it 
		String item2 = "Special Brownies"; 
		Menu testMenu = new Menu(); 

		String Rest_ID = "Tom's Grill"; 
		List<Menu> menulist = new ArrayList<Menu>(); 

		menulist = db.addItemToMenu(item2, 4.99, Rest_ID);

		//This is more like a regular JUnit test. 
		//The function looks like it returns items, not removes them.
		testMenu = db.deleteFromMenu(item2); 

	}

	public void getOrdersFromRestaurantTest() throws Exception{
		//Need to run the application to see how many orders there are. 
		String RestName = "Trump's Steaks"; 
		List<Order> orderlist = new ArrayList<Order>(); 

		orderlist = db.getOrdersByRestaurant(RestName); 
		assertEquals(2, orderlist.size()); 

		if(orderlist.isEmpty()) {
			System.out.println("There are no orders from that restaurant. Perhaps you can be the first");
			fail("Order something, then this will be populated"); 
		}
		else {
			OrderList = new ArrayList<Order>(); 
			for (Order o: orderlist) {
				OrderList.add(o);
				System.out.println("Adding orders to the list");
			}
		}
	}


	public void getOrderByPatronName() throws Exception{
		//Another one ill need to run the application to ensure the tests fully run. 
		String patronName = "userGuy"; 
		List<Order> orderlist = new ArrayList<Order>(); 
		
		orderlist = db.getOrderByPatronUname(patronName);
		assertEquals(2, orderlist.size()); 
		
		if(orderlist.isEmpty()) {
			System.out.println("This user has no orders");
			fail("Maybe this user shouldn't be such a slacker");
		}
		
		else{
			OrderList = new ArrayList<Order>(); 
			for (Order o: orderlist) {
				OrderList.add(o);
				System.out.println("Adding user orders to the database");
			}
		}
	}

	public void UpdateOrderStatusTest() {
		Order testOrder = new Order(); 
		List<Order> orderlist = new ArrayList<Order>(); 
		orderlist = db.updateOrderStatus(testOrder.getStatus(), testOrder.getorderNumber());
		if(orderlist.isEmpty()) {
			System.out.println("There are no orders to update");
			fail("Manuel!");
		}
		else {
			OrderList = new ArrayList<Order>(); 
			for(Order o : orderlist) {
				OrderList.add(o);
				System.out.println("");
			}
		}
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
			// otherwise, the test was successful.  Now remove the book just inserted to return the DB
			// to it's original state, except for using an author_id and a book_id
			else {
				System.out.println("New rest (rest: " + rest + ") successfully added to rest table: <" + rest + ">");
								
			}
		}
	
}


