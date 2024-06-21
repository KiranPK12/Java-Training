import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class GetAllProducts {
    public void getAllProduct() {
        MongoDBSingleton mongoSingleton = MongoDBSingleton.getInstance();
        MongoDatabase db = mongoSingleton.getDatabase("ProductManagement");
        MongoCollection<Document> productCollection = db.getCollection("Products");
        try (MongoCursor<Document> cursor = productCollection.find().iterator()) {
            if (productCollection.countDocuments() == 0) {
                System.out.println("No Products");
            } else {
                while (cursor.hasNext()) {
                    Document document = cursor.next();
                    if (!Boolean.parseBoolean(document.getString("soft_delete"))) {
                        String prodId = document.getString("prod_id");
                        String prodName = document.getString("prod_name");
                        int prodQuantity = document.getInteger("prod_quantity");
                        int prodPrice = document.getInteger("prod_price");
                        Document categoryDoc = document.get("category", Document.class);
                        String categoryName = categoryDoc.getString("category_name");

                        System.out.println("Product ID: " + prodId);
                        System.out.println("Product Name: " + prodName);
                        System.out.println("Quantity: " + prodQuantity);
                        System.out.println("Price: " + prodPrice);
                        System.out.println("Category: " + categoryName);
                        System.out.println();
                    }
                }
            }
        }
    }
}
