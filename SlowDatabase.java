import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SlowDatabase {
    private List<Map<String, String>> data;

    public SlowDatabase(String csvFile) {
        this.data = new ArrayList<>();
        loadData(csvFile);
    }

    private void loadData(String csvFile) {
        System.out.println("Loading data from " + csvFile + " into memory...");
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String headerLine = br.readLine();
            String[] headers = headerLine.split(",");
            this.data = br.lines()
                    .map(line -> {
                        String[] values = line.split(",");
                        Map<String, String> row = new java.util.HashMap<>();
                        for (int i = 0; i < headers.length; i++) {
                            row.put(headers[i], values[i]);
                        }
                        return row;
                    })
                    .collect(Collectors.toList());
            System.out.println("Data loaded successfully. Total records: " + this.data.size());
        } catch (IOException e) {
            System.err.println("Error reading the CSV file: " + e.getMessage());
        }
    }

    // Simulate a slow query to the database
    public List<Map<String, String>> executeQuery(String gender) {
        System.out.println("Executing SLOW query for Gender: " + gender);
        try {
            // Pretend this takes 2 seconds to run
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        List<Map<String, String>> result = this.data.stream()
                .filter(row -> gender.equalsIgnoreCase(row.get("Gender")))
                .collect(Collectors.toList());

        System.out.println("Query finished. Found " + result.size() + " records.");
        return result;
    }
}