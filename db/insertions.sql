insert into recipes () values ();
insert into recipes () values ();

insert into ingredients (name, price_per_gram) values ('Ananas', 0.4);
insert into ingredients (name, price_per_gram) values ('Schinken', 0.4);
insert into ingredients (name, price_per_gram) values ('Käse', 0.4);

insert into recipe_entries (id_recipe, id_ingredient, quantity_in_grams) values (1, 1, 200);
insert into recipe_entries (id_recipe, id_ingredient, quantity_in_grams) values (1, 2, 400);
insert into recipe_entries (id_recipe, id_ingredient, quantity_in_grams) values (2, 2, 300);
insert into recipe_entries (id_recipe, id_ingredient, quantity_in_grams) values (2, 3, 100);
	
insert into pizza_variations (name, id_recipe_small, id_recipe_large, id_recipe_xlarge, price_small, price_large, price_xlarge) values ('Margherita', 1, 1, 1, 3, 6, 10);
insert into pizza_variations (name, id_recipe_small, id_recipe_large, id_recipe_xlarge, price_small, price_large, price_xlarge) values ('Hawaii', 2, 2, 2, 3, 6, 10);

insert into toppings (name, price, id_recipe) values ('Spaghetti', 2.00, 1);
insert into toppings (name, price, id_recipe) values ('Käse', 2.00, 1);
insert into toppings (name, price, id_recipe) values ('Pilze', 2.00, 1);
insert into toppings (name, price, id_recipe) values ('Scharfe Peperoni', 2.00, 1);
insert into toppings (name, price, id_recipe) values ('Milde Peperoni', 2.00, 1);
insert into toppings (name, price, id_recipe) values ('Oliven', 2.00, 1);