import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class MealSearch {
    private final Map<String, Meal> mealDetails;
    private final Map<String, Integer> searchCount;

    public MealSearch() {
        this.mealDetails = new HashMap<>();
        this.searchCount = new HashMap<>();
    }

    public void loadCSVData(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean isHeader = true; // Skip the header row
            while ((line = reader.readLine()) != null) {
                if (isHeader) {
                    isHeader = false;
                    continue;
                }
                String[] parts = CSVUtils.parseCSVLine(line);
                if (parts.length == 8) {
                    String website = parts[0].trim();
                    String name = parts[1].toLowerCase().trim();  // Ensure lowercase for searching
                    mealDetails.put(name, new Meal(website, name));
                    searchCount.put(name, 0); // Initialize search count
                } else {
                    System.err.println("⚠️ Malformed row. Skipping: " + line);
                }
            }
        } catch (IOException e) {
            System.err.println("⚠️ Error reading file: " + filePath + " - " + e.getMessage());
        }
    }

    public void searchMeal(String query) {
        query = query.toLowerCase().trim();  // Convert query to lowercase and trim spaces

        boolean found = false;
        System.out.println("\nMeal(s) found with query " + query + ":");
        for (String mealName : mealDetails.keySet()) {
            if (kmpSearch(mealName, query)) {
                Meal meal = mealDetails.get(mealName);
                displayMealDetails(meal);
                incrementSearchCount(mealName);  // Increment search count for the found meal
                found = true;
            }
        }

        if (!found) {
            System.out.println("No meals found with query " + query);
        }
    }

    public void displayPopularMeals() {
        List<Map.Entry<String, Integer>> mealList = new ArrayList<>(searchCount.entrySet());
        mealList.sort((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()));  // Sort by count descending

        System.out.println("\nPopular Meals:");
        int count = 0;
        for (Map.Entry<String, Integer> entry : mealList) {
            if (count >= 5) break;
            String mealName = entry.getKey();
            System.out.println("Meal: " + mealName + " | Search Count: " + entry.getValue());
            count++;
        }
    }

    private void incrementSearchCount(String mealName) {
        searchCount.put(mealName, searchCount.getOrDefault(mealName, 0) + 1);
    }

    private void displayMealDetails(Meal meal) {
        System.out.printf("%-85s | %-20s%n", meal.name, meal.website);
    }

    private boolean kmpSearch(String text, String pattern) {
        int[] lps = computeLPSArray(pattern);
        int i = 0, j = 0;

        while (i < text.length()) {
            if (pattern.charAt(j) == text.charAt(i)) {
                i++;
                j++;
            }

            if (j == pattern.length()) {
                return true;
            } else if (i < text.length() && pattern.charAt(j) != text.charAt(i)) {
                if (j != 0) {
                    j = lps[j - 1];
                } else {
                    i++;
                }
            }
        }
        return false;
    }
    

    private int[] computeLPSArray(String pattern) {
        int[] lps = new int[pattern.length()];
        int length = 0;
        int i = 1;

        while (i < pattern.length()) {
            if (pattern.charAt(i) == pattern.charAt(length)) {
                length++;
                lps[i] = length;
                i++;
            } else {
                if (length != 0) {
                    length = lps[length - 1];
                } else {
                    lps[i] = 0;
                    i++;
                }
            }
        }
        return lps;
    }
}
