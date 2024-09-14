package VO;

public class MealDetails {

	private int mealType;
	private String mealDate;
	private int userId;
	private String foodName;
	private double quantity;
	private double calories;
	private double protein;
	private double carbs;
	private double vitamins;

	public int getMealType() {
		return mealType;
	}

	public void setMealType(int mealType) {
		this.mealType = mealType;
	}

	public String getMealDate() {
		return mealDate;
	}

	public void setMealDate(String mealDate) {
		this.mealDate = mealDate;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getFoodName() {
		return foodName;
	}

	public void setFoodName(String foodName) {
		this.foodName = foodName;
	}

	public double getQuantity() {
		return quantity;
	}

	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}

	public double getCalories() {
		return calories;
	}

	public void setCalories(double calories) {
		this.calories = calories;
	}

	public double getProtein() {
		return protein;
	}

	public void setProtein(double protein) {
		this.protein = protein;
	}

	public double getCarbs() {
		return carbs;
	}

	public void setCarbs(double carbs) {
		this.carbs = carbs;
	}

	public double getVitamins() {
		return vitamins;
	}

	public void setVitamins(double vitamins) {
		this.vitamins = vitamins;
	}
}
