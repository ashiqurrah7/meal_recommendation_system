import java.io.*;
import java.util.*;

public class MealPlanHandler {

    public static void findTop10CheapestMeals() {
        String filePath = "meal_plan.csv";
        List<MealPlan> mealPlans = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean isFirstLine = true;

            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }

                String[] values = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
                if (values.length < 4)
                    continue;

                String website = values[0];
                String mealPlan = values[1];
                int mealsPerWeek = Integer.parseInt(values[2].trim());
                double perMealPrice = Double.parseDouble(values[3].trim().replaceAll("[^0-9.]", ""));

                mealPlans.add(new MealPlan(website, mealPlan, mealsPerWeek, perMealPrice));
            }

            PriorityQueue<MealPlan> maxHeap = new PriorityQueue<>(Comparator.comparingDouble(MealPlan::getPerMealPrice).reversed());

            for (MealPlan plan : mealPlans) {
                maxHeap.offer(plan);
                if (maxHeap.size() > 10) {
                    maxHeap.poll();
                }
            }

            System.out.println("\nTop 10 Cheapest Meal Plans:");
            while (!maxHeap.isEmpty()) {
                MealPlan plan = maxHeap.poll();
                System.out.printf("- %s (Website: %s, Meals/Week: %d, Price/Meal: $%.2f)%n",
                        plan.getMealPlanName(), plan.getWebsite(), plan.getMealsPerWeek(), plan.getPerMealPrice());
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error reading or parsing the CSV file: " + e.getMessage());
        }
    }
}
