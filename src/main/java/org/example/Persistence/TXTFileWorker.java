package org.example.Persistence;

import org.example.Model.Category;
import org.example.Model.Meal;
import org.example.Model.MealItem;
import org.example.Model.Product;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class TXTFileWorker {

    private final String productsPath;
    private final String mealsPath;

    public TXTFileWorker(String productsPath, String mealsPath) {
        this.mealsPath = mealsPath;
        this.productsPath = productsPath;
    }

    public ArrayList<Product> readProducts() {

        ArrayList<Product> products = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(productsPath))) {
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

    public ArrayList<Meal> readMeals()   {

        ArrayList<MealItem> items = new ArrayList<>();
        ArrayList<Meal> meals = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(mealsPath))) {
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

    public void writeProducts(ArrayList<Product> products)  {
        try (FileWriter fileWriter = new FileWriter(productsPath, false)) {
            for (Product product : products) {
                String line =
                        product.getName() + ";" +
                        product.getCarbs() + ";" +
                        product.getFats() + ";" +
                        product.getProteins() + ";" +
                        product.getCategory().name().toUpperCase() + ";" + "\n";
                fileWriter.write(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeMeals(ArrayList<Meal> meals)    {
        try (FileWriter fileWriter = new FileWriter(mealsPath, false)) {
            for (Meal meal : meals) {
                StringBuilder lineBuilder = new StringBuilder("%" + meal.getType() + "\n");

                for (MealItem mealItem : meal.getItems()) {
                    lineBuilder.append(mealItem.getProduct().getName()).append(";")
                            .append(mealItem.getProduct().getCarbs()).append(";")
                            .append(mealItem.getProduct().getFats()).append(";")
                            .append(mealItem.getProduct().getProteins()).append(";")
                            .append(mealItem.getProduct().getCategory().name().toUpperCase()).append(";")
                            .append(mealItem.getWeight()).append(";").append("\n");
                }

                String line = lineBuilder.toString();
                fileWriter.write(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
