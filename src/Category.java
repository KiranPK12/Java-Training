import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import org.bson.Document;


import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Category {

    public void addCategory() {
        Scanner sc = ScannerSingleton.getInstance();
        MongoDBSingleton mongoSingleton = MongoDBSingleton.getInstance();
        try {
            System.out.println("Enter the new category");
            String newCategoryName = sc.nextLine();
            MongoDatabase db = mongoSingleton.getDatabase("ProductManagement");
            MongoCollection<Document> collection = db.getCollection("Category");
            try (MongoCursor<Document> cursor = collection.find().iterator()) {

                List<String> existingCategories = new ArrayList<>();

                while (cursor.hasNext()) {
                    Document document = cursor.next();
                    String categoryName = document.getString("category_name");
                    existingCategories.add(categoryName);
                }
                int index = existingCategories.size() + 1;
                if (!existingCategories.contains(newCategoryName.toLowerCase())) {
                    Document newCategory = new Document();
                    newCategory.append("id", "CATEGORY_" + index)
                            .append("category_name", newCategoryName.toLowerCase());
                    collection.insertOne(newCategory);
                    System.out.println("New category added to the database: " + newCategoryName);
                } else {
                    System.out.println("Category name already exists in the database");
                }
            }
        } catch (InputMismatchException e) {
            System.out.println("Enter a valid Inputs");
            sc.nextLine();
        }
    }
}
