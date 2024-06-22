import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;

public class MongoDBSingleton {

    private static MongoDBSingleton instance;

    // MongoDB client instance
    private MongoClient mongoClient;

    private MongoDBSingleton() {
        // Initialize MongoDB client
        mongoClient = MongoClients.create("mongodb://localhost:27017");
    }

    // Method for singleton instance
    public static MongoDBSingleton getInstance() {
        if (instance == null) {
            synchronized (MongoDBSingleton.class) {
                if (instance == null) {
                    instance = new MongoDBSingleton();
                }
            }
        }
        return instance;
    }

    public MongoClient getClient() {
        return mongoClient;
    }

    public MongoDatabase getDatabase(String databaseName) {
        return mongoClient.getDatabase(databaseName);
    }

    public void close() {
        mongoClient.close();
    }
}
