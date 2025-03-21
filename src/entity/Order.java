package entity;

import java.util.List;
import java.util.Objects;

public class Order {
    private int orderHistoryId;
    private int orderIdRef;
    private List<OrderStatusHistory> orderStatus;
    private List<DishOrder> dishOrders;

    public Order(int orderHistoryId, int orderIdRef, List<OrderStatusHistory> orderStatus) {
        this.orderHistoryId = orderHistoryId;
        this.orderIdRef = orderIdRef;
        this.orderStatus = orderStatus;
    }

    public Order(int orderHistoryId, int orderIdRef, List<OrderStatusHistory> orderStatus, List<DishOrder> dishOrders) {
        this.orderHistoryId = orderHistoryId;
        this.orderIdRef = orderIdRef;
        this.orderStatus = orderStatus;
        this.dishOrders = dishOrders;
    }

    public int getOrderHistoryId() {
        return orderHistoryId;
    }

    public void setOrderHistoryId(int orderHistoryId) {
        this.orderHistoryId = orderHistoryId;
    }

    public int getOrderIdRef() {
        return orderIdRef;
    }

    public void setOrderIdRef(int orderIdRef) {
        this.orderIdRef = orderIdRef;
    }

    public List<OrderStatusHistory> getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(List<OrderStatusHistory> orderStatus) {
        this.orderStatus = orderStatus;
    }

    public List<DishOrder> getDishOrders() {
        return dishOrders;
    }

    public void setDishOrders(List<DishOrder> dishOrders) {
        this.dishOrders = dishOrders;
    }

    public List<OrderStatusHistory> getActualStatus () {
        if (orderStatus.isEmpty()) {
            throw new IllegalStateException("No status history!!");
        }

        orderStatus.sort((h1, h2) ->
                h2.getOrderDateTime().compareTo(h1.getOrderDateTime()));

        return orderStatus;
    }

    public DishOrder getDishOrder() {
        if (dishOrders.isEmpty()) {
            return null;
        }

        return dishOrders.stream()
                .filter((dishOrders) -> dishOrders.getOrderIdRef() == orderIdRef)
                .findFirst()
                .orElse(null);
    }

    public double getTotaAmount() {
        return dishOrders.stream()
                .mapToDouble(dishOrder -> (dishOrder.getDish().getUnitPrice()) * dishOrder.getQuantity())
                .sum();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return orderHistoryId == order.orderHistoryId && orderIdRef == order.orderIdRef && Objects.equals(orderStatus, order.orderStatus) && Objects.equals(dishOrders, order.dishOrders);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderHistoryId, orderIdRef, orderStatus, dishOrders);
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderHistoryId=" + orderHistoryId +
                ", orderIdRef=" + orderIdRef +
                ", orderStatus=" + orderStatus +
                ", dishOrders=" + dishOrders +
                '}';
    }
}
