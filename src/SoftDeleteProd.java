import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.HashMap;
import java.util.Scanner;

public class SoftDeleteProd {
    public void DeleteProd(HashMap<Integer, Product> ProductList) {
        Scanner sc = new Scanner(System.in);
        MongoDBSingleton mongoSingleton = MongoDBSingleton.getInstance();
        MongoDatabase db = mongoSingleton.getDatabase("ProductManagement");
        MongoCollection<Document> productCollection = db.getCollection("Products");
        if (productCollection.countDocuments() == 0) {
            System.out.println("No products exists");
        } else {
            System.out.println("Enter the Id to update price: ");
            String targetId = sc.nextLine().toUpperCase();
            Document filter = new Document("prod_id", targetId);
            Document existingProduct = productCollection.find(filter).first();

            if (existingProduct == null) {
                System.out.println("Product does not exist");
            } else {
                Document updateDoc = new Document("$set", new Document("soft_delete", existingProduct.getString("soft_delete").equals("false") ? "true" : "false"));
                productCollection.updateOne(filter, updateDoc);
                System.out.println("Product visibility toggled");
            }
        }
    }
}
