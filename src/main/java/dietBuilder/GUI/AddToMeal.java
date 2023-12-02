package dietBuilder.GUI;

import dietBuilder.Model.Meal;
import dietBuilder.Model.MealItem;
import dietBuilder.Model.Product;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class AddToMeal extends JFrame {
    private JPanel grandPanel;
    private JTextField quantity;
    private JButton addButton;

    public AddToMeal(ArrayList<Meal> meals, String meal, Product product, DefaultTableModel tblModel2) {

        setContentPane(grandPanel);
        setTitle("Add to Meal");
        setSize(300, 200);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);

        addButton.addActionListener(e -> {

            try {
                double weight = Double.parseDouble(quantity.getText());
                MealItem item = new MealItem(product, weight);
                meals.stream()
                        .filter(Meal -> Meal.getType().equals(meal))
                        .findFirst()
                        .ifPresent(Meal -> Meal.getItems().add(item));

                String[] data = new String[]{product.getName(), String.valueOf(product.getCarbs()), String.valueOf(product.getFats()), String.valueOf(product.getProteins()), String.valueOf(weight)};
                tblModel2.addRow(data);
                tblModel2.fireTableDataChanged();

                JOptionPane.showMessageDialog(AddToMeal.this, "Added successfully");
            } catch (NumberFormatException exception)   {
                JOptionPane.showMessageDialog(AddToMeal.this, "Invalid data");
            }
        });
    }
}
