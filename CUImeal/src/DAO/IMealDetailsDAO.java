package DAO;

import java.sql.*;
import java.util.List;

import VO.*;

public interface IMealDetailsDAO {
	  boolean createMealDetail(MealDetails mealDetails) throws SQLException;

		boolean vaildUser(int userId) throws SQLException;

		List<MealDetails> findById(int userId) throws SQLException;

		boolean vaildMealId(int mealId) throws SQLException;

		boolean deletemealDetails(int mealId) throws SQLException;

		boolean updateMealType(int mealType, int mealId)throws SQLException;

		boolean updateMealDate(String mealDate, int mealId) throws SQLException;

		boolean updateFood(String food, int mealId) throws SQLException;

		List<UserInfo>  displayUsingJoins() throws SQLException;
}
