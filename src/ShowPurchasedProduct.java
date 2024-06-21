import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.HashMap;

public class ShowPurchasedProduct {
    public void showAllPurchasedProduct(HashMap<Integer, Purchase> PurchasedProduct) {

        MongoDBSingleton mongoSingleton = MongoDBSingleton.getInstance();
        MongoDatabase db = mongoSingleton.getDatabase("ProductManagement");
        MongoCollection<Document> purchaseCollection = db.getCollection("Purchase");
        try (MongoCursor<Document> cursor = purchaseCollection.find().iterator()) {
            if (purchaseCollection.countDocuments() == 0) {
                System.out.println("No Products");
            } else {
                while (cursor.hasNext()) {
                    Document document = cursor.next();
//TODO:add filter based on date
                    String prodId = document.getString("purchase_id");
                    String category = document.getString("category_name");
                    String prodName = document.getString("prod_name");
                    int buyQuantity = document.getInteger("buy_quantity");
                    int prodPrice = document.getInteger("prod_price");
                    int netValue = buyQuantity * prodPrice;


                    System.out.println("Product ID: " + prodId);
                    System.out.println("Product Name: " + prodName);
                    System.out.println("Category: " + category);
                    System.out.println("Quantity: " + buyQuantity);
                    System.out.println("Price: " + prodPrice);
                    System.out.println("Net Value: " + netValue);
                    System.out.println();

                }
                ;
            }
        }
    }
}
