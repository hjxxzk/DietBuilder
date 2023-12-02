package dietBuilder.Model;

public class Product {
    private String name;
    private double carbs;
    private double fats;
    private double proteins;
    private Category category;

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

    public void setName(String name) {
        this.name = name;
    }

    public void setCarbs(double carbs) {
        this.carbs = carbs;
    }

    public void setFats(double fats) {
        this.fats = fats;
    }

    public void setProteins(double proteins) {
        this.proteins = proteins;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

}
