import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import java.util.List;

public class ShowPurchasedProduct {
    public void showAllPurchasedProduct() {
        MongoDBSingleton mongoSingleton = MongoDBSingleton.getInstance();
        MongoDatabase db = mongoSingleton.getDatabase("ProductManagement");
        MongoCollection<Document> ordersCollection = db.getCollection("Orders");
        try (MongoCursor<Document> cursor = ordersCollection.find().iterator()) {
            while (cursor.hasNext()) {
                Document orderDocument = cursor.next();
                System.out.println("Order ID: " + orderDocument.getString("order_id"));
                System.out.println("Total Quantity: " + orderDocument.getInteger("totalQuantity"));
                System.out.println("Total Price: " + orderDocument.getInteger("totalPrice"));
                System.out.println("Created At: " + orderDocument.get("created_at"));
                List<Document> orders = orderDocument.getList("orders", Document.class);
                System.out.println("Product Details:");
                for (Document order : orders) {
                    System.out.println("  Product ID: " + order.getString("prod_id"));
                    System.out.println("  Product Name: " + order.getString("prod_name"));
                    System.out.println("  Category Name: " + order.getString("category_name"));
                    System.out.println("  Buy Quantity: " + order.getInteger("buy_quantity"));
                    System.out.println("  Product Price: " + order.getInteger("prod_price"));
                    System.out.println();
                }
                System.out.println("-----------------------------------------");
            }
        }
    }
}
