package org.example.GUI;

import org.example.Model.Meal;
import org.example.Model.MealItem;
import org.example.Model.ShoppingList;
import org.example.Persistence.PDFWorker;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Objects;

public class ExportToPDF extends JFrame {
    private JPanel mainPane;
    private JComboBox mealBox;
    private JButton addToShoppingListButton;
    private JButton exportButton;
    private JTable mealTable;
    ArrayList<Meal> selected = new ArrayList<>();
    ArrayList<MealItem> items = new ArrayList<>();
    public ExportToPDF(ArrayList<Meal> meals)    {

        setContentPane(mainPane);
        setTitle("Create shopping list");
        setSize(300, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
        makeTable();
        setLocationRelativeTo(null);
        meals.forEach(Meal -> mealBox.addItem(Meal.getType()));
        addToShoppingListButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                meals.stream()
                        .filter(meal -> meal.getType().equals(Objects.requireNonNull(mealBox.getSelectedItem()).toString()))
                        .findFirst()
                        .ifPresent(selectedMeal -> {
                            selected.add(selectedMeal);
                            makeTable();
                        });
            }
        });
        exportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                selected.forEach(Meal -> items.addAll(Meal.getItems()));
                ShoppingList list = new ShoppingList(items);
                PDFWorker.exportToPDF(list.getBuy());
                JOptionPane.showMessageDialog(ExportToPDF.this, "Exported successfully");
                dispose();
            }
        });
    }
    private void makeTable()    {

        Object[][] data = selected.stream()
                .map(meal -> new Object[]{meal.getType()})
                .toArray(Object[][]::new);

        DefaultTableModel newModel = new DefaultTableModel(
                data,
                new String[]{"Meal"}
        );

        mealTable.setModel(newModel);
    }
}
