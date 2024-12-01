import java.io.*;
import java.util.*;
import java.util.regex.*;

public class MealFilter {
  List<Meal> meals;

  public MealFilter(String filename) {
    List<Meal> meals = readMealsFromCSV(filename);
    this.meals = meals;
  }

  private List<Meal> readMealsFromCSV(String filename) {
        List<Meal> meals = new ArrayList<>();
        
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            // Skip header line
            String line = br.readLine();
            
            while ((line = br.readLine()) != null) {
                try {
                    // Split by comma, but not within quotes
                    String[] parts = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
                    
                    if (parts.length < 3) continue; // Skip invalid lines
                    
                    // Get website (index 0), meal name (index 1) and nutrition info (index 2)
                    String website = parts[0].trim().replace("\"", "");
                    String name = parts[1].trim().replace("\"", "");
                    String nutritionInfo = parts[2].trim().replace("\"", "");
                    
                    // Skip lines that don't look like meal entries
                    if (name.toLowerCase().contains("calories") || 
                        name.toLowerCase().contains("mins") ||
                        name.length() < 3) {
                        continue;
                    }
                    
                    // Parse nutrition information
                    Map<String, Double> nutritionValues = parseNutritionInfo(nutritionInfo);
                    
                    // Create new meal with extracted values, defaulting to 0 if not found
                    Meal meal = new Meal(
                        website,
                        name,
                        nutritionValues.getOrDefault("Calories", 0.0),
                        nutritionValues.getOrDefault("Fat", 0.0),
                        nutritionValues.getOrDefault("Carbohydrate", 0.0),
                        nutritionValues.getOrDefault("Sugar", 0.0),
                        nutritionValues.getOrDefault("Dietary Fiber", 0.0),
                        nutritionValues.getOrDefault("Protein", 0.0),
                        nutritionValues.getOrDefault("Sodium", 0.0)
                    );
                    
                    // Only add meals that have at least calories information
                    if (meal.calories > 0) {
                        meals.add(meal);
                    }
                    
                } catch (Exception e) {
                    // Skip problematic lines
                    continue;
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading CSV file: " + e.getMessage());
        }
        
        return meals;
    }

    private static Map<String, Double> parseNutritionInfo(String nutritionInfo) {
        Map<String, Double> values = new HashMap<>();
        
        // Updated pattern to better match nutrition information
        Pattern pattern = Pattern.compile("(Calories|Fat|Carbohydrate|Sugar|Dietary Fiber|Protein|Sodium)\\s+(\\d+(?:\\.\\d+)?)(?:kcal|g|mg)?");
        Matcher matcher = pattern.matcher(nutritionInfo);
        
        while (matcher.find()) {
            String nutrient = matcher.group(1);
            double value = Double.parseDouble(matcher.group(2));
            values.put(nutrient, value);
        }
        
        return values;
    }

    public List<Meal> filterMeals(String nutrient, double threshold, boolean lessThan) {

        List<Meal> filteredMeals = new ArrayList<>();
        
        for (Meal meal : this.meals) {
            double value = switch (nutrient.toLowerCase()) {
                case "calories" -> meal.calories;
                case "fat" -> meal.fat;
                case "carbs" -> meal.carbs;
                case "sugar" -> meal.sugar;
                case "fiber" -> meal.fiber;
                case "protein" -> meal.protein;
                case "sodium" -> meal.sodium;
                default -> 0.0;
            };
            
            if (lessThan ? value < threshold : value > threshold) {
                if(!(lessThan && value == 0.0)) {
                  filteredMeals.add(meal);
                }      
            }
        }
        
        return filteredMeals;
    }
}
