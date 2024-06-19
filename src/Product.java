public class Product {
    private String name ;
    private int stock;
    private int price;
    private boolean isFlag;

    public Product(String n , int s , int p,boolean flag){
        name=n;stock=s;price=p;isFlag=flag;
    }
    public Product(String n , int s , int p){
        name=n;stock=s;price=p;
    }

    public int getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public String getName() {
        return name;
    }

    public boolean getIsFlaged(){return isFlag;}

    public void setName(String name) {
        this.name = name;
    }


    public Product setFlag(boolean flag){
        this.isFlag=flag;
        return this;
    }

    public Product setPrice(int price) {
        this.price = price;
        return this;
    }

    public Product setStock(int stock) {
        this.stock = stock;
        return this;
    }

}
