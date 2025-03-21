do
$$
    begin
        if not exists(select from pg_type where typname = 'unit') then
        create type "unit" as enum ('G','L', 'U');
    end if;
end
$$;

create table if not exists dish
(
    id_dish    int primary key,
    name       varchar not null,
    unit_price numeric
);

create table if not exists ingredient
(
    id_ingredient    int primary key,
    name       varchar not null,
    unit_price numeric,
    unit unit not null,
    update_datetime timestamp not null default current_timestamp
);

create table if not exists dish_ingredient
(
    id_dish int,
    id_ingredient int,
    required_quantity numeric,
    unit unit not null,
    primary key (id_dish, id_ingredient),
    constraint fk_id_dish foreign key (id_dish) references dish(id_dish),
    constraint fk_id_ingredient foreign key (id_ingredient) references ingredient(id_ingredient)
);

-- changing ingredient table:
alter table ingredient drop column unit_price;
alter table ingredient drop column update_datetime;

-- new table for the new dbStructure:
create table if not exists ingredientPriceHistory
(
    id_price_history int primary key,
    id_ingredient int not null,
    price numeric,
    history_date timestamp not null default current_timestamp,
    constraint fk_ingredient foreign key (id_ingredient) references ingredient(id_ingredient)
);

-- stock management part:
    ---- creating movement type:
do
$$
    begin
        if not exists(select from pg_type where typname = 'mvt') then
        create type "mvt" as enum ('IN', 'OUT');
    end if;
end
$$;

    ---- creating StockMovementTable:
create table if not exists stock_movement
(
    id_movement int primary key,
    id_ingredient int not null,
    movement mvt,
    quantity numeric,
    unit unit,
    movement_datetime timestamp not null default current_timestamp,
    constraint fk_ingredient foreign key (id_ingredient) references ingredient(id_ingredient)
);



    --- Ordering management:
do
$$
    begin
        if not exists(select from pg_type where typname = 'order_status') then
        create type "order_status" as enum ('CREATED','CONFIRMED', 'IN_PREPARATION', 'COMPLETED', 'SERVED');
    end if;
end
$$;

create table if not exists "order"
(
    id_order_as_reference int primary key
);

create table if not exists dish_in_order
(
    id_dish_in_order int primary key,
    id_dish int,
    id_order_as_reference int,
    quantity numeric not null,
    constraint fk_id_dish foreign key (id_dish) references dish(id_dish),
    constraint fk_id_order_as_reference foreign key (id_order_as_reference) references "order"(id_order_as_reference)
);

create table if not exists order_status_history
(
    id_order_status_history int primary key,
    id_order_as_reference int not null,
    order_status order_status not null,
    status_datetime timestamp not null default current_timestamp,
    constraint fk_id_order_as_reference foreign key (id_order_as_reference) references "order"(id_order_as_reference)
);

create table if not exists dish_order_status
(
    id_dish_order_status int primary key,
    dish_order_status order_status not null,
    status_datetime timestamp not null default current_timestamp,
    id_dish_in_order int not null,
    constraint fk_id_dish_in_order foreign key (id_dish_in_order) references dish_in_order(id_dish_in_order)
);
