package dietBuilder.Persistence;

import dietBuilder.Model.Category;
import dietBuilder.Model.Product;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class TXTFileWorker {

    public static ArrayList<Product> readFromFile(String filePath) {
        ArrayList<Product> productList = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";");
                    String name = parts[0];
                    double carbs = Double.parseDouble(parts[1]);
                    double fats = Double.parseDouble(parts[2]);
                    double proteins = Double.parseDouble(parts[3]);
                    Category category = Category.valueOf(parts[4].toUpperCase());
                    productList.add(new Product(name, carbs, fats, proteins, category));
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }

        return productList;
    }

}
