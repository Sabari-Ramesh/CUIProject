package BO;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import CustomException.*;
import DAO.*;
import Main.Main;
import VO.MealDetails;
import VO.UserInfo;

public class MealDetailsBO {

	public IMealDetailsDAO mdDAO;
	static Logger Log = Logger.getLogger(Main.class);

	// Constructor

	public MealDetailsBO() throws SQLException {
		mdDAO = new MealDetailsDAO();
	}

	// Insert

	public boolean createMealDetail(MealDetails md)
			throws ValidMealType, ValidQuantity, ValidDateEx, SQLException, ValidUser, validItems {

		boolean flag = false;
		validmealType(md.getMealType());
		validQuantity(md.getQuantity());
		validDate(md.getMealDate());
		validUserId(md.getUserId());
		validCalories(md.getCalories(), md.getQuantity());
		validVitamins(md.getVitamins(), md.getQuantity());
		validProteins(md.getProtein(), md.getQuantity());
		validCarbohydrate(md.getCarbs(), md.getQuantity());
		flag=mdDAO.createMealDetail(md);
		return flag;
	}

	// Fetch

	public List<MealDetails> findbyId(int userId) throws SQLException, ValidUser {

		List<MealDetails> md = new ArrayList<>();
		boolean flag = false;
		validUserId(userId);
		md = mdDAO.findById(userId);
		flag = true;
		return md;

	}

	// Update

	public boolean updateMealType(int mealType, int mealId) throws validItems, SQLException, ValidMealType {

		boolean flag = false;
		validmealType(mealType);
		validmealId(mealId);
		flag=mdDAO.updateMealType(mealType, mealId);
		return flag;

	}

	public boolean updateMealDate(String mealDate, int mealId) throws ValidDateEx, SQLException, validItems {

		boolean flag = false;
		validmealId(mealId);
		validDate(mealDate);
		flag=mdDAO.updateMealDate(mealDate, mealId);
		return flag;

	}

	public boolean updateFood(String food, int mealId) throws validItems, SQLException {

		boolean flag = false;
		validmealId(mealId);
		flag=mdDAO.updateFood(food, mealId);
		return flag;

	}

	// Delete

	public boolean deletemealDetails(int mealId) throws validItems, SQLException {

		boolean flag = false;
		validmealId(mealId);
		flag=mdDAO.deletemealDetails(mealId);
		flag = true;
		return flag;

	}

	// Display using Joins

	public List<UserInfo> displayUsingJoins() throws SQLException {
		List<UserInfo> user = null;
		user = mdDAO.displayUsingJoins();
		return user;

	}

	// Validation

	private void validCarbohydrate(double carbs, double quantity) throws ValidQuantity {

		if (carbs <= 0 || carbs > quantity) {
			throw new ValidQuantity("In valid Quantity");
		}

	}

	private void validProteins(double protein, double quantity) throws ValidQuantity {

		if (protein <= 0 || protein > quantity) {
			throw new ValidQuantity("In valid Quantity");
		}

	}

	private void validVitamins(double vitamins, double quantity) throws ValidQuantity {

		if (vitamins <= 0 || vitamins > quantity) {
			throw new ValidQuantity("In valid Quantity");
		}

	}

	private void validCalories(double calories, double quantity) throws ValidQuantity {

		if (calories <= 0 || calories > quantity) {
			throw new ValidQuantity("In valid Quantity");
		}
	}

	private void validUserId(int userId) throws SQLException, ValidUser {

		if (!mdDAO.vaildUser(userId) || userId <= 0) {
			throw new ValidUser("Invalid UserId");
		}
	}

	public boolean validDate(String mealDate) throws ValidDateEx {

		try {
			LocalDate userDate = LocalDate.parse(mealDate);
			Date currentDate=new Date();
			 LocalDate currDate = LocalDate.now();
			 if (userDate.isEqual(currDate) || userDate.isBefore(currDate)) {
	                return true;
	            } else {
	            	throw new ValidDateEx("InValid Date");
	            }
		} catch (DateTimeParseException e) {
			throw new ValidDateEx("InValid Date");
		}

	}

	private void validQuantity(double quantity) throws ValidQuantity {

		if (quantity <= 0) {
			throw new ValidQuantity("In valid Quantity");
		}

	}

	private void validmealType(int mealType) throws ValidMealType {

		if (mealType <= 0 || mealType > 4) {
			throw new ValidMealType("InValid MealType ");
		}

	}

	public void validmealId(int mealId) throws validItems, SQLException {

		if (!mdDAO.vaildMealId(mealId) || mealId <= 0) {
			throw new validItems("mealId doesnot exits");
		}

	}

}
