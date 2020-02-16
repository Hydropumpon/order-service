create sequence hibernate_sequence;

alter sequence hibernate_sequence owner to postgres;

create sequence customer_id_seq start with 1 increment by 1 cache 5;

alter sequence customer_id_seq owner to postgres;

create sequence order_id_seq start with 1 increment by 1 cache 5;

alter sequence order_id_seq owner to postgres;

create sequence order_line_id_seq start with 1 increment by 1 cache 5;

alter sequence order_line_id_seq owner to postgres;

create table if not exists customer
(
    id integer not null
        constraint customer_pkey
            primary key,
    email varchar(255) not null,
    phone_number varchar(255) not null
);

alter table customer owner to postgres;

create table if not exists orders
(
    id integer not null
        constraint orders_pkey
            primary key,
    amount numeric(19,2),
    check_date date,
    creation_date date not null,
    state varchar(255) not null,
    customer_id integer
        constraint fk_customer_id
            references customer
);

alter table orders owner to postgres;

create table if not exists order_line
(
    id integer not null
        constraint order_line_pkey
            primary key,
    item_id integer not null,
    quantity integer not null,
    order_id integer
        constraint fk_order_id
            references orders
);

alter table order_line owner to postgres;

alter sequence customer_id_seq owned by customer.id;

alter sequence order_line_id_seq owned by order_line.id;

alter sequence order_id_seq owned by orders.id;