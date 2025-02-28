package test;

import dao.StockMovementDAO;
import entity.Ingredient;
import entity.StockMovement;
import entity.Unit;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestStockMvt {
    StockMovementDAO stockMovementDAO = new StockMovementDAO();

    @Test
    public void read_all_stock_movement() {
        List<StockMovement> subject = stockMovementDAO.getAll(1, 10);

        System.out.println(subject);
        assertNotNull(subject, "should not be null");
        assertFalse(subject.isEmpty(), "should not be empty");
    }

    @Test
    public void read_stock_movement_by_id() {

    }

    @Test
    public void read_stock_movement_by_ingredientName() {

    }

    @Test
    public void countStockMovement() {
        LocalDateTime now = null;

        Ingredient Egg = new Ingredient(3, "oeuf", Unit.U);
        Ingredient Bred = new Ingredient(4, "pain", Unit.U);
        Ingredient saucage = new Ingredient(1, "saucisse", Unit.G);
        Ingredient Oil = new Ingredient(2, "Oil", Unit.L);

        double expectedEggsQuantity = 80;
        double expectedBredsQuantity = 80;
        double expectedSaucagesQuantity = 10000;
        double expectedOilsQuantity = 20;

        double actualEggQuantityNow = Egg.getStockMvtStateAtDate(now);
        double actualBredQuantityNow = Bred.getStockMvtStateAtDate(now);
        double actualSaucageQuantityNow = saucage.getStockMvtStateAtDate(now);
        double actualOilQuantityNow = Oil.getStockMvtStateAtDate(now);

        assertEquals(expectedEggsQuantity, actualEggQuantityNow);
        assertEquals(expectedBredsQuantity, actualBredQuantityNow);
        assertEquals(expectedSaucagesQuantity, actualSaucageQuantityNow);
        assertEquals(expectedOilsQuantity, actualOilQuantityNow);
    }
}
