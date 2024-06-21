import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.time.LocalDateTime;
import java.util.*;

import static com.mongodb.client.model.Accumulators.push;
import static com.mongodb.client.model.Accumulators.sum;
import static com.mongodb.client.model.Aggregates.group;
import static com.mongodb.client.model.Aggregates.match;
import static com.mongodb.client.model.Filters.eq;

public class PurchaseProduct {
    public void purchaseProduct() {
        Scanner sc = new Scanner(System.in);
        MongoDBSingleton mongoSingleton = MongoDBSingleton.getInstance();
        MongoDatabase db = mongoSingleton.getDatabase("ProductManagement");
        MongoCollection<Document> productCollection = db.getCollection("Products");
        MongoCollection<Document> orderItemsCollection = db.getCollection("Order_Item");
        MongoCollection<Document> orderCollection = db.getCollection("Orders");
        int cntn = 1;
        long orderId = orderCollection.countDocuments() + 1;
        do {
            if (productCollection.countDocuments() == 0) {
                System.out.println("No products exists");
            } else {
                System.out.println("Enter the Product Id to purchase: ");
                String targetId = sc.nextLine().toUpperCase();
                Document filter = new Document("prod_id", targetId);
                Document existingProduct = productCollection.find(filter).first();
                if (existingProduct == null || Boolean.parseBoolean(existingProduct.getString("soft_delete"))) {
                    System.out.println("Invalid Product ID");
                } else {
                    System.out.println("Enter the stocks to buy : ");
                    int buyCount;
                    try {
                        buyCount = Integer.parseInt(sc.nextLine()); // Read input as string and parse as integer
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Please enter a valid number.");
                        continue; // Restart loop if input is not a valid number
                    }
                    int existingQuantity = existingProduct.getInteger("prod_quantity");
                    if (existingQuantity == 0) {
                        System.out.println("Out of stocks");
                    } else if (buyCount < 0) {
                        System.out.println("Cannot buy negative stocks");
                    } else if (existingQuantity < buyCount) {
                        System.out.println("More offer than availability");
                    } else {
                        Document categoryDoc = existingProduct.get("category", Document.class);
                        String categoryName = categoryDoc.getString("category_name");
                        Document insertDoc = new Document("purchase_id", "PUR_" + orderId).append("prod_id", targetId).append("buy_quantity", buyCount).append("prod_name", existingProduct.getString("prod_name")).append("category_name", categoryName).append("created_at", LocalDateTime.now()).append("prod_price", existingProduct.getInteger("prod_price"));
                        orderItemsCollection.insertOne(insertDoc);
                        Document updateDoc = new Document("$set", new Document("prod_quantity", existingQuantity - buyCount));
                        productCollection.updateOne(filter, updateDoc);
                        System.out.println("Purchase Completed");
                    }
                }
            }
            System.out.println("Do you want to add few more products. Press 1 to continue");
            try {
                cntn = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                cntn = 0;
            }
        } while (cntn == 1);
//      System.out.print("Sure you are good with this less product ? n for no else any key");
        AggregateIterable<Document> result = orderItemsCollection.aggregate(Arrays.asList(
                        match(eq("purchase_id", "PUR_" + orderId)),
                        group("$purchase_id",
                                push("orders", "$$ROOT"),
                                sum("totalQuantity", "$buy_quantity"),
                                sum("totalPrice", new Document("$multiply", Arrays.asList("$buy_quantity", "$prod_price")))
                        )
                )).maxTime(60000, java.util.concurrent.TimeUnit.MILLISECONDS)
                .allowDiskUse(true);

        Document resultantDoc = result.first();
        assert resultantDoc != null;
        List<Document> orders = resultantDoc.getList("orders", Document.class);
        int price = resultantDoc.getInteger("totalPrice");
        int quantity = resultantDoc.getInteger("totalQuantity");

        Document orderDocument = new Document("order_id", "PUR_" + orderId)
                .append("orders", orders)
                .append("totalQuantity", quantity)
                .append("totalPrice", price).append("created_at", LocalDateTime.now());
        orderCollection.insertOne(orderDocument);
    }
}

