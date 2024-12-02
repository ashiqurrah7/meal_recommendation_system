public class Meal {
  String website;
  String name;
  String ingredients;
  double calories;
  double fat;
  double carbs;
  double sugar;
  double fiber;
  double protein;
  double sodium;

  public Meal(String website, String name, double calories, double fat, double carbs, 
             double sugar, double fiber, double protein, double sodium) {
      this.website = website;
      this.name = name;
      this.calories = calories;
      this.fat = fat;
      this.carbs = carbs;
      this.sugar = sugar;
      this.fiber = fiber;
      this.protein = protein;
      this.sodium = sodium;
  }

  public Meal(String website, String name, String ingredients) {
    this.website = website;
    this.name = name;
    this.ingredients = ingredients;
  }

  public String toString() {
    String formattedMeal = "Website: " + website +"\n Nutrition Values:\n";

    if (calories == 0.0) {
      formattedMeal += "Calories: Not specified\n";
    } else {
      formattedMeal += String.format("Calories: %.0f kcal\n", calories);
    }

    if (fat == 0.0) {
      formattedMeal += "Fat: Not specified\n";
    } else {
      formattedMeal += String.format("Fat: %.1fg\n", fat);
    }

    if (carbs == 0.0) {
      formattedMeal += "Carbs: Not specified\n";
    } else {
      formattedMeal += String.format("Carbs: %.1fg\n", carbs);
    }

    if (sugar == 0.0) {
      formattedMeal += "Sugar: Not specified\n";
    } else {
      formattedMeal += String.format("Sugar: %.1fg\n", calories);
    }

    if (fiber == 0.0) {
      formattedMeal += "Fiber: Not specified\n";
    } else {
      formattedMeal += String.format("Fiber: %.1fg\n", calories);
    }

    if (protein == 0.0) {
      formattedMeal += "Protein: Not specified\n";
    } else {
      formattedMeal += String.format("Protein: %.1fg\n", protein);
    }

    if (sodium == 0.0) {
      formattedMeal += "Sodium: Not specified\n";
    } else {
      formattedMeal += String.format("Sodium: %.0fmg\n", sodium);
    }

    return formattedMeal;
  }
}
