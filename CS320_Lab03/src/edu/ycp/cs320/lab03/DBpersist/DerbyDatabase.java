package edu.ycp.cs320.lab03.DBpersist;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import edu.ycp.cs320.lab03.model.Favorites;
import edu.ycp.cs320.lab03.model.Menu;
import edu.ycp.cs320.lab03.model.Order;
import edu.ycp.cs320.lab03.model.Restaurant;
import edu.ycp.cs320.lab03.model.User;

public class DerbyDatabase implements IDatabase {
	static {
		try {
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
		} catch (Exception e) {
			throw new IllegalStateException("Could not load Derby driver");
		}
	}

	private interface Transaction<ResultType> {
		public ResultType execute(Connection conn) throws SQLException;
	}

	private static final int MAX_ATTEMPTS = 100;
	//*********************************
	//find restaurants by a given city
	//*********************************
	@Override
	public List<Restaurant> getListOfRestaurantsByCity(final String city) {
		return executeTransaction(new Transaction<List<Restaurant>>() {
			@Override
			public List<Restaurant> execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;

				try {


					stmt = conn.prepareStatement(
							"select * from restaurants " +
									" where rest_city = ? "
							);
					stmt.setString(1, city);
					List<Restaurant> result = new ArrayList<Restaurant>();
					resultSet = stmt.executeQuery();

					// for testing that a result was returned
					Boolean found = false;

					while (resultSet.next()) {
						found = true;

						Restaurant rest = new Restaurant();
						loadRestaurant(rest, resultSet, 1);
						result.add(rest);
					}

					// check if the title was found
					if (!found) {
						System.out.println("<" + city + "> was not found in the restaurants table");
					}

					return result;


				} finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}
	//*********************************
		//find restaurants by a given name
		//*********************************
		@Override
		public List<Restaurant> getRestByName(final String name) {
			return executeTransaction(new Transaction<List<Restaurant>>() {
				@Override
				public List<Restaurant> execute(Connection conn) throws SQLException {
					PreparedStatement stmt = null;
					ResultSet resultSet = null;

					try {


						stmt = conn.prepareStatement(
								"select * from restaurants " +
										" where rest_name = ? "
								);
						stmt.setString(1, name);
						List<Restaurant> result = new ArrayList<Restaurant>();
						resultSet = stmt.executeQuery();

						// for testing that a result was returned
						Boolean found = false;

						while (resultSet.next()) {
							found = true;

							Restaurant rest = new Restaurant();
							loadRestaurant(rest, resultSet, 1);
							result.add(rest);
						}

						// check if the title was found
						if (!found) {
							System.out.println("<" + name + "> was not found in the restaurants table");
						}

						return result;


					} finally {
						DBUtil.closeQuietly(resultSet);
						DBUtil.closeQuietly(stmt);
					}
				}
			});
		}
	@Override
	public List<Restaurant> getListOfRestaurantsByOwner(final String username) {
		return executeTransaction(new Transaction<List<Restaurant>>() {
			@Override
			public List<Restaurant> execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;

				try {


					stmt = conn.prepareStatement(
							"select restaurants.* from restaurants, users " +
							" where users.user_userName = ? " +
							" and users.user_id = restaurants.user_id"
							);
					stmt.setString(1, username);
					List<Restaurant> result = new ArrayList<Restaurant>();
					resultSet = stmt.executeQuery();

					// for testing that a result was returned
					Boolean found = false;

					while (resultSet.next()) {
						found = true;

						Restaurant rest = new Restaurant();
						loadRestaurant(rest, resultSet, 1);
						result.add(rest);
					}

					// check if the title was found
					if (!found) {
						System.out.println("<" + username + "> was not found in the restaurants table");
					}

					return result;


				} finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}
	//********************
	//adding a user to the database
	//********************
	@Override
	public List<User> addUserToDatabase(final String name, final String pswd, final String email, final String type, final String first,
			final String last) {
		return executeTransaction(new Transaction<List<User>>() {
			@Override
			public List<User> execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				PreparedStatement stmt2 = null;
				ResultSet resultSet = null;

				try {
					stmt = conn.prepareStatement(
							"insert into users(user_userName, user_passWord, user_email, user_accountType, user_firstName, user_lastName) " +
									" values(?, ?, ?, ?, ?, ?) "
							);
					stmt.setString(1, name);
					stmt.setString(2, pswd);
					stmt.setString(3, email);
					stmt.setString(4, type);
					stmt.setString(5, first);
					stmt.setString(6, last);
					stmt.executeUpdate();
					
					stmt2 = conn.prepareStatement(
							"select * " +
									" from users " +
									" where user_userName = ?"
							);
					stmt2.setString(1, name);
					
					resultSet = stmt2.executeQuery();

					// for testing that a result was returned
					Boolean found = false;
					List<User> result = new ArrayList<User>();
					while (resultSet.next()) {
						found = true;
						User u = new User();
						loadUser(u, resultSet, 1);
						result.add(u);
					}

					// check if the title was found
					if (!found) {
						System.out.println("<" + name + "> was not found in the users table");
					}

					return result;


				} finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
					DBUtil.closeQuietly(stmt2);
				}
			}
		});
	}
	//*************************************************
	//match user name with password for authentication
	//*************************************************
	@Override
	public List<User> matchUsernameWithPassword(final String name) {
		
		return executeTransaction(new Transaction<List<User>>() {
			@Override
			public List<User> execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;

				try {


					stmt = conn.prepareStatement(
							"select * from Users " +
									" where user_userName = ? "
							);
					stmt.setString(1, name);
					List<User> result = new ArrayList<User>();
					resultSet = stmt.executeQuery();

					// for testing that a result was returned
					Boolean found = false;

					while (resultSet.next()) {
						found = true;

						User u = new User();
						loadUser(u, resultSet, 1);
						result.add(u);
					}

					// check if the title was found
					if (!found) {
						System.out.println("<" + name + "> was not found in the Users table");
					}

					return result;


				} finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}


	//********************************
	//Delete a user from the database
	//********************************
	@Override
	public List<User> DeleteUserFromDatabase(final String name, final String pswd) {
		return executeTransaction(new Transaction<List<User>>() {
			@Override
			public List<User> execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				PreparedStatement stmt2 = null; 
				ResultSet resultSet = null;

				try {

					stmt2 = conn.prepareStatement(
							"select * from users " +
									"where user_userName = ?"
							);
					stmt2.setString(1, name);
					resultSet = stmt2.executeQuery();
					List<User> result = new ArrayList<User>();
					
					Boolean found = false;

					while (resultSet.next()) {
						found = true;

						User u = new User();
						loadUser(u, resultSet, 1);
						result.add(u);
					}
					
					stmt = conn.prepareStatement(
							"delete from users " +
									" where user_Id = ? "
							);
					stmt.setInt(1, result.get(0).getUserId());
//					stmt.setString(2, result.get(0).getUsername());
//					stmt.setString(3, result.get(0).getPassWord());
//					stmt.setString(4, result.get(0).getEmail());
//					stmt.setString(5, result.get(0).getAccountType());
//					stmt.setString(6, result.get(0).getFirstName());
//					stmt.setString(7, result.get(0).getLastName());
					stmt.executeUpdate();

					// return all users and see that the one entered was deleted
					

					// check if the title was found
					if (!found) {
						System.out.println("<" + name + "> users list is empty");
					}

					return result;


				} finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
					DBUtil.closeQuietly(stmt2);
				}
			}
		});
	}
	//************************
	//Change username
	//***********************
	@Override
	public List<User> changeUsername(final String name, final String newName, final String pswd) {
		return executeTransaction(new Transaction<List<User>>() {
			@Override
			public List<User> execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				PreparedStatement stmt2 = null;
				ResultSet resultSet = null;

				try {


					stmt = conn.prepareStatement(
							"update users " +
									" set user_userName = ? " +
									" where user_userName = ? " +
									" and user_passWord = ? "
							);
					stmt.setString(1, newName);
					stmt.setString(2, name);
					stmt.setString(3, pswd);
					stmt.executeUpdate();
					System.out.printf("Querry Completed: Update user's name");
					// return all users and see that the one entered was deleted
					
					stmt2 = conn.prepareStatement(
							"select * from users " 	+
									" where user_userName = ? "
							);
					//ensure new userName is in database
					stmt2.setString(1, newName);
					resultSet = stmt2.executeQuery();
					System.out.printf("Where does the querry die?");
					List<User> result = new ArrayList<User>();
					
					Boolean found = false;

					while (resultSet.next()) {
						found = true;

						User u = new User();
						loadUser(u, resultSet, 1);
						result.add(u);
					}

					// check if the title was found
					if (!found) {
						System.out.println("<" + name + "> users list is empty");
					}

					return result;


				} finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
					DBUtil.closeQuietly(stmt2);
				}
			}
		});
	}

	@Override
	public List<User> getAccountInfo(final String name) {
		
		return executeTransaction(new Transaction<List<User>>() {
			@Override
			public List<User> execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;
				try {
					stmt = conn.prepareStatement(
							"select * from Users " +
									" where user_userName = ? "
							);
					stmt.setString(1, name);
					List<User> result = new ArrayList<User>();
					resultSet = stmt.executeQuery();
					Boolean found = false;
					while (resultSet.next()) {
						found = true;

						User u = new User();
						loadUser(u, resultSet, 1);
						result.add(u);
					}

					// check if the title was found
					if (!found) {
						System.out.println("<" + name + "> was not found in the Users table");
					}

					return result;


				} finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}
	//***********************
	//Add item to menu
	//************************
	@Override
	public List<Menu> addItemToMenu(final String item, final String price, final String rest_name) {
		return executeTransaction(new Transaction<List<Menu>>() {
			@Override
			public List<Menu> execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				PreparedStatement stmt2 = null;
				PreparedStatement stmt3 = null;
				ResultSet resultSet = null;
				ResultSet resultSet2 = null;


				try {
					stmt = conn.prepareStatement(
							"select rest_id from restaurants " +
									" where rest_name = ? "
							);
					stmt.setString(1, rest_name);
					resultSet = stmt.executeQuery();
					int rest_id = 0;
					if(resultSet.next()) {
						rest_id = resultSet.getInt(1);
					}
					stmt2 = conn.prepareStatement(
							"insert into menu(rest_id, menu_item, menu_price) " +
									" values(?, ?, ?) "
							);
					stmt2.setInt(1, rest_id);
					stmt2.setString(2, item);
					stmt2.setString(3, price);
					stmt2.executeUpdate();
					
					stmt3 = conn.prepareStatement(
							"select * " +
									" from menu " +
									" where menu_item = ?"
							);
					stmt3.setString(1, item);
					
					resultSet2 = stmt3.executeQuery();

					// for testing that a result was returned
					Boolean found = false;
					List<Menu> result = new ArrayList<Menu>();
					while (resultSet2.next()) {
						found = true;
						Menu m = new Menu();
						loadMenu(m, resultSet2, 1);
						result.add(m);
					}

					// check if the title was found
					if (!found) {
						System.out.println("<" + item + "> was not found in the menu table");
					}

					return result;


				} finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
					DBUtil.closeQuietly(stmt2);
					DBUtil.closeQuietly(stmt3);
				}
			}
		});
	}
	//*************************************
	//delete item from menu
	//*************************************
	@Override
	public Menu deleteFromMenu(final String item) {
		return executeTransaction(new Transaction<Menu>() {
			@Override
			public Menu execute(Connection conn) throws SQLException {
//				PreparedStatement stmt = null; unused
				PreparedStatement stmt2 = null;
				PreparedStatement stmt3 = null;
				ResultSet resultSet = null;
				ResultSet resultSet2 = null;

				try {
					
					
					stmt2 = conn.prepareStatement(
							" delete from menu " +
									" where menu_item = ?"
							);
					stmt2.setString(1, item);
					
					stmt2.executeUpdate();
					
					stmt3 = conn.prepareStatement(
							"select * " +
									" from menu " +
									" where menu_item = ?"
							);
					stmt3.setString(1, item);
					
					resultSet2 = stmt3.executeQuery();

					// for testing that a result was returned
					Boolean found = false;
					Menu result = new Menu();
					while (resultSet2.next()) {
						found = true;
						Menu m = new Menu();
						loadMenu(m, resultSet, 1);
						result = m;
					}

					// check if the title was found
					if (!found) {
						System.out.println("<" + item + "> was not found in the menu table");
					}

					return result;


				} finally {
					DBUtil.closeQuietly(resultSet2);
					DBUtil.closeQuietly(stmt2);
					DBUtil.closeQuietly(stmt3);
				}
			}
		});
		
	}
	

	//************************************
	//Show user a menu
	//**************************************
	@Override
	public List<Menu> menuByRestName(final String rest) {
		return executeTransaction(new Transaction<List<Menu>>() {
			@Override
			public List<Menu> execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;

				try {


					stmt = conn.prepareStatement(
							"select menu.*  " +
									" from menu, restaurants "  +
									" where rest_name = ? "  +
									" and menu.rest_id = restaurants.rest_id "
							);
					stmt.setString(1, rest);
					List<Menu> result = new ArrayList<Menu>();
					resultSet = stmt.executeQuery();

					// for testing that a result was returned
					Boolean found = false;

					while (resultSet.next()) {
						found = true;

						Menu m = new Menu();
						loadMenu(m, resultSet, 1);
						result.add(m);
					}

					// check if the title was found
					if (!found) {
						System.out.println("<" + rest + "> was not found in the menu table");
					}

					return result;


				} finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}
	//***********************************************
	//Get the price of an item to build an order
	//***********************************************
	@Override
	public Menu getPriceOfMenuItem(final String item) {
		return executeTransaction(new Transaction<Menu>() {
			@Override
			public Menu execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;

				try {


					stmt = conn.prepareStatement(
							"select menu.*  " +
									" from menu "  +
									" where menu_item = ? "  
							);
					stmt.setString(1, item);
					Menu result = new Menu();
					resultSet = stmt.executeQuery();

					// for testing that a result was returned
					Boolean found = false;

					while (resultSet.next()) {
						found = true;

						Menu m = new Menu();
						loadMenu(m, resultSet, 1);
						result = m;
					}

					// check if the title was found
					if (!found) {
						System.out.println("<" + item + "> was not found in the menu table");
					}

					return result;


				} finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}
	//******************************************
	//build an order with items and prices
	//******************************************
	@Override
	public List<Order> ceateOrderInTable(final int patId, final String rest, final int orderNum, final String item, final int quantity, final String price, final String status) {
		return executeTransaction(new Transaction<List<Order>>() {
			@Override
			public List<Order> execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				PreparedStatement stmt2 = null;
				ResultSet resultSet = null;

				try {
					stmt = conn.prepareStatement(
							" insert into orders(patron_id, rest_name, order_number, item, quantity, price, status) " +
									" values(?, ?, ?, ?, ?, ?, ?) "
							);
					stmt.setInt(1, patId);
					stmt.setString(2, rest);
					stmt.setInt(3, orderNum);
					stmt.setString(4, item);
					stmt.setInt(5, quantity);
					stmt.setString(6, price);
					stmt.setString(7, status);
					stmt.executeUpdate();
					
					stmt2 = conn.prepareStatement(
							" select * from orders " +
									" where order_number = ? " +
									" and patron_id = ? "      +
									" and rest_name = ? "
							);
					stmt2.setInt(1, orderNum);
					stmt2.setInt(2, patId);
					stmt2.setString(3, rest);
					

					resultSet = stmt2.executeQuery();

					// for testing that a result was returned
					Boolean found = false;
					List<Order> result = new ArrayList<Order>();
					while (resultSet.next()) {
						found = true;
						Order o = new Order();
						loadOrder(o, resultSet, 1);
						result.add(o);
					}

					// check if the title was found
					if (!found) {
						System.out.println("<" + item + "> was not found in the menu table");
					}

					return result;


				} finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
					DBUtil.closeQuietly(stmt2);
				}
			}
		});
	}
	//********************************************************
	//send back an order based on the confirmation number
	//**********************************************************
	@Override
	public List<Order> getOrderByConfirmationNumber(final Integer orderNumber) {
		return executeTransaction(new Transaction<List<Order>>() {
			@Override
			public List<Order> execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;

				try {


					stmt = conn.prepareStatement(
							"select orders.*  " +
									" from orders "  +
									" where order_number = ? "  
							);
					stmt.setInt(1, orderNumber);
					List<Order> result = new ArrayList<Order>();
					resultSet = stmt.executeQuery();

					// for testing that a result was returned
					Boolean found = false;

					while (resultSet.next()) {
						found = true;

						Order o = new Order();
						loadOrder(o, resultSet, 1);
						result.add(o);
					}

					// check if the title was found
					if (!found) {
						System.out.println("<" + orderNumber + "> was not found in the orders table");
					}

					return result;


				} finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
				}
			}
		});
		
	}
	//****************************
	//get orders for a restaurant
	//***************************
	@Override
	public List<Order> getOrdersByRestaurant(final String rest) {
		return executeTransaction(new Transaction<List<Order>>() {
			@Override
			public List<Order> execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;

				try {


					stmt = conn.prepareStatement(
							"select orders.*  " +
									" from orders "  +
									" where rest_name = ? "  
							);
					stmt.setString(1, rest);
					List<Order> result = new ArrayList<Order>();
					resultSet = stmt.executeQuery();

					// for testing that a result was returned
					Boolean found = false;

					while (resultSet.next()) {
						found = true;

						Order o = new Order();
						loadOrder(o, resultSet, 1);
						result.add(o);
					}

					// check if the title was found
					if (!found) {
						System.out.println("<" + rest + "> had no orders for their food");
					}

					return result;


				} finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}
	//*****************************************************
	//view past orders by a patron using the patron/user Id
	//****************************************************
	@Override
	public List<Order> getOrderByPatronUname(final String username) {
		return executeTransaction(new Transaction<List<Order>>() {
			@Override
			public List<Order> execute(Connection conn) throws SQLException {
				//PreparedStatement stmt = null; unused
				PreparedStatement stmt1 = null;
				PreparedStatement stmt = null;
				ResultSet resultSet = null;
				ResultSet resultSet1 = null;
				

				try {
					//first, get patron id from users table 
					//patron id is not available from jsp in this situation
					stmt1 = conn.prepareStatement(
							"select user_id" +
									" from users " +
									" where user_userName = ?"
							);
					stmt1.setString(1, username);
					int patId = 0;
					resultSet = stmt1.executeQuery();
					while (resultSet.next()) {
						patId = resultSet.getInt(1);
					}
					
					//get order by order id
					stmt = conn.prepareStatement(
							"select orders.*  " +
									" from orders "  +
									" where patron_id = ? "  
							);
					stmt.setInt(1, patId);
					List<Order> result = new ArrayList<Order>();
					resultSet1 = stmt.executeQuery();

					// for testing that a result was returned
					Boolean found = false;

					while (resultSet1.next()) {
						found = true;

						Order o = new Order();
						loadOrder(o, resultSet1, 1);
						result.add(o);
					}

					// check if the title was found
					if (!found) {
						System.out.println("<" + patId + "> was not found in the orders table");
					}

					return result;


				} finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(resultSet1);
					DBUtil.closeQuietly(stmt);
					DBUtil.closeQuietly(stmt1);
				}
			}
		});
		
	}
	/*********************
	 * Update Order Status
	 **********************/
	@Override
	public List<Order> updateOrderStatus(final String status, final int orderNum) {
		return executeTransaction(new Transaction<List<Order>>() {
			@Override
			public List<Order> execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				PreparedStatement stmt2 = null;
				ResultSet resultSet = null;

				try {


					stmt = conn.prepareStatement(
							"update orders " +
									" set status = ? " +
									" where order_number = ? "
							);
					stmt.setString(1, status);
					stmt.setInt(2, orderNum);
					stmt.executeUpdate();

					// return all users and see that the one entered was deleted
					
					stmt2 = conn.prepareStatement(
							"select * from users " 	+
									" where user_userName = ? "
							);
					//ensure new userName is in database
					stmt2.setString(1, status);
					resultSet = stmt2.executeQuery();
					List<Order> result = new ArrayList<Order>();
					
					Boolean found = false;

					while (resultSet.next()) {
						found = true;

						Order o = new Order();
						loadOrder(o, resultSet, 1);
						result.add(o);
					}

					// check if the title was found
					if (!found) {
						System.out.println("<> users list is empty");
					}

					return result;


				} finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
					DBUtil.closeQuietly(stmt2);
				}
			}
		});
	}
	//********************************************
		//patron adds a restaurant to their favorites 
		//********************************************
		@Override
		public List<Favorites> getFromFavorites(final Integer userId) {
			return executeTransaction(new Transaction<List<Favorites>>() {
				@Override
				public List<Favorites> execute(Connection conn) throws SQLException {
					PreparedStatement stmt = null;
					PreparedStatement stmt2 = null;
					ResultSet resultSet = null;
					//add new restaurant to db
					try {
						stmt2 = conn.prepareStatement(
								"select * " +
										" from favRests " +
										" where patron_id = ?"
								);
						stmt2.setInt(1, userId);
						
						resultSet = stmt2.executeQuery();

						// for testing that a result was returned
						Boolean found = false;
						List<Favorites> result = new ArrayList<Favorites>();
						while (resultSet.next()) {
							found = true;
							Favorites u = new Favorites();
							loadFavoriteRest(u, resultSet, 1);
							result.add(u);
						}

						// check if the title was found
						if (!found) {
							System.out.println("<> was not found in the Restaurants table");
						}

						return result;


					} finally {
						DBUtil.closeQuietly(resultSet);
						DBUtil.closeQuietly(stmt);
						DBUtil.closeQuietly(stmt2);
					}
				}
			});
		}
	//********************************************
	//patron adds a restaurant to their favorites 
	//********************************************
	@Override
	public List<Favorites> addToFavoriteRests(final String rest, final Integer userId) {
		return executeTransaction(new Transaction<List<Favorites>>() {
			@Override
			public List<Favorites> execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				PreparedStatement stmt2 = null;
				ResultSet resultSet = null;
				//add new restaurant to db
				try {
					stmt = conn.prepareStatement(
							"insert into favRests(patron_id, rest_name) " +
									" values(?, ?) "
							);
					stmt.setInt(1, userId);
					stmt.setString(2, rest);
					
					stmt.executeUpdate();
					//stmt to return object
					stmt2 = conn.prepareStatement(
							"select * " +
									" from favRests " +
									" where patron_id = ?"
							);
					stmt2.setInt(1, userId);
					
					resultSet = stmt2.executeQuery();

					// for testing that a result was returned
					Boolean found = false;
					List<Favorites> result = new ArrayList<Favorites>();
					while (resultSet.next()) {
						found = true;
						Favorites u = new Favorites();
						loadFavoriteRest(u, resultSet, 1);
						result.add(u);
					}

					// check if the title was found
					if (!found) {
						System.out.println("<" + rest + "> was not found in the Restaurants table");
					}

					return result;


				} finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
					DBUtil.closeQuietly(stmt2);
				}
			}
		});
	}
	
		
	public<ResultType> ResultType executeTransaction(Transaction<ResultType> txn) {
		try {
			return doExecuteTransaction(txn);
		} catch (SQLException e) {
			throw new PersistenceException("Transaction failed", e);
		}
	}

	public<ResultType> ResultType doExecuteTransaction(Transaction<ResultType> txn) throws SQLException {
		Connection conn = connect();

		try {
			int numAttempts = 0;
			boolean success = false;
			ResultType result = null;

			while (!success && numAttempts < MAX_ATTEMPTS) {
				try {
					result = txn.execute(conn);
					conn.commit();
					success = true;
				} catch (SQLException e) {
					if (e.getSQLState() != null && e.getSQLState().equals("41000")) {
						// Deadlock: retry (unless max retry count has been reached)
						numAttempts++;
					} else {
						// Some other kind of SQLException
						throw e;
					}
				}
			}

			if (!success) {
				throw new SQLException("Transaction failed (too many retries)");
			}

			// Success!
			return result;
		} finally {
			DBUtil.closeQuietly(conn);
		}
	}

	private Connection connect() throws SQLException {
		Connection conn = DriverManager.getConnection("jdbc:derby:C:/Users/austin/Desktop/CS201/eclipse/CS320_Project/project.db;create=true");

		// Set autocommit to false to allow multiple the execution of
		// multiple queries/statements as part of the same transaction.
		conn.setAutoCommit(false);

		return conn;
	}
	//these build the collections to return to the servlets, controlles
	private void loadUser(User user, ResultSet resultSet, int index) throws SQLException {
		user.setUserId(resultSet.getInt(index++));
		user.setUserName(resultSet.getString(index++));
		user.setPassWord(resultSet.getString(index++));
		user.setEmail(resultSet.getString(index++));
		user.setAccountType(resultSet.getString(index++));
		user.setFirstName(resultSet.getString(index++));
		user.setLastName(resultSet.getString(index++));
	}

	private void loadRestaurant(Restaurant rest, ResultSet resultSet, int index) throws SQLException {
		rest.setRestID(resultSet.getInt(index++));
		rest.setUserId(resultSet.getInt(index++));
		rest.setName(resultSet.getString(index++));
		rest.setAddress(resultSet.getString(index++));
		rest.setCity(resultSet.getString(index++));
		rest.setZipCode(resultSet.getString(index++));
	}
	
	private void loadMenu(Menu m, ResultSet resultSet, int index) throws SQLException {
		m.setMenuId(resultSet.getInt(index++));
		m.setRestId(resultSet.getInt(index++));
		m.setItem(resultSet.getString(index++));
		m.setPrice(resultSet.getString(index++));
	}
	private void loadOrder(Order o, ResultSet resultSet, int index) throws SQLException {
		o.setOrderId(resultSet.getInt(index++));
		o.setPatronId(resultSet.getInt(index++));
		o.setRest(resultSet.getString(index++));
		o.setorderNumber(resultSet.getInt(index++));
		o.setItem(resultSet.getString(index++));
		o.setQuantity(resultSet.getInt(index++));
		o.setPrice(resultSet.getString(index++));
		o.setStatus(resultSet.getString(index++));
	}
	private void loadFavoriteRest(Favorites fav, ResultSet resultSet, int index) throws SQLException {
		fav.setFavID(resultSet.getInt(index++));;
		fav.setUserID(resultSet.getInt(index++));
		fav.setName(resultSet.getString(index++));
	}


	//creating the tables
	public void createTables() {
		executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt1 = null;
				PreparedStatement stmt2 = null;
				PreparedStatement stmt3 = null;
				PreparedStatement stmt4 = null;
				PreparedStatement stmt5 = null;

				try {
					stmt1 = conn.prepareStatement(
							"create table users (" +
									"	user_id integer primary key " +
									"		generated always as identity (start with 1, increment by 1), " +									
									"	user_userName varchar(40),"     +
									"	user_passWord varchar(40), "     +
									"   user_email varchar(40), "        +
									"   user_accountType varchar(30), " +
									"    user_firstName varchar(50), "  +
									"    user_lastNAme varchar(50) "    +
									")"
							);	
					stmt1.executeUpdate();

					//the users referenced in the user_id constraing are owners
					stmt2 = conn.prepareStatement(
							"create table restaurants (" +
									"	rest_id integer primary key " +
									"		generated always as identity (start with 1, increment by 1), " +
									"	user_id integer constraint user_id references users, " +
									"	rest_name varchar(40),"    +
									"	rest_address varchar(90)," +
									"   rest_city varchar(30),"	   +
									"   rest_zipcode varchar(10)"  +
									")"
							);
					stmt2.executeUpdate();
					//the following tables do not use a constraint to avoid overlapping foreign keys
					stmt3 = conn.prepareStatement(
							" create table menu (" +
									" menu_id integer primary key " +
									" 		generated always as identity (start with 1, increment by 1), " +
									" rest_id integer, "   +
									" menu_item varchar(40), "      +
									" menu_price varchar(10)"      +
									")"
							);
					stmt3.executeUpdate();
					//order is attached to a patron
					stmt4 = conn.prepareStatement(
							" create table orders (" +
									" order_id integer primary key " +
									" 		generated always as identity (start with 1, increment by 1), " +
									" patron_id integer, "    +
									" rest_name varchar(40)," +
									" order_number integer, " +
									" item varchar(40), "	  +
									" quantity integer,"	  +
									" price varchar(10),"		  +
									" status varchar(40)"     +
									")"
							);
					stmt4.executeUpdate();
					stmt5 = conn.prepareStatement(
							" create table favRests (" +
									" favRestId integer primary key " +
									" 		generated always as identity (start with 1, increment by 1), " +
									" patron_id integer, "   +
									" rest_name varchar(40)" +
									")"
							);
					stmt5.executeUpdate();

					return true;
				} finally {
					DBUtil.closeQuietly(stmt1);
					DBUtil.closeQuietly(stmt2);
					DBUtil.closeQuietly(stmt3);
					DBUtil.closeQuietly(stmt4);
					DBUtil.closeQuietly(stmt5);
				}
			}
		});
	}
	//loading initial data for basic website navigation
	public void loadInitialData() {
		executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				List<Restaurant> restList;
				List<User> userList;
				List<Menu> menuList;
				//initial data for order will be built when an order is made
				try {
					restList = InitialData.getRestaurants();
					userList = InitialData.getUsers();
					menuList = InitialData.getMenus();
				} catch (IOException e) {
					throw new SQLException("Couldn't read initial data", e);
				}

				PreparedStatement insertRestaurants = null;
				PreparedStatement insertUsers = null;
				PreparedStatement insertMenus = null;
				try {


					insertUsers = conn.prepareStatement("insert into users (user_userName, user_passWord, user_email, user_accountType, user_firstName, user_Lastname) "
							+ "		values (?, ?, ?, ?, ?, ?)");
					for (User u : userList) {
						insertUsers.setString(1, u.getUserName());
						insertUsers.setString(2, u.getPassWord());
						insertUsers.setString(3, u.getEmail());
						insertUsers.setString(4, u.getAccountType());
						insertUsers.setString(5, u.getFirstName());
						insertUsers.setString(6, u.getLastName());
						insertUsers.addBatch();
					}
					insertUsers.executeBatch();
					System.out.println("Users table populated");


					insertRestaurants = conn.prepareStatement("insert into restaurants (user_id, rest_name, rest_address, rest_city, rest_zipcode) "

							+ "	values (?, ?, ?, ?, ?)");

					for (Restaurant rest: restList) {
						insertRestaurants.setInt(1, rest.getUserId());

						insertRestaurants.setString(2,rest.getName());
						insertRestaurants.setString(3, rest.getAddress());
						insertRestaurants.setString(4, rest.getCity());
						insertRestaurants.setString(5, rest.getZipCode());

						insertRestaurants.addBatch();
					}
					insertRestaurants.executeBatch();
					System.out.println("restaurants table populated");
					
					
					insertMenus = conn.prepareStatement("insert into menu (rest_id, menu_item, menu_price) " + "values (?, ?, ?)");
					for (Menu m : menuList) {
						insertMenus.setInt(1, m.getRestId());
						insertMenus.setString(2, m.getItem());
						insertMenus.setString(3, m.getPrice());
						insertMenus.addBatch();
					}
					insertMenus.executeBatch();
					System.out.println("menu created");

					return true;
				} finally {
					DBUtil.closeQuietly(insertUsers);
					DBUtil.closeQuietly(insertRestaurants);
					DBUtil.closeQuietly(insertMenus);
					
					
				}
			}
		});
	}

	// The main method creates the database tables and loads the initial data.
	public static void main(String[] args) throws IOException {
		System.out.println("Creating tables...");
		DerbyDatabase db = new DerbyDatabase();
		db.createTables();
		System.out.println("Loading initial data...");
		db.loadInitialData();
		System.out.println("loaded intial data");
		System.out.println("austin got it the first time, you never will");
		System.out.println("you got it this time, dont get cocky");
	}



}
