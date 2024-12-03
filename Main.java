import java.util.*;

public class Main {

  private static void println(String message) {
    System.out.println(message);
  }

  private static void print(String message) {
    System.out.print(message);
  }

  public static void main(String[] args) {
    String mealsPath = "meals.csv";
    MealFilter filter = new MealFilter(mealsPath);
    MealsWithoutAllergens mealsWithoutAllergens = new MealsWithoutAllergens(mealsPath);
    MealDescription mealDescription = new MealDescription(mealsPath);
    BoyerMooreFrequency frequencySearch = new BoyerMooreFrequency(mealDescription.getWebsiteDescriptions());
    MealSearch tracker = new MealSearch();
    tracker.loadCSVData(mealsPath);
    Scanner scanner = new Scanner(System.in);

    while (true) {
      try {
        int choice;

        println("\nChoose an option:");
        println("1.  Look for sites according to your dietary preference");
        println("2.  Look for meals according to your nutrition preference");
        println("3.  Look for meals according to your allergens");
        println("4.  Search for word frequencies");
        println("5.  Search for meals");
        println("6.  Search for meals excluding an ingredient");
        println("7.  View top 10 cheapest meal plans");
        println("8.  Search for website and see website rankings");
        println("9.  Get list of meal plans by site");
        println("10. Exit");
        print("Enter your choice (1-10): ");

        choice = scanner.nextInt();

        if (choice < 1 || choice > 10) {
          println("Please select a valid option");
          continue;
        }

        if (choice == 1) {
          println("\nChoose dietary preference:");
          println("1. Vegetarian");
          println("2. Pescatarian");
          println("3. Low Carb");
          println("4. High Protein");
          println("5. Go back");
          print("Enter your choice (1-5): ");
  
          int dietChoice = scanner.nextInt();
          String preference = "";
  
          switch (dietChoice) {
            case 1:
              preference = "vegetarian";
              break;
            case 2:
              preference = "pescatarian";
              break;
            case 3:
              preference = "low carb";
              break;
            case 4:
              preference = "high protein";
              break;
            case 5:
              continue;
            default:
              println("Invalid choice");
              continue;
          }
  
          DietaryFilter dietaryFilter = new DietaryFilter("dietary_options.csv");
          List<String> matchingWebsites = dietaryFilter.getWebsitesForDiet(preference);
  
          if (matchingWebsites.isEmpty()) {
            println("\nNo websites found matching your dietary preference.");
          } else {
            println("\nWebsites offering " + preference + " options:");
            for (String website : matchingWebsites) {
              println("- " + website);
            }
          }
        }
  
        if (choice == 2) {
          println("1. Calories < x");
          println("2. Fat < x");
          println("3. Carbs < x");
          println("4. Sugar < x");
          println("5. Fiber > x");
          println("6. Protein > x");
          println("7. Sodium < x");
          println("8. Go back");
  
          print("Enter your choice (1-8): ");
  
          choice = scanner.nextInt();
  
          if (choice < 1 || choice > 8) {
            println("Please select a valid option");
            continue;
          }
  
          if (choice == 8) {
            continue;
          }
  
          print("Enter value: ");
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
            println("\nMatching meals:");
            for (Meal meal : filteredMeals) {
              println("\n" + meal);
            }
            println("Total matching meals: " + filteredMeals.size());
          }
        }
  
        if (choice == 3) {
          scanner.nextLine();
          System.out.println("Allergens List:");
          mealsWithoutAllergens.getAllergensDictionary().forEach(System.out::println);
  
          boolean repeatAllergenCheck = true;
  
          while (repeatAllergenCheck) {
            System.out.println("\nDo you have any allergen? (y/n)");
            String hasAllergen = scanner.nextLine().trim().toLowerCase();
  
            if (hasAllergen.equals("y")) {
              System.out.println("Enter the allergen:");
              String userInput = scanner.nextLine().trim();
  
              // Find closest match
              String closestMatch = mealsWithoutAllergens.findClosestMatch(userInput);
  
              System.out.println("Did you mean \"" + closestMatch + "\"? (y/n)");
              String confirmation = scanner.nextLine().trim().toLowerCase();
  
              if (confirmation.equals("y")) {
                // Get and display meals without the specified allergen
                List<String[]> safeMeals = mealsWithoutAllergens.getMealsWithoutAllergen(closestMatch);
  
                System.out.println("\nMeals without \"" + closestMatch + "\":");
                safeMeals.forEach(meal -> {
                  System.out.println("Website: " + meal[0]);
                  System.out.println("Meal: " + meal[1]);
                  System.out.println("Ingredients: " + meal[2]);
                  System.out.println();
                });
  
                repeatAllergenCheck = false; // Exit after showing results
              } else {
                System.out.println("Do you want to try again? (y/n)");
                String tryAgain = scanner.nextLine().trim().toLowerCase();
                repeatAllergenCheck = tryAgain.equals("y");
              }
            } else {
              System.out.println("No allergen specified. Exiting...");
              repeatAllergenCheck = false;
            }
          }
        }
  
        if (choice == 4) {
          scanner.nextLine();
          boolean repeat = true;
  
          while (repeat) {
            System.out.println("Enter the string to search:");
            String searchString = scanner.nextLine();
  
            // Perform frequency search
            Map<String, Integer> frequencies = frequencySearch.search(searchString);
  
            // Display results sorted by frequency
            System.out.println("\nSearch Results:");
            frequencies.entrySet().stream()
                .sorted((a, b) -> b.getValue().compareTo(a.getValue()))
                .forEach(entry -> System.out.println(entry.getKey() + ": " + entry.getValue()));
  
            // Ask if user wants to search for another word
            System.out.println("\nDo you want to check another word? (y/n):");
            String response = scanner.nextLine().trim().toLowerCase();
            if (!response.equals("y")) {
              repeat = false;
              System.out.println("Goodbye!");
            }
          }
        }
  
        if (choice == 5) {
          scanner.nextLine();
          println("\nEnter a meal to search, 'popular' to search for most popular meals, or 'exit' to quit");
          String query = scanner.nextLine().trim();
          while (!query.equalsIgnoreCase("exit")) {
            if (query.equalsIgnoreCase("popular")) {
              tracker.displayPopularMeals();
            } else {
              tracker.searchMeal(query);
            }
            println("\nEnter a meal to search, 'popular' to search for most popular meals, or 'exit' to quit");
            query = scanner.nextLine().trim();
          }
        }
  
        if (choice == 6) {
          scanner.nextLine();
          ExclusionHandler.handleMeals(scanner);
        }
  
        if (choice == 7) {
          scanner.nextLine();
          MealPlanHandler.findTop10CheapestMeals();
        }

        if (choice == 8) {
          scanner.nextLine();
          MostPopularWebsite.searchSiteAndDisplayRank(scanner);
        }

        if (choice == 9) {
          scanner.nextLine();
          MealPlanExtractor.getMealPlansBySite(scanner);
        }
  
        if (choice == 10) {
          println("\nThank you for using our meal recommendation system!");
          break;
        }

      } catch (InputMismatchException e) {
        println("Please enter a number within 1-8");
        scanner.nextLine();
        continue;
      }
    }

    scanner.close();
  }
}
