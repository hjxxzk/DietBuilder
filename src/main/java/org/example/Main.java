package org.example;

import org.example.GUI.MainForm;
import org.example.Model.Meal;
import org.example.Model.Product;
import org.example.Persistence.TXTFileWorker;

import javax.swing.*;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        String productsTXTPath = args[0];
        String mealsTXTPath = args[1];
        TXTFileWorker worker = new TXTFileWorker(productsTXTPath, mealsTXTPath);
        ArrayList<Product> products = worker.readProducts();
        ArrayList<Meal> meals = worker.readMeals();

        SwingUtilities.invokeLater(() -> new MainForm(products, meals, worker));
    }
}