package test;

import dao.OrderDAO;
import entity.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class OrderTest {
    OrderDAO subject = new OrderDAO();

    private Dish hotDogSimple () {
        return new Dish(1, "Hot Dog", 15000.0);
    }

    @Test
    public void read_all_orders() {
        List<Order> actual = subject.getAll(1, 1);

        assertEquals(1, actual.size());
    }

    @Test
    public void read_order_by_id() {
        int id = 1;
        Order actual = subject.findById(id);

        assertEquals(id, actual.getOrderIdRef());
    }

    @Test
    public void read_most_recent_orders() {
        int orderIdRef = 1;
        LocalDateTime dateTime = LocalDateTime.now();

        List<Order> actual = subject.getAllByIdBeforeDate(orderIdRef, dateTime);

        assertNotNull(actual);

        actual.forEach(order -> assertEquals(orderIdRef, order.getOrderIdRef()));

        actual.forEach(order -> order.getOrderStatus().forEach(status ->
                assertTrue(status.getOrderDateTime().isBefore(dateTime) || status.getOrderDateTime().isEqual(dateTime))
        ));
    }

    @Test
    public void getActualStatus_for_0rder() {
        LocalDateTime actual = LocalDateTime.now();
        LocalDateTime before = actual.minusHours(1);

        OrderStatusHistory recentStatus = new OrderStatusHistory(OrderStatus.SERVED, actual);
        OrderStatusHistory olderStatus = new OrderStatusHistory(OrderStatus.IN_PREPARATION, before);

        List<OrderStatusHistory> statusHistory = new ArrayList<>();
        statusHistory.add(recentStatus);
        statusHistory.add(olderStatus);

        Order order = new Order(1, 101, statusHistory);

        OrderStatusHistory actualStatus = order.getActualStatus().get(0);

        assertEquals(OrderStatus.SERVED, actualStatus.getOrderStatus());
    }

    @Test
    public void getDishOrder() {
        int expectedOrderIdRef = 1;
        LocalDateTime now = LocalDateTime.now();

        Dish hotDog = hotDogSimple();

        List<DishOrderStatusHistory> statusHistory = new ArrayList<>();
        statusHistory.add(new DishOrderStatusHistory(OrderStatus.SERVED, now));
        statusHistory.add(new DishOrderStatusHistory(OrderStatus.IN_PREPARATION, now.minusMinutes(1)));

        DishOrder hotDogOrder = new DishOrder(1, expectedOrderIdRef, hotDog, 4, statusHistory);

        List<DishOrder> dishOrders = new ArrayList<>();
        dishOrders.add(hotDogOrder);

        Order order = new Order(1, expectedOrderIdRef, new ArrayList<>(), dishOrders);

        DishOrder actualDishOrder = order.getDishOrder();

        assertEquals(expectedOrderIdRef, actualDishOrder.getOrderIdRef());
    }

    @Test
    public void getTotalAmount() {
        LocalDateTime now = LocalDateTime.now();
        Dish hotDog = hotDogSimple();

        List<DishOrderStatusHistory> statusHistory = new ArrayList<>();
        statusHistory.add(new DishOrderStatusHistory(OrderStatus.SERVED, now));
        statusHistory.add(new DishOrderStatusHistory(OrderStatus.IN_PREPARATION, now.minusMinutes(1)));

        DishOrder hotDogOrder = new DishOrder(1, 1, hotDog, 4, statusHistory);

        List<DishOrder> dishOrders = new ArrayList<>();
        dishOrders.add(hotDogOrder);

        Order order = new Order(1, 1, new ArrayList<>(), dishOrders);

        double unitPrice = hotDog.getUnitPrice();
        double quantity = hotDogOrder.getQuantity();
        double totalAmoutExpected = unitPrice * quantity;

        double actualTotalAmount = order.getTotaAmount();

        assertEquals(totalAmoutExpected, actualTotalAmount);
    }
}
