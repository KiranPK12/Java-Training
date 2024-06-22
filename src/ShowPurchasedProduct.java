
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ShowPurchasedProduct {
    public void showAllPurchasedProduct() {
        String orderQuery = "SELECT * FROM orders";
        try (Connection connection = PostgresConnect.getConnection();
             PreparedStatement orderStmt = connection.prepareStatement(orderQuery);
             ResultSet orderRs = orderStmt.executeQuery()) {
            while (orderRs.next()) {
                int orderId = orderRs.getInt("order_items_fk");
                int totalQuantity = orderRs.getInt("total_quantity");
                double totalPrice = orderRs.getDouble("total_price");
                String status = orderRs.getString("status");
                java.sql.Timestamp createdAt = orderRs.getTimestamp("created_at");

                System.out.println("Order ID: " + orderId);
                System.out.println("Total Quantity: " + totalQuantity);
                System.out.println("Total Price: " + totalPrice);
                System.out.println("Status: " + status);
                System.out.println("Created At: " + createdAt);

                String productQuery = "SELECT * FROM order_items WHERE order_id = ?";
                PreparedStatement productStmt = connection.prepareStatement(productQuery);
                productStmt.setInt(1, orderId);
                ResultSet productRs = productStmt.executeQuery();

                while (productRs.next()) {
                    String prodId = productRs.getString("prod_id");
                    int buyQuantity = productRs.getInt("buy_quantity");
                    String prodName = productRs.getString("prod_name");
                    String prodCategory = productRs.getString("prod_category");
                    double prodPrice = productRs.getDouble("prod_price");

                    System.out.println("  Product ID: " + prodId);
                    System.out.println("  Buy Quantity: " + buyQuantity);
                    System.out.println("  Product Name: " + prodName);
                    System.out.println("  Product Category: " + prodCategory);
                    System.out.println("  Product Price: " + prodPrice);
                }
                productRs.close();
                productStmt.close();

                System.out.println("-----");
            }
            orderRs.close();
            orderStmt.close();
        } catch (SQLException e) {
            System.out.println("SQL Exception error in purchase display");
            e.printStackTrace();
        }
    }
}
