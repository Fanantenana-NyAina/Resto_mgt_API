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
import java.util.logging.Filter;

public class StockMovementDAO implements DAO<StockMovement>{
    private final DataSource dataSource = new DataSource();

    @Override
    public List<StockMovement> getAll(int page, int size) {
        String sql = "select s.id_movement, i.name,  s.quantity, i.unit, s.movement, s.movement_datetime from ingredient i " +
                "join stock_movement s on i.id_ingredient = s.id_ingredient limit ? offset ?";
        List<StockMovement> stockMovements = new ArrayList<>();

        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, size);
            preparedStatement.setInt(2, (page-1)*size);

            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                while(resultSet.next()) {
                    int id = resultSet.getInt("id_movement");
                    String name = resultSet.getString("name");
                    double quantity = resultSet.getInt("quantity");
                    Unit unit = Unit.valueOf(resultSet.getString("unit"));
                    Movement movement = Movement.valueOf(resultSet.getString("movement"));
                    LocalDateTime movementDatetime = resultSet.getTimestamp("movement_datetime").toLocalDateTime();

                    StockMovement stockMovement = new StockMovement(id, name, quantity, unit, movement, movementDatetime);
                    stockMovements.add(stockMovement);
                }

                return stockMovements;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public StockMovement findById(int id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public StockMovement getByName(String name) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<StockMovement> findByCriteria(List<Criteria> criteria) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<StockMovement> findAndOrderAndPaginate(List<Criteria> criteria, FilterBy filterBy, Order order, int page, int size) {
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
    public List<StockMovement> saveAll(List<StockMovement> entity) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
