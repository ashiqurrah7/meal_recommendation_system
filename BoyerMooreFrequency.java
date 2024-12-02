import java.util.*;

public class BoyerMooreFrequency {
    private Map<String, List<String>> websiteDescriptions;

    public BoyerMooreFrequency(Map<String, List<String>> websiteDescriptions) {
        this.websiteDescriptions = websiteDescriptions;
    }

    public Map<String, Integer> search(String searchString) {
        Map<String, Integer> frequencyMap = new HashMap<>();

        for (Map.Entry<String, List<String>> entry : websiteDescriptions.entrySet()) {
            String website = entry.getKey();
            List<String> descriptions = entry.getValue();

            int totalFrequency = 0;
            for (String description : descriptions) {
                totalFrequency += boyerMoore(description, searchString);
            }

            frequencyMap.put(website, totalFrequency);
        }

        return frequencyMap;
    }

    private int boyerMoore(String text, String pattern) {
        // Build the last occurrence map
        Map<Character, Integer> last = buildLast(pattern);

        int n = text.length();
        int m = pattern.length();
        int i = m - 1;

        if (i > n - 1) return 0;

        int j = m - 1;
        int matchCount = 0;

        while (i <= n - 1) {
            if (pattern.charAt(j) == text.charAt(i)) {
                if (j == 0) {
                    matchCount++;
                    i += m;
                    j = m - 1;
                } else {
                    i--;
                    j--;
                }
            } else {
                i += m - Math.min(j, 1 + last.getOrDefault(text.charAt(i), -1));
                j = m - 1;
            }
        }

        return matchCount;
    }

    private Map<Character, Integer> buildLast(String pattern) {
        Map<Character, Integer> last = new HashMap<>();

        for (int i = 0; i < pattern.length(); i++) {
            last.put(pattern.charAt(i), i);
        }

        return last;
    }
}
