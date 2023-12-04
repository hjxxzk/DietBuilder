package org.example.GUI;

import org.example.Model.Category;
import org.example.Model.Meal;
import org.example.Model.MealItem;
import org.example.Model.Product;
import org.example.Persistence.TXTFileWorker;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Objects;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class MainForm extends JFrame {
    private JPanel mainPanel;
    private JTable table1;
    private JButton AddProductButton;
    private JTable table2;
    private JTextField ProteinsField;
    private JTextField FatsField;
    private JTextField CarbsField;
    private JTextField NameField;
    private JComboBox MealsBox;
    private JButton deleteButton;
    private JButton editButton;
    private JComboBox CategoryBox;
    private JPanel ImagePanel;
    private JButton addToMealButton;
    private JTextField Kcal;
    private JTextField Carbs;
    private JTextField Fats;
    private JTextField Proteins;
    private JButton createMealButton;
    private JButton deleteMealButton;
    private JButton deleteFromMealButton;
    private JButton exportToPDFButton;
    private JLabel NameLabel;
    private JLabel CarbsLabel;
    private JLabel FatsLabel;
    private JLabel ProteinsLabel;
    private JLabel CategoryLabel;
    private JLabel appname;
    ArrayList<Product> products;
    ArrayList<Meal> meals;
    TXTFileWorker worker;
    public MainForm(ArrayList<Product> products, ArrayList<Meal> meals, TXTFileWorker worker) {

        this.products = products;
        this.meals = meals;
        this.worker = worker;
        makeTable();
        setMealsBox();
        makeMealTable();
        setContentPane(mainPanel);
        Kcal.setEditable(false);
        Carbs.setEditable(false);
        Proteins.setEditable(false);
        Fats.setEditable(false);
        setTitle("Diet Builder");
        setSize(1000, 800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        setLocationRelativeTo(null);
        DefaultTableModel tblModel = (DefaultTableModel)table1.getModel();

        table1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                String Name = tblModel.getValueAt(table1.getSelectedRow(), 0).toString();
                String Carbs = tblModel.getValueAt(table1.getSelectedRow(), 1).toString();
                String Fats = tblModel.getValueAt(table1.getSelectedRow(), 2).toString();
                String Proteins = tblModel.getValueAt(table1.getSelectedRow(), 3).toString();
                String Category = tblModel.getValueAt(table1.getSelectedRow(), 4).toString();

                NameField.setText(Name);
                CarbsField.setText(Carbs);
                FatsField.setText(Fats);
                ProteinsField.setText(Proteins);
                CategoryBox.setSelectedItem(Category);

            }
        });
        AddProductButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(NameField.getText().isEmpty() || NameField.getText().isEmpty() || CarbsField.getText().isEmpty() || FatsField.getText().isEmpty() || ProteinsField.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(MainForm.this, "Missing data");
                } else {
                    try {
                        Product product = new Product(NameField.getText(), Double.parseDouble(CarbsField.getText()), Double.parseDouble(FatsField.getText()), Double.parseDouble(ProteinsField.getText()), Category.valueOf(Objects.requireNonNull(CategoryBox.getSelectedItem()).toString()));
                        products.add(product);

                        String[] data = new String[]{NameField.getText(), CarbsField.getText(), FatsField.getText(), ProteinsField.getText(), CategoryBox.getSelectedItem().toString()};

                        tblModel.addRow(data);
                        tblModel.fireTableDataChanged();

                        JOptionPane.showMessageDialog(MainForm.this, "Product added");
                        NameField.setText("");
                        CarbsField.setText("");
                        FatsField.setText("");
                        ProteinsField.setText("");
                    } catch (NumberFormatException exception)   {
                        JOptionPane.showMessageDialog(MainForm.this, "Invalid data");
                    }
                }
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(table1.getSelectedRowCount() == 1) {
                    products.remove(table1.getSelectedRow());
                    tblModel.removeRow(table1.getSelectedRow());
                    tblModel.fireTableDataChanged();

                }   else if (table1.getSelectedRowCount() == 0) {
                    JOptionPane.showMessageDialog(MainForm.this, "No row selected");
                }   else {
                    JOptionPane.showMessageDialog(MainForm.this, "Please select a single row");
                }
            }
        });
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(table1.getSelectedRowCount() == 1) {

                    String Name = NameField.getText();
                    String Carbs = CarbsField.getText();
                    String Fats = FatsField.getText();
                    String Proteins = ProteinsField.getText();
                    String Category = Objects.requireNonNull(CategoryBox.getSelectedItem()).toString();

                    try {
                        Product product = (Product) products.get(table1.getSelectedRow());
                        product.setName(Name);
                        product.setCarbs(Double.parseDouble(Carbs));
                        product.setFats(Double.parseDouble(Fats));
                        product.setProteins(Double.parseDouble(Proteins));
                        product.setCategory(org.example.Model.Category.valueOf(Category));

                        tblModel.setValueAt(Name, table1.getSelectedRow(), 0);
                        tblModel.setValueAt(Carbs, table1.getSelectedRow(), 1);
                        tblModel.setValueAt(Fats, table1.getSelectedRow(), 2);
                        tblModel.setValueAt(Proteins, table1.getSelectedRow(), 3);
                        tblModel.setValueAt(Category, table1.getSelectedRow(), 4);

                        JOptionPane.showMessageDialog(MainForm.this, "Update successful");

                    } catch (NumberFormatException exception)   {
                        JOptionPane.showMessageDialog(MainForm.this, "Invalid data");
                    }

                } else if (table1.getRowCount() == 0) {
                    JOptionPane.showMessageDialog(MainForm.this, "No data to edit");
                } else {
                    JOptionPane.showMessageDialog(MainForm.this, "Please select a single row");
                }
            }
        });
        createMealButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CreateMeal(meals, MealsBox);
            }
        });
        deleteMealButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                meals.remove(MealsBox.getSelectedIndex());
                MealsBox.removeItem(MealsBox.getSelectedItem());

            }
        });
        addToMealButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(table1.getSelectedRowCount() == 1) {
                    String type = Objects.requireNonNull(CategoryBox.getSelectedItem()).toString();
                    String meal = Objects.requireNonNull(MealsBox.getSelectedItem()).toString();
                    Product product = new Product(NameField.getText(), Double.parseDouble(CarbsField.getText()), Double.parseDouble(FatsField.getText()), Double.parseDouble(ProteinsField.getText()), Category.valueOf(type));

                    SwingUtilities.invokeLater(() -> {
                        AddToMeal addToMeal = new AddToMeal(meals, meal, product);
                        addToMeal.addWindowListener(new WindowAdapter() {
                            @Override
                            public void windowClosed(WindowEvent e) {
                                makeMealTable();
                            }
                        });
                    });
                } else if (table1.getSelectedRowCount() == 0) {
                    JOptionPane.showMessageDialog(MainForm.this, "No row selected");
                }   else {
                    JOptionPane.showMessageDialog(MainForm.this, "Please select a single row");
                }
            }
        });
        MealsBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                makeMealTable();
            }
        });
        deleteFromMealButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(table2.getSelectedRowCount() == 1)   {
                int selectedRow = table2.getSelectedRow();

                if (selectedRow != -1) {
                    String selectedMealType = Objects.requireNonNull(MealsBox.getSelectedItem()).toString();

                    meals.stream()
                            .filter(meal -> meal.getType().equals(selectedMealType))
                            .findFirst()
                            .ifPresent(meal -> {
                                    meal.getItems().remove(selectedRow);
                            });

                    makeMealTable();
                }
                } else {
                    JOptionPane.showMessageDialog(MainForm.this, "No row selected");
                }
            }
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                worker.writeProducts(products);
                worker.writeMeals(meals);
                System.exit(0);
            }
        });
        exportToPDFButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                new ExportToPDF(meals);
            }
        });
    }

    private void makeTable()    {

        Object[][] data = products.stream()
                        .map(Product -> new Object[]{Product.getName(), round(Product.getCarbs()), round(Product.getFats()), round(Product.getProteins()), Product.getCategory().toString()})
                                .toArray(Object[][]::new);

        table1.setModel(new DefaultTableModel(
                data,
                new String[]{"Name", "Carbs", "Fats", "Proteins", "Category"}
        ));
    }

    public void makeMealTable()    {

        ArrayList<MealItem> items = meals.stream()
                .filter(Meal -> Meal.getType().equals(Objects.requireNonNull(MealsBox.getSelectedItem()).toString()))
                .findFirst()
                .map(Meal::getItems)
                .orElse(new ArrayList<>());

        Object[][] data = items.stream()
                .map(MealItem -> new Object[]{MealItem.getProduct().getName(), round(MealItem.getProduct().getCarbs()), round(MealItem.getProduct().getFats()), round(MealItem.getProduct().getProteins()), round(MealItem.getWeight())})
                .toArray(Object[][]::new);

        table2.setModel(new DefaultTableModel(
                data,
                new String[]{"Name", "Carbs", "Fats", "Proteins", "Weight [g]"}
        ));
        calculate();
    }

    private void setMealsBox()  {
        meals.forEach(Meal -> MealsBox.addItem(String.valueOf(Meal.getType())));
    }

    private void calculate()  {

        double car, pro, fat;
        double kcal = 0.0;
        car = meals.stream()
                .filter(meal -> meal.getType().equals(Objects.requireNonNull(MealsBox.getSelectedItem()).toString()))
                .findFirst()
                .map(meal -> meal.getItems().stream()
                        .mapToDouble(mealItem -> mealItem.getProduct().getCarbs())
                        .sum())
                .orElse(0.0);
        Carbs.setText(String.valueOf(round(car)));

        pro = meals.stream()
                .filter(meal -> meal.getType().equals(Objects.requireNonNull(MealsBox.getSelectedItem()).toString()))
                .findFirst()
                .map(meal -> meal.getItems().stream()
                        .mapToDouble(mealItem -> mealItem.getProduct().getProteins())
                        .sum())
                .orElse(0.0);
        Proteins.setText(String.valueOf(round(pro)));

        fat = meals.stream()
                .filter(meal -> meal.getType().equals(Objects.requireNonNull(MealsBox.getSelectedItem()).toString()))
                .findFirst()
                .map(meal -> meal.getItems().stream()
                        .mapToDouble(mealItem -> mealItem.getProduct().getFats())
                        .sum())
                .orElse(0.0);
        Fats.setText(String.valueOf(round(fat)));
        kcal = ( car + pro ) * 4 + fat * 9;
        Kcal.setText(String.valueOf(round(kcal)));

    }

    private static double round(double value) {
        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }


}
