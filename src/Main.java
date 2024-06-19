import java.io.*;
import java.util.HashMap;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {

            Scanner sc = new Scanner(System.in);
            WriteFile writeFiles = new WriteFile();
            HashMap<Integer, Product> ProductList = new HashMap<>();
            HashMap<Integer, Product> PurchaseList = new HashMap<>();
            File prodFile = new File("Product.txt");
            File purchaseFile = new File("Purchase.txt");
            writeFiles.readExistingFileData(prodFile,ProductList,false);
            writeFiles.readExistingFileData(purchaseFile,PurchaseList,true);
            System.out.println("File Read");

            AddProduct addProd= new AddProduct();
            ViewProduct viewProd = new ViewProduct();
            GetAllProducts getAllProd = new GetAllProducts();
            UpdateStock updateProdStock = new UpdateStock();
            UpdatePrice updateProdPrice= new UpdatePrice();
            PurchaseProduct purchaseProduct = new PurchaseProduct();
            ShowPurchasedProduct purchasedProduct = new ShowPurchasedProduct();
            SoftDeleteProd deleteProd = new SoftDeleteProd();

            char val;
            do {
                System.out.println("1.Enter the Product\n2.View the Product\n3.View all products\n4.Update Stock\n5.Update price\n6.Purchase product\n7.Show purchased Product\n8.Delete Product/Revert Delete\n9.Exit");
                System.out.print("Enter your choice: ");
                int choice = sc.nextInt();
                switch (choice){
                    case 1:
                        addProd.getProductDetails(ProductList);
                        writeFiles.existOrCreateFile(prodFile,ProductList,false);
                        break;
                    case 2:
                        viewProd.viewProduct(ProductList);
                        break;
                    case 3:
                        getAllProd.getAllProduct(ProductList);
                        break;
                    case 4:
                        updateProdStock.UpdateStockCount(ProductList);
                        writeFiles.existOrCreateFile(prodFile,ProductList,false);

                        break;
                    case 5:
                        updateProdPrice.UpdateProdPrice(ProductList);
                        writeFiles.existOrCreateFile(prodFile,ProductList,false);

                        break;
                    case 6:
                        purchaseProduct.purchaseProduct(ProductList,PurchaseList);
                        writeFiles.existOrCreateFile(prodFile,ProductList,false);
                        writeFiles.existOrCreateFile(purchaseFile,PurchaseList,true);
                        break;
                    case 7:
                        purchasedProduct.showAllPurchasedProduct(PurchaseList);
                        break;
                case 9:
                    writeFiles.existOrCreateFile(prodFile,ProductList,false);
                    writeFiles.existOrCreateFile(purchaseFile,PurchaseList,true);
                    System. exit(0);
                case 8 :
                    deleteProd.DeleteProd(ProductList);
                    writeFiles.existOrCreateFile(prodFile,ProductList,false);

                    break;
                    default:
                        System.out.println("Invalid access");
                        break;
                }

                System.out.print("Do you want to continue. If yes Press 'y' otherwise any key: ");
                val=sc.next().charAt(0);
            }while (val=='y');
            sc.close();
        }

    }
