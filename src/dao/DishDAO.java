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

public class DishDAO implements DAO<Dish> {
    private final DataSource dataSource = new DataSource();

    @Override
    public List<Dish> getAll(int page, int size) {
        List<Dish> dishes = new ArrayList<>();

        String sql = "select\n" +
                "    d.id_dish, d.name AS dish_name, d.unit_price AS dish_price,\n" +
                "    string_agg(i.name||' '||di.required_quantity||i.unit,', ') AS ingredients\n" +
                "from\n" +
                "    dish d\n" +
                "join\n" +
                "    dish_ingredient di ON d.id_dish = di.id_dish\n" +
                "join\n" +
                "    ingredient i ON di.id_ingredient = i.id_ingredient\n" +
                "group by d.id_dish" +
                " limit ? offset ?;";

        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, size);
            ps.setInt(2, (page - 1) * size);

            try (ResultSet rs = ps.executeQuery()) {
                Dish dish = null;
                while (rs.next()) {
                    int id = rs.getInt("id_dish");
                    String name = rs.getString("dish_name");
                    double price = rs.getDouble("dish_price");
                    String ingredient = rs.getString("ingredients");

                    dish = new Dish(id, name, price, ingredient);
                    dishes.add(dish);
                }

                return dishes;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Dish findById(int id) {
        String sql = "select id_dish, name, unit_price from dish where id_dish=?";
        Dish dish = null;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);

            dish = getDish(dish, ps);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return dish;
    }

    private Dish getDish(Dish dish, PreparedStatement ps) throws SQLException {
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                int id_dish = rs.getInt("id_dish");
                String name = rs.getString("name");
                double price = rs.getDouble("unit_price");

                dish = new Dish(id_dish, name, price);
            }
        }
        return dish;
    }

    @Override
    public Dish getByName(String dishName) {
        String sql = "select id_dish, name, unit_price from dish where name ilike ?";
        Dish dish = null;

        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, "%" + dishName + "%");

            dish = getDish(dish, ps);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return dish;
    }

    @Override
    public List<Dish> findByCriteria(List<Criteria> criteria) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Dish> findAndOrderAndPaginate(List<Criteria> criteria, LogicalConnector logicalConnector, FilterBy filterBy, Order order, int page, int size) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public double getPrice(int id) {
        throw new UnsupportedOperationException("Not supported yet");
    }

    @Override
    public double getPriceAtDate(int id, LocalDateTime dateTime) {
        throw new UnsupportedOperationException("Not supported yet");
    }

    @Override
    public List<Dish> saveAll(List<Dish> entities) {
        List<Dish> newDish = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            entities.forEach(entityToSave -> {
                try (PreparedStatement ps = connection.prepareStatement("insert into dish (id_dish, name, unit_price) values (?, ?, ?)")) {
                    ps.setInt(1, entityToSave.getIdDish());
                    ps.setString(2, entityToSave.getDishName());
                    ps.setDouble(3, entityToSave.getUnitPrice());

                    ps.executeUpdate();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                newDish.add(findById(entityToSave.getIdDish()));
            });
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return newDish;
    }

    @Override
    public List<Dish> getAllByIdBeforeDate(int id, LocalDateTime dateTime) {
        return List.of();
    }
}