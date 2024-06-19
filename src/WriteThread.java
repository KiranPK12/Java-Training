import java.io.File;
import java.util.HashMap;

class WriteThread extends Thread{
    WriteFile writeFile;
    File file;
    HashMap<Integer, Product> ProductList;
    boolean purchase;

    WriteThread(WriteFile wp,File f, HashMap<Integer, Product> pl,boolean p){
        writeFile=wp;
        file=f;
        ProductList=pl;
        purchase=p;
    }
    WriteThread(WriteFile wp,File f, HashMap<Integer, Product> pl){
        writeFile=wp;
        file=f;
        ProductList=pl;

    }
    @Override
     public  void  run(){
        writeFile.existOrCreateFile(file,ProductList,purchase);
    }
}
