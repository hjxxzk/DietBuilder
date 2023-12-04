package org.example.GUI;

import org.example.Model.Meal;
import org.example.Model.MealItem;
import org.example.Model.Product;

import javax.swing.*;
import java.util.ArrayList;


public class AddToMeal extends JFrame {
    private JPanel grandPanel;
    private JTextField quantity;
    private JButton addButton;

    public AddToMeal(ArrayList<Meal> meals, String meal, Product product) {

        setContentPane(grandPanel);
        setTitle("Add to Meal");
        setSize(300, 200);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
        setLocationRelativeTo(null);

        addButton.addActionListener(e -> {

            try {
                double weight = Double.parseDouble(quantity.getText());
                product.setCarbs(product.getCarbs() * weight / 100);
                product.setProteins(product.getProteins() * weight / 100);
                product.setFats(product.getFats() * weight / 100);
                MealItem item = new MealItem(product, weight);
                meals.stream()
                        .filter(Meal -> Meal.getType().equals(meal))
                        .findFirst()
                        .ifPresent(Meal -> Meal.getItems().add(item));
                dispose();
                JOptionPane.showMessageDialog(AddToMeal.this, "Added successfully");
            } catch (NumberFormatException exception)   {
                JOptionPane.showMessageDialog(AddToMeal.this, "Invalid data");
            }
        });
    }
}
