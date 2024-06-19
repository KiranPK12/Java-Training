import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;

class AddProduct {
    Scanner sc = new Scanner(System.in);
    public void getProductDetails(HashMap<Integer, Product> ProductList){
        try{
        int id=ProductList.size()+1;
        System.out.println("Enter the details of your product ");
        System.out.print("Enter the Product name:  ");
        String productName = sc.nextLine();
        System.out.print("Enter the Product Stock:  ");
        int stockSize = sc.nextInt();
        System.out.print("Enter the Price:  ");
        int price = sc.nextInt();
        sc.nextLine();
        if (price<1||stockSize<1){
            System.out.println("Invalid Stock or Price count");
        }else {
            ProductList.put(id,new Product(productName,stockSize,price));
            System.out.println("Product Added and ID is "+id+". Continue with other Options");
        }
        }catch (InputMismatchException e){
            System.out.println("Enter a valid Inputs");
        }
    }}

