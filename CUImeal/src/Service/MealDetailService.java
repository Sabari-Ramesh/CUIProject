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
	static Logger Log = Logger.getLogger(Main.class);
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
			response.setSucessMessage("Meal Detail Created successfully " + md.getUserId());
			Log.info("Meal Detail Created successfully " + md.getUserId());
		} catch (SQLException e) {
			if (e.getErrorCode() == 1062) {
				System.err.println("Duplicate entry:"+e.getMessage());
				Log.error("Duplicate Entry",e);
			} else {
				System.err.println("SQL Error: " + e.getMessage());
				Log.error("SQL Error: ", e);
			}
		} catch (validItems e) {
			System.err.println("Validation Error: " + e.getMessage());
			Log.error("Validation Error: ", e);
		} catch (ValidMealType e) {
			System.err.println("Meal Type Error: " + e.getMessage());
			Log.error("Meal Type Error: ", e);
		} catch (ValidQuantity e) {
			System.err.println("Quantity Error: " + e.getMessage());
			Log.error("Quantity Error: ", e);
		} catch (ValidDateEx e) {
			System.err.println("Date Error: " + e.getMessage());
			Log.error("Date error", e);
		} catch (ValidUser e) {
			System.err.println("User Error: " + e.getMessage());
			Log.error("User Error: ", e);
		}
		response.setFailureMessage("Meal Details not Created Successfully " + md.getUserId());
		Log.info("Meal Details not Created Successfully " + md.getUserId());
		return response;
	}

	// Fetch

	public static ResponseHandle findById(int userId) throws validItems, ValidUser {

		try {
			List<MealDetails> md = mealDetailsBO.findbyId(userId);
			response.setMd(md);
			response.setSucessMessage("Sucessfully Id is fetched " + userId);
			Log.info("Sucessfully Id is fetched " + userId);
		} catch (SQLException e) {
			System.err.println("SQL Error: " + e.getMessage());
			Log.error("SQL Error: ", e);
		} catch (ValidUser e) {
			System.err.println("Valid User Error: " + e.getMessage());
			Log.error("Valid User Error: ", e);
		}
		response.setFailureMessage(userId + " User Id not Fetched Sucessfully");
		Log.info(userId + " User Id not Fetched Sucessfully");
		return response;

	}

	// Update

	public ResponseHandle updateMealType(int mealType, int mealId) throws validItems,DuplicateMealDetailException {

		try {
			boolean flag = mealDetailsBO.updateMealType(mealType, mealId);
			if (flag) {
				response.setSucessMessage("MealType Successfully Updated " + mealId);
				Log.info("MealType Successfully Updated " + mealId);
			}
		} catch (SQLException e) {
			if (e.getErrorCode() == 1062) {
				System.err.println("Duplicate entry:"+e.getMessage());
				Log.error("Duplicate Entry",e);
			} else {
				System.err.println("SQL Error: " + e.getMessage());
				Log.error("SQL Error: ", e);
			}
		} catch (ValidMealType e) {
			System.err.println("Valid mealType " + e.getMessage());
			Log.error("Valid mealType ", e);
		} catch (validItems e) {
			System.err.println("Valid meal ID " + e.getMessage());
			Log.error("Valid meal ID ", e);
		}
		response.setFailureMessage("Failure to Update Meal Type " + mealId);
		Log.info("Failure to Update Meal Type " + mealId);
		return response;

	}

	public ResponseHandle updateMealDate(String mealDate, int mealId)throws DuplicateMealDetailException,ValidDateEx {

		try {
			boolean flag = mealDetailsBO.updateMealDate(mealDate, mealId);
			if (flag) {
				response.setSucessMessage("Meal Date Sucessfully Updated " + mealId);
				Log.info("Meal Date Sucessfully Updated " + mealId);
			}
		} catch (SQLException e) {
			if (e.getErrorCode() == 1062) {
				System.err.println("Duplicate entry:"+e.getMessage());
				Log.error("Duplicate Entry",e);
			} else {
				System.err.println("SQL Error: " + e.getMessage());
				Log.error("SQL Error: ", e);
			}
		} catch (ValidDateEx e) {
			System.err.println("Date Error: " + e.getMessage());
			Log.error("Date Error: ", e);
		} catch (validItems e) {
			System.err.println("Valid meal ID : " + e.getMessage());
			Log.error("Valid meal ID : ", e);
		}
		response.setFailureMessage("Failure to Update MealDate " + mealId);
		Log.info("Failure to Update MealDate " + mealId);
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
				System.err.println("Duplicate entry:"+e.getMessage());
				Log.error("Duplicate Entry",e);
			} else {
				System.err.println("SQL Error: " + e.getMessage());
				Log.error("SQL Error: ", e);
			}
		} catch (validItems e) {
			System.err.println("Valid meal ID : " + e.getMessage());
			Log.error("Valid meal ID : ", e);
		}
		response.setFailureMessage("Failure to Update the MealType " + mealId);
		Log.info("Failure to Update the MealType " + mealId);
		return response;

	}

	// Delete

	public ResponseHandle deletemealDetails(int mealId) throws validItems {

		try {
			boolean flag = mealDetailsBO.deletemealDetails(mealId);
			if (flag) {
				response.setSucessMessage("Sucessfully Deleted " + mealId);
				Log.info("Sucessfully Deleted " + mealId);
			}
		} catch (validItems e) {
			System.err.println("Error Enter valid mealId :" + e.getMessage());
			Log.error("Error Enter valid mealId :", e);
		} catch (SQLException e) {
			System.err.println("SQL Error: " + e.getMessage());
			Log.error("SQL Error: ", e);
		}
		response.setFailureMessage(mealId + " Not deleted");
		Log.info(mealId + " Not deleted");
		return response;
	}

	// Display

	public ResponseHandle displayUsingJoins() {

		try {
			List<UserInfo> user = mealDetailsBO.displayUsingJoins();
			if (user.size() > 0) {
				response.setUser(user);
				response.setSucessMessage("Details are Sucessfully Displayed");
				Log.info("Details are Sucessfully Displayed");
			}
		} catch (SQLException e) {
			System.err.println("SQL Error: " + e.getMessage());
			Log.error("SQl Error : ", e);
		}
		response.setFailureMessage("Failure to Display the Details");
		Log.info("Failure to Display the Details");
		return response;

	}

	public ResponseHandle getUserByGender(int value) {

		try {
			List<UserInfo> user = mealDetailsBO.displayUsingJoins();
			if (user.size() > 0) {
				List<UserInfo> result;

				if (value == 1) {
					result = user.stream().filter(users -> "Female".equalsIgnoreCase(users.getGender()))
							.collect(Collectors.toList());
				} else {
					result = user.stream().filter(users -> "Male".equalsIgnoreCase(users.getGender()))
							.collect(Collectors.toList());
				}

				response.setUser(result);
				response.setSucessMessage("Details are Sucessfully Displayed");
				Log.info("Details are Sucessfully Displayed");
			}
		} catch (SQLException e) {
			System.err.println("SQL Error: " + e.getMessage());
			Log.error("SQl Error : ", e);
		}
		response.setFailureMessage("Failure to Display the Details");
		Log.info("Failure to Display the Details");
		return response;
	}

}
