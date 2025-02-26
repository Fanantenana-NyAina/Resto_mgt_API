package entity;

import dao.IngredientPriceHistoryDAO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class Dish {
    private int idDish;
    private String dishName;
    private double unitPrice;
    private List<DishIngredient> dishIngredients;
    private String ingredientsAndQuantity;

    public Dish(int idDish, String dishName, double unitPrice, List<DishIngredient> dishIngredients) {
        this.idDish = idDish;
        this.dishName = dishName;
        this.unitPrice = unitPrice;
        this.dishIngredients = dishIngredients;
    }

    public Dish(int idDish,String dishName, double unitPrice, String ingredientsAndQuantity) {
        this.idDish = idDish;
        this.dishName = dishName;
        this.unitPrice = unitPrice;
        this.ingredientsAndQuantity = ingredientsAndQuantity;
    }

    public Dish(int idDish, String dishName, double unitPrice) {
        this.idDish = idDish;
        this.dishName = dishName;
        this.unitPrice = unitPrice;
    }

    public double totalIngredientsCost(LocalDateTime dateChoice) {
        double totalCost = 0;

        for (DishIngredient dishIngredient : dishIngredients) {
            Ingredient ingredient = dishIngredient.getIngredient();
            double ingredientPrice = getPriceForIngredientAtDate(ingredient, dateChoice);
            double requiredQuantity = dishIngredient.getRequiredQuantity();

            totalCost += ingredientPrice * requiredQuantity;
        }

        return totalCost;
    }

    public double getPriceForIngredientAtDate(Ingredient ingredient, LocalDateTime dateChoice) {
        IngredientPriceHistoryDAO ingredientPriceHistoryService = new IngredientPriceHistoryDAO();

        return ingredientPriceHistoryService.getPriceAtDate(ingredient.getIdIngredient(), dateChoice);
    }

    public int getIdDish() {
        return idDish;
    }

    public void setIdDish(int idDish) {
        this.idDish = idDish;
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public List<DishIngredient> getDishIngredients() {
        return dishIngredients;
    }

    public void setDishIngredients(List<DishIngredient> dishIngredients) {
        this.dishIngredients = dishIngredients;
    }

    public String getIngredientsAndQuantity() {
        return ingredientsAndQuantity;
    }

    public void setIngredientsAndQuantity(String ingredientsAndQuantity) {
        this.ingredientsAndQuantity = ingredientsAndQuantity;
    }

    @Override
    public String toString() {
        return "Dish{" +
                "idDish=" + idDish +
                ", dishName='" + dishName + '\'' +
                ", unitPrice=" + unitPrice +
                ", dishIngredients=" + dishIngredients +
                ", ingredientsAndQuantity='" + ingredientsAndQuantity + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Dish dish = (Dish) o;
        return idDish == dish.idDish && Double.compare(unitPrice, dish.unitPrice) == 0 && Objects.equals(dishName, dish.dishName) && Objects.equals(dishIngredients, dish.dishIngredients) && Objects.equals(ingredientsAndQuantity, dish.ingredientsAndQuantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idDish, dishName, unitPrice, dishIngredients, ingredientsAndQuantity);
    }
}
