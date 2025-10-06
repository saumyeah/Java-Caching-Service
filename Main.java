import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        // IMPORTANT: Make sure User_Data.csv is in the root directory of your project
        DatabaseService service = new DatabaseService("User_Data.csv");

        // 1. First query for "Male" -> SLOW (2 seconds)
        List<Map<String, String>> maleResults = service.getDataForGender("Male");
        System.out.println("Received " + maleResults.size() + " records for Male.");

        // 2. Second query for "Male" -> INSTANT (Cache HIT)
        maleResults = service.getDataForGender("Male");
        System.out.println("Received " + maleResults.size() + " records for Male.");

        // 3. First query for "Female" -> SLOW (2 seconds)
        List<Map<String, String>> femaleResults = service.getDataForGender("Female");
        System.out.println("Received " + femaleResults.size() + " records for Female.");

        // 4. Query for "Male" again -> INSTANT (Cache HIT, still in cache)
        maleResults = service.getDataForGender("Male");
        System.out.println("Received " + maleResults.size() + " records for Male.");

        // 5. Query for a new gender "Unknown" -> SLOW (and will evict "Female" which is now the LRU)
        List<Map<String, String>> otherResults = service.getDataForGender("Unknown");
        System.out.println("Received " + otherResults.size() + " records for Unknown.");

        // 6. Query for "Female" again -> SLOW (Cache MISS, was evicted)
        femaleResults = service.getDataForGender("Female");
        System.out.println("Received " + femaleResults.size() + " records for Female.");
    }
}