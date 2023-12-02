package dietBuilder.Persistence;

import dietBuilder.Model.Category;
import dietBuilder.Model.Meal;
import dietBuilder.Model.MealItem;
import dietBuilder.Model.Product;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Objects;

public class TXTFileWorker {

    public static String getURL(String filename)   {

        ClassLoader classLoader = TXTFileWorker.class.getClassLoader();
        URL data = classLoader.getResource(filename);

        if(data != null) {
            try {
                URI uri = data.toURI();
                Path path = Paths.get(uri);
                return path.toString();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static ArrayList<Product> readProducts() {

        ArrayList<Product> products = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(getURL("Products.txt")))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";");
                    String name = parts[0];
                    double carbs = Double.parseDouble(parts[1]);
                    double fats = Double.parseDouble(parts[2]);
                    double proteins = Double.parseDouble(parts[3]);
                    Category category = Category.valueOf(parts[4].toUpperCase());
                    products.add(new Product(name, carbs, fats, proteins, category));
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }

        return products;
    }

    public static ArrayList<Meal> readMeals()   {

        ArrayList<MealItem> items = new ArrayList<>();
        ArrayList<Meal> meals = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(getURL("Meals.txt")))) {
            String line;
            while((line = br.readLine()) != null)    {

                if(line.startsWith("%"))    {
                    items = new ArrayList<>();
                    line = line.substring(1).trim();
                    meals.add(new Meal(line, items));
                }   else {
                    String[] parts = line.split(";");
                    String name = parts[0];
                    double carbs = Double.parseDouble(parts[1]);
                    double fats = Double.parseDouble(parts[2]);
                    double proteins = Double.parseDouble(parts[3]);
                    Category category = Category.valueOf(parts[4].toUpperCase());
                    double weight = Double.parseDouble(parts[5]);
                    items.add(new MealItem(new Product(name, carbs, fats, proteins, category), weight));
                }
            }

        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
        return meals;
    }

    public static void writeProducts(ArrayList<Product> products)  {
        try (FileWriter fileWriter = new FileWriter(Objects.requireNonNull(getURL("Products.txt")))) {
            for (Product product : products) {
                String line = String.format("%s;%.1f;%.1f;%.1f;%s;%n",
                        product.getName(),
                        product.getCarbs(),
                        product.getFats(),
                        product.getProteins(),
                        product.getCategory().name());
                fileWriter.write(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeMeals(ArrayList<Meal> meals)    {
        try (FileWriter fileWriter = new FileWriter(Objects.requireNonNull(getURL("Meals.txt")))) {
            for (Meal meal : meals) {
                String line = String.format("%%%s%n", meal.getType());

                for (MealItem mealItem : meal.getItems()) {
                    line += String.format("%s;%.1f;%.1f;%.1f;%s;%n;%.1f;%n",
                            mealItem.getProduct().getName(),
                            mealItem.getProduct().getCarbs(),
                            mealItem.getProduct().getFats(),
                            mealItem.getProduct().getProteins(),
                            mealItem.getProduct().getCategory().name(),
                            mealItem.getWeight());
                }

                fileWriter.write(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
