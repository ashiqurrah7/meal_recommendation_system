import java.io.*;
import java.util.*;

public class MealDescription {
    private Map<String, List<String>> websiteDescriptions;

    public MealDescription(String filePath) {
        websiteDescriptions = new HashMap<>();
        readCSV(filePath);
    }

    private void readCSV(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line = br.readLine(); // Read the header and skip it

            while ((line = br.readLine()) != null) {
                try {
                    // Split by comma, but not within quotes
                    String[] parts = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");

                    if (parts.length < 8) continue; // Skip invalid lines

                    // Extract website (index 0) and description (index 7)
                    String website = parts[0].trim().replace("\"", "");
                    String description = parts[7].trim().replace("\"", "");

                    // Add description to the corresponding website
                    websiteDescriptions
                            .computeIfAbsent(website, k -> new ArrayList<>())
                            .add(description);
                } catch (Exception e) {
                    // Skip problematic lines
                    continue;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Map<String, List<String>> getWebsiteDescriptions() {
        return websiteDescriptions;
    }
}
