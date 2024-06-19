import java.util.HashMap;
import java.util.Scanner;

public class UpdateStock {
    public void UpdateStockCount(HashMap<Integer, Product> ProductList){
        Scanner sc = new Scanner(System.in);
        if (ProductList.isEmpty()){
            System.out.println("No products exists");
        }else{
            System.out.println("Enter the Id to update stock: ");
            int targetId=sc.nextInt();
            if (targetId<0||targetId>ProductList.size()||ProductList.get(targetId).getIsFlaged()){
                System.out.println("Invalid Product ID");
            }else{
                System.out.println("Enter the stock to be updated: ");
                int stockCount = sc.nextInt();
                if (stockCount<0){
                    System.out.println("Invalid Stock Count");
                }else{
                    System.out.println("Do you want to Override or Increment? type 'o' for override , 'i' for increment");
                    char i = sc.next().charAt(0);
                    if (i=='o'){
                        ProductList.put(targetId,ProductList.get(targetId).setStock(stockCount));
                    }else{
                        ProductList.put(targetId,(ProductList.get(targetId).setStock(ProductList.get(targetId).getStock()+stockCount)));
                    }

                    System.out.println("Stocked updated in ID "+targetId);
                }


            }
        }


    }
}
