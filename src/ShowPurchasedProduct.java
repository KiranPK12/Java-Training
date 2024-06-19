import java.util.HashMap;

public class ShowPurchasedProduct {
    public void showAllPurchasedProduct(HashMap<Integer, Product> PurchasedProduct){
        if (PurchasedProduct.isEmpty()){
            System.out.println("No products exists");
        }else{
            PurchasedProduct.forEach((k,v)->Display(v));
        }
    }
    public  void Display(Product p){
        System.out.println("=========================");
        System.out.printf("Product Details:%nName: %s%nPrice: %d%nStock: %d%n",p.getName(),p.getPrice(),p.getStock());
        System.out.println("=========================");

    }}
