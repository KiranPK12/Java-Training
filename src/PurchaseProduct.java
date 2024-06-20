import java.util.HashMap;
import java.util.Scanner;

public class PurchaseProduct {
    public void purchaseProduct(HashMap<Integer, Product> ProductList, HashMap<Integer, Purchase> PurchaseList) {
        int purchaseId = PurchaseList.size() + 1;
        if (ProductList.isEmpty()) {
            System.out.println("No products exists");
        } else {
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter the Product Id to purchase: ");
            int targetId = sc.nextInt();
            if (targetId < 0 || targetId > ProductList.size() || ProductList.get(targetId).getIsFlaged()) {
                System.out.println("Invalid Product ID");
            } else {
                System.out.println("Enter the amount of items to be purchased: ");
                int buyCount = sc.nextInt();
                Product prod = ProductList.get(targetId);
                if (prod.getStock() == 0) {
                    System.out.println("Out of stocks");
                } else if (buyCount < 0) {
                    System.out.println("Cannot buy Negative quantity");
                } else if (prod.getStock() < buyCount) {
                    System.out.println("More offer than availability");
                } else {
                    prod.setStock(prod.getStock() - buyCount);
                    PurchaseList.put(purchaseId, new Purchase(prod.getName(), buyCount, prod.getPrice(),targetId));
                    System.out.println("Purchase Completed");
                }
            }
        }
    }

}
