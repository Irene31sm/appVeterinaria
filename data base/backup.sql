/*
SQLyog Ultimate v12.5.1 (64 bit)
MySQL - 10.4.25-MariaDB : Database - veterinaria
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`veterinaria` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

USE `veterinaria`;

/*Table structure for table `animales` */

DROP TABLE IF EXISTS `animales`;

CREATE TABLE `animales` (
  `idanimal` int(11) NOT NULL AUTO_INCREMENT,
  `nombreamimal` varchar(50) NOT NULL,
  `create_at` datetime NOT NULL DEFAULT current_timestamp(),
  `update_at` datetime DEFAULT NULL,
  `estado` char(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`idanimal`),
  UNIQUE KEY `uk_nombreanimal_animales` (`nombreamimal`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4;

/*Data for the table `animales` */

insert  into `animales`(`idanimal`,`nombreamimal`,`create_at`,`update_at`,`estado`) values 
(1,'Perro','2023-09-23 21:39:58',NULL,'1'),
(2,'Gato','2023-09-23 21:39:58',NULL,'1'),
(3,'Conejo','2023-09-23 21:39:58',NULL,'1');

/*Table structure for table `clientes` */

DROP TABLE IF EXISTS `clientes`;

CREATE TABLE `clientes` (
  `idcliente` int(11) NOT NULL AUTO_INCREMENT,
  `apellidos` varchar(50) NOT NULL,
  `nombres` varchar(50) NOT NULL,
  `dni` char(8) NOT NULL,
  `claveacceso` varchar(200) NOT NULL,
  `create_at` datetime NOT NULL DEFAULT current_timestamp(),
  `update_at` datetime DEFAULT NULL,
  `estado` char(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`idcliente`),
  UNIQUE KEY `uk_dni_clientes` (`dni`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4;

/*Data for the table `clientes` */

insert  into `clientes`(`idcliente`,`apellidos`,`nombres`,`dni`,`claveacceso`,`create_at`,`update_at`,`estado`) values 
(1,'Suarez matias','Irene Alejandra','76364010','$2y$10$NuVfjFmtw0u.cZWMs6fwruByjIfP1KnR2Pmov5PWksjJCwYpFiCLm','2023-09-23 21:39:58',NULL,'1'),
(2,'Neyra Vilela','Fabiola','76364011','$2y$10$hyjexLEn0NQa0JKFvfSOue.8BEWPjLqvA7guLdvFfEFZDezRqSxxm','2023-09-23 21:39:58',NULL,'1'),
(3,'Tasayco Pachas','Kiara Yanina','76364012','$2y$10$fjNV5nIV3S25m7nieE72ouEIv.ZuE4g3bSOrIXrlwspaDZqkYzHCe','2023-09-23 21:39:58',NULL,'1'),
(4,'Suarez Matias','Karen','47671169','$2y$10$OQUOTHxHxO3B08KZ4CDfFeTlITflvGOl3rVXhRCODE9YnC7wKYc86','2023-09-23 23:59:17',NULL,'1'),
(5,'Hernandez Gomez','Geral','47671170','$2y$10$wDbvyHU5gGLb9zO3bGKIn.tX1ZeT5K5YaMPF3PJ4CsX611979/Pba','2023-09-24 00:11:10',NULL,'1'),
(6,'Cortez Matias','Milagros','12345678','$2y$10$f.Bpp23k/kG6Uit.8WsUnue4hmI9RjJoxpUzM9gHxfcswLskIlMdm','2023-09-24 00:39:38',NULL,'1'),
(7,'Pachas Atuncar','Andrea','09876543','$2y$10$sQrYyt1KlqQWpt5FJzf5yOTbIKkp5FIlYHfQethCSTxI6ALifAq1q','2023-09-24 13:52:22',NULL,'1'),
(8,'Ramos de la cruz','Julia','76364020','$2y$10$AqCn7TvIFlw5NWjVPmQndOyojpx.XTPAP2U4FldQeCjdZr4Q/PcXK','2023-09-25 01:59:11',NULL,'1'),
(9,'Quispe Rojas','Joseph','76364030','$2y$10$1WEGbxpeuwdkvVSDjf3Xbu05qsZSh08hHZ39bXMUlF1ymGRYKZ/42','2023-09-25 02:00:58',NULL,'1');

/*Table structure for table `mascotas` */

DROP TABLE IF EXISTS `mascotas`;

CREATE TABLE `mascotas` (
  `idmascota` int(11) NOT NULL AUTO_INCREMENT,
  `idcliente` int(11) NOT NULL,
  `idraza` int(11) NOT NULL,
  `nombre` varchar(60) NOT NULL,
  `fotografia` varchar(300) DEFAULT NULL,
  `color` varchar(30) NOT NULL,
  `genero` char(1) NOT NULL,
  `create_at` datetime NOT NULL DEFAULT current_timestamp(),
  `update_at` datetime DEFAULT NULL,
  `estado` char(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`idmascota`),
  KEY `fk_idcliente_mascotas` (`idcliente`),
  KEY `fk_idraza_mascotas` (`idraza`),
  CONSTRAINT `fk_idcliente_mascotas` FOREIGN KEY (`idcliente`) REFERENCES `clientes` (`idcliente`),
  CONSTRAINT `fk_idraza_mascotas` FOREIGN KEY (`idraza`) REFERENCES `razas` (`idraza`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4;

/*Data for the table `mascotas` */

insert  into `mascotas`(`idmascota`,`idcliente`,`idraza`,`nombre`,`fotografia`,`color`,`genero`,`create_at`,`update_at`,`estado`) values 
(1,1,6,'Maya','perro1.jpg','Caramelo','H','2023-09-23 21:39:58',NULL,'1'),
(2,1,2,'Baely','perro2.jpg','Blanco','M','2023-09-23 21:39:58',NULL,'1'),
(3,1,11,'Micha','gato1.jpg','gris con blanco','H','2023-09-23 21:39:58',NULL,'1'),
(4,2,6,'Danger',NULL,'Negro','M','2023-09-23 21:39:58',NULL,'1'),
(5,2,5,'Dulce',NULL,'Marrón','H','2023-09-23 21:40:31',NULL,'1'),
(6,1,9,'Paco',NULL,'crema','M','2023-09-25 03:03:10',NULL,'1');

/*Table structure for table `razas` */

DROP TABLE IF EXISTS `razas`;

CREATE TABLE `razas` (
  `idraza` int(11) NOT NULL AUTO_INCREMENT,
  `idanimal` int(11) NOT NULL,
  `nombreRaza` varchar(60) NOT NULL,
  `create_at` datetime NOT NULL DEFAULT current_timestamp(),
  `update_at` datetime DEFAULT NULL,
  `estado` char(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`idraza`),
  UNIQUE KEY `uk_nombreRaza_razas` (`nombreRaza`),
  KEY `fk_idanimal_razas` (`idanimal`),
  CONSTRAINT `fk_idanimal_razas` FOREIGN KEY (`idanimal`) REFERENCES `animales` (`idanimal`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4;

/*Data for the table `razas` */

insert  into `razas`(`idraza`,`idanimal`,`nombreRaza`,`create_at`,`update_at`,`estado`) values 
(1,1,'Husky siberiano','2023-09-23 21:39:58',NULL,'1'),
(2,1,'Labrador','2023-09-23 21:39:58',NULL,'1'),
(3,1,'perro Mestizo','2023-09-23 21:39:58',NULL,'1'),
(4,1,'Pastor alemán','2023-09-23 21:39:58',NULL,'1'),
(5,1,'Cocker','2023-09-23 21:39:58',NULL,'1'),
(6,1,'Golden Reatriever','2023-09-23 21:39:58',NULL,'1'),
(7,2,'Azul ruso','2023-09-23 21:39:58',NULL,'1'),
(8,2,'Americano Peludo','2023-09-23 21:39:58',NULL,'1'),
(9,2,'Bosque de noruega','2023-09-23 21:39:58',NULL,'1'),
(10,2,'Fold escocés','2023-09-23 21:39:58',NULL,'1'),
(11,2,'gato Mestizo','2023-09-23 21:39:58',NULL,'1'),
(12,3,'Enano','2023-09-23 21:39:58',NULL,'1'),
(13,3,'Belier','2023-09-23 21:39:58',NULL,'1'),
(14,3,'Cabeza de león','2023-09-23 21:39:58',NULL,'1');

/* Procedure structure for procedure `spu_filtro_raza` */

/*!50003 DROP PROCEDURE IF EXISTS  `spu_filtro_raza` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `spu_filtro_raza`(in _idanimal int)
BEGIN
	SELECT *
	FROM razas
	where idanimal = _idanimal;
END */$$
DELIMITER ;

/* Procedure structure for procedure `spu_informacion_mascota` */

/*!50003 DROP PROCEDURE IF EXISTS  `spu_informacion_mascota` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `spu_informacion_mascota`(
	IN _idmascota 	INT
)
BEGIN
	SELECT mascotas.idmascota,mascotas.nombre,razas.nombreRaza,animales.nombreamimal, mascotas.fotografia ,mascotas.color, mascotas.genero
	FROM mascotas
	INNER JOIN razas ON razas.idraza = mascotas.idraza
	INNER JOIN animales ON animales.idanimal = razas.idanimal
	WHERE mascotas.idmascota = _idmascota;
END */$$
DELIMITER ;

/* Procedure structure for procedure `spu_inicio_sesion` */

/*!50003 DROP PROCEDURE IF EXISTS  `spu_inicio_sesion` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `spu_inicio_sesion`(
	IN _dni 	CHAR(8)
)
BEGIN
	SELECT *
	FROM clientes
	WHERE dni = _dni;
END */$$
DELIMITER ;

/* Procedure structure for procedure `spu_listar_mascotasClientes` */

/*!50003 DROP PROCEDURE IF EXISTS  `spu_listar_mascotasClientes` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `spu_listar_mascotasClientes`(
	IN _idcliente 	INT
)
BEGIN
	SELECT mascotas.idmascota,clientes.nombres, clientes.apellidos, mascotas.nombre, mascotas.color, mascotas.genero
	FROM mascotas
	INNER JOIN clientes ON clientes.idcliente = mascotas.idcliente
	INNER JOIN razas ON razas.idraza = mascotas.idraza
	WHERE mascotas.idcliente = _idcliente;
END */$$
DELIMITER ;

/* Procedure structure for procedure `spu_lista_animales` */

/*!50003 DROP PROCEDURE IF EXISTS  `spu_lista_animales` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `spu_lista_animales`()
BEGIN
	SELECT *
	FROM animales;
END */$$
DELIMITER ;

/* Procedure structure for procedure `spu_registrar_cliente` */

/*!50003 DROP PROCEDURE IF EXISTS  `spu_registrar_cliente` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `spu_registrar_cliente`(
	IN _apellidos 	VARCHAR(50),
	in _nombres	VARCHAR(50),
	in _dni 	CHAR(8),
	in _claveacceso VARCHAR(200) 
)
BEGIN
	INSERT INTO clientes (apellidos, nombres, dni, claveacceso) VALUES 
	(_apellidos,_nombres,_dni,_claveacceso);
END */$$
DELIMITER ;

/* Procedure structure for procedure `spu_registrar_mascota` */

/*!50003 DROP PROCEDURE IF EXISTS  `spu_registrar_mascota` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `spu_registrar_mascota`(
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
END */$$
DELIMITER ;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
