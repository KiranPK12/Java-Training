import java.io.File;
import java.io.IOException;
import java.util.HashMap;

class WriteProdThread extends Thread{
    FileHandler writeFile;
    File file;
    HashMap<Integer, Product> ProductList;

    WriteProdThread(FileHandler wp, File f, HashMap<Integer, Product> pl){
        writeFile=wp;
        file=f;
        ProductList=pl;
    }
    @Override
     public  void  run(){
//        System.out.println("Write thread started");
        writeFile.existOrCreateProdFile(file,ProductList);
//        System.out.println("write thread completed");
    }
}

class  WritePurchaseThread extends  Thread{
    FileHandler writeFile;
    File file;
    HashMap<Integer, Purchase> PurchaseList;

    WritePurchaseThread(FileHandler wp, File f, HashMap<Integer, Purchase> pl){
        writeFile=wp;
        file=f;
        PurchaseList=pl;
    }
    @Override
    public  void  run(){
//        System.out.println("Write thread started");
        writeFile.existOrCreatePurchaseFile(file,PurchaseList);
//        System.out.println("write thread completed");
    }
}
