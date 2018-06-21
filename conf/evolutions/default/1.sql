# CARRO schema

# --- !Ups
CREATE TABLE CARRO (
    PLACA varchar(255) NOT NULL,
    MARCA varchar(255) NOT NULL,
    MODELO numeric(10) NOT NULL,
    PRIMARY KEY (PLACA)
);

# --- !Downs
DROP TABLE CARRO;

