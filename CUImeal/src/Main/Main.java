package Main;

import VO.*;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.Logger;
import CustomException.*;
import Response.*;
import Service.*;

public class Main {

	static Scanner sc = new Scanner(System.in);
	private static ResponseHandle response;
	static Logger Log = Logger.getLogger(Main.class);

	public static void main(String[] args)
			throws SQLException, validItems, ValidMealType, ValidQuantity, ValidDateEx, ValidUser, DuplicateMealDetailException {

		PropertyConfigurator.configure("D:\\JAVA ECLIPSE\\Demo\\src\\properties\\Log4j.properties");
		Log.info(" Application Started Started..");
		boolean flag = true;

		while (flag) {
			System.out.println("\nMenu:");
			System.out.println("\t 1. Create MealDetails");
			System.out.println("\t 2. Fetch By UserId");
			System.out.println("\t 3. Update MealDetails");
			System.out.println("\t 4. Delete MealDetails");
			System.out.println("\t 5. List All Using Joins");
			System.out.println("\t 6. Display Using Stream Api");
			System.out.println("\t 7. Exit");
			System.out.println("-------------------------------------------------------------------------------------");
			System.out.print("Enter your choice: ");
			int option = sc.nextInt();

			switch (option) {
			case 1: {
				insertData();
				break;
			}
			case 2: {
				findbyId();
				break;
			}
			case 3: {
				update();
				break;
			}
			case 4: {
				deletemealDetails();
				break;
			}
			case 5: {
				displayUsingJoins();
				break;
			}
			case 6: {
				displayUsingStream();
				break;
			}
			case 7: {
				flag = false;
				System.out.println("Thank You !!! \n \t you are Sucessfully Exit");
				break;
			}
			default: {
				System.out.println("Please enter vaild Option !!! ");
			}
			}// SWITCH

		} // WHILE
		Log.info(" Application Ended..");
	}

	// Insert

	private static void insertData() throws SQLException, ValidMealType, ValidQuantity, ValidDateEx, ValidUser, DuplicateMealDetailException {

		Log.info("Created Meal Details is triggered.....");
		MealDetails md = new MealDetails();
		MealDetailService mdservice = new MealDetailService();
		System.out.println("\n Meal Type :");
		System.out.println("\t1.BreakFast \n\t2.Lunch \n\t3.Dinner \n\t4.Snacks");
		System.out.println("Enter Meal Type :");
		int option = sc.nextInt();

		switch (option) {

		case 1: {

			md.setMealType(1);
			break;

		}

		case 2: {

			md.setMealType(2);
			break;

		}

		case 3: {

			md.setMealType(3);
			break;

		}

		case 4: {

			md.setMealType(4);
			break;

		}

		default: {

			System.out.println("Enter Valid One");

		}

		}

		sc.nextLine();
		System.out.println("Enter Meal Date in format (YYYY-MM-DD):");
		md.setMealDate(sc.next());
		System.out.println("Enter User ID :");
		md.setUserId(sc.nextInt());
		sc.nextLine();
		System.out.println("Enter Food Name :");
		md.setFoodName(sc.nextLine());
		System.out.println("Enter Meal Quantity :");
		md.setQuantity(sc.nextDouble());
		System.out.println("Enter Meal Calories :");
		md.setCalories(sc.nextDouble());
		System.out.println("Enter Meal Protein :");
		md.setProtein(sc.nextDouble());
		System.out.println("Enter Meal Carbohydrate :");
		md.setCarbs(sc.nextDouble());
		System.out.println("Enter Meal Vitamins :");
		md.setVitamins(sc.nextDouble());
		ResponseHandle response = MealDetailService.createmealDetails(md);
		if (response.getSucessMessage() != null) {
			System.out.println(response.getSucessMessage());
		} else {
			System.out.println(response.getFailureMessage());
		}
	}

	// Fetch

	private static void findbyId() throws SQLException, ValidUser, validItems {

		Log.info("Find By user ID method  is triggered.....");
		MealDetailService mdservice = new MealDetailService();
		System.out.println("Enter the UserId :");
		int userId = sc.nextInt();
		ResponseHandle response = mdservice.findById(userId);
		List<MealDetails> md = response.getMd();
		printMealDetails(md,response);
		if (response.getSucessMessage() != null) {
			System.out.println(response.getSucessMessage());
		} else {
			System.out.println(response.getFailureMessage());
		}

	}

	// Update

	private static void update() throws SQLException, validItems, ValidMealType, ValidDateEx, DuplicateMealDetailException {

		Log.info("Update method is triggred.....");
		MealDetailService mdservice = new MealDetailService();
		System.out.println("Enter the meal_Id to Update :");
		int mealId = sc.nextInt();
		System.out.println("Enter the field to Update");
		System.out.println("\t1.UpdatemealType\n\t2.UpdateDate\n\t3.updateFoodName");
		int option = sc.nextInt();

		switch (option) {

		case 1: {

			System.out.println("Enter the meal Type :");
			System.out.println("\t 1.BreakFast\n\t2.Lunch\n\t3.Dinner\n\t4.Snacks");
			int mealType = sc.nextInt();
			response = mdservice.updateMealType(mealType, mealId);
			if (response.getSucessMessage() != null) {
				System.out.println(response.getSucessMessage());
			} else {
				System.out.println(response.getFailureMessage());
			}
			break;

		}

		case 2: {

			System.out.println("Enter new Date in (YYYY-MM-DD) Format:");
			String mealDate = sc.next();
			response = mdservice.updateMealDate(mealDate, mealId);
			if (response.getSucessMessage() != null) {
				System.out.println(response.getSucessMessage());
			} else {
				System.out.println(response.getFailureMessage());
			}
			break;

		}

		case 3: {

			System.out.println("Enter new Food Name:");
			String food = sc.next();
			response = mdservice.updateFood(food, mealId);
			if (response.getSucessMessage() != null) {
				System.out.println(response.getSucessMessage());
			} else {
				System.out.println(response.getFailureMessage());
			}
			break;
		}

		default: {

			System.out.println("Enter the valid one !!!");
		}

		}

	}

	// DELETE

	private static void deletemealDetails() throws SQLException, validItems, ValidUser {

		Log.info("Delete Meal Details is triggered.....");
		MealDetailService mdservice = new MealDetailService();
		System.out.println("Enter the maelId :");
		int mealId = sc.nextInt();
		ResponseHandle response = mdservice.deletemealDetails(mealId);
		if (response.getSucessMessage() != null) {
			System.out.println(response.getSucessMessage());
		} else {
			System.out.println(response.getFailureMessage());
		}
	}

	// Display Using Joins

	private static void displayUsingJoins() throws SQLException {

		Log.info("Display Using Joins is Triggred.........");
		MealDetailService mdservice = new MealDetailService();
		ResponseHandle response = mdservice.displayUsingJoins();
		List<UserInfo> user = response.getUser();
		printUserInfo(user);
		if (response.getSucessMessage() != null) {
			System.out.println(response.getSucessMessage());
		} else {
			System.out.println(response.getFailureMessage());
		}

	}

	private static void displayUsingStream() throws SQLException {
		Log.info("Display Using Stream Function is Triggered............");
		MealDetailService mdservice = new MealDetailService();
		ResponseHandle response = null;
		System.out.println("\t1.Female\n\t2.Male");
		System.out.println("Enter Your Choice :");
		int option=sc.nextInt();
		switch(option) {
		case 1:{
			response=mdservice.getUserByGender(option);
			break;
		}case 2:{
			response=mdservice.getUserByGender(option);
		}
		default :{
			System.out.println("Enter Vaild option");
		}
		}		
		printUserInfo(response.getUser());
		
	}

	// Print Details

	private static void printMealDetails(List<MealDetails> md, ResponseHandle response) {

		if (md != null && !md.isEmpty()) {

			System.out.println(
					"MealId\tmealDate\tfoodname\tquantity\tcalories\tprotein\t\tcarbs\tvitamins userId\tmeal\t");
			System.out.println(
					"-----------------------------------------------------------------------------------------------------------------------------");

			md.stream().forEach(data -> {
				System.out.print(data.getMealType() + "\t" + data.getMealDate() + "\t" + data.getFoodName() + "\t"
						+ data.getQuantity() + "\t\t" + data.getCalories() + "\t\t" + data.getProtein() + "\t\t"
						+ data.getCarbs() + "\t" + data.getVitamins() + "\t" + data.getUserId() + "\t"
						+ data.getFoodName());
				System.out.println();
			});
			System.out.println(
					"---------------------------------------------------------------------------------------------------------------------------------");
		} else if(response.getSucessMessage()!=null){
			System.out.println("No meal details available.");
		}

	}

	// Print User Info

	private static void printUserInfo(List<UserInfo> user) {
		if (!user.isEmpty()) {
			System.out.println("UserId\tName\tGender\tTotal Quantity");
			System.out.println("------------------------------------------------------------");
			user.stream().forEach(users -> System.out.println(
					users.getId() + "\t" + users.getName() + "\t" + users.getGender() + "\t" + users.getQuantity()));
		} else {
			System.out.println("No users found.");
		}
	}

}
