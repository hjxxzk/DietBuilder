package org.example.GUI;

import org.example.Model.Meal;
import org.example.Model.MealItem;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class CreateMeal extends JFrame {
    private JPanel Panel1;
    private JTextField MealName;
    private JButton Create;

    public CreateMeal(ArrayList<Meal> meals, JComboBox MealsBox) {

        setContentPane(Panel1);
        setTitle("Create Meal");
        setSize(300, 200);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
        setLocationRelativeTo(null);
        Create.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                meals.add(new Meal(MealName.getText(), new ArrayList<MealItem>()));
                MealsBox.addItem(MealName.getText());
                JOptionPane.showMessageDialog(CreateMeal.this, "Created successfully");
            }
        });
    }
}


