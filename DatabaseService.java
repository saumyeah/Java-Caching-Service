import java.util.List;
import java.util.Map;

public class DatabaseService {
    // Our cache will store query string and the list of results.
    // Let's give it a small capacity to see the eviction policy in action.
    private final LRUCache<String, List<Map<String, String>>> cache;
    private final SlowDatabase database;

    public DatabaseService(String csvFile) {
        this.cache = new LRUCache<>(2); // Cache capacity of 2 queries
        this.database = new SlowDatabase(csvFile);
    }

    public List<Map<String, String>> getDataForGender(String gender) {
        System.out.println("\n--- Requesting data for gender: " + gender + " ---");
        
        // Try to get the result from the cache first
        List<Map<String, String>> cachedResult = cache.get(gender);

        if (cachedResult != null) {
            System.out.println(">>> Cache HIT! Returning data instantly.");
            return cachedResult;
        }

        // If not in cache, it's a "Cache MISS"
        System.out.println(">>> Cache MISS! Querying the slow database...");
        List<Map<String, String>> dbResult = database.executeQuery(gender);
        
        // Put the new result into the cache for future requests
        cache.put(gender, dbResult);
        return dbResult;
    }
}