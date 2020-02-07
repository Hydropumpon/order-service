delete
from order_line;
delete
from orders;
delete
from customer;

alter sequence customer_id_seq restart with 1;

insert into customer(id, email, phone_number)
VALUES (nextval('customer_id_seq'), 'cust1@mail.ru', '11111111111');
insert into customer(id, email, phone_number)
VALUES (nextval('customer_id_seq'), 'cust2@mail.ru', '22222222222');
