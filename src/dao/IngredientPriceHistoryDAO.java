package dao;

import db.DataSource;
import entity.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public class IngredientPriceHistoryDAO implements DAO<IngredientPriceHistory> {
    private final DataSource dataSource = new DataSource();

    @Override
    public List<IngredientPriceHistory> getAll(int page, int size) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public IngredientPriceHistory findById(int id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public IngredientPriceHistory getByName(String name) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<IngredientPriceHistory> findByCriteria(List<Criteria> criteria) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<IngredientPriceHistory> findAndOrderAndPaginate(List<Criteria> criteria, LogicalConnector logicalConnector, FilterBy filterBy, Ordering order, int page, int size) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public double getPrice(int id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public double getPriceAtDate(int id_ingredient, LocalDateTime dateTime) {
        double price = 0;

        String sql = "select price from ingredient_price_history where id_ingredient = ? and history_date <= ? order by history_date desc";

        try(Connection connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id_ingredient);
            ps.setObject(2, dateTime);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    price = rs.getDouble("price");
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return price;
    }

    @Override
    public List<IngredientPriceHistory> saveAll(List<IngredientPriceHistory> entity) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<IngredientPriceHistory> getAllByIdBeforeDate(int id, LocalDateTime dateTime) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
