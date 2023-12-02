package dietBuilder;

import dietBuilder.GUI.MainForm;
import dietBuilder.Model.Meal;
import dietBuilder.Model.Product;

import javax.swing.*;
import java.util.ArrayList;

import static dietBuilder.Persistence.TXTFileWorker.*;

public class Main {
    public static void main(String[] args) {
        ArrayList<Product> products = readProducts();
        ArrayList<Meal> meals = readMeals();

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
              new MainForm(products, meals);
            }
        });

//        writeProducts(products);
//        writeMeals(meals);

        products.stream()
                .forEach(product -> System.out.println(product.getName() + ": "
                        + "Carbs: " + product.getCarbs()
                        + ", Fats: " + product.getFats()
                        + ", Proteins: " + product.getProteins()
                        + ", Category: " + product.getCategory()));

        meals.stream()
                .forEach(meal -> {
                    System.out.println("Meal Type: " + meal.getType());
                    System.out.println("Meal Items:");
                    meal.getItems().forEach(mealItem -> {
                        System.out.println("  Product: " + mealItem.getProduct().getName()
                                + ", Weight: " + mealItem.getWeight());
                    });
                    System.out.println("------------------------");
                });

    }
}