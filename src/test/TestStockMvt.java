package test;

import dao.StockMovementDAO;
import entity.StockMovement;
import org.junit.jupiter.api.Test;

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
}
