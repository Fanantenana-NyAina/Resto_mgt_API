package dao;

import db.DataSource;
import entity.Criteria;
import entity.DishIngredient;
import entity.FilterBy;
import entity.Order;

import java.time.LocalDateTime;
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
    public List<DishIngredient> findAndOrderAndPaginate(List<Criteria> criteria, FilterBy filterBy, Order order, int page, int size) {
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
    public List<DishIngredient> saveAll(List<DishIngredient> entity) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<DishIngredient> getAllByIdBeforeDate(int id, LocalDateTime dateTime) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}