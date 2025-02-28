package dao;

import db.DataSource;
import entity.*;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class StockMovementDAO implements DAO<StockMovement>{
    private final DataSource dataSource = new DataSource();

    @Override
    public List<StockMovement> getAll(int page, int size) {
        String sql = "select s.id_movement, i.id_ingredient, i.name,  s.quantity, i.unit, s.movement, s.movement_datetime from ingredient i " +
                "join stock_movement s on i.id_ingredient = s.id_ingredient limit ? offset ?";
        List<StockMovement> stockMovements = new ArrayList<>();

        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, size);
            preparedStatement.setInt(2, (page-1)*size);

            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                while(resultSet.next()) {
                    int id = resultSet.getInt("id_movement");
                    int id_ingredient = resultSet.getInt("id_ingredient");
                    String name = resultSet.getString("name");
                    double quantity = resultSet.getInt("quantity");
                    Unit unit = Unit.valueOf(resultSet.getString("unit"));
                    Movement movement = Movement.valueOf(resultSet.getString("movement"));
                    LocalDateTime movementDatetime = resultSet.getTimestamp("movement_datetime").toLocalDateTime();

                    StockMovement stockMovement = new StockMovement(id, id_ingredient, name, quantity, unit, movement, movementDatetime);
                    stockMovements.add(stockMovement);
                }

                return stockMovements;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public StockMovement findById(int idStockMvt) {
        String sql = "select s.id_movement, i.id_ingredient, i.name,  s.quantity, i.unit, s.movement, s.movement_datetime from ingredient i " +
                "join stock_movement s on i.id_ingredient = s.id_ingredient where s.id_movement = ?";
        StockMovement stockMovement = null;

        try(Connection connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idStockMvt);

            try(ResultSet resultSet = ps.executeQuery()) {
                while(resultSet.next()) {
                    int id = resultSet.getInt("id_movement");
                    int id_ingredient = resultSet.getInt("id_ingredient");
                    String name = resultSet.getString("name");
                    double quantity = resultSet.getInt("quantity");
                    Unit unit = Unit.valueOf(resultSet.getString("unit"));
                    Movement movement = Movement.valueOf(resultSet.getString("movement"));
                    LocalDateTime movementDatetime = resultSet.getTimestamp("movement_datetime").toLocalDateTime();

                    stockMovement = new StockMovement(id, id_ingredient, name, quantity, unit, movement, movementDatetime);
                }

                return stockMovement;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public StockMovement getByName(String ingredientName) {
        String sql = "select s.id_movement, i.id_ingredient, i.name,  s.quantity, i.unit, s.movement, s.movement_datetime from ingredient i" +
                "join ingredient i s on i.id_ingredient = s.id_ingredient where i.name = ?";
        StockMovement stockMovement = null;

        try(Connection connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, ingredientName);

            try(ResultSet resultSet = ps.executeQuery()) {
                while(resultSet.next()) {
                    int id = resultSet.getInt("id_movement");
                    int id_ingredient = resultSet.getInt("id_ingredient");
                    String name = resultSet.getString("name");
                    double quantity = resultSet.getInt("quantity");
                    Unit unit = Unit.valueOf(resultSet.getString("unit"));
                    Movement movement = Movement.valueOf(resultSet.getString("movement"));
                    LocalDateTime movementDatetime = resultSet.getTimestamp("movement_datetime").toLocalDateTime();

                    stockMovement = new StockMovement(id, id_ingredient, name, quantity, unit, movement, movementDatetime);
                }

                return stockMovement;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
    public List<StockMovement> saveAll(List<StockMovement> stockMovements) {
        List<StockMovement> newStockMovements = new ArrayList<>();
        String sql = "INSERT INTO stock_movement (id_movement, id_ingredient, movement, quantity, unit, movement_datetime) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = dataSource.getConnection()) {
            for (StockMovement stockMovement : stockMovements) {
                try (PreparedStatement ps = connection.prepareStatement(sql)) {
                    ps.setInt(1, stockMovement.getIdMovement());
                    ps.setInt(2, stockMovement.getIdIngredient());
                    ps.setObject(3, stockMovement.getMovement(), Types.OTHER);
                    ps.setDouble(4, stockMovement.getQuantity());
                    ps.setObject(5, stockMovement.getUnit(), Types.OTHER);
                    ps.setObject(6, stockMovement.getMovementDateTime());

                    int rows = ps.executeUpdate();
                    if (rows > 0) {
                        System.out.println("done!");
                    } else {
                        System.out.println("failed!");
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return newStockMovements;
    }



    @Override
    public List<StockMovement> getAllByIdBeforeDate(int StockMovementId, LocalDateTime dateTime) {
        String sql = "select s.id_movement, i.id_ingredient, i.name,  s.quantity, i.unit, s.movement, s.movement_datetime from stock_movement s " +
                "join ingredient i on i.id_ingredient = s.id_ingredient where s.id_ingredient = ?" +
                " and s.movement_datetime <= ? order by s.movement_datetime asc";

        List<StockMovement> stockMovements = new ArrayList<>();

        try(Connection connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, StockMovementId);
            ps.setObject(2, dateTime);

            try(ResultSet resultSet = ps.executeQuery()) {
                while(resultSet.next()) {
                    int id = resultSet.getInt("id_movement");
                    int id_ingredient = resultSet.getInt("id_ingredient");
                    String name = resultSet.getString("name");
                    double quantity = resultSet.getInt("quantity");
                    Unit unit = Unit.valueOf(resultSet.getString("unit"));
                    Movement movement = Movement.valueOf(resultSet.getString("movement"));
                    LocalDateTime movementDatetime = resultSet.getTimestamp("movement_datetime").toLocalDateTime();

                    StockMovement stockMovement = new StockMovement(id, id_ingredient, name, quantity, unit, movement, movementDatetime);
                    stockMovements.add(stockMovement);
                }

                return stockMovements;
            }

        } catch ( SQLException e ) {
            throw new RuntimeException(e);
        }
    }
}
