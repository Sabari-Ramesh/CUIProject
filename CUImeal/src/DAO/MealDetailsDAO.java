package DAO;

import java.sql.*;
import java.util.*;

import VO.MealDetails;
import VO.UserInfo;

public class MealDetailsDAO implements IMealDetailsDAO {
	private Connection conn;

	// Conect to Database

	public MealDetailsDAO() throws SQLException {

		String url = "jdbc:mysql://localhost:3306/project";
		String user = "root";
		String password = "tiger";
		conn = DriverManager.getConnection(url, user, password);

	}

	// Insert

	public boolean createMealDetail(MealDetails mealDetails) throws SQLException {

		String query = "INSERT INTO meal_details (meal_type, meal_date, user_id, food_name, quantity, calories, protein, carbs, vitamins) VALUES(?,?,?,?,?,?,?,?,?)";
		boolean flag = false;
		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, mealDetails.getMealType());
			ps.setString(2, mealDetails.getMealDate());
			ps.setInt(3, mealDetails.getUserId());
			ps.setString(4, mealDetails.getFoodName());
			ps.setDouble(5, mealDetails.getQuantity());
			ps.setDouble(6, mealDetails.getCalories());
			ps.setDouble(7, mealDetails.getProtein());
			ps.setDouble(8, mealDetails.getCarbs());
			ps.setDouble(9, mealDetails.getVitamins());
			int a = ps.executeUpdate();
			if (a > 0) {
				flag = true;
			}
		}
		finally {
			try {
				if (conn != null)
					conn.close();
			} // TRY
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return flag;
	}

	// Fetch By Id

	public List<MealDetails> findById(int userId) throws SQLException {
		List<MealDetails> mealDetailsList = new ArrayList<>();

		String query = "SELECT md.meal_id, md.meal_date, md.food_name, md.quantity, md.calories, md.protein, md.carbs, md.vitamins, md.user_id, mi.meal "
				+ "FROM meal_details md JOIN meal_info mi ON md.meal_type = mi.meal_type WHERE md.user_id = ?";

		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, userId);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				MealDetails mealDetails = new MealDetails();
				mealDetails.setMealType(rs.getInt(1));
				mealDetails.setMealDate(rs.getString(2));
				mealDetails.setFoodName(rs.getString(3));
				mealDetails.setQuantity(rs.getDouble(4));
				mealDetails.setCalories(rs.getDouble(5));
				mealDetails.setProtein(rs.getDouble(6));
				mealDetails.setCarbs(rs.getDouble(7));
				mealDetails.setVitamins(rs.getDouble(8));
				mealDetails.setUserId(rs.getInt(9));
				mealDetails.setFoodType(rs.getString(10));
				mealDetailsList.add(mealDetails);
			}
		}
		finally {
			if (conn != null)
				conn.close();
		}

		return mealDetailsList;
	}

	// Update

	@Override
	public boolean updateMealType(int mealType, int mealId) throws SQLException {

		String querry = "UPDATE meal_details SET meal_Type=? WHERE meal_id=? ";
		try {
			PreparedStatement ps = conn.prepareStatement(querry);
			ps.setInt(1, mealType);
			ps.setInt(2, mealId);
			int a = ps.executeUpdate();
			return true;
		}
		finally {
				if (conn != null)
					conn.close();
		}

	}

	@Override
	public boolean updateMealDate(String mealDate, int mealId) throws SQLException {

		boolean flag = false;
		String querry = "UPDATE meal_details SET meal_Date=? WHERE meal_id=? ";
		try {
			PreparedStatement ps = conn.prepareStatement(querry);
			ps.setString(1, mealDate);
			ps.setInt(2, mealId);
			int a = ps.executeUpdate();
			if (a > 0) {
				flag = true;
			}
		}
		finally {
				if (conn != null)
					conn.close();
		}
		return flag;
	}

	@Override
	public boolean updateFood(String food, int mealId) throws SQLException {

		boolean flag = false;
		String querry = "UPDATE meal_details SET food_name=? WHERE meal_id=? ";
		try {
			PreparedStatement ps = conn.prepareStatement(querry);
			ps.setString(1, food);
			ps.setInt(2, mealId);
			int a = ps.executeUpdate();
			if (a > 0) {
				flag = true;
			}
		}
		finally {
				if (conn != null)
					conn.close();
		}
		return flag;
	}

	// FIND ALL

	@Override
	public List<MealDetails> findAllDetails() throws SQLException {

		List<MealDetails> mealDetailsList = new ArrayList<>();

		String query = "SELECT md.meal_id, md.meal_date, md.food_name, md.quantity, md.calories, md.protein, md.carbs, md.vitamins, md.user_id, mi.meal "
				+"FROM meal_details md JOIN meal_info mi ON md.meal_type = mi.meal_type";

		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
			    MealDetails mealDetails = new MealDetails();
			    
			    mealDetails.setMealType(rs.getInt(1));         
			    mealDetails.setMealDate(rs.getString(2));     
			    mealDetails.setFoodName(rs.getString(3));    
			    mealDetails.setQuantity(rs.getDouble(4));   
			    mealDetails.setCalories(rs.getDouble(5));    
			    mealDetails.setProtein(rs.getDouble(6));     
			    mealDetails.setCarbs(rs.getDouble(7));      
			    mealDetails.setVitamins(rs.getDouble(8));     
			    mealDetails.setUserId(rs.getInt(9));         
			    mealDetails.setFoodType(rs.getString(10));
			    mealDetailsList.add(mealDetails);
			}
		}
		finally {
			if (conn != null)
				conn.close();
		}

		return mealDetailsList;

	}

	// Display Using Joins

	@Override
	public List<UserInfo> displayUsingJoins() throws SQLException {
		List<UserInfo> userList = new ArrayList<>();
		String query = "SELECT u.user_id, u.user_name, g.gender, COALESCE(SUM(m.quantity), 0) AS total_quantity "
				+ "FROM Users u " + "INNER JOIN gender_info g ON u.gender_id = g.gender_id "
				+ "INNER JOIN meal_details m ON u.user_id = m.user_id " + "GROUP BY u.user_id, u.user_name, g.gender "
				+ "ORDER BY u.user_id";

		System.out.println("syntac");
		try{
			PreparedStatement ps = conn.prepareStatement(query); 
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				UserInfo userInfo = new UserInfo();
				userInfo.setId(rs.getInt(1));
				userInfo.setName(rs.getString("user_name"));
				userInfo.setGender(rs.getString("gender"));
				userInfo.setQuantity(rs.getDouble("total_quantity"));
				userList.add(userInfo);
				//System.out.println(rs.getInt(1));
			}

		}
		finally {
			if (conn != null)
				conn.close();
		}

		return userList;
	}
		
	// Check User Exit or Not

	@Override
	public boolean vaildUser(int userId) throws SQLException {

		boolean flag = false;
		String query = "SELECT 1 FROM Users WHERE  user_id=?";
		ResultSet rs = null;
		PreparedStatement ps = conn.prepareStatement(query);
		ps.setInt(1, userId);
		rs = ps.executeQuery();
		if (rs.next()) {
			flag = true;
		}
		return flag;

	}

	// Vaild meald ID OR NOT

	@Override
	public boolean vaildMealId(int mealId) throws SQLException {

		boolean flag = false;
		String query = "SELECT 1 FROM meal_details WHERE  meal_id=?";
		ResultSet rs = null;
		PreparedStatement ps = conn.prepareStatement(query);
		ps.setInt(1, mealId);
		rs = ps.executeQuery();
		if (rs.next()) {
			flag = true;
		}
		return flag;

	}

}
