package dietBuilder.GUI;

import dietBuilder.Model.Category;
import dietBuilder.Model.Meal;
import dietBuilder.Model.MealItem;
import dietBuilder.Model.Product;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Objects;

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
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JButton createMealButton;
    private JButton deleteMealButton;
    private JButton deleteFromMealButton;
    private JButton exportToPDFButton;

    public MainForm(ArrayList<Product> products, ArrayList<Meal> meals) {

        setMealsBox(meals);
        makeTable(products);
        makeMealTable(meals);
        setContentPane(mainPanel);
        setTitle("Diet Builder");
        setSize(1000, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);

        DefaultTableModel tblModel = (DefaultTableModel)table1.getModel();
        DefaultTableModel tblModel2 = (DefaultTableModel)table2.getModel();

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
                        product.setCategory(dietBuilder.Model.Category.valueOf(Category));

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
                meals.removeIf(product -> product.equals(Objects.requireNonNull(CategoryBox.getSelectedItem()).toString()));
                MealsBox.removeItem(MealsBox.getSelectedItem());

            }
        });
        addToMealButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String type = Objects.requireNonNull(CategoryBox.getSelectedItem()).toString();
                Product product = new Product(NameField.getText(),Double.parseDouble(CarbsField.getText()), Double.parseDouble(FatsField.getText()), Double.parseDouble(ProteinsField.getText()), Category.valueOf(type));

                new AddToMeal(meals, type, product, tblModel2);
            }
        });
        MealsBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                makeMealTable(meals);
            }
        });
        deleteFromMealButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(table2.getSelectedRowCount() == 1) {
                    meals.stream()
                                    .filter(Meal -> Meal.getType().equals(Objects.requireNonNull(CategoryBox.getSelectedItem()).toString()))
                                            .findFirst()
                                                    .ifPresent(Meal -> Meal.getItems().remove(table2.getSelectedRow()));    // POSSIBLY WRONG
                    tblModel2.removeRow(table2.getSelectedRow());
                    tblModel2.fireTableDataChanged();
                }   else if (table2.getSelectedRowCount() == 0) {
                    JOptionPane.showMessageDialog(MainForm.this, "No row selected");
                }   else {
                    JOptionPane.showMessageDialog(MainForm.this, "Please select a single row");
                }
                makeMealTable(meals);
            }
        });
    }

    private void makeTable(ArrayList<Product> products)    {

        Object[][] data = products.stream()
                        .map(Product -> new Object[]{Product.getName(), Product.getCarbs(), Product.getFats(), Product.getProteins(), Product.getCategory().toString()})
                                .toArray(Object[][]::new);

        table1.setModel(new DefaultTableModel(
                data,
                new String[]{"Name", "Carbs", "Fats", "Proteins", "Category"}
        ));
    }

    private void makeMealTable(ArrayList<Meal> meal)    {

        ArrayList<MealItem> items = meal.stream()
                .filter(Meal -> Meal.getType().equals(Objects.requireNonNull(MealsBox.getSelectedItem()).toString()))
                .findFirst()
                .map(Meal::getItems)
                .orElse(new ArrayList<>());

        Object[][] data = items.stream()
                .map(MealItem -> new Object[]{MealItem.getProduct().getName(), MealItem.getProduct().getCarbs(), MealItem.getProduct().getFats(), MealItem.getProduct().getProteins(), MealItem.getWeight()})
                .toArray(Object[][]::new);

        table2.setModel(new DefaultTableModel(
                data,
                new String[]{"Name", "Carbs", "Fats", "Proteins", "Weight"}
        ));
    }

    private void setMealsBox(ArrayList<Meal> meals)  {

        meals.forEach(Meal -> MealsBox.addItem(String.valueOf(Meal.getType())));

    }

}
