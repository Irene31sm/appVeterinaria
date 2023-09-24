CREATE DATABASE veterinaria;
USE veterinaria;

CREATE TABLE clientes 
(
	idcliente	INT AUTO_INCREMENT PRIMARY KEY,
	apellidos 	VARCHAR(50) 	NOT NULL,
	nombres		VARCHAR(50) 	NOT NULL,
	dni			CHAR(8) 			NOT NULL,
	claveacceso	VARCHAR(200) 	NOT NULL,
	create_at	DATETIME 		NOT NULL DEFAULT NOW(),
	update_at	DATETIME 		NULL,
	estado		CHAR(1)			NOT NULL DEFAULT "1",
	CONSTRAINT uk_dni_clientes UNIQUE (dni)
)ENGINE = INNODB;

CREATE TABLE animales 
(
	idanimal			INT AUTO_INCREMENT PRIMARY KEY,
	nombreamimal 	VARCHAR(50) 	NOT NULL,
	create_at		DATETIME 		NOT NULL DEFAULT NOW(),
	update_at		DATETIME  		NULL,
	estado			CHAR(1) 			NOT NULL DEFAULT "1",
	CONSTRAINT uk_nombreanimal_animales UNIQUE (nombreamimal)
)ENGINE = INNODB;

CREATE TABLE razas
(
	idraza			INT AUTO_INCREMENT PRIMARY KEY,
	idanimal 		INT 			NOT NULL,
	nombreRaza 		VARCHAR(60) NOT NULL,
	create_at		DATETIME 		NOT NULL DEFAULT NOW(),
	update_at		DATETIME  		NULL,
	estado			CHAR(1) 			NOT NULL DEFAULT "1",
	CONSTRAINT fk_idanimal_razas FOREIGN KEY (idanimal) REFERENCES animales(idanimal),
	CONSTRAINT uk_nombreRaza_razas UNIQUE (nombreRaza)
)ENGINE = INNODB;

SELECT * FROM razas;
CREATE TABLE mascotas
(
	idmascota	INT AUTO_INCREMENT PRIMARY KEY,
	idcliente	INT 		NOT NULL,
	idraza		INT 		NOT NULL,
	nombre		VARCHAR(60) 	NOT NULL,
	fotografia	VARCHAR(300)	NULL,
	color			VARCHAR(30)		NOT NULL,
	genero 		CHAR(1)			NOT NULL, -- M = MACHO - H = HEMBRA
	create_at		DATETIME 		NOT NULL DEFAULT NOW(),
	update_at		DATETIME  		NULL,
	estado			CHAR(1) 			NOT NULL DEFAULT "1",
	CONSTRAINT fk_idcliente_mascotas FOREIGN KEY (idcliente) REFERENCES clientes(idcliente),
	CONSTRAINT fk_idraza_mascotas FOREIGN KEY (idraza) REFERENCES razas(idraza)
)ENGINE = INNODB;

INSERT INTO clientes (apellidos, nombres, dni, claveacceso) VALUES 
("Suarez matias", "Irene Alejandra", "76364010", "123456"),
("Neyra Vilela", "Fabiola", "76364011", "123456"),
("Tasayco Pachas", "Kiara Yanina", "76364012", "123456");

INSERT INTO animales (nombreamimal) VALUES
("Perro"),
("Gato"),
("Conejo");

INSERT INTO razas (idanimal ,nombreRaza) VALUES
(1,"Husky siberiano"),
(1,"Labrador"),
(1,"perro Mestizo"),
(1,"Pastor alemán"),
(1,"Cocker"),
(1,"Golden Reatriever"),
(2,"Azul ruso"),
(2,"Americano Peludo"),
(2,"Bosque de noruega"),
(2,"Fold escocés"),
(2,"gato Mestizo"),
(3,"Enano"),
(3,"Belier"),
(3,"Cabeza de león");

INSERT INTO mascotas (idcliente, idraza, nombre, fotografia, color, genero) VALUES 
(1, 6, "Maya", "perro1.jpg", "Caramelo", "H"),
(1, 2, "Baely", "perro2.jpg", "Blanco", "M"),
(1, 11, "Micha", "gato1.jpg", "gris con blanco", "H"),
(2, 6, "Danger", "", "Negro", "M");

SELECT * FROM clientes;
SELECT * FROM animales;
SELECT * FROM razas;
SELECT * FROM mascotas;

SELECT mascotas.idmascota,clientes.nombres, clientes.apellidos, mascotas.nombre, mascotas.color, mascotas.genero
FROM mascotas
INNER JOIN clientes ON clientes.idcliente = mascotas.idcliente
INNER JOIN razas ON razas.idraza = mascotas.idraza
WHERE mascotas.idcliente = 2;

SELECT mascotas.idmascota,mascotas.nombre,animales.nombreamimal ,mascotas.color, mascotas.genero
FROM mascotas
INNER JOIN razas ON razas.idraza = mascotas.idraza
INNER JOIN animales ON animales.idanimal = razas.idanimal
WHERE mascotas.idmascota = 1;
