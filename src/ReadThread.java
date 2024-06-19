import java.io.File;
import java.io.IOException;
import java.util.HashMap;

class ReadThread extends Thread{
    FileHandler readFile;
    File file;
    HashMap<Integer, Product> readList;
    boolean isPurchase;

    ReadThread(FileHandler wp, File f, HashMap<Integer, Product> pl, boolean p){
        readFile=wp;
        file=f;
        readList=pl;
        isPurchase=p;
    }
    ReadThread(FileHandler wp, File f, HashMap<Integer, Product> pl){
        readFile=wp;
        file=f;
        readList=pl;

    }
    @Override
    public  void  run(){
        try {
            readFile.readExistingFileData(file,readList,isPurchase);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
