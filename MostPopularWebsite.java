import java.util.*;

public class MostPopularWebsite {
    public static void searchSiteAndDisplayRank(Scanner scanner) {
        // List of web pages with predefined page ranks
        List<WebPage> webPages = Arrays.asList(
                new WebPage("https://www.greenchef.com/", 0),
                new WebPage("https://www.hellofresh.ca/", 0),
                new WebPage("https://www.chefsplate.com/", 0),
                new WebPage("https://cook.homechef.com/", 0),
                new WebPage("https://www.makegoodfood.ca/", 0)
        );

        // Map for simplifying user input (e.g., "chefplate" maps to the full URL)
        Map<String, String> websiteMap = new HashMap<>();
        websiteMap.put("greenchef", "https://www.greenchef.com/");
        websiteMap.put("hellofresh", "https://www.hellofresh.ca/");
        websiteMap.put("chefplate", "https://www.chefsplate.com/");
        websiteMap.put("homechef", "https://cook.homechef.com/");
        websiteMap.put("makegoodfood", "https://www.makegoodfood.ca/");

        // Map to track search frequency for each website
        Map<String, WebPage> websiteTracker = new HashMap<>();
        for (WebPage page : webPages) {
            websiteTracker.put(page.getUrl(), page);
        }

        // Inverted index to track search terms (keywords) and associated websites
        Map<String, Map<String, Integer>> invertedIndex = new HashMap<>();

        while (true) {
            System.out.println("\nEnter the keyword (website name) you want to search (or type 'exit' to quit): ");
            String userInput = scanner.nextLine().trim().toLowerCase(); // Convert to lowercase for case-insensitivity

            if (userInput.equalsIgnoreCase("exit")) {
                break;
            }

            // Check if user input matches any simplified website name
            if (websiteMap.containsKey(userInput)) {
                String url = websiteMap.get(userInput);
                WebPage selectedPage = websiteTracker.get(url);
                selectedPage.incrementSearchFrequency();
                System.out.println("Search recorded for: " + userInput);
                System.out.println("Full URL: " + url); // Show full URL

                // Update inverted index for this search term
                invertedIndex.putIfAbsent(userInput, new HashMap<>());
                Map<String, Integer> websiteSearchFrequency = invertedIndex.get(userInput);
                websiteSearchFrequency.put(url, websiteSearchFrequency.getOrDefault(url, 0) + 1);
            } else {
                System.out.println("No website found for the keyword: " + userInput);
            }

            // Calculate and display popularity scores
            displayRankedWebsites(webPages);
        }

        // Display final most popular website
        displayMostPopularWebsite(webPages);

        // Display Inverted Index (for debugging purposes)
        System.out.println("\nInverted Index:");
        displayInvertedIndex(invertedIndex);
    }

    private static void displayRankedWebsites(List<WebPage> webPages) {
        final int PAGE_RANK_WEIGHT = 2;
        final int SEARCH_FREQUENCY_WEIGHT = 1;

        webPages.sort((p1, p2) ->
                Integer.compare(
                        p2.calculatePopularityScore(PAGE_RANK_WEIGHT, SEARCH_FREQUENCY_WEIGHT),
                        p1.calculatePopularityScore(PAGE_RANK_WEIGHT, SEARCH_FREQUENCY_WEIGHT)
                )
        );

        System.out.println("\nRanked Websites by Popularity:");
        for (WebPage page : webPages) {
            int score = page.calculatePopularityScore(PAGE_RANK_WEIGHT, SEARCH_FREQUENCY_WEIGHT);
            System.out.println("URL: " + page.getUrl() + " | Popularity Score: " + score);
        }
    }

    private static void displayMostPopularWebsite(List<WebPage> webPages) {
        if (webPages.isEmpty()) {
            System.out.println("\nNo websites available.");
            return;
        }

        WebPage mostPopular = webPages.get(0);
        System.out.println("\nMost Popular Website:");
        System.out.println("URL: " + mostPopular.getUrl() +
                " | Page Rank: " + mostPopular.getPageRank() +
                " | Search Frequency: " + mostPopular.getSearchFrequency());
    }

    private static void displayInvertedIndex(Map<String, Map<String, Integer>> invertedIndex) {
        for (Map.Entry<String, Map<String, Integer>> entry : invertedIndex.entrySet()) {
            System.out.println("Search Term: " + entry.getKey());
            for (Map.Entry<String, Integer> websiteEntry : entry.getValue().entrySet()) {
                System.out.println("  Website: " + websiteEntry.getKey() + " | Search Frequency: " + websiteEntry.getValue());
            }
        }
    }
}
