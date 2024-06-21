import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.HashMap;
import java.util.Scanner;

public class UpdatePrice {
    public void UpdateProdPrice(HashMap<Integer, Product> ProductList) {
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
            if (existingProduct == null || Boolean.parseBoolean(existingProduct.getString("soft_delete"))) {
                System.out.println("Invalid Product ID");
            } else {
                int existingPrice = existingProduct.getInteger("prod_price");
                System.out.println("Enter the new price: ");
                int newPrice = sc.nextInt();
                if (newPrice < 0) {
                    System.out.println("Invalid Pricing");
                } else {
                    System.out.println("Do you want to Override or Increment? type 'o' for override , 'i' for increment");
                    char i = sc.next().charAt(0);
                    if (i == 'o') {
                        Document updateDoc = new Document("$set", new Document("prod_price", newPrice));
                        productCollection.updateOne(filter, updateDoc);
                    } else {
                        Document updateDoc = new Document("$set", new Document("prod_price", newPrice + existingPrice));
                        productCollection.updateOne(filter, updateDoc);
                    }
                    System.out.println("Price updated in ID " + targetId);
                }
            }
        }
    }
}
