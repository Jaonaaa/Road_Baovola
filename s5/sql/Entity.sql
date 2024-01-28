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


CREATE TABLE supplier (
    id SERIAL PRIMARY KEY,
    name VARCHAR(120) NOT NULL
);

CREATE TABLE stock (
    id SERIAL PRIMARY KEY,
    id_materiaux INT REFERENCES materiaux (id) ON DELETE CASCADE ,
    quantity NUMERIC(10,2)
);

CREATE TABLE movement (
    id SERIAL PRIMARY KEY,
    id_materiaux INT REFERENCES meteriaux (id) ON DELETE CASCADE ,
    quantity NUMERIC(10,2),
    status_movement VARCHAR(50)-- in || out  ,
    date TIMESTAMP
);


CREATE TABLE achat (
    id SERIAL PRIMARY KEY,
    id_supplier INT REFERENCES supplier (id) ON DELETE CASCADE ,
    id_materiaux  INT REFERENCES materiaux (id) ON DELETE CASCADE ,
    id_movement INT REFERENCES movement (id) ON DELETE CASCADE
    quantity NUMERIC(10,2)
);




CREATE TABLE outils ( -- employé 
    id SERIAL PRIMARY KEY,
    name VARCHAR(150) ,
    salaire NUMERIC(10,2) -- par heure
)

CREATE TABLE price_vente(
    id SERIAL PRIMARY KEY,
    id_road_type_quality INT REFERENCES road_type_quality (id) ON DELETE CASCADE ,
    price NUMERIC(10,2)
)

CREATE TABLE outils_by_size (
    id SERIAL PRIMARY KEY,
    id_size_road INT REFERENCES road_size (id) ON DELETE CASCADE,
    quantity NUMERIC(10)
);

CREATE TABLE time_by_quality (
    id SERIAL PRIMARY KEY,
    id_quality INT REFERENCES quality (id) ON DELETE CASCADE ,
    time_to_work NUMERIC(10,2)
);

CREATE TABLE main_ouevre (
    id SERIAL PRIMARY KEY,
    id_road_type_quality INT REFERENCES road_type_quality (id) ON DELETE CASCADE ,
    id_outils INT REFERENCES main_ouevre outils (id) ON DELETE CASCADE ,
    time_to_work NUMERIC(10,2)
);



CREATE TABLE employer (
    id SERIAL PRIMARY KEY,
    id_outils INT REFERENCES main_ouevre outils (id) ON DELETE CASCADE ,
    date_embauche TIMESTAMP,
)

CREATE TABLE grade (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    upgrade_salaire NUMERIC(10,2) NOT NULL, // fois
    upgrade_after NUMERIC(10,2) NOT NULL -- year
)



CREATE TABLE type_entreprise (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
)

INSERT INTO type_entreprise (id,name) VALUES (2000,'Privé');
INSERT INTO type_entreprise (id,name) VALUES (2001,'Public');


CREATE TABLE entreprise (
    id SERIAL PRIMARY KEY,
        name VARCHAR(255) NOT NULL
    id_type_entreprise INT REFERENCES type_entreprise (id) ON DELETE CASCADE ,
)

INSERT INTO entreprise (id,name,id_type_entreprise) VALUES (2000,'Nanisana ',2001);
INSERT INTO entreprise (id,name,id_type_entreprise) VALUES (2001,'Elbo',2000);


CREATE TABLE vente (
    id SERIAL PRIMARY KEY,
    id_road_type_quality INT REFERENCES road_type_quality (id) ON DELETE CASCADE ,
    quantity NUMERIC(10,2),
    price NUMERIC(10,2) ,
    id_entreprise INT REFERENCES entreprise (id) ON DELETE CASCADE ,
    date TIMESTAMP
);
delete from employer;
delete from grade;

( select 
road_type_quality.id_type ,
road_type_quality.id_quality ,
road_type_quality.id_size_road ,
road_materiaux.id_road_type_quality,
road_materiaux.id_materiaux,
road_materiaux.quantity
 from road_type_quality join road_materiaux on road_type_quality.id = road_materiaux.id_road_type_quality ) s ;


CREATE OR REPLACE view v_price_road as SELECT SUM(p.price_materiaux) price ,p.id_road_type_quality 
    FROM ( SELECT 
                SUM(price_materiaux.price*road_materiaux.quantity) price_materiaux ,road_materiaux.id_materiaux ,
                road_materiaux.id_road_type_quality
            FROM road_materiaux JOIN price_materiaux 
            ON road_materiaux.id_materiaux = price_materiaux.id_materiaux 
            GROUP BY road_materiaux.id_materiaux , road_materiaux.id_road_type_quality 
            ORDER BY road_materiaux.id_road_type_quality ) 
    as p  
        GROUP BY p.id_road_type_quality;

SELECT * FROM v_price_road JOIN road_type_quality on v_price_road.id_road_type_quality = road_type_quality.id ;


INSERT INTO materiaux (id, name) VALUES (100,'sable');
INSERT INTO materiaux (id, name) VALUES (101,'gravion');
INSERT INTO materiaux (id, name) VALUES (102,'goudron');
INSERT INTO materiaux (id, name) VALUES (103,'charbon');
INSERT INTO materiaux (id, name) VALUES (104,'bois');
INSERT INTO materiaux (id, name) VALUES (105,'vato be');

-- fasika goudron  => 


select materiaux.name,price_materiaux.price from materiaux join price_materiaux on materiaux.id = price_materiaux.id_materiaux ;