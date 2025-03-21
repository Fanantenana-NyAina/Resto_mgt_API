package dao;

import db.DataSource;
import entity.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO implements DAO<Order> {
    private DataSource dataSource = new DataSource();

    @Override
    public List<Order> getAll(int page, int size) {
        List<Order> orders = new ArrayList<>();
        String sql = "select osh.id_order_status_history, o.id_order_as_reference,\n" +
                "\tosh.order_status, osh.status_datetime from \"order\" o\n" +
                "join order_status_history osh\n" +
                "\ton o.id_order_as_reference = osh.id_order_as_reference" + " limit ? offset ?";

        try(Connection con = dataSource.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, size);
            ps.setInt(2, (page-1)*size);

            try(ResultSet rs = ps.executeQuery()) {
                Order order = null;
                while (rs.next()) {
                    int orderStatusId = rs.getInt("id_order_status_history");
                    int orderAsReferenceId = rs.getInt("id_order_as_reference");
                    OrderStatus orderStatus = OrderStatus.valueOf(rs.getString("order_status"));
                    LocalDateTime orderDatetime = rs.getTimestamp("status_datetime").toLocalDateTime();

                    OrderStatusHistory orderStatusHistory = new OrderStatusHistory(orderStatus, orderDatetime);
                    List<OrderStatusHistory> orderStatusHistories = new ArrayList<>();
                    orderStatusHistories.add(orderStatusHistory);

                    order = new Order(orderStatusId, orderAsReferenceId, orderStatusHistories);
                }

                orders.add(order);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return orders;
    }

    @Override
    public Order findById(int id) {
        Order order = null;
         String sql = "select osh.id_order_status_history, o.id_order_as_reference,\n" +
                 "\tosh.order_status, osh.status_datetime from \"order\" o\n" +
                 "join order_status_history osh\n" +
                 "\ton o.id_order_as_reference = osh.id_order_as_reference" + " where o.id_order_as_reference = ?";

         try(Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
             ps.setInt(1, id);

             try(ResultSet rs = ps.executeQuery()) {
                 if (rs.next()) {
                     int orderStatusId = rs.getInt("id_order_status_history");
                     int orderAsReferenceId = rs.getInt("id_order_as_reference");
                     OrderStatus orderStatus = OrderStatus.valueOf(rs.getString("order_status"));
                     LocalDateTime orderDatetime = rs.getTimestamp("status_datetime").toLocalDateTime();

                     OrderStatusHistory orderStatusHistory = new OrderStatusHistory(orderStatus, orderDatetime);
                     List<OrderStatusHistory> orderStatusHistories = new ArrayList<>();
                     orderStatusHistories.add(orderStatusHistory);

                     order = new Order(orderStatusId, orderAsReferenceId, orderStatusHistories);
                 }
             }
         } catch (SQLException e) {
             throw new RuntimeException(e);
         }
        return order;
    }

    @Override
    public Order getByName(String name) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Order> findByCriteria(List<Criteria> criteria) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Order> findAndOrderAndPaginate(List<Criteria> criteria, LogicalConnector logicalConnector, FilterBy filterBy, Ordering order, int page, int size) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public double getPrice(int id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public double getPriceAtDate(int id, LocalDateTime dateTime) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Order> saveAll(List<Order> entities) {
        List<Order> orders = new ArrayList<>();

        String insertOrder = "insert into \"order\" (id_order_as_reference) " +
                "values (?)";
        String insertOrderStatusHistory = "insert into order_status_history (id_dish_order_status, dish_order_status, status_datetime, id_dish_in_order) " +
                "values (?, ?, ?, ?)";

        try (Connection con = dataSource.getConnection()) {
            con.setAutoCommit(false);

            try (PreparedStatement psOrder = con.prepareStatement(insertOrder);
                 PreparedStatement psOrderStatusHistory = con.prepareStatement(insertOrderStatusHistory)) {

                entities.forEach(orderToSave -> {
                    try {
                        psOrder.setInt(1, orderToSave.getOrderIdRef());

                        psOrder.addBatch();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                });

                psOrder.executeBatch();

                entities.forEach(orderToSave -> orderToSave.getOrderStatus().forEach(statusHistory -> {
                    try {
                        psOrderStatusHistory.setInt(1, orderToSave.getOrderHistoryId());
                        psOrderStatusHistory.setString(2, statusHistory.getOrderStatus().name());
                        psOrderStatusHistory.setObject(3, statusHistory.getOrderDateTime());
                        psOrderStatusHistory.setInt(4, orderToSave.getOrderIdRef());

                        psOrderStatusHistory.addBatch();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }));

                psOrderStatusHistory.executeBatch();
                con.commit();

            } catch (SQLException e) {
                con.rollback();
                throw new RuntimeException(e);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return orders;
    }

    @Override
    public List<Order> getAllByIdBeforeDate(int OrderIdRef, LocalDateTime dateTime) {
        List<Order> orders = new ArrayList<>();
        String sql = "select osh.id_order_status_history, o.id_order_as_reference,\n" +
                "\tosh.order_status, osh.status_datetime from \"order\" o\n" +
                "join order_status_history osh\n" +
                "\ton o.id_order_as_reference = osh.id_order_as_reference" + " where o.id_order_as_reference = ? " +
                "and osh.status_datetime <= ? order by osh.status_datetime desc";

        try(Connection con = dataSource.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, OrderIdRef);
            ps.setObject(2, dateTime);

            try(ResultSet rs = ps.executeQuery()) {
                Order order = null;
                while (rs.next()) {
                    int orderStatusId = rs.getInt("id_order_status_history");
                    int orderAsReferenceId = rs.getInt("id_order_as_reference");
                    OrderStatus orderStatus = OrderStatus.valueOf(rs.getString("order_status"));
                    LocalDateTime orderDatetime = rs.getTimestamp("status_datetime").toLocalDateTime();

                    OrderStatusHistory orderStatusHistory = new OrderStatusHistory(orderStatus, orderDatetime);
                    List<OrderStatusHistory> orderStatusHistories = new ArrayList<>();
                    orderStatusHistories.add(orderStatusHistory);

                    order = new Order(orderStatusId, orderAsReferenceId, orderStatusHistories);
                }

                orders.add(order);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return orders;
    }
}
