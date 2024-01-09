CREATE DATABASE Baovola;


CREATE TABLE type 
(
    id SERIAL PRIMARY KEY ,
    name VARCHAR(100) 
);

CREATE TABLE materiaux (
    id SERIAL PRIMARY KEY ,
    name VARCHAR(100) ,
    price NUMERIC(10,2)
)

CREATE TABLE price_materiaux (
    id  SERIAL PRIMARY KEY ,
    id_materiaux INT REFERENCES materiaux (id) ON DELETE CASCADE ,$
    price NUMERIC(10,2),
    unit NUMERIC(10,2)
);

INSERT INTO price_materiaux (id,id_materiaux,price,unit) VALUES(500,22,1500,1);
INSERT INTO price_materiaux (id,id_materiaux,price,unit) VALUES(501,23,2100,1);
INSERT INTO price_materiaux (id,id_materiaux,price,unit) VALUES(502,24,2300,1);

CREATE TABLE type_materiaux(
    id SERIAL PRIMARY KEY ,
    id_type INT REFERENCES type (id) ON DELETE CASCADE ,
    id_materiaux INT REFERENCES materiaux (id) ON DELETE CASCADE ,
);

CREATE TABLE quality (
    id SERIAL PRIMARY KEY ,
    name VARCHAR(100) 
);

CREATE TABLE time_road(
    id SERIAL PRIMARY KEY ,
    id_quality INT REFERENCES quality (id) ON DELETE CASCADE ,
    id_type INT REFERENCES type (id) ON DELETE CASCADE ,
    time_m_square   NUMERIC(10)-- minute
);
--
CREATE TABLE size_road(
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) ,
);

CREATE TABLE road_type_quality (
    id SERIAL PRIMARY KEY ,
    id_type INT REFERENCES type (id) ON DELETE CASCADE ,
    id_quality INT REFERENCES quality (id) ON DELETE CASCADE ,
    id_size_road INT REFERENCES size_road (id) ON DELETE CASCADE 
);

CREATE TABLE road_materiaux(
    id SERIAL PRIMARY KEY, 
    id_road_type_quality INT REFERENCES road_type_quality(id) ON DELETE CASCADE ,
    id_materiaux INT REFERENCES materiaux (id) ON DELETE CASCADE ,
    quantity NUMERIC(10,2)
);



INSERT INTO materiaux (id, name) VALUES (100,'sable');
INSERT INTO materiaux (id, name) VALUES (101,'gravion');
INSERT INTO materiaux (id, name) VALUES (102,'goudron');
INSERT INTO materiaux (id, name) VALUES (103,'charbon');
INSERT INTO materiaux (id, name) VALUES (104,'bois');
INSERT INTO materiaux (id, name) VALUES (105,'vato be');

-- fasika goudron  => 


select materiaux.name,price_materiaux.price from materiaux join price_materiaux on materiaux.id = price_materiaux.id_materiaux ;