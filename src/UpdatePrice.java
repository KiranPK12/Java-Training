import java.util.HashMap;
import java.util.Scanner;

public class UpdatePrice {
    public void UpdateProdPrice(HashMap<Integer, Product> ProductList){
        Scanner sc = new Scanner(System.in);
        if (ProductList.isEmpty()){
            System.out.println("No products exists");
        }else{
            System.out.println("Enter the Id to update price: ");
            int targetId=sc.nextInt();
            if (targetId<0||targetId>ProductList.size()||ProductList.get(targetId).getIsFlaged()){
                System.out.println("Invalid Product ID");
            }else{
                System.out.println("Enter the new price: ");
                int newPrice = sc.nextInt();
                if (newPrice<0){
                    System.out.println("Invalid Pricing");
                }else{
                    System.out.println("Do you want to Override or Increment? type 'o' for override , 'i' for increment");
                    char i = sc.next().charAt(0);
                    if (i=='o'){
                        ProductList.put(targetId,ProductList.get(targetId).setPrice(newPrice));
                    }else{
                        ProductList.put(targetId,(ProductList.get(targetId).setPrice(ProductList.get(targetId).getStock()+newPrice)));
                    }                    System.out.println("Price updated in ID "+targetId);
                }
            }
        }
    }
}
