create table shopping.shop (
    id serial primary key,
    user_identifier varchar(100) not null,
    date timestamp not null,
    total float not null
);

create table shopping.item (
    shop_id serial REFERENCES shopping.shop(id),
    product_identifier varchar(100) not null,
    price float not null
);