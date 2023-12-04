package org.example.Model;

import java.util.ArrayList;
import java.util.HashMap;

public class ShoppingList {

    public HashMap<Category, ArrayList<MealItem>> getBuy() {
        return buy;
    }

    HashMap<Category, ArrayList<MealItem>> buy;

    public ShoppingList(ArrayList<MealItem> items)   {
        this.buy = createShoppingList(items);
    }

    private HashMap<Category, ArrayList<MealItem>> createShoppingList(ArrayList<MealItem> items)   {

        HashMap<Category, ArrayList<MealItem>> list = new HashMap<>();

        for (MealItem item : items) {

            Category category = item.getProduct().getCategory();

            if (list.containsKey(category)) {
                boolean productExists = list.get(category).stream()
                        .anyMatch(MealItem -> MealItem.getProduct().getName().equals(item.getProduct().getName()));

                if (productExists) {
                    list.get(category).stream()
                            .filter(MealItem -> MealItem.getProduct().getName().equals(item.getProduct().getName()))
                            .findFirst()
                            .ifPresent(existingMealItem -> {
                                double weight = existingMealItem.getWeight() + item.getWeight();
                                existingMealItem.setWeight(weight);
                            });
                } else {
                    MealItem newItem = new MealItem(item.getProduct(), item.getWeight());
                    list.get(category).add(newItem);
                }

            } else {
                ArrayList<MealItem> products = new ArrayList<>();
                MealItem newItem = new MealItem(item.getProduct(), item.getWeight());
                products.add(newItem);
                list.put(category, products);

            }
        }

        return list;
    }
}
