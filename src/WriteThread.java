import java.io.File;
import java.util.HashMap;

class WriteThread extends Thread{
    FileHandler writeFile;
    File file;
    HashMap<Integer, Product> ProductList;
    boolean purchase;

    WriteThread(FileHandler wp, File f, HashMap<Integer, Product> pl, boolean p){
        writeFile=wp;
        file=f;
        ProductList=pl;
        purchase=p;
    }
    WriteThread(FileHandler wp, File f, HashMap<Integer, Product> pl){
        writeFile=wp;
        file=f;
        ProductList=pl;

    }
    @Override
     public  void  run(){
        writeFile.existOrCreateFileAndWrite(file,ProductList,purchase);
    }
}
