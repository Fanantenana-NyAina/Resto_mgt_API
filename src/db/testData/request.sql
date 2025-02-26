select d.name as dish_name, SUM(i.unit_price * di.required_quantity) as total_cost
from dish_ingredient di join dish d on di.id_dish = d.id_dish
join ingredient i on di.id_ingredient = i.id_ingredient
where d.id_dish = 1 group by d.id_dish;

select * from ingredient i where i.name ilike '%u%' and i.unit_price <=1000;

select * from ingredient i where i.name ilike '%u%' and i.unit_price <=1000 order by name ASC;
select * from ingredient i where i.name ilike '%u%' and i.unit_price <=1000 order by unit_price DESC;

select * from ingredient i where i.name ilike '%u%' and i.unit_price <=1000 order limit 1 offset 0;
select * from ingredient i where i.name ilike '%u%' and i.unit_price <=1000 order limit 1 offset 1;


select d.id_dish, d.name as dish_name, d.unit_price as dish_price,
       i.name as ingredient_name, di.required_quantity, di.unit as ingredient_unit,
       i.unit_price as ingredient_price
from dish d
join
    dish_ingredient di on d.id_dish=di.id_dish
join
    ingredient i on di.id_ingredient=i.id_ingredient
where
    d.name ilike '%hot dog%';



select
    d.id_dish, d.name AS dish_name, d.unit_price AS dish_price,
    STRING_AGG(i.name, ', ') AS ingredients
from
    dish d
join
    dish_ingredient di ON d.id_dish = di.id_dish
join
    ingredient i ON di.id_ingredient = i.id_ingredient
group by d.id_dish;


select
    d.name AS dish_name, d.unit_price AS dish_price,
    string_agg(i.name ||' '||di.required_quantity||i.unit, ', ') AS ingredients
from
    dish d
join
    dish_ingredient di ON d.id_dish = di.id_dish
join
    ingredient i ON di.id_ingredient = i.id_ingredient
group by d.id_dish;
