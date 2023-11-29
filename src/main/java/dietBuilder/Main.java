package dietBuilder;

import dietBuilder.GUI.MainForm;
import dietBuilder.Model.Product;

import javax.swing.*;
import java.util.ArrayList;

import static dietBuilder.Persistence.TXTFileWorker.readFromFile;

public class Main {
    public static void main(String[] args) {
        ArrayList<Product> demo = readFromFile("C:/Users/agnie/IdeaProjects/Laboratorium_44/src/main/resources/Data.txt");


        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainForm(demo);
            }
        });
    }
}