import java.util.HashMap;
import java.util.Scanner;

public class PurchaseProduct {
    public void purchaseProduct(HashMap<Integer, Product> ProductList, HashMap<Integer, Product> PurchaseList){
        int id=PurchaseList.size()+1;

        if (ProductList.isEmpty()){
            System.out.println("No products exists");
        }else{
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter the Id to purchase: ");
            int targetId=sc.nextInt();
            if (targetId<0||targetId>ProductList.size()||ProductList.get(targetId).getIsFlaged()){
                System.out.println("Invalid Product ID");
            }else{
                System.out.println("Enter the amount of items to be purchased: ");
                int buyCount=sc.nextInt();
                Product prod = ProductList.get(targetId);
                if (prod.getStock()<buyCount){
                    System.out.println("More offer than availability");
                } else if (prod.getStock()==0) {
                    System.out.println("Out of stocks");
                } else if (buyCount<=0) {
                    System.out.println("Cannot buy");
                } else{
                    prod.setStock(prod.getStock()-buyCount);
//                    if (PurchaseList.containsKey(targetId)){
//                        PurchaseList.computeIfPresent(targetId, (k, existingProd) -> new Product(existingProd.getName(), existingProd.getStock() + buyCount, (int) existingProd.getPrice()));
//                    }else{
                    PurchaseList.put(id,new Product(prod.getName(),buyCount, (int) prod.getPrice()));
                    }
                    System.out.println("Purchase Completed");
                }
            }
        }

}
