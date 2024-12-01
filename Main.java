import java.util.*;

public class Main {
    public static void main(String[] args) {
      MealFilter filter = new MealFilter("meals.csv");
      Scanner scanner = new Scanner(System.in);
      
      while (true) {
          int choice;

          System.out.println("\nChoose an option:");
          System.out.println("1. Look for sites according to your dietary preference");
          System.out.println("2. Look for meals according to your nutrition preference");
          System.out.println("3. Exit");
          System.out.print("Enter your choice (1-3): ");

          choice = scanner.nextInt();

          if (choice < 1 || choice > 3) {
            System.out.println("Please select a valid option");
          }

          if (choice == 1) {
            System.out.println("\nChoose dietary preference:");
            System.out.println("1. Vegetarian");
            System.out.println("2. Pescatarian");
            System.out.println("3. Low Carb");
            System.out.println("4. High Protein");
            System.out.print("Enter your choice (1-4): ");
            
            int dietChoice = scanner.nextInt();
            String preference = "";
            
            switch (dietChoice) {
                case 1: preference = "vegetarian"; break;
                case 2: preference = "pescatarian"; break;
                case 3: preference = "low carb"; break;
                case 4: preference = "high protein"; break;
                default:
                    System.out.println("Invalid choice");
                    continue;
            }
            
            DietaryFilter dietaryFilter = new DietaryFilter("dietary_options.csv");
            List<String> matchingWebsites = dietaryFilter.getWebsitesForDiet(preference);
            
            if (matchingWebsites.isEmpty()) {
                System.out.println("\nNo websites found matching your dietary preference.");
            } else {
                System.out.println("\nWebsites offering " + preference + " options:");
                for (String website : matchingWebsites) {
                    System.out.println("- " + website);
                }
            }
          }

          if (choice == 2) {
            System.out.println("1. Calories < x");
            System.out.println("2. Fat < x");
            System.out.println("3. Carbs < x");
            System.out.println("4. Sugar < x");
            System.out.println("5. Fiber > x");
            System.out.println("6. Protein > x");
            System.out.println("7. Sodium < x");
            System.out.println("8. Exit");
            
            
            System.out.print("Enter your choice (1-8): ");

            choice = scanner.nextInt();

            if (choice < 1 || choice > 8) {
              System.out.println("Please select a valid option");
              continue;
            }

            if (choice == 8) {
              break;
            }

            System.out.print("Enter value: ");
            double value = scanner.nextDouble();
          
            List<Meal> filteredMeals = null;
            switch (choice) {
              case 1:
                  filteredMeals = filter.filterMeals("calories", value, true);
                  break;
              case 2:
                  filteredMeals = filter.filterMeals("fat", value, true);
                  break;
              case 3:
                  filteredMeals = filter.filterMeals("carbs", value, true);
                  break;
              case 4:
                  filteredMeals = filter.filterMeals("sugar", value, true);
                  break;
              case 5:
                  filteredMeals = filter.filterMeals("fiber", value, false);
                  break;
              case 6:
                  filteredMeals = filter.filterMeals("protein", value, false);
                  break;
              case 7:
                  filteredMeals = filter.filterMeals("sodium", value, true);
                  break;
            }
          
            if (filteredMeals != null) {
                System.out.println("\nMatching meals:");
                for (Meal meal : filteredMeals) {
                    System.out.println("\n" + meal);
                }
                System.out.println("Total matching meals: " + filteredMeals.size());
            }
          }
          if (choice == 3) {
            break;
          }
      }
      
      scanner.close();
  }
}
