import java.io.File;
import java.io.IOException;
import java.util.HashMap;

class ReadProdThread extends Thread{
    FileHandler readFile;
    File file;
    HashMap<Integer, Product> readList;

    ReadProdThread(FileHandler wp, File f, HashMap<Integer, Product> pl){
        readFile=wp;
        file=f;
        readList=pl;
    }
    @Override
    public  void  run(){
//        System.out.println("Read thread started");
        try {
            readFile.readExistingProdFile(file,readList);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
//        System.out.println("Read thread completed");
    }
}

class ReadPurchaseThread extends  Thread{
    FileHandler readFile;
    File file;
    HashMap<Integer, Purchase> readList;

    ReadPurchaseThread(FileHandler wp, File f, HashMap<Integer, Purchase> pl){
        readFile=wp;
        file=f;
        readList=pl;
    }
    @Override
    public  void  run(){
//        System.out.println("Read thread started");
        try {
            readFile.readExistingPurchaseFile(file,readList);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
//        System.out.println("Read thread completed");
    }
}