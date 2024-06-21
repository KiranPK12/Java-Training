import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Scanner;

import com.mongodb.MongoClient;
import org.postgresql.jdbc.PgConnection;

public class Main {
    public static void main(String[] args) throws IOException {

        Scanner sc = new Scanner(System.in);
        FileHandler fileHandler = new FileHandler();
        HashMap<Integer, Product> ProductList = new HashMap<>();
        HashMap<Integer, Purchase> PurchaseList = new HashMap<>();
        File prodFile = new File("Product.txt");
        File purchaseFile = new File("Purchase.txt");
//        ReadProdThread readProdData = new ReadProdThread(fileHandler, prodFile, ProductList);
//        ReadPurchaseThread readPurchaseData = new ReadPurchaseThread(fileHandler, purchaseFile, PurchaseList);
//        ReadThread readPurchaseData = new ReadThread(fileHandler, purchaseFile, ProductList);
//        readProdData.start();
//        readPurchaseData.start();
//        fileHandler.readExistingFileData(prodFile, ProductList, false);
//        fileHandler.readExistingProdFile(purchaseFile, PurchaseList, true);

        AddProduct addProd = new AddProduct();
        ViewProduct viewProd = new ViewProduct();
        GetAllProducts getAllProd = new GetAllProducts();
        UpdateStock updateProdStock = new UpdateStock();
        UpdatePrice updateProdPrice = new UpdatePrice();
        PurchaseProduct purchaseProduct = new PurchaseProduct();
        ShowPurchasedProduct purchasedProduct = new ShowPurchasedProduct();
        SoftDeleteProd deleteProd = new SoftDeleteProd();
        Category categoryHandling = new Category();
        GenerateReport generateReport = new GenerateReport();
//        String sqlQuery = "SELECT * FROM Orders";
//        try (Connection conn = PostgresConnect.getConnection();
//             PreparedStatement stmt = conn.prepareStatement(sqlQuery);
//             ResultSet rs = stmt.executeQuery()) {
//            System.out.println("postgres connected");
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }finally {
//            System.out.println("Terminated");
//        }

        try {
            char val;
            do {
//                WriteProdThread prodWriteThread = new WriteProdThread(fileHandler, prodFile, ProductList);
//                WritePurchaseThread purchaseWriteThread = new WritePurchaseThread(fileHandler, purchaseFile, PurchaseList);
                System.out.println("1.Enter the Product\n2.View the Product\n3.View all products\n4.Update Stock\n5.Update price\n6.Purchase product\n7.Show purchased Product\n8.Delete Product/Revert Delete\n9.Create Category\n10.Exit\n11.Generate Report");
                System.out.print("Enter your choice: ");
                int choice = sc.nextInt();
                switch (choice) {
                    case 1:
                        addProd.getProductDetails(ProductList);
//                        prodWriteThread.start();
                        break;
                    case 2:
                        viewProd.viewProduct(ProductList);
                        break;
                    case 3:
                        getAllProd.getAllProduct(ProductList);
                        break;
                    case 4:
                        updateProdStock.UpdateStockCount(ProductList);
//                        prodWriteThread.start();
//                        fileHandler.existOrCreateFile(prodFile,ProductList,false);
                        break;
                    case 5:
                        updateProdPrice.UpdateProdPrice(ProductList);
//                        prodWriteThread.start();
//                        fileHandler.existOrCreateFile(prodFile,ProductList,false);
                        break;
                    case 6:
                        purchaseProduct.purchaseProduct(ProductList, PurchaseList);
//                        prodWriteThread.start();
//                        purchaseWriteThread.start();
//                        fileHandler.existOrCreateFile(prodFile,ProductList,false);
//                    fileHandler.existOrCreateFile(purchaseFile, PurchaseList, true);
                        break;
                    case 7:
                        purchasedProduct.showAllPurchasedProduct(PurchaseList);
                        break;
                    case 8:
                        deleteProd.DeleteProd(ProductList);
//                        prodWriteThread.start();
//                    fileHandler.existOrCreateFile(prodFile, ProductList, false);
                        break;
                    case 9:
//                        prodWriteThread.start();
//                    fileHandler.existOrCreateFile(prodFile,ProductList,false);
//                        purchaseWriteThread.start();
//                    fileHandler.existOrCreateFile(purchaseFile, PurchaseList, true);
                        categoryHandling.addCategory();
                        break;
                    case 10:
                        System.exit(0);

                    case 11:
                        generateReport.generateReportOfSalesBetweenTwoDates();
                        break;

                    default:
                        System.out.println("Invalid access");
                        break;
                }
                System.out.print("Do you want to continue. If yes Press 'y' otherwise any key: ");
                val = sc.next().charAt(0);
            } while (val == 'y');
        } catch (Exception error) {
            System.err.println("Enter Valid Inputs");
        }
        sc.close();
    }

}
