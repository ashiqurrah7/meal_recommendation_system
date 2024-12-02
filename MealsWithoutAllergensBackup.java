import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class MealsWithoutAllergensBackup {
    private Set<String> allergensDictionary;
    private List<String[]> meals;

    public MealsWithoutAllergensBackup(String filePath) {
        allergensDictionary = new HashSet<>();
        meals = new ArrayList<>();
        processCSV(filePath);
    }

    private void processCSV(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line = br.readLine(); // Read header and skip it

            while ((line = br.readLine()) != null) {
                // Split line by handling quotes and commas
                String[] parts = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");

                if (parts.length < 8) continue; // Skip invalid lines

                String allergens = parts[5].trim().replace("\"", "");
                String name = parts[1].trim().replace("\"", "");
                String ingredients = parts[6].trim().replace("\"", "");

                // Populate allergen dictionary (handle null values gracefully)
                if (!allergens.isEmpty()) {
                    String[] allergenList = allergens.split(",\\s*");
                    allergensDictionary.addAll(Arrays.asList(allergenList));
                }

                // Store meals for filtering
                meals.add(new String[]{name, ingredients, allergens});
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Set<String> getAllergensDictionary() {
        return allergensDictionary;
    }

    public List<String[]> getMealsWithoutAllergen(String allergen) {
        return meals.stream()
                .filter(meal -> meal[2] == null || !meal[2].contains(allergen))
                .collect(Collectors.toList());
    }

    public String findClosestMatch(String input) {
        return allergensDictionary.stream()
                .min(Comparator.comparingInt(a -> levenshteinDistance(a.toLowerCase(), input.toLowerCase())))
                .orElse(input); // Default to input if no matches found
    }

    private int levenshteinDistance(String a, String b) {
        int[][] dp = new int[a.length() + 1][b.length() + 1];

        for (int i = 0; i <= a.length(); i++) {
            for (int j = 0; j <= b.length(); j++) {
                if (i == 0) {
                    dp[i][j] = j;
                } else if (j == 0) {
                    dp[i][j] = i;
                } else if (a.charAt(i - 1) == b.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    dp[i][j] = 1 + Math.min(dp[i - 1][j - 1],
                            Math.min(dp[i - 1][j], dp[i][j - 1]));
                }
            }
        }

        return dp[a.length()][b.length()];
    }
}
