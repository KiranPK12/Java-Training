import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Scanner;

public class PurchaseProduct {
    public void purchaseProduct(HashMap<Integer, Product> ProductList, HashMap<Integer, Purchase> PurchaseList) {
        int purchaseId = PurchaseList.size() + 1;
        MongoDBSingleton mongoSingleton = MongoDBSingleton.getInstance();
        MongoDatabase db = mongoSingleton.getDatabase("ProductManagement");
        MongoCollection<Document> productCollection = db.getCollection("Products");
        MongoCollection<Document> purchaseCollection = db.getCollection("Purchase");


        if (productCollection.countDocuments() == 0) {
            System.out.println("No products exists");
        } else {
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter the Product Id to purchase: ");
            String targetId = sc.nextLine().toUpperCase();
            Document filter = new Document("prod_id", targetId);
            Document existingProduct = productCollection.find(filter).first();
            if (existingProduct == null || Boolean.parseBoolean(existingProduct.getString("soft_delete"))) {
                System.out.println("Invalid Product ID");
            } else {
                System.out.println("Enter the stocks to buy : ");
                int buyCount = sc.nextInt();
                int existingQuantity = existingProduct.getInteger("prod_quantity");
                if (existingQuantity == 0) {
                    System.out.println("Out of stocks");
                } else if (buyCount < 0) {
                    System.out.println("Cannot buy negative stocks");
                } else if (existingQuantity < buyCount) {
                    System.out.println("More offer than availability");
                } else {
                    long count = purchaseCollection.countDocuments();
                    Document categoryDoc = existingProduct.get("category", Document.class);
                    String categoryName = categoryDoc.getString("category_name");
                    LocalDateTime customDateTime = LocalDateTime.of(2024, 6, 19, 10, 30, 0);
//"created_at", LocalDateTime.now()
                    Document insertDoc = new Document("purchase_id", "PUR_" + count).append("prod_id", targetId).append("buy_quantity", buyCount).append("prod_name", existingProduct.getString("prod_name")).append("category_name", categoryName).append("created_at", "2024-06-19T00:00:00").append("prod_price", existingProduct.getInteger("prod_price")).append("purchase_value", existingProduct.getInteger("prod_price") * buyCount);
                    purchaseCollection.insertOne(insertDoc);
                    Document updateDoc = new Document("$set", new Document("prod_quantity", existingQuantity - buyCount));
                    productCollection.updateOne(filter, updateDoc);
                    System.out.println("Purchase Completed");
                }
            }
        }
    }

}
