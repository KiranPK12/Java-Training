import java.util.HashMap;
import java.util.Scanner;

public class SoftDeleteProd {
    public void DeleteProd(HashMap<Integer, Product> ProductList){
        Scanner sc = new Scanner(System.in);
        if (ProductList.isEmpty()){
            System.out.println("No products exists");
        }else{
            System.out.println("Enter the Id to soft delete / revert back: ");
            int targetId=sc.nextInt();
            if (targetId<0||targetId>ProductList.size()){
                System.out.println("Invalid Product ID");
            }else{
               ProductList.get(targetId).setFlag(!ProductList.get(targetId).getIsFlaged());
               System.out.println("Updated");
            }
        }
    }
}
