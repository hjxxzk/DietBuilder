package org.example.Model;

import java.util.ArrayList;

public class Meal {
    private final String type;
    private ArrayList<MealItem> items;

    public Meal(String type, ArrayList<MealItem> items)   {
        this.type = type;
        this.items = items;
    }

    public ArrayList<MealItem> getItems() {
        return items;
    }

    public String getType() {
        return type;
    }
}
