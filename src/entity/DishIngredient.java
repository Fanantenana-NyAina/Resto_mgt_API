package entity;

public class DishIngredient {
    private Ingredient ingredient;
    private double requiredQuantity;
    private Unit unit;


    public DishIngredient(Ingredient ingredient, double requiredQuantity, Unit unit) {
        this.ingredient = ingredient;
        this.requiredQuantity = requiredQuantity;
        this.unit = unit;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public double getRequiredQuantity() {
        return requiredQuantity;
    }

    public void setRequiredQuantity(double requiredQuantity) {
        this.requiredQuantity = requiredQuantity;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }
}