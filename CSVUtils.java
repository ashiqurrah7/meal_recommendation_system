import java.util.ArrayList;
import java.util.List;

public class CSVUtils {
    public static String[] parseCSVLine(String line) {
        List<String> result = new ArrayList<>();
        StringBuilder currentField = new StringBuilder();
        boolean insideQuotes = false;

        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);

            if (c == '"') {
                insideQuotes = !insideQuotes;
            } else if (c == ',' && !insideQuotes) {
                result.add(currentField.toString().trim());
                currentField.setLength(0);  // Reset the StringBuilder for the next field
            } else {
                currentField.append(c);
            }
        }

        // Add the last field
        result.add(currentField.toString().trim());

        return result.toArray(new String[0]);
    }
}
