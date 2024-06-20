import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class FileHandler {
    public void existOrCreateProdFile(File file, HashMap<Integer, Product> ProductList) {
        try {
            if (file.createNewFile()) {
                System.out.println("File created: " + file.getName());
            } else {
                PrintWriter writer = new PrintWriter(file);
                writer.print("");
                writer.close();
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
        } finally {
            try {
                FileWriter myWriter = new FileWriter(file.getName());
                if (ProductList.isEmpty()) {
                    myWriter.write(" ");
                } else {
                    BufferedWriter buffer = new BufferedWriter(myWriter);
                    for (Map.Entry<Integer, Product> entry : ProductList.entrySet()) {
                        int key = entry.getKey();
                        Product value = entry.getValue();
                        buffer.write(key + "," + value.getName() + "," + value.getStock() + "," + value.getPrice() + "," + value.getIsFlaged());
                        buffer.newLine();
                    }
                    buffer.close();
                }
            } catch (IOException e) {
                System.out.println("An error occurred.");
            }
        }
    }

    public void readExistingProdFile(File prodFile, HashMap<Integer, Product> ProductList) throws IOException {
        if (prodFile.exists()) {
            BufferedReader bf = new BufferedReader(
                    new FileReader(prodFile));
            String line = bf.readLine();
            while (line != null) {
                String[] prodValues = line.split(",");
                ProductList.put(Integer.parseInt(prodValues[0]), new Product(prodValues[1], Integer.parseInt(prodValues[2]), Integer.parseInt(prodValues[3]), Boolean.parseBoolean(prodValues[4])));
                line = bf.readLine();
            }
            bf.close();
        }
    }

    public void existOrCreatePurchaseFile(File file, HashMap<Integer, Purchase> PurchaseList) {
        try {
            if (file.createNewFile()) {
                System.out.println("File created: " + file.getName());
            } else {
                PrintWriter writer = new PrintWriter(file);
                writer.print("");
                writer.close();
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
        } finally {
            try {
                FileWriter myWriter = new FileWriter(file.getName());
                if (PurchaseList.isEmpty()) {
                    myWriter.write(" ");
                } else {
                    BufferedWriter buffer = new BufferedWriter(myWriter);
                    for (Map.Entry<Integer, Purchase> entry : PurchaseList.entrySet()) {
                        int key = entry.getKey();
                        Purchase value = entry.getValue();
                        buffer.write(key + "," + value.getName() + "," + value.getStock() + "," + value.getPrice()+","+value.getProductId());
                        buffer.newLine();
                    }
                    buffer.close();
                }
            } catch (IOException e) {
                System.out.println("An error occurred.");
            }
        }
    }

    public void readExistingPurchaseFile(File prodFile, HashMap<Integer, Purchase> PurchaseList) throws IOException {
        if (prodFile.exists()) {
            BufferedReader bf = new BufferedReader(
                    new FileReader(prodFile));
            String line = bf.readLine();
            while (line != null) {
                String[] prodValues = line.split(",");
                PurchaseList.put(Integer.parseInt(prodValues[0]), new Purchase(prodValues[1], Integer.parseInt(prodValues[2]), Integer.parseInt(prodValues[3]), Integer.parseInt(prodValues[4])));
                line = bf.readLine();
            }
            bf.close();
        }
    }

}
