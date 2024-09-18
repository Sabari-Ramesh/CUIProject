package Service;

import VO.MealDetails;
import VO.UserInfo;

import java.sql.*;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;

import BO.*;
import CustomException.*;
import Main.Main;
import Response.*;

public class MealDetailService {

	private static MealDetailsBO mealDetailsBO;
	static Logger Log = Logger.getLogger(MealDetailService.class);
	static ResponseHandle response;

	public MealDetailService() throws SQLException {
		mealDetailsBO = new MealDetailsBO();
		response = new ResponseHandle();
	}

	// Insert

	public static ResponseHandle createmealDetails(MealDetails md) throws DuplicateMealDetailException {
		boolean flag = false;
		try {
			flag = mealDetailsBO.createMealDetail(md);
			response.setSucessMessage("Meal Detail Created successfully created for User :" + md.getUserId());
			Log.info("Meal Detail Created successfully " + md.getUserId());
		} catch (SQLException e) {
			if (e.getErrorCode() == 1062) {
				System.err.println("Duplicate Entry");
				Log.error("Duplicate Entry", e);
			} else {
				Log.error("SQL Error: ", e);
			}
		} catch (validItems e) {
			Log.error("Validation Error: ", e);
		} catch (ValidMealType e) {
			Log.error("Meal Type Error: ", e);
			System.err.println("In valid Meal Type");
		} catch (ValidQuantity e) {
			System.err.println("In valid Quantity");
			Log.error("Quantity Error: ", e);
		} catch (ValidDateEx e) {
			System.err.println("In Valid Date");
			Log.error("Date error", e);
		} catch (ValidUser e) {
			System.err.println("In valid User");
			Log.error("User Error: ", e);
		}
		response.setFailureMessage("Meal Details not Created Successfully  for user Id" + md.getUserId());
		if (response.getSucessMessage() == null) {
			Log.info("Meal Details not Created Successfully " + md.getUserId());
		}
		return response;
	}

	// Fetch

	public static ResponseHandle findById(int userId) throws validItems, ValidUser {

		try {
			List<MealDetails> mealDetails = mealDetailsBO.findbyId(userId);
			response.setmealDetails(mealDetails);
			response.setSucessMessage("Sucessfully Id is fetched " + userId);
			Log.info(userId + " Sucessfully Id is fetched " + mealDetails);
		} catch (SQLException e) {
			Log.error("SQL Error: ", e);
		} catch (ValidUser e) {
			System.err.println("In valid User");
			Log.error("Valid User Error: ", e);
		}
		response.setFailureMessage(userId + " User Id not Fetched Sucessfully ");
		if (response.getSucessMessage() == null) {
			Log.info(userId + " User Id not Fetched Sucessfully");
		}
		return response;

	}

	// Update

	public ResponseHandle updateMealType(int mealType, int mealId) throws validItems, DuplicateMealDetailException {

		try {
			boolean flag = mealDetailsBO.updateMealType(mealType, mealId);
			if (flag) {
				response.setSucessMessage("MealType Successfully Updated " + mealId);
				Log.info("MealType Successfully Updated " + mealId);
			}
		} catch (SQLException e) {
			if (e.getErrorCode() == 1062) {
				System.err.println("Duplicate Entry");
				Log.error("Duplicate Entry", e);
			} else {
				Log.error("SQL Error: ", e);
			}
		} catch (ValidMealType e) {
			System.err.println("Enter the Valid MealType");
			Log.error("Valid mealType ", e);
		} catch (validItems e) {
			Log.error("Valid meal ID ", e);
		}
		response.setFailureMessage("Failure to Update Meal Type for Id" + mealId);
		if (response.getSucessMessage() == null) {
			Log.info("Failure to Update Meal Type " + mealId);
		}
		return response;

	}

	public ResponseHandle updateMealDate(String mealDate, int mealId) throws DuplicateMealDetailException, ValidDateEx {

		try {
			boolean flag = mealDetailsBO.updateMealDate(mealDate, mealId);
			if (flag) {
				response.setSucessMessage("Meal Date Sucessfully Updated " + mealId);
				Log.info("Meal Date Sucessfully Updated " + mealId);
			}
		} catch (SQLException e) {
			if (e.getErrorCode() == 1062) {
				System.err.println("Duplicate Entry");
				Log.error("Duplicate Entry", e);
			} else {
				Log.error("SQL Error: ", e);
			}
		} catch (ValidDateEx e) {
			System.err.println("In valid Date");
			Log.error("Date Error: ", e);
		} catch (validItems e) {
			Log.error("Valid meal ID : ", e);
		}
		response.setFailureMessage("Failure to Update MealDate " + mealId);
		if (response.getSucessMessage() == null) {
			Log.info("Failure to Update MealDate " + mealId);
		}
		return response;

	}

	public ResponseHandle updateFood(String food, int mealId) throws validItems {

		try {
			boolean flag = mealDetailsBO.updateFood(food, mealId);
			if (flag) {
				response.setSucessMessage("Sucessfully Update the Food " + mealId);
				Log.info("Sucessfully Update the Food " + mealId);
			}
		} catch (SQLException e) {
			if (e.getErrorCode() == 1062) {
				System.err.println("Duplicate Entry");
				Log.error("Duplicate Entry", e);
			} else {
				Log.error("SQL Error: ", e);
			}
		} catch (validItems e) {
			System.out.println("In valid MealId " + mealId);
			Log.error("Valid meal ID : ", e);
		}
		response.setFailureMessage("Failure to Update the MealType for meal Id " + mealId);
		if (response.getSucessMessage() == null) {
			Log.info("Failure to Update the MealType for meal Id" + mealId);
		}
		return response;

	}

	// FIND ALL

	public ResponseHandle findAllDetails() throws validItems {

		try {
			List<MealDetails> mealDetails = mealDetailsBO.findAllDetails();
			response.setmealDetails(mealDetails);
			response.setSucessMessage("Sucessfully Details is fetched ");
			Log.info("Sucessfully Details are fetched " + mealDetails);
		} catch (SQLException e) {
			Log.error("SQL Error: ", e);
		}
		response.setFailureMessage(" Details not Fetched Sucessfully");
		if (response.getSucessMessage() == null) {
			Log.info("Details not Fetched Sucessfully");
		}
		return response;
	}

	// Display

	public ResponseHandle displayUsingJoins() {

		try {
			List<UserInfo> users = mealDetailsBO.displayUsingJoins();
			if (users.size() > 0) {
				response.setUser(users);
				response.setSucessMessage("Details are Sucessfully Displayed");
				Log.info("Details are Sucessfully Displayed " + users);
			}
		} catch (SQLException e) {
			Log.error("SQl Error : ", e);
		}
		response.setFailureMessage("Failure to Display the Details");
		if (response.getSucessMessage() == null) {
			Log.info("Failure to Display the Details");
		}
		return response;

	}
//Streams

	public ResponseHandle getUserByGender(int value) {

		try {
			List<UserInfo> users = mealDetailsBO.displayUsingStreams(value);
			response.setUser(users);
			response.setSucessMessage("Details are Sucessfully Displayed");
			Log.info("Details are Sucessfully Displayed");
		} catch (SQLException e) {
			Log.error("SQl Error : ", e);
		}
		response.setFailureMessage("Failure to Display the Details");
		if (response.getSucessMessage() == null) {
			Log.info("Failure to Display the Details");
		}
		return response;
	}

}
