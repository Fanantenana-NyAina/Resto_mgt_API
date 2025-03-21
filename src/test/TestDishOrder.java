package test;

import dao.DishOrderDAO;
import entity.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestDishOrder {
    DishOrderDAO subject = new DishOrderDAO();

    private Dish hotDogSimple () {
        return new Dish(1, "Hot Dog", 15000.0);
    }

    @Test
    public void read_all_dish_orders() {
        List<DishOrder> actual = subject.getAll(1, 1);

        assertEquals(1, actual.size());
    }

    @Test
    public void read_dish_order_by_id() {
        int idToFind = 1;

        DishOrder actual = subject.findById(idToFind);

        assertEquals(idToFind, actual.getDishOrderId());
    }

    @Test
    public void read_dish_order_by_dishName() {
        String dishName = "Hot Dog";

        DishOrder actual = subject.getByName(dishName);

        assertEquals(dishName, actual.getDish().getDishName());
    }

    @Test
    public void read_recent_dish_orders() {
        int orderIdRef = 1;
        LocalDateTime dateTime = LocalDateTime.now();

        List<DishOrder> actual = subject.getAllByIdBeforeDate(orderIdRef, dateTime);

        assertNotNull(actual);
        assertFalse(actual.isEmpty());
        assertTrue(actual.stream().allMatch(dishOrder ->
                dishOrder.getOrderHistory().stream().allMatch(status ->
                        status.getOrderDateTime().isBefore(dateTime) || status.getOrderDateTime().isEqual(dateTime)
                )
        ));
    }

    @Test
    public void getActualDishOrderStatus() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS");
        String dateTimeString = "2025-03-19 09:49:16.014281";
        LocalDateTime dateTime = LocalDateTime.parse(dateTimeString, formatter);

        OrderStatus expectedStatus = OrderStatus.SERVED;
        DishOrder dishOrderSubject = getDishOrder(expectedStatus, dateTime);

        OrderStatus actualStatus = dishOrderSubject.getActualStatus().getOrderStatus();

        assertEquals(expectedStatus, actualStatus);
    }

    private DishOrder getDishOrder(OrderStatus expectedStatus, LocalDateTime dateTime) {
        DishOrderStatusHistory historyOfTheDishOrder = new DishOrderStatusHistory(expectedStatus, dateTime);
        List<DishOrderStatusHistory> histories = new ArrayList<>();
        histories.add(historyOfTheDishOrder);

        LocalDateTime olderDateTime = dateTime.minusDays(1);
        DishOrderStatusHistory olderHistory = new DishOrderStatusHistory(OrderStatus.IN_PREPARATION, olderDateTime);
        histories.add(olderHistory);

        return new DishOrder(1, hotDogSimple(), 4, histories);
    }
}
