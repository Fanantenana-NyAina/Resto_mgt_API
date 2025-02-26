package test;

import dao.IngredientDAO;
import entity.Criteria;
import entity.Ingredient;
import entity.Order;
import entity.Unit;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestIngredient {
    IngredientDAO subject = new IngredientDAO();

    public Ingredient getIngredient() {
        return new Ingredient(1, "Saucisse", Unit.G);
    }

    @Test
    public void find_ingredient_by_id() {
        Ingredient expected = getIngredient();

        Ingredient actual = subject.findById(expected.getIdIngredient());

        assertEquals(expected, actual);
    }

    @Test
    void read_by_criteria() {
        ArrayList<Criteria> criteria = new ArrayList<>();
        criteria.add(new Criteria("name", "Saucisse"));
        criteria.add(new Criteria("unit_price", List.of(500.0, 10000.0)));
        criteria.add(new Criteria("unit", "U"));
        criteria.add(new Criteria("update_datetime", List.of(
                LocalDateTime.parse("2025-01-01T00:00:00"),
                LocalDateTime.parse("2025-01-01T00:00:00")
        )));

        List<Ingredient> expected = List.of(
                new Ingredient(1, "Saucisse", 20.0, Unit.G, LocalDateTime.parse("2025-01-01T00:00:00")),  // Saucisse, unit "G", price 20
                new Ingredient(3, "Oeuf", 1000.0, Unit.U, LocalDateTime.parse("2025-01-01T00:00:00")),     // Oeuf, unit "U", price 1000
                new Ingredient(4, "Pain", 1000.0, Unit.U, LocalDateTime.parse("2025-01-01T00:00:00"))      // Pain, unit "U", price 1000
        );


        List<Ingredient> actual = subject.findByCriteria(criteria);

        assertEquals(expected, actual);
        assertEquals(expected.size(), actual.size());
        assertTrue(actual.stream()
                .allMatch(ingredient -> ingredient.getIngredientName().toLowerCase().contains("saucisse")
                        || (ingredient.getUnitPrice() >= 500.0 && ingredient.getUnitPrice() <= 10000.0)
                        || ingredient.getUnit() == Unit.U
                        || (ingredient.getUpdateDateTime().isEqual(LocalDateTime.parse("2025-01-01T00:00:00"))
                        || (ingredient.getUpdateDateTime().isAfter(LocalDateTime.parse("2025-01-01T00:00:00"))
                        && ingredient.getUpdateDateTime().isBefore(LocalDateTime.parse("2025-01-01T00:00:00"))))));
    }

    @Test
    void find_paginate_and_order_priceInterval_dateInterval() {
        List<Criteria> criteria = new ArrayList<>();
        criteria.add(new Criteria("name", "oeuf"));
        criteria.add(new Criteria("unit_price", List.of(500.0, 2000.0)));
        criteria.add(new Criteria("unit", "U"));
        criteria.add(new Criteria("update_datetime", List.of(
                LocalDateTime.parse("2025-01-01T00:00:00"),
                LocalDateTime.parse("2025-01-01T00:00:00")
        )));

        String filterBy = "price";
        Order order = Order.ASC;
        int page = 1;
        int size = 10;

        List<Ingredient> expected = List.of(
                new Ingredient(1, "Saucisse", 20.0, Unit.G, LocalDateTime.parse("2025-01-01T00:00:00")),
                new Ingredient(2, "Huile", 10000.0, Unit.L, LocalDateTime.parse("2025-01-01T00:00:00")),
                new Ingredient(3, "Oeuf", 1000.0, Unit.U, LocalDateTime.parse("2025-01-01T00:00:00")),
                new Ingredient(4, "Pain", 1000.0, Unit.U, LocalDateTime.parse("2025-01-01T00:00:00"))
        );

        List<Ingredient> actual = subject.findAndOrderAndPaginate(criteria, filterBy, order, page, size);

        assertEquals(expected.size(), actual.size());
        assertTrue(actual.stream()
                .allMatch(ingredient -> ingredient.getIngredientName().toLowerCase().contains("oeuf")
                        || (ingredient.getUnitPrice() >= 500.0 && ingredient.getUnitPrice() <= 2000.0)
                        || ingredient.getUnit() == Unit.U
                        || (ingredient.getUpdateDateTime().isEqual(LocalDateTime.parse("2025-01-01T00:00:00"))
                        || (ingredient.getUpdateDateTime().isAfter(LocalDateTime.parse("2025-01-01T00:00:00"))
                        && ingredient.getUpdateDateTime().isBefore(LocalDateTime.parse("2025-01-01T00:00:00"))))));
    }
}
