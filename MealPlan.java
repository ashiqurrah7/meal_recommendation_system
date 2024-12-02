public class MealPlan {
    private final String website;
    private final String mealPlanName;
    private final int mealsPerWeek;
    private final double perMealPrice;

    public MealPlan(String website, String mealPlanName, int mealsPerWeek, double perMealPrice) {
        this.website = website;
        this.mealPlanName = mealPlanName;
        this.mealsPerWeek = mealsPerWeek;
        this.perMealPrice = perMealPrice;
    }

    public String getWebsite() {
        return website;
    }

    public String getMealPlanName() {
        return mealPlanName;
    }

    public int getMealsPerWeek() {
        return mealsPerWeek;
    }

    public double getPerMealPrice() {
        return perMealPrice;
    }
}
