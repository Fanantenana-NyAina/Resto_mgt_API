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

public class DishIngredientDAO implements DAO<DishIngredient> {
    private final DataSource dataSource = new DataSource();

    @Override
    public List<DishIngredient> getAll(int page, int size) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public DishIngredient findById(int id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public DishIngredient getByName(String name) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<DishIngredient> findByCriteria(List<Criteria> criteria) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<DishIngredient> findAndOrderAndPaginate(List<Criteria> criteria, LogicalConnector logicalConnector, FilterBy filterBy, Ordering order, int page, int size) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<DishIngredient> finDishIngredientByIdDish(int id) {
        List<DishIngredient> dishIngredient = new ArrayList<>();

        String sql = "select di.id_dish, di.id_ingredient, i.name, di.required_quantity, di.unit " +
                "from dish_ingredient di join ingredient i on di.id_ingredient = i.id_ingredient where di.id_dish = ?";

        try(Connection con = dataSource.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);

            try(ResultSet rs = ps.executeQuery()) {
                DishIngredient dishIngredientRes = null;
                while(rs.next()) {
                    int id_ingredient = rs.getInt("id_ingredient");
                    String name = rs.getString("name");
                    double required_quantity = rs.getDouble("required_quantity");
                    Unit unit = Unit.valueOf(rs.getString("unit"));

                    Ingredient ingredient = new Ingredient(id_ingredient, name, unit);
                    dishIngredientRes = new DishIngredient(ingredient, required_quantity, unit);
                    dishIngredient.add(dishIngredientRes);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return dishIngredient;
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
    public List<DishIngredient> saveAll(List<DishIngredient> entity) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<DishIngredient> getAllByIdBeforeDate(int id, LocalDateTime dateTime) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}