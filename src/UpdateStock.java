import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.Scanner;

public class UpdateStock {
    public void UpdateStockCount() {
        Scanner sc = ScannerSingleton.getInstance();
        MongoDBSingleton mongoSingleton = MongoDBSingleton.getInstance();
        MongoDatabase db = mongoSingleton.getDatabase("ProductManagement");
        MongoCollection<Document> productCollection = db.getCollection("Products");
        if (productCollection.countDocuments() == 0) {
            System.out.println("No products exists");
        } else {
            System.out.println("Enter the Id to update stock: ");
            String targetId = sc.nextLine().toUpperCase();
            Document filter = new Document("prod_id", targetId);
            Document existingProduct = productCollection.find(filter).first();
            if (existingProduct == null || Boolean.parseBoolean(existingProduct.getString("soft_delete"))) {
                System.out.println("Invalid Product ID");
            } else {
                int existingQuantity = existingProduct.getInteger("prod_quantity");
                System.out.println("Enter the new stock: ");
                int newStockCount = sc.nextInt();
                sc.nextLine();
                if (newStockCount < 0) {
                    System.out.println("At least one product need to be added");
                } else {
                    System.out.println("Do you want to Override or Increment? type 'o' for override , 'i' for increment");
                    String input = sc.nextLine().trim();
                    if (input.isEmpty() ) {
                        System.out.println("Invalid Options");
                    } else {
                        Document updateDoc;
                        if (input.charAt(0) == 'o') {
                            updateDoc = new Document("$set", new Document("prod_quantity", newStockCount));
                        } else {
                            updateDoc = new Document("$set", new Document("prod_quantity", newStockCount + existingQuantity));
                        }
                        productCollection.updateOne(filter, updateDoc);
                        System.out.println("Price updated in ID " + targetId);
                    }
                }
            }
        }
    }
}
