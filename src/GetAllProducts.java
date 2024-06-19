import java.util.HashMap;

public class GetAllProducts {
    public void getAllProduct(HashMap<Integer, Product> ProductList){
        if (ProductList.isEmpty()){
            System.out.println("No products exists");
        }else{
            ProductList.forEach((k,v)->Display(v));
        }
}
    public  void Display(Product p){
        if (!p.getIsFlaged()){
            System.out.printf("Product Details:%nName: %s%nPrice: %d%nStock: %d%n",p.getName(),p.getPrice(),p.getStock());
        }
    }
}
