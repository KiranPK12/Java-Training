import java.util.HashMap;

public class ShowPurchasedProduct {
    public void showAllPurchasedProduct(HashMap<Integer, Purchase> PurchasedProduct){
        if (PurchasedProduct.isEmpty()){
            System.out.println("No products Purchased");
        }else{
            PurchasedProduct.forEach((k,v)->Display(v));
        }
    }
    public  void Display(Purchase p){
        System.out.println("=========================");
        System.out.printf("Purchase Details:%nName: %s%nPrice: %d%nStock: %d%nTotal Spend: %d%n",p.getName(),p.getPrice(),p.getStock(),p.getCartValue());
        System.out.println("=========================");

    }}
