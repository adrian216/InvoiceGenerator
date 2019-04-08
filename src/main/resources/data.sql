-- Address
INSERT INTO ADDRESS (id, street, postal_code, city, country) VALUES (21, 'Some Street', 'V54 12G', 'Toronto', 'Canada');
INSERT INTO ADDRESS (id, street, postal_code, city, country) VALUES (22, 'Some Street', 'V54 12G', 'Toronto', 'Canada');
INSERT INTO ADDRESS (id, street, postal_code, city, country) VALUES (23, 'Some Street', 'V54 12G', 'Toronto', 'Canada');
INSERT INTO ADDRESS (id, street, postal_code, city, country) VALUES (24, 'Some Street', 'V54 12G', 'Toronto', 'Canada');
INSERT INTO ADDRESS (id, street, postal_code, city, country) VALUES (25, 'Some Street', 'V54 12G', 'Toronto', 'Canada');
INSERT INTO ADDRESS (id, street, postal_code, city, country) VALUES (26, 'Some Street', 'V54 12G', 'Toronto', 'Canada');
INSERT INTO ADDRESS (id, street, postal_code, city, country) VALUES (27, 'Some Street', 'V54 12G', 'Toronto', 'Canada');
INSERT INTO ADDRESS (id, street, postal_code, city, country) VALUES (28, 'Some Street', 'V54 12G', 'Toronto', 'Canada');
INSERT INTO ADDRESS (id, street, postal_code, city, country) VALUES (29, 'Some Street', 'V54 12G', 'Toronto', 'Canada');
INSERT INTO ADDRESS (id, street, postal_code, city, country) VALUES (30, 'Some Street', 'V54 12G', 'Toronto', 'Canada');
INSERT INTO ADDRESS (id, street, postal_code, city, country) VALUES (31, 'Some Street', 'V54 12G', 'Toronto', 'Canada');


-- Customers
INSERT INTO CUSTOMER(id, first_name, last_name, created_at, address_id) VALUES (1, 'Holmes', 'Sherlock', '2013-02-22',21);
INSERT INTO CUSTOMER(id, first_name, last_name, created_at, address_id) VALUES (2, 'Micheal', 'Fleming', '2012-01-01',22);
INSERT INTO CUSTOMER(id, first_name, last_name, created_at, address_id) VALUES (3, 'Albert', 'Yearannaidu', '2012-04-21',23);
INSERT INTO CUSTOMER(id, first_name, last_name, created_at, address_id) VALUES (4, 'Ravindran', 'Ramanathan', '2015-02-19',24);
INSERT INTO CUSTOMER(id, first_name, last_name, created_at, address_id) VALUES (5, 'Cook', 'Jacks', '2018-11-14',25);
INSERT INTO CUSTOMER(id, first_name, last_name, created_at, address_id) VALUES (6, 'Stuart', 'Shilton', '2019-12-16',26);
INSERT INTO CUSTOMER(id, first_name, last_name, created_at, address_id) VALUES (7, 'Karl', 'Torento', '2011-10-23',27);
INSERT INTO CUSTOMER(id, first_name, last_name, created_at, address_id) VALUES (8, 'Srinivas', 'Sundariya', '2018-09-11',28);
INSERT INTO CUSTOMER(id, first_name, last_name, created_at, address_id) VALUES (9, 'Karolina', 'Brann', '2014-07-04',29);
INSERT INTO CUSTOMER(id, first_name, last_name, created_at, address_id) VALUES (10, 'Martin', 'Sock', '2014-01-06',30);
INSERT INTO CUSTOMER(id, first_name, last_name, created_at, address_id) VALUES (11, 'Alias', 'Beckdoub', '2015-08-04',31);

-- Products
INSERT INTO PRODUCT(id, name, sku, details, price, vat, created_at) VALUES(1, 'Casio Edifice MTP-12', 'MTP12B124', 'Zegarek meski casio', 599.0, 23,  '2013-02-22');
INSERT INTO PRODUCT(id, name, sku, details, price, vat, created_at) VALUES(2, 'Lorus ASG565', 'ASG565', 'Zegarek meski lorus', 1299.0, 23,  NOW());
INSERT INTO PRODUCT(id, name, sku, details, price, vat, created_at) VALUES(3, 'Kolczyki złote serca', 'SR585AR', 'Kolczyki dla dziecka serca', 399.0, 23,  '2013-02-22');
INSERT INTO PRODUCT(id, name, sku, details, price, vat, created_at) VALUES(4, 'Bransoleta pancer', 'AR232-LH', 'Bransoleta meska 7mm', 2599.0, 23,  '2013-02-22');
INSERT INTO PRODUCT(id, name, sku, details, price, vat, created_at) VALUES(5, 'Łańcuszek anker AG925', 'AG1314LS-pancer', 'łancuszek damski srebrny 3mm', 99.0, 23,  '2013-02-22');
INSERT INTO PRODUCT(id, name, sku, details, price, vat, created_at) VALUES(6, 'Kolia złota 585 szmaragdy', 'KO585SZ', 'Kolia złota ze szmaragdem', 2299.0, 23,  '2013-02-22');
INSERT INTO PRODUCT(id, name, sku, details, price, vat, created_at) VALUES(7, 'Pierścionek z diamentami 0,2ct', 'P585D02', 'Pierscionek z 12 diamentami o masie 0,2ct', 1199.0, 23,  '2013-02-22');
INSERT INTO PRODUCT(id, name, sku, details, price, vat, created_at) VALUES(8, 'Sygnet srebrny emalia', 'S925BE', 'Sygnet emaliowany srebrny', 199.0, 23,  '2013-02-22');
INSERT INTO PRODUCT(id, name, sku, details, price, vat, created_at) VALUES(9, 'Pierścionek srebrny cyrkonia', 'P925C', 'rozmiar 18', 59.0, 23,  '2013-02-22');
INSERT INTO PRODUCT(id, name, sku, details, price, vat, created_at) VALUES(10, 'Naszyjnik złoty 333', 'N33350', 'Naszyjnik z cyrkoniami', 299.0, 23,  '2013-02-22');
INSERT INTO PRODUCT(id, name, sku, details, price, vat, created_at) VALUES(11, 'Adriatica LMT-1243-BCR', 'LMT-1243', 'Zegarek meski adriatica', 899.0, 23,  '2013-02-22');
INSERT INTO PRODUCT(id, name, sku, details, price, vat, created_at) VALUES(12, 'Kurier zagraniczny', '00040', 'Przesylka pocztowa zagraniczna ubezpieczona', 90.0, 0,  '2013-02-22');
INSERT INTO PRODUCT(id, name, sku, details, price, vat, created_at) VALUES(13, 'Kurier poczta', '00003', 'Przesyłka pocztowa', 15.0, 0,  '2013-02-22');
INSERT INTO PRODUCT(id, name, sku, details, price, vat, created_at) VALUES(14, 'Kurier pobranie', '00002', 'Kurier pobranie', 25.0, 0,  '2013-02-22');
INSERT INTO PRODUCT(id, name, sku, details, price, vat, created_at) VALUES(15, 'Kurier', '00001', 'Kurier', 20.0, 0,  '2013-02-22');
