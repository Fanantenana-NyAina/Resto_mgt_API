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

public class DishOrderDAO implements DAO<DishOrder>{
    private final DataSource dataSource = new DataSource();

    @Override
    public List<DishOrder> getAll(int page, int size) {
        List<DishOrder> dishOrders = new ArrayList<>();

        String sql = "select dio.id_dish_in_order, o.id_order_as_reference, " +
                "d.id_dish, d.name, d.unit_price, " +
                "dos.dish_order_status, dos.status_datetime, " +
                "dio.quantity from dish_in_order dio " +
                "join \"order\" o on dio.id_order_as_reference = o.id_order_as_reference " +
                "join dish_order_status dos on dio.id_dish_in_order = dos.id_dish_in_order " +
                "join dish d on dio.id_dish = d.id_dish " +
                "limit ? offset ?";

        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, size);
            ps.setInt(2, (page - 1) * size);

            try (ResultSet rs = ps.executeQuery()) {
                DishOrder dishOrder = null;
                while (rs.next()) {
                    int idDishInOrder = rs.getInt("id_dish_in_order");
                    int idDish = rs.getInt("id_dish");
                    String dishName = rs.getString("name");
                    double dishUnitPrice = rs.getDouble("unit_price");
                    OrderStatus dishOrderStatus = OrderStatus.valueOf(rs.getString("dish_order_status"));
                    LocalDateTime statusDatetime = rs.getTimestamp("status_datetime").toLocalDateTime();
                    double quantity = rs.getDouble("quantity");

                    Dish dish = new Dish(idDish, dishName, dishUnitPrice);

                    DishOrderStatusHistory statusHistory = new DishOrderStatusHistory(dishOrderStatus, statusDatetime);

                    List<DishOrderStatusHistory> dishOrderStatusHistoryList = new ArrayList<>();
                    dishOrderStatusHistoryList.add(statusHistory);

                    dishOrder = new DishOrder(idDishInOrder, dish, quantity, dishOrderStatusHistoryList);
                    dishOrders.add(dishOrder);
                }

                return dishOrders;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public DishOrder findById(int id) {
        DishOrder dishOrder = null;
        String sql = "select dio.id_dish_in_order, o.id_order_as_reference, " +
                "d.id_dish, d.name, d.unit_price, " +
                "dos.dish_order_status, dos.status_datetime, " +
                "dio.quantity from dish_in_order dio " +
                "join \"order\" o on dio.id_order_as_reference = o.id_order_as_reference " +
                "join dish_order_status dos on dio.id_dish_in_order = dos.id_dish_in_order " +
                "join dish d on dio.id_dish = d.id_dish " +
                "where dio.id_dish_in_order = ?";

        try(Connection con = dataSource.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int idDishInOrder = rs.getInt("id_dish_in_order");
                    int idDish = rs.getInt("id_dish");
                    String dishName = rs.getString("name");
                    double dishUnitPrice = rs.getDouble("unit_price");
                    OrderStatus dishOrderStatus = OrderStatus.valueOf(rs.getString("dish_order_status"));
                    LocalDateTime statusDatetime = rs.getTimestamp("status_datetime").toLocalDateTime();
                    double quantity = rs.getDouble("quantity");

                    Dish dish = new Dish(idDish, dishName, dishUnitPrice);

                    DishOrderStatusHistory statusHistory = new DishOrderStatusHistory(dishOrderStatus, statusDatetime);

                    List<DishOrderStatusHistory> dishOrderStatusHistoryList = new ArrayList<>();
                    dishOrderStatusHistoryList.add(statusHistory);

                    dishOrder = new DishOrder(idDishInOrder, dish, quantity, dishOrderStatusHistoryList);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return dishOrder;
    }

    @Override
    public DishOrder getByName(String name) {
        DishOrder dishOrder = null;
        String sql = "select dio.id_dish_in_order, o.id_order_as_reference, " +
                "d.id_dish, d.name, d.unit_price, " +
                "dos.dish_order_status, dos.status_datetime, " +
                "dio.quantity from dish_in_order dio " +
                "join \"order\" o on dio.id_order_as_reference = o.id_order_as_reference " +
                "join dish_order_status dos on dio.id_dish_in_order = dos.id_dish_in_order " +
                "join dish d on dio.id_dish = d.id_dish " +
                "where d.name ilike ?";

        try(Connection con = dataSource.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, "%" + name + "%");

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int idDishInOrder = rs.getInt("id_dish_in_order");
                    int idDish = rs.getInt("id_dish");
                    String dishName = rs.getString("name");
                    double dishUnitPrice = rs.getDouble("unit_price");
                    OrderStatus dishOrderStatus = OrderStatus.valueOf(rs.getString("dish_order_status"));
                    LocalDateTime statusDatetime = rs.getTimestamp("status_datetime").toLocalDateTime();
                    double quantity = rs.getDouble("quantity");

                    Dish dish = new Dish(idDish, dishName, dishUnitPrice);

                    DishOrderStatusHistory statusHistory = new DishOrderStatusHistory(dishOrderStatus, statusDatetime);

                    List<DishOrderStatusHistory> dishOrderStatusHistoryList = new ArrayList<>();
                    dishOrderStatusHistoryList.add(statusHistory);

                    dishOrder = new DishOrder(idDishInOrder, dish, quantity, dishOrderStatusHistoryList);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return dishOrder;
    }

    @Override
    public List<DishOrder> findByCriteria(List<Criteria> criteria) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<DishOrder> findAndOrderAndPaginate(List<Criteria> criteria, LogicalConnector logicalConnector, FilterBy filterBy, Ordering order, int page, int size) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public double getPrice(int orderIdRef) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public double getPriceAtDate(int id, LocalDateTime dateTime) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<DishOrder> saveAll(List<DishOrder> entities) {
        List<DishOrder> newDishOrders = new ArrayList<>();

        try(Connection con = dataSource.getConnection()) {
            entities.forEach(entityToSave -> {
                try(PreparedStatement ps = con.prepareStatement("insert into dish_in_order (id_dish_in_order, id_dish, id_order_as_reference, quantity) values (?, ?, ?, ?)")) {
                    ps.setInt(1, entityToSave.getDishOrderId());
                    ps.setInt(2, entityToSave.getDish().getIdDish());
                    ps.setInt(3, entityToSave.getOrderIdRef());
                    ps.setDouble(4, entityToSave.getQuantity());

                    ps.executeUpdate();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                newDishOrders.add(findById(entityToSave.getDishOrderId()));
            });
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return newDishOrders;
    }

    @Override
    public List<DishOrder> getAllByIdBeforeDate(int orderIdRef, LocalDateTime dateTime) {
        List<DishOrder> dishOrders = new ArrayList<>();
        String sql = "select dio.id_dish_in_order, o.id_order_as_reference, " +
                "d.id_dish, d.name, d.unit_price, " +
                "dos.dish_order_status, dos.status_datetime, " +
                "dio.quantity from dish_in_order dio " +
                "join \"order\" o on dio.id_order_as_reference = o.id_order_as_reference " +
                "join dish_order_status dos on dio.id_dish_in_order = dos.id_dish_in_order " +
                "join dish d on dio.id_dish = d.id_dish "+ "where o.id_order_as_reference = ?" +
                " and dos.status_datetime <= ? order by dos.dish_order_status desc";

        try(Connection con = dataSource.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)) {
            System.out.println(sql);
            ps.setInt(1, orderIdRef);
            ps.setObject(2, dateTime);

            try (ResultSet rs = ps.executeQuery()) {
                DishOrder dishOrder = null;
                while (rs.next()) {
                    int idDishInOrder = rs.getInt("id_dish_in_order");
                    int idDish = rs.getInt("id_dish");
                    String dishName = rs.getString("name");
                    double dishUnitPrice = rs.getDouble("unit_price");
                    OrderStatus dishOrderStatus = OrderStatus.valueOf(rs.getString("dish_order_status"));
                    LocalDateTime statusDatetime = rs.getTimestamp("status_datetime").toLocalDateTime();
                    double quantity = rs.getDouble("quantity");

                    Dish dish = new Dish(idDish, dishName, dishUnitPrice);

                    DishOrderStatusHistory statusHistory = new DishOrderStatusHistory(dishOrderStatus, statusDatetime);

                    List<DishOrderStatusHistory> dishOrderStatusHistoryList = new ArrayList<>();
                    dishOrderStatusHistoryList.add(statusHistory);

                    dishOrder = new DishOrder(idDishInOrder, dish, quantity, dishOrderStatusHistoryList);
                    dishOrders.add(dishOrder);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return dishOrders;
    }
}
