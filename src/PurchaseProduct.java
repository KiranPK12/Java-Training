import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class PurchaseProduct {
    public void purchaseProduct() throws SQLException {
        Scanner sc = ScannerSingleton.getInstance();
        MongoDBSingleton mongoSingleton = MongoDBSingleton.getInstance();
        MongoDatabase db = mongoSingleton.getDatabase("ProductManagement");
        MongoCollection<Document> productCollection = db.getCollection("Products");
        int order_id = 0;
        String maxIdQuery = "SELECT MAX(order_id) AS max_id FROM orders";
        try (Connection conn = PostgresConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(maxIdQuery);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                order_id = rs.getInt("max_id");
            }
            order_id++;
        } catch (SQLException e) {
            System.out.println("SQL Exception");
            e.printStackTrace();
        } finally {
            PostgresConnect.closeConnection();
            System.out.println("Terminated");
        }
        int total_prod_quantity = 0;
        int total_prod_price = 0;

        System.out.println("Order id os " + order_id);
        int ctn = 1;
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
                        buyCount = Integer.parseInt(sc.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Please enter a valid number.");
                        continue;
                    }
                    int existingQuantity = existingProduct.getInteger("prod_quantity");
                    if (existingQuantity == 0) {
                        System.out.println("Out of stocks");
                    } else if (buyCount < 0) {
                        System.out.println("Cannot buy negative stocks");
                    } else if (existingQuantity < buyCount) {
                        System.out.println("More offer than availability");
                    } else {
                        Document updateDoc = new Document("$set", new Document("prod_quantity", existingQuantity - buyCount));
                        productCollection.updateOne(filter, updateDoc);

                        Document categoryDoc = existingProduct.get("category", Document.class);
                        String categoryName = categoryDoc.getString("category_name");
                        int productPrice = existingProduct.getInteger("prod_price");
                        String insertQuery = "INSERT INTO order_items (order_id, prod_id, buy_quantity, prod_name, prod_category, prod_price, created_at) VALUES (?, ?, ?, ?, ?, ?, NOW())";

                        try (Connection conn = PostgresConnect.getConnection();
                             PreparedStatement stmt = conn.prepareStatement(insertQuery)) {

                            stmt.setInt(1, order_id);
                            stmt.setString(2, targetId);
                            stmt.setInt(3, buyCount);
                            stmt.setString(4, existingProduct.getString("prod_name"));
                            stmt.setString(5, categoryName);
                            stmt.setDouble(6, productPrice);
                            int rowsAffected = stmt.executeUpdate();
                            System.out.println("Rows inserted: " + rowsAffected);

                        } catch (SQLException e) {
                            e.printStackTrace();
                        } finally {
                            PostgresConnect.closeConnection();
                            total_prod_quantity += buyCount;
                            total_prod_price += (buyCount * productPrice);
                            System.out.println("Purchase Completed");
                        }
                    }
                }
            }
            System.out.println("Do you want to add few more products. Press 1 to continue");
            try {
                ctn = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                ctn = 0;
            }
        } while (ctn == 1);
        String insertQuery = "INSERT INTO orders (order_items_fk,total_quantity, total_price, status, created_at) VALUES (?,?, ?, ?, NOW())";
        try (Connection conn = PostgresConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(insertQuery)) {

            // Set the values for the insert query
            stmt.setInt(1, order_id);
            stmt.setInt(2, total_prod_quantity);
            stmt.setInt(3, total_prod_price);
            stmt.setString(4, "Order Placed");

            int rowsAffected = stmt.executeUpdate();
            System.out.println("Rows inserted: " + rowsAffected);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            PostgresConnect.closeConnection();
            System.out.println("Terminated");
        }
    }
}

