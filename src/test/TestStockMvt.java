package test;

import dao.StockMovementDAO;
import db.DataSource;
import entity.Ingredient;
import entity.Movement;
import entity.StockMovement;
import entity.Unit;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestStockMvt {
    private final DataSource dataSource = new DataSource();
    StockMovementDAO subject = new StockMovementDAO();

    public StockMovement getStockMovement() {
        return new StockMovement(
                1,
                3,
                "Oeuf",
                100,
                Unit.U,
                Movement.IN,
                LocalDateTime.parse("2025-02-01T08:00:00")
        );
    }

    @Test
    public void read_all_stock_movement() {
        List<StockMovement> expected = subject.getAll(1, 10);

        assertNotNull(expected, "should not be null");
        assertFalse(expected.isEmpty(), "should not be empty");
    }

    @Test
    public void read_stock_movement_by_id() {
        StockMovement expected = getStockMovement();

        StockMovement actual = subject.findById(expected.getIdMovement());

        assertEquals(expected.getIdMovement(), actual.getIdMovement());
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

    @Test
    public void save_stock() {
        Ingredient salt = new Ingredient(5, "sel", Unit.G);
        Ingredient rice = new Ingredient(6, "rice", Unit.G);

        StockMovement saltIn = new StockMovement(
                9,
                salt.getIdIngredient(),
                salt.getIngredientName(),
                10,
                salt.getUnit(),
                Movement.IN,
                LocalDateTime.now());

        StockMovement riceIn = new StockMovement(
                10,
                rice.getIdIngredient(),
                rice.getIngredientName(),
                10,
                rice.getUnit(),
                Movement.IN,
                LocalDateTime.now());

        StockMovement saltOut = new StockMovement(
                11,
                salt.getIdIngredient(),
                salt.getIngredientName(),
                5,
                salt.getUnit(),
                Movement.OUT,
                LocalDateTime.now());

        StockMovement riceOut = new StockMovement(
                12,
                riceIn.getIdIngredient(),
                rice.getIngredientName(),
                5,
                rice.getUnit(),
                Movement.OUT,
                LocalDateTime.now());

        List<StockMovement> expected = List.of(saltIn, saltOut, riceIn, riceOut);
        List<StockMovement> actual = subject.saveAll(expected);

        for (StockMovement movement : actual) {
            assertTrue(isStockMovementSavedInDatabase(movement.getIdMovement()));
        }
    }

    private boolean isStockMovementSavedInDatabase(int id) {
        String sql = "SELECT id FROM stock_movements WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
