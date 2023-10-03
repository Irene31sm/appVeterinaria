USE veterinaria;

-- -------------------------------------------------------------------------------------------------------------------------

DELIMITER $$
CREATE PROCEDURE spu_registrar_mascota
(
	IN _idcliente 	INT,
	IN _idraza 		INT,
	IN _nombre 		VARCHAR(60),
	IN _fotografia VARCHAR(300),
	IN _color 		VARCHAR(30),
	IN _genero 		CHAR(1)
)
BEGIN
	IF _fotografia = "" THEN SET _fotografia = NULL; END IF;
	INSERT INTO mascotas (idcliente, idraza, nombre, fotografia, color, genero) VALUES 
	(_idcliente, _idraza, _nombre, _fotografia, _color, _genero);
END $$

CALL spu_registrar_mascota(2,5,"Dulce", "", "Marrón", "H");
-- -------------------------------------------------------------------------------------------------------------------------
DELIMITER $$
CREATE PROCEDURE spu_listar_mascotasClientes
(
	IN _idcliente 	INT
)
BEGIN
	SELECT mascotas.idmascota,clientes.nombres, clientes.apellidos, mascotas.nombre, mascotas.color, mascotas.genero
	FROM mascotas
	INNER JOIN clientes ON clientes.idcliente = mascotas.idcliente
	INNER JOIN razas ON razas.idraza = mascotas.idraza
	WHERE mascotas.idcliente = _idcliente;
END $$

-- -------------------------------------------------------------------------------------------------------------------------

DELIMITER $$
CREATE PROCEDURE spu_informacion_mascota
(
	IN _idmascota 	INT
)
BEGIN
	SELECT mascotas.idmascota,mascotas.nombre,razas.nombreRaza,animales.nombreamimal, mascotas.fotografia ,mascotas.color, mascotas.genero
	FROM mascotas
	INNER JOIN razas ON razas.idraza = mascotas.idraza
	INNER JOIN animales ON animales.idanimal = razas.idanimal
	WHERE mascotas.idmascota = _idmascota;
END $$

-- -------------------------------------------------------------------------------------------------------------------------------
DELIMITER $$
CREATE PROCEDURE spu_lista_animales()
BEGIN
	SELECT *
	FROM animales;
END $$

DELIMITER $$
CREATE PROCEDURE spu_filtro_raza(IN _idanimal INT)
BEGIN
	SELECT *
	FROM razas
	WHERE idanimal = _idanimal;
END $$
-- ------------------------------------------------------------------------------------------------------------------------------

DELIMITER $$
CREATE PROCEDURE spu_inicio_sesion
(
	IN _dni 	CHAR(8)
)
BEGIN
	SELECT *
	FROM clientes
	WHERE dni = _dni;
END $$

-- ---------------------------------------------------------------------
DELIMITER $$
CREATE PROCEDURE spu_listar_clientes()
BEGIN 
	SELECT idcliente, apellidos, nombres, dni 
	FROM clientes;
END $$

-- -------------------------------------------------------------------------------------------------------------------------
DELIMITER $$
CREATE PROCEDURE spu_registrar_cliente
(
	IN _apellidos 	VARCHAR(50),
	IN _nombres	VARCHAR(50),
	IN _dni 	CHAR(8),
	IN _claveacceso VARCHAR(200) 
)
BEGIN
	INSERT INTO clientes (apellidos, nombres, dni, claveacceso) VALUES 
	(_apellidos,_nombres,_dni,_claveacceso);
END $$

CALL spu_registrar_cliente("Suarez Matias", "Karen", "47671169", "123456");


