package dietBuilder.Model;

public class MealItem {

    private final double weight;
    private final Product product;

    public Product getProduct() {
        return product;
    }

    public double getWeight() {
        return weight;
    }

    public MealItem(Product product, double weight) {
        this.product = product;
        this.weight = weight;
    }
}
