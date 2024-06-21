import java.io.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        AddProduct addProd = new AddProduct();
        ViewProduct viewProd = new ViewProduct();
        GetAllProducts getAllProd = new GetAllProducts();
        UpdateStock updateProdStock = new UpdateStock();
        UpdatePrice updateProdPrice = new UpdatePrice();
        PurchaseProduct purchaseProduct = new PurchaseProduct();
        ShowPurchasedProduct purchasedProduct = new ShowPurchasedProduct();
        SoftDeleteProd deleteProd = new SoftDeleteProd();
        Category categoryHandling = new Category();
        try {
            char val;
            do {
                System.out.println("1.Enter the Product\n2.View the Product\n3.View all products\n4.Update Stock\n5.Update price\n6.Purchase product\n7.Show purchased Product\n8.Delete Product/Revert Delete\n9.Create Category\n10.Exit");
                System.out.print("Enter your choice: ");
                int choice = sc.nextInt();
                switch (choice) {
                    case 1:
                        addProd.getProductDetails();
                        break;
                    case 2:
                        viewProd.viewProduct();
                        break;
                    case 3:
                        getAllProd.getAllProduct();
                        break;
                    case 4:
                        updateProdStock.UpdateStockCount();
                        break;
                    case 5:
                        updateProdPrice.UpdateProdPrice();
                        break;
                    case 6:
                        purchaseProduct.purchaseProduct();
                        break;
                    case 7:
                        purchasedProduct.showAllPurchasedProduct();
                        break;
                    case 8:
                        deleteProd.DeleteProd();
                        break;
                    case 9:
                        categoryHandling.addCategory();
                        break;
                    case 10:
                        System.exit(0);

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
