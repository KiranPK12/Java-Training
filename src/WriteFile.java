import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class WriteFile {
    public void existOrCreateFile(File file, HashMap<Integer, Product> ProductList,boolean purchase) {
        try {
            if (file.createNewFile()) {
                System.out.println("File created: " + file.getName());
            } else {
                PrintWriter writer = new PrintWriter(file
                );
                writer.print("");
                writer.close();
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }finally {
            try {
                FileWriter myWriter = new FileWriter(file.getName());
                if (ProductList.isEmpty()){
                    myWriter.write(" ");
                }else{
                    BufferedWriter buffer = new BufferedWriter(myWriter);
                    if (purchase){
                        for (Map.Entry<Integer, Product> entry : ProductList.entrySet()) {
                            int key = entry.getKey();
                            Product value = entry.getValue();
                            buffer.write(key+","+value.getName()+","+value.getStock()+","+value.getPrice());
                            buffer.newLine();
                        }
                    }else{
                        for (Map.Entry<Integer, Product> entry : ProductList.entrySet()) {
                            int key = entry.getKey();
                            Product value = entry.getValue();
                            buffer.write(key+","+value.getName()+","+value.getStock()+","+value.getPrice()+","+value.getIsFlaged());
                            buffer.newLine();
                        }
                    }
                    buffer.close();
                }
            } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
        }
    }
    public void readExistingFileData(File prodFile,HashMap<Integer, Product> ProductList,boolean purchase) throws IOException {
        if (prodFile.exists()) {
            BufferedReader bf = new BufferedReader(
                    new FileReader(prodFile));
            String line = bf.readLine();
            while (line != null) {
                String[] prodValues = line.split(",");
                if (purchase){
                    ProductList.put(Integer.parseInt(prodValues[0]), new Product(prodValues[1], Integer.parseInt(prodValues[2]), Integer.parseInt(prodValues[3])));
                }else {
                    ProductList.put(Integer.parseInt(prodValues[0]), new Product(prodValues[1], Integer.parseInt(prodValues[2]), Integer.parseInt(prodValues[3]),Boolean.parseBoolean(prodValues[4])));
                }
                line = bf.readLine();
            }
            bf.close();
        }
    }
}
