package dietBuilder.Model;

public class Product {
    private final String name;
    private final double carbs;
    private final double fats;
    private final double proteins;
    private final Category category;

    public Product(String name, double carbs, double fats, double proteins, Category category) {
        this.name = name;
        this.carbs = carbs;
        this.fats = fats;
        this.proteins = proteins;
        this.category = category;
    }


    public String getName() {
        return name;
    }

    public double getCarbs() {
        return carbs;
    }

    public double getFats() {
        return fats;
    }

    public double getProteins() {
        return proteins;
    }

    public Category getCategory() {
        return category;
    }


}
