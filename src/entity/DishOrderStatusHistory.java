package entity;

import java.time.LocalDateTime;
import java.util.Objects;

public class DishOrderStatusHistory {
    private OrderStatus orderStatus;
    private LocalDateTime orderDateTime;

    public DishOrderStatusHistory(OrderStatus orderStatus, LocalDateTime orderDateTime) {
        this.orderStatus = orderStatus;
        this.orderDateTime = orderDateTime;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public LocalDateTime getOrderDateTime() {
        return orderDateTime;
    }

    public void setOrderDateTime(LocalDateTime orderDateTime) {
        this.orderDateTime = orderDateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        DishOrderStatusHistory that = (DishOrderStatusHistory) o;
        return orderStatus == that.orderStatus && Objects.equals(orderDateTime, that.orderDateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderStatus, orderDateTime);
    }

    @Override
    public String toString() {
        return "DishOrderStatusHistory{" +
                "orderStatus=" + orderStatus +
                ", orderDateTime=" + orderDateTime +
                '}';
    }
}


