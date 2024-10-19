create table products.product (
    id serial primary key,
    product_identifier varchar not null,
    nome varchar(100) not null,
    descricao varchar not null,
    preco float not null,
    category_id serial REFERENCES products.category(id)
);