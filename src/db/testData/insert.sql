            ----- HOT DOG ------
-- Insertion du plat "Hot Dog"
INSERT INTO dish (id_dish, name, unit_price)
VALUES (1, 'Hot Dog', 5.00);

-- Insertion des ingrédients pour "Hot Dog"
INSERT INTO ingredient (id_ingredient, name, unit_price, unit, update_datetime)
VALUES
    (1, 'Saucisse', 20, 'G', CURRENT_TIMESTAMP),
    (2, 'Huile', 10000, 'L', CURRENT_TIMESTAMP),
    (3, 'Oeuf', 1000, 'U', CURRENT_TIMESTAMP),
    (4, 'Pain', 1000, 'U', CURRENT_TIMESTAMP);

-- Insertion de la relation entre le "Hot Dog" et ses ingrédients
INSERT INTO dish_ingredient (id_dish, id_ingredient, required_quantity, unit)
VALUES
    (1, 1, 100, 'G'),
    (1, 2, 0.15, 'L'),
    (1, 3, 1, 'U'),
    (1, 4, 1, 'U');


INSERT INTO ingredient_price_history (id_price_history, id_ingredient, price, history_date)
VALUES
    (1, 1, 20, '2025-01-01 00:00:00'),
    (2, 2, 10000, '2025-01-01 00:00:00'),
    (3, 3, 1000, '2025-01-01 00:00:00'),
    (4, 4, 1000, '2025-01-01 00:00:00');

                                        ----- OMELETTE (if needed)-------
-- Insertion du plat "Omelette"
INSERT INTO dish (id_dish, name, unit_price)
VALUES (2, 'Omelette', 3.50);

-- Insertion des ingrédients pour "Omelette"
INSERT INTO ingredient (id_ingredient, name, unit_price, unit, update_datetime)
VALUES
    (5, 'Oeuf', 0.50, 'U', CURRENT_TIMESTAMP),
    (6, 'Huile', 2.00, 'L', CURRENT_TIMESTAMP),
    (7, 'Sel', 0.10, 'U', CURRENT_TIMESTAMP),
    (8, 'Poivre', 0.15, 'U', CURRENT_TIMESTAMP);

-- Insertion de la relation entre l'"Omelette" et ses ingrédients
INSERT INTO dish_ingredient (id_dish, id_ingredient, required_quantity, unit)
VALUES
    (2, 5, 1, 'U'),
    (2, 6, 0.05, 'L'),
    (2, 7, 1, 'U'),
    (2, 8, 1, 'U');


----- STOCK MANAGEMENT PART  --------
    --- insert stock:
insert into stock_movement (id_movement, id_ingredient, movement, quantity, unit, movement_datetime)
values
    (1, 3, 'IN', 100, 'U', '2025-02-01 08:00:00'),
    (2, 4, 'IN', 50, 'U', '2025-02-01 08:00:00'),
    (3, 1, 'IN', 10000, 'G', '2025-02-01 08:00:00'),
    (4, 2, 'IN', 20, 'L', '2025-02-01 08:00:00');

