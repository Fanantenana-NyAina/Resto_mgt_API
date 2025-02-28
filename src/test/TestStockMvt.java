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
        Ingredient ingredient = new Ingredient(4, "pain", Unit.U);

        LocalDateTime now = null; // localDateTime at getStockMvtStateAtDate()
        LocalDateTime atDate = LocalDateTime.parse("2025-02-04T08:00:00");


        double expectedQuantity = 50; //pain
        double actualQuantity1 = ingredient.getStockMvtStateAtDate(now);
        double actualQuantity2 = ingredient.getStockMvtStateAtDate(atDate);

        assertEquals(expectedQuantity, actualQuantity1, "now quantity state");
        assertEquals(expectedQuantity, actualQuantity2, "at date quantity state");
    }
}
