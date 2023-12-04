package org.example;

import org.example.GUI.MainForm;
import org.example.Model.Meal;
import org.example.Model.Product;
import org.example.Persistence.TXTFileWorker;

import javax.swing.*;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        String a = "C:/Users/agnie/IdeaProjects/Laboratorium_44/target/Products.txt";
        String b = "C:/Users/agnie/IdeaProjects/Laboratorium_44/target/Meals.txt";
        TXTFileWorker worker = new TXTFileWorker(a, b);
        ArrayList<Product> products = worker.readProducts();
        ArrayList<Meal> meals = worker.readMeals();

        SwingUtilities.invokeLater(() -> new MainForm(products, meals, worker));
    }
}