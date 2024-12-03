import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class MealPlanExtractor {

    public static void getMealPlansBySite(Scanner scanner) {
        String filePath = "meal_plan.csv";
        // List to store meal plans for the specified website
        ArrayList<String> mealPlans = new ArrayList<>();

        // Read user input for website name
        System.out.print("Enter website name: ");
        String websiteName = scanner.nextLine().trim();

        // Reading the file
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean isHeader = true; // Skip the header row

            while ((line = br.readLine()) != null) {
                // Skip the header row
                if (isHeader) {
                    isHeader = false;
                    continue;
                }

                // Split the line by commas
                String[] values = line.split(",");

                // Check if the website matches the user input
                if (values.length > 1 && values[0].trim().equalsIgnoreCase(websiteName)) {
                    mealPlans.add(values[1].trim()); // Add the Meal Plan
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Print the results
        if (mealPlans.isEmpty()) {
            System.out.println("No meal plans found for the website: " + websiteName);
        } else {
            System.out.println("Website: " + websiteName);
            System.out.println("Meal Plans:");
            for (String mealPlan : mealPlans) {
                System.out.println("- " + mealPlan);
            }
        }
    }
}
