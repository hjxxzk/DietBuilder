package dietBuilder.GUI;

import dietBuilder.Model.Category;
import dietBuilder.Model.Product;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
    private JComboBox comboBox1;
    private JButton deleteButton;
    private JButton editButton;
    private JButton createMealButton;
    private JButton exportToPDFButton;
    private JButton deleteProductButton;
    private JComboBox CategoryBox;
    private JPanel ImagePanel;
    private JButton addToMealButton;

    public MainForm(ArrayList demo) {

        makeTable(demo);
        setContentPane(mainPanel);
        setTitle("Diet Builder");
        setSize(1000, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);

        table1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                DefaultTableModel tblModel = (DefaultTableModel)table1.getModel();


            }
        });
        AddProductButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(NameField.getText().isEmpty() || NameField.getText().isEmpty() || CarbsField.getText().isEmpty() || FatsField.getText().isEmpty() || ProteinsField.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(MainForm.this, "Missing data");
                } else {
                        Product product = new Product(NameField.getText(), Double.parseDouble(CarbsField.getText()), Double.parseDouble(FatsField.getText()), Double.parseDouble(ProteinsField.getText()), Category.valueOf(Objects.requireNonNull(CategoryBox.getSelectedItem()).toString()));
                        demo.add(product);

                        String[] data = new String[]{NameField.getText(), CarbsField.getText(), FatsField.getText(), ProteinsField.getText(), CategoryBox.getSelectedItem().toString()};
                        DefaultTableModel tblModel = (DefaultTableModel)table1.getModel();

                        tblModel.addRow(data);
                        JOptionPane.showMessageDialog(MainForm.this, "Product added");
                        NameField.setText("");
                        CarbsField.setText("");
                        FatsField.setText("");
                        ProteinsField.setText("");

                }
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultTableModel tblModel = (DefaultTableModel) table1.getModel();

                if(table1.getSelectedRowCount() == 1) {
                    demo.remove(table1.getSelectedRow());
                    tblModel.removeRow(table1.getSelectedRow());
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

                DefaultTableModel tblModel = (DefaultTableModel) table1.getModel();

                if(table1.getSelectedRow() == 1) {

                    String Name = tblModel.getValueAt(table1.getSelectedRow(), 0).toString();
                    String Carbs = tblModel.getValueAt(table1.getSelectedRow(), 1).toString();
                    String Fats = tblModel.getValueAt(table1.getSelectedRow(), 2).toString();
                    String Proteins = tblModel.getValueAt(table1.getSelectedRow(), 3).toString();
                    String Category = tblModel.getValueAt(table1.getSelectedRow(), 4).toString();

                    tblModel.setValueAt(Name, table1.getSelectedRow(), 0);
                    tblModel.setValueAt(Carbs, table1.getSelectedRow(), 1);
                    tblModel.setValueAt(Fats, table1.getSelectedRow(), 2);
                    tblModel.setValueAt(Proteins, table1.getSelectedRow(), 3);
                    tblModel.setValueAt(Category, table1.getSelectedRow(), 4);

                    JOptionPane.showMessageDialog(MainForm.this, "Update successful");

                } else if (table1.getRowCount() == 0) {
                    JOptionPane.showMessageDialog(MainForm.this, "No data to edit");
                } else {
                    JOptionPane.showMessageDialog(MainForm.this, "Please select a single row");
                }
            }
        });
    }

    private void makeTable(ArrayList<Product> demo)    {

        Object[][] data = demo.stream()
                        .map(Product -> new Object[]{Product.getName(), Product.getCarbs(), Product.getFats(), Product.getProteins(), Product.getCategory()})
                                .toArray(Object[][]::new);

        table1.setModel(new DefaultTableModel(
                data,
                new String[]{"Name", "Carbs", "Fats", "Proteins", "Category"}
        ));
    }



}
