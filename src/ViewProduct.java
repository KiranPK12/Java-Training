import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;

import java.util.HashMap;
import java.util.Scanner;

public class ViewProduct {
    public void viewProduct(HashMap<Integer, Product> ProductList) {
        Scanner sc = new Scanner(System.in);
        MongoDBSingleton mongoSingleton = MongoDBSingleton.getInstance();
        MongoDatabase db = mongoSingleton.getDatabase("ProductManagement");

        MongoCollection<Document> productCollection = db.getCollection("Products");

        System.out.println("Enter the Id to view product: ");
        String targetId = sc.nextLine().toUpperCase();
        Document query = productCollection.find(Filters.eq("prod_id", targetId)).first();
        if (query != null && !Boolean.parseBoolean(query.getString("soft_delete"))) {
            String prodId = query.getString("prod_id");
            String prodName = query.getString("prod_name");
            int prodQuantity = query.getInteger("prod_quantity");
            int prodPrice = query.getInteger("prod_price");
            Document categoryDoc = query.get("category", Document.class);
            String categoryName = categoryDoc.getString("category_name");

            System.out.println("Product ID: " + prodId);
            System.out.println("Product Name: " + prodName);
            System.out.println("Quantity: " + prodQuantity);
            System.out.println("Price: " + prodPrice);
            System.out.println("Category: " + categoryName);
        } else {
            System.out.println("Product with ID " + targetId + " not found.");
        }
    }
}
