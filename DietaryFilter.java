import java.io.*;
import java.util.*;

public class DietaryFilter {
    private Map<String, List<String>> websiteDietaryOptions;
    
    public DietaryFilter(String filename) {
        websiteDietaryOptions = new HashMap<>();
        loadDietaryOptions(filename);
    }
    
    private void loadDietaryOptions(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            // Skip header line
            br.readLine();
            
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                String website = parts[0].trim().toLowerCase();
                String option = parts[1].trim().toLowerCase();
                
                websiteDietaryOptions.computeIfAbsent(website, k -> new ArrayList<>())
                    .add(option);
            }
        } catch (IOException e) {
            System.err.println("Error reading dietary options file: " + e.getMessage());
        }
    }
    
    public List<String> getWebsitesForDiet(String preference) {
        List<String> matchingWebsites = new ArrayList<>();
        preference = preference.toLowerCase();
        
        // Map user-friendly input to file terms
        Map<String, List<String>> dietaryMappings = new HashMap<>();
        dietaryMappings.put("vegetarian", Arrays.asList("vegetarian", "vegan"));
        dietaryMappings.put("pescatarian", Arrays.asList("pescatarian"));
        dietaryMappings.put("low carb", Arrays.asList("low carb", "carb smart", "carb-conscious"));
        dietaryMappings.put("high protein", Arrays.asList("high protein", "protein-packed"));
        
        List<String> searchTerms = dietaryMappings.get(preference);
        if (searchTerms == null) {
            return matchingWebsites;
        }
        
        for (Map.Entry<String, List<String>> entry : websiteDietaryOptions.entrySet()) {
            List<String> siteOptions = entry.getValue();
            for (String searchTerm : searchTerms) {
                if (siteOptions.stream().anyMatch(option -> option.contains(searchTerm))) {
                    matchingWebsites.add(entry.getKey());
                    break;
                }
            }
        }
        
        return matchingWebsites;
    }
}
