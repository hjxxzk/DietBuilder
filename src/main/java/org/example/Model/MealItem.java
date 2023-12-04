package org.example.Model;

public class MealItem {

    private double weight;
    private final Product product;

    public Product getProduct() {
        return product;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public MealItem(Product product, double weight) {
        this.product = product;
        this.weight = weight;
    }
}
