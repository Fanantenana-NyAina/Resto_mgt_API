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

public class IngredientDAO implements DAO<Ingredient> {
    private final DataSource dataSource = new DataSource();

    @Override
    public List<Ingredient> getAll(int page, int size) {
        List<Ingredient> ingredients = new ArrayList<>();
        String sql = "select  id_ingredient, name, unit from ingredient limit ? offset ?";

        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, size);
            ps.setInt(2, (page - 1) * size);

            try (ResultSet rs = ps.executeQuery()) {
                Ingredient ingredient = null;
                while (rs.next()) {
                    int id = rs.getInt("id_ingredient");
                    String name = rs.getString("name");
                    Unit unit = Unit.valueOf(rs.getString("unit"));

                    ingredient = new Ingredient(id, name, unit);
                    ingredients.add(ingredient);
                }

                return ingredients;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Ingredient findById(int id) {
        String sql = "select id_ingredient, name, unit from ingredient where id_ingredient=?";
        Ingredient ingredient = null;

        try(Connection connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);

            ingredient = getIngredient(ingredient, ps);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return ingredient;
    }

    @Override
    public Ingredient getByName(String ingredientName) {
        String sql = "select id_ingredient, name, unit from ingredient where name ilike ?";
        Ingredient ingredient = null;

        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, "%" + ingredientName + "%");

            ingredient = getIngredient(ingredient, ps);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return ingredient;
    }

    private Ingredient getIngredient(Ingredient ingredient, PreparedStatement ps) throws SQLException {
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                int id_ingredient = rs.getInt("id_ingredient");
                String name = rs.getString("name");
                Unit unit = Unit.valueOf(rs.getString("unit"));

                ingredient = new Ingredient(id_ingredient, name, unit);
            }
        }
        return ingredient;
    }

    @Override
    public List<Ingredient> findByCriteria(List<Criteria> ingredientCriterias) {
        //StringBuilder sql = new StringBuilder("select i.id_ingredient, i.name, i.unit_price, i.unit, i.update_datetime from ingredient i where 1=1");
        StringBuilder sql = new StringBuilder(
                "select i.id_ingredient, i.name, i.unit, iph.price, iph.history_date from ingredient i " +
                        "join ingredient_price_history iph on i.id_ingredient = iph.id_ingredient where 1=1"
        );

        for (Criteria criteria : ingredientCriterias) {
            if ("name".equals(criteria.getColumn())) {
                sql.append(" and i.name ilike '%").append(criteria.getValue()).append("%'");
            } else if ("price".equals(criteria.getColumn())) {
                List<Double> priceRange = (List<Double>) criteria.getValue();
                if (priceRange.size() == 2) {
                    double minPrice = priceRange.get(0);
                    double maxPrice = priceRange.get(1);
                    sql.append(" or i.price between ").append(minPrice).append(" and ").append(maxPrice);
                }
            } else if ("history_date".equals(criteria.getColumn())) {
                List<LocalDateTime> dates = (List<LocalDateTime>) criteria.getValue();
                if (dates.size() == 2) {
                    LocalDateTime startDate = dates.get(0);
                    LocalDateTime endDate = dates.get(1);
                    sql.append(" or iph.history_date between '").append(startDate).append("' and '").append(endDate).append("'");
                }
            } else if ("unit".equals(criteria.getColumn())) {
                sql.append(" or i.unit = '").append(criteria.getValue()).append("'");
            }
        }

        return getIngredients(sql);
    }

    private List<Ingredient> getIngredients(StringBuilder sql) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql.toString())) {

            try (ResultSet rs = ps.executeQuery()) {
                List<Ingredient> ingredients = new ArrayList<>();
                while (rs.next()) {
                    int idIngredient = rs.getInt("id_ingredient");
                    String name = rs.getString("name");
                    double price = rs.getDouble("price");
                    Unit unit = Unit.valueOf(rs.getString("unit"));
                    LocalDateTime updateDatetime = rs.getTimestamp("history_date").toLocalDateTime();

                    ingredients.add(new Ingredient(idIngredient, name, price, unit, updateDatetime));
                }

                return ingredients;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Ingredient> findAndOrderAndPaginate(List<Criteria> ingredientCriterias, FilterBy filterBy, Order order, int page, int size) {
        //StringBuilder sql = new StringBuilder("select i.id_ingredient, i.name, i.unit_price, i.unit, i.update_datetime from ingredient i where 1=1");
        StringBuilder sql = new StringBuilder(
                "select i.id_ingredient, i.name, i.unit, iph.price, iph.history_date from ingredient i " +
                        "join ingredient_price_history iph on i.id_ingredient = iph.id_ingredient where 1=1"
        );

        for (Criteria criteria : ingredientCriterias) {
            if ("name".equals(criteria.getColumn())) {
                sql.append(" or i.name ilike '%").append(criteria.getValue()).append("%'");
            } else if ("price".equals(criteria.getColumn())) {
                List<Double> priceRange = (List<Double>) criteria.getValue();
                if (priceRange.size() == 2) {
                    double minPrice = priceRange.get(0);
                    double maxPrice = priceRange.get(1);
                    sql.append(" or iph.price between ").append(minPrice).append(" and ").append(maxPrice);
                }
            } else if ("history_date".equals(criteria.getColumn())) {
                List<LocalDateTime> dates = (List<LocalDateTime>) criteria.getValue();
                if (dates.size() == 2) {
                    LocalDateTime startDate = dates.get(0);
                    LocalDateTime endDate = dates.get(1);
                    sql.append(" or iph.history_date between '").append(startDate).append("' and '").append(endDate).append("'");
                }
            } else if ("unit".equals(criteria.getColumn())) {
                sql.append(" or unit = '").append(criteria.getValue()).append("'");
            }
        }


        if(filterBy.toString().equalsIgnoreCase("name")|| filterBy.toString().equalsIgnoreCase("unit")) {
            sql.append(" order by i.").append(filterBy.toString().toLowerCase()).append(" ").append(order.toString().toLowerCase());
        } else if (filterBy.toString().equalsIgnoreCase("price")||filterBy.toString().equalsIgnoreCase("history_date")) {
            sql.append(" order by iph.").append(filterBy.toString().toLowerCase()).append(" ").append(order.toString().toLowerCase());
        }
        sql.append(" limit ").append(size).append(" offset ").append((page - 1) * size);

        return getIngredients(sql);
    }


    @Override
    public double getPrice(int idIngredient) {
        String sql = "select unit_price from ingredient where id_ingredient = ?";
        double price = 0;

        try(Connection connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idIngredient);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    price = rs.getDouble("unit_price");
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return price;
    }

    @Override
    public double getPriceAtDate(int id, LocalDateTime dateTime) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Ingredient> saveAll(List<Ingredient> entity) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
