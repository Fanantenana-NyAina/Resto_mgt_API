package test;

import dao.DishDAO;
import entity.Dish;
import entity.DishIngredient;
import entity.Ingredient;
import entity.Unit;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestDish {
    DishDAO subject = new DishDAO();

    private Dish hotDogFull() {
        Ingredient ingredient1 = new Ingredient(1, "Saucisse", 20.0, Unit.G, null);
        Ingredient ingredient2 = new Ingredient(2, "Huile", 10000.0, Unit.L, null);
        Ingredient ingredient3 = new Ingredient(3, "Oeuf", 1000.0, Unit.U, null);
        Ingredient ingredient4 = new Ingredient(4, "Pain", 1000.0, Unit.U, null);

        DishIngredient dishIngredient1 = new DishIngredient(ingredient1, 100.0, Unit.G);
        DishIngredient dishIngredient2 = new DishIngredient(ingredient2, 0.15, Unit.L);
        DishIngredient dishIngredient3 = new DishIngredient(ingredient3, 1.0, Unit.U);
        DishIngredient dishIngredient4 = new DishIngredient(ingredient4, 1.0, Unit.U);

        List<DishIngredient> dishIngredients = List.of(dishIngredient1, dishIngredient2, dishIngredient3, dishIngredient4);

        return new Dish(1, "Hot Dog", 15000.0, dishIngredients);
    }

    private Dish hotDogSimple () {
        return new Dish(1, "Hot Dog", 15000.0);
    }

    @Test
    public void read_all_dish() {
        List<Dish> actual = subject.getAll(1, 1);
        assertEquals(1, actual.size());
    }

    @Test
    public void find_dish_by_id() {
        Dish expectedDIsh = hotDogSimple();
        Dish actualDish = subject.findById(expectedDIsh.getIdDish());

        //System.out.println(expectedDIsh);
        assertEquals(expectedDIsh, actualDish);
    }

    @Test
    public void read_dish_name() {
        Dish expectedDish = hotDogSimple();
        Dish actualDish = subject.getByName(expectedDish.getDishName());

        assertEquals(expectedDish.getDishName(), actualDish.getDishName());
    }

    @Test
    public void getTotalIngredientsCost() {
        Dish hotDog = hotDogFull();
        LocalDateTime dateTest = LocalDateTime.now();

        double expectedCost = (20 * 100) + (10000 * 0.15) + (1000 * 1) + (1000 * 1);
        //System.out.println("expected: " + expectedCost);
        double actualCost = hotDog.totalIngredientsCost(dateTest);

        //System.out.println("actual: " + actualCost);
        assertEquals(expectedCost, actualCost);
    }

}
