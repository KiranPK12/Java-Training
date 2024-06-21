import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

class AddProduct {
    Scanner sc = new Scanner(System.in);

    public void getProductDetails(HashMap<Integer, Product> ProductList) {
        MongoDBSingleton mongoSingleton = MongoDBSingleton.getInstance();
        try {
            MongoDatabase db = mongoSingleton.getDatabase("ProductManagement");
            MongoCollection<Document> categoryCollection = db.getCollection("Category");
            MongoCollection<Document> productCollection = db.getCollection("Products");

            System.out.println("Enter the details of your product ");
            System.out.print("Enter the Product name:  ");
            String productName = sc.nextLine();
            System.out.print("Enter the Product Stock:  ");
            int stockSize = sc.nextInt();
            System.out.print("Enter the Price:  ");
            int price = sc.nextInt();
            sc.nextLine();
            if (price < 1 || stockSize < 1) {
                System.out.println("Invalid Stock or Price count");
            } else {
                String category;
                int count = 0;
                Map<String, String> categoryMap = new HashMap<>();
                try (MongoCursor<Document> cursor = categoryCollection.find().iterator();) {
                    while (cursor.hasNext()) {
                        Document document = cursor.next();
                        String categoryName = document.getString("category_name");
                        String categoryId = document.getString("id");
                        categoryMap.put(categoryName.toLowerCase(), categoryId);
                    }
                }
                if (categoryMap.isEmpty()) {
                    System.out.println("Add category first");
                } else {
                    System.out.println("Choose the product category :  ");

                    for (Map.Entry<String, String> entry : categoryMap.entrySet()) {
                        System.out.println(entry.getKey());
                    }
                    category = sc.nextLine().toLowerCase();
                    try (MongoCursor<Document> cursor = productCollection.find().iterator();) {
                        while (cursor.hasNext()) {
                            Document document = cursor.next();
                            count += 1;
                        }
                        Document categoryDoc = new Document("category_name", category).append("category_id", categoryMap.get(category));
                        Document productDoc = new Document("prod_id", "PROD_" + count)
                                .append("prod_name", productName)
                                .append("prod_quantity", stockSize)
                                .append("prod_price", price)
                                .append("category", categoryDoc)
                                .append("created_at", LocalDateTime.now()).append("soft_delete", "false");
                        productCollection.insertOne(productDoc);

                        System.out.println("New product added to the database.");
                    }
                }
            }
        } catch (
                InputMismatchException e) {
            System.out.println("Enter valid inputs like product name , stock count and price");
            sc.nextLine();
        }
    }
}

