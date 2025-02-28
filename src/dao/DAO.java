package dao;

import entity.Criteria;
import entity.FilterBy;
import entity.Order;

import java.time.LocalDateTime;
import java.util.List;

public interface DAO<E> {
    List<E> getAll(int page, int size);

    E findById(int id);

    E getByName(String name);

    List<E> findByCriteria(List<Criteria> criteria);

    List<E> findAndOrderAndPaginate(List<Criteria> criteria, FilterBy filterBy, Order order, int page, int size);

    double getPrice(int id);

    double getPriceAtDate(int id, LocalDateTime dateTime);

    List<E> saveAll (List<E> entity);

    List<E> getAllByIdBeforeDate (int id, LocalDateTime dateTime);
}
