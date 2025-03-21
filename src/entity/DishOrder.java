package entity;

import java.util.List;
import java.util.Objects;

public class DishOrder {
    private int dishOrderId;
    private int orderIdRef;
    private Dish dish;
    private double quantity;
    List<DishOrderStatusHistory> orderHistory;

    public DishOrder(int dishOrderId, Dish dish, double quantity, List<DishOrderStatusHistory> orderHistory) {
        this.dishOrderId = dishOrderId;
        this.dish = dish;
        this.quantity = quantity;
        this.orderHistory = orderHistory;
    }

    public DishOrder(int dishOrderId, int orderIdRef, Dish dish, double quantity, List<DishOrderStatusHistory> orderHistory) {
        this.dishOrderId = dishOrderId;
        this.orderIdRef = orderIdRef;
        this.dish = dish;
        this.quantity = quantity;
        this.orderHistory = orderHistory;
    }

    public int getDishOrderId() {
        return dishOrderId;
    }

    public void setDishOrderId(int dishOrderId) {
        this.dishOrderId = dishOrderId;
    }

    public int getOrderIdRef() {
        return orderIdRef;
    }

    public void setOrderIdRef(int orderIdRef) {
        this.orderIdRef = orderIdRef;
    }

    public Dish getDish() {
        return dish;
    }

    public void setDish(Dish dish) {
        this.dish = dish;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public List<DishOrderStatusHistory> getOrderHistory() {
        return orderHistory;
    }

    public void setOrderHistory(List<DishOrderStatusHistory> orderHistory) {
        this.orderHistory = orderHistory;
    }

    public DishOrderStatusHistory getActualStatus() {
        if (orderHistory.isEmpty()) {
            throw new IllegalStateException("No status history!!");
        }

        orderHistory.sort((h1, h2) ->
                h2.getOrderDateTime().compareTo(h1.getOrderDateTime()));

        return orderHistory.get(0);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        DishOrder dishOrder = (DishOrder) o;
        return Double.compare(quantity, dishOrder.quantity) == 0 && Objects.equals(dish, dishOrder.dish) && Objects.equals(orderHistory, dishOrder.orderHistory);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dish, quantity, orderHistory);
    }

    @Override
    public String toString() {
        return "DishOrder{" +
                "dish=" + dish +
                ", quantity=" + quantity +
                ", orderHistory=" + orderHistory +
                '}';
    }
}
