package dietBuilder.GUI;

import dietBuilder.Model.Product;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class MainForm extends JFrame {
    private JPanel mainPanel;
    private JTable table1;
    private JLabel napis;
    private JButton button1;

    public MainForm(ArrayList demo) {

        makeTable(demo);
        setContentPane(mainPanel);
        setTitle("Diet Builder");
        setSize(400, 500);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

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
