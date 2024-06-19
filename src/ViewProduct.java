import java.util.HashMap;
import java.util.Scanner;

public class ViewProduct {
    public void viewProduct(HashMap<Integer, Product> ProductList){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the Id to view product: ");
        int targetId = sc.nextInt();
        if (ProductList.isEmpty()||targetId>ProductList.size()||ProductList.get(targetId).getIsFlaged()){
            System.out.println("No products exists");
        }else{
            if (targetId<0){
                System.out.println("Invalid Product ID");
            }else{
                Product pr = ProductList.get(targetId);
                System.out.print("Product: "+pr.getName()+"\nProduct Price: "+ pr.getPrice()+"\nProduct Stock: "+pr.getStock()+"\n");
            }
        }
    }
}
