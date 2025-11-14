-- MySQL dump 10.13  Distrib 8.0.43, for Win64 (x86_64)
--
-- Host: localhost    Database: gaaf
-- ------------------------------------------------------
-- Server version	8.0.43

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `bodega`
--

DROP TABLE IF EXISTS `bodega`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bodega` (
  `idBodega` int NOT NULL AUTO_INCREMENT,
  `lugar` varchar(100) NOT NULL,
  `movimiento` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`idBodega`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bodega`
--

LOCK TABLES `bodega` WRITE;
/*!40000 ALTER TABLE `bodega` DISABLE KEYS */;
INSERT INTO `bodega` VALUES (1,'Bodega Norte',1),(2,'Bodega Sur',0),(3,'Bodega Centro',1),(4,'Bodega Oriental',0),(5,'Bodega Occidental',1);
/*!40000 ALTER TABLE `bodega` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `inventario`
--

DROP TABLE IF EXISTS `inventario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `inventario` (
  `idInventario` int NOT NULL AUTO_INCREMENT,
  `idPedido` int NOT NULL,
  `idBodega` int NOT NULL,
  `cantidadTotal` float DEFAULT '0',
  `cantidadPorBodega` float DEFAULT '0',
  PRIMARY KEY (`idInventario`),
  KEY `idPedido` (`idPedido`),
  KEY `idBodega` (`idBodega`),
  CONSTRAINT `inventario_ibfk_1` FOREIGN KEY (`idPedido`) REFERENCES `pedido` (`idPedido`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `inventario_ibfk_2` FOREIGN KEY (`idBodega`) REFERENCES `bodega` (`idBodega`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `inventario`
--

LOCK TABLES `inventario` WRITE;
/*!40000 ALTER TABLE `inventario` DISABLE KEYS */;
INSERT INTO `inventario` VALUES (1,1,1,100,50),(2,2,2,50,25),(3,3,3,75,40),(4,4,4,60,30),(5,5,5,200,100);
/*!40000 ALTER TABLE `inventario` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `trg_actualizar_inventario` AFTER INSERT ON `inventario` FOR EACH ROW BEGIN
    UPDATE bodega
    SET movimiento = TRUE
    WHERE idBodega = NEW.idBodega;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `pedido`
--

DROP TABLE IF EXISTS `pedido`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pedido` (
  `idPedido` int NOT NULL AUTO_INCREMENT,
  `idProveedor` int NOT NULL,
  `idUsuario` int NOT NULL,
  `fechaEntrega` date DEFAULT NULL,
  `cantidadTotal` int DEFAULT '0',
  `fechaCreacion` date NOT NULL,
  `pesoTotal` float DEFAULT '0',
  `recibido` tinyint(1) DEFAULT '0',
  `observaciones` text,
  PRIMARY KEY (`idPedido`),
  KEY `idProveedor` (`idProveedor`),
  KEY `idUsuario` (`idUsuario`),
  CONSTRAINT `pedido_ibfk_1` FOREIGN KEY (`idProveedor`) REFERENCES `proveedor` (`idProveedor`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `pedido_ibfk_2` FOREIGN KEY (`idUsuario`) REFERENCES `usuario` (`idUsuario`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pedido`
--

LOCK TABLES `pedido` WRITE;
/*!40000 ALTER TABLE `pedido` DISABLE KEYS */;
INSERT INTO `pedido` VALUES (1,1,3,'2025-10-01',100,'2025-09-25',250.5,1,'Pedido recibido completo'),(2,2,3,'2025-10-02',50,'2025-09-26',120,0,'Pendiente entrega parcial'),(3,3,3,'2025-10-03',75,'2025-09-27',180,1,'Recibido sin novedades'),(4,4,3,'2025-10-04',60,'2025-09-28',150.2,0,'Pendiente revisión'),(5,5,3,'2025-10-05',200,'2025-09-29',500,1,'Entrega rápida'),(6,6,3,'2025-10-06',90,'2025-09-30',210,1,'Recibido a tiempo'),(7,7,3,'2025-10-07',120,'2025-09-30',300,0,'Pendiente recepción'),(8,8,3,'2025-10-08',40,'2025-09-30',100.5,1,'Recibido incompleto'),(9,9,3,'2025-10-09',55,'2025-09-30',130.3,0,'Pendiente confirmar calidad'),(10,10,3,'2025-10-10',150,'2025-09-30',400,1,'Entrega en óptimas condiciones');
/*!40000 ALTER TABLE `pedido` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pedido_producto`
--

DROP TABLE IF EXISTS `pedido_producto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pedido_producto` (
  `idPedido` int NOT NULL,
  `idProducto` int NOT NULL,
  `cantidad` int NOT NULL,
  PRIMARY KEY (`idPedido`,`idProducto`),
  KEY `idProducto` (`idProducto`),
  CONSTRAINT `pedido_producto_ibfk_1` FOREIGN KEY (`idPedido`) REFERENCES `pedido` (`idPedido`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `pedido_producto_ibfk_2` FOREIGN KEY (`idProducto`) REFERENCES `producto` (`idCodigo`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `pedido_producto_chk_1` CHECK ((`cantidad` > 0))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pedido_producto`
--

LOCK TABLES `pedido_producto` WRITE;
/*!40000 ALTER TABLE `pedido_producto` DISABLE KEYS */;
INSERT INTO `pedido_producto` VALUES (1,1,20),(2,2,15),(3,3,10),(4,4,25),(5,5,30),(6,6,12),(7,7,18),(8,8,22),(9,9,16),(10,10,20);
/*!40000 ALTER TABLE `pedido_producto` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `trg_actualizar_pedido` AFTER INSERT ON `pedido_producto` FOR EACH ROW BEGIN
    UPDATE pedido
    SET 
        cantidadTotal = (
            SELECT SUM(pp.cantidad)
            FROM pedido_producto pp
            WHERE pp.idPedido = NEW.idPedido
        ),
        pesoTotal = (
            SELECT SUM(pp.cantidad * pr.peso)
            FROM pedido_producto pp
            JOIN producto pr ON pr.idProducto = pp.idProducto
            WHERE pp.idPedido = NEW.idPedido
        )
    WHERE idPedido = NEW.idPedido;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `producto`
--

DROP TABLE IF EXISTS `producto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `producto` (
  `idCodigo` int NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(150) NOT NULL,
  `peso` float DEFAULT NULL,
  `estado` varchar(50) DEFAULT NULL,
  `humedad` float DEFAULT NULL,
  `fermentacion` float DEFAULT NULL,
  PRIMARY KEY (`idCodigo`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `producto`
--

LOCK TABLES `producto` WRITE;
/*!40000 ALTER TABLE `producto` DISABLE KEYS */;
INSERT INTO `producto` VALUES (1,'Cacao tipo A',25,'Bueno',7.5,65),(2,'Cacao tipo B',30,'Excelente',6,70),(3,'Cacao tipo C',28,'Regular',8,55),(4,'Cacao tipo D',40,'Bueno',7.2,68),(5,'Cacao tipo E',35,'Excelente',5.5,72),(6,'Cacao tipo F',32,'Malo',9,50),(7,'Cacao tipo G',29,'Bueno',6.8,66),(8,'Cacao tipo H',31,'Excelente',5,75),(9,'Cacao tipo I',33,'Bueno',7,67),(10,'Cacao tipo J',27,'Regular',8.5,60);
/*!40000 ALTER TABLE `producto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `proveedor`
--

DROP TABLE IF EXISTS `proveedor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `proveedor` (
  `idProveedor` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100) NOT NULL,
  `direccion` varchar(150) DEFAULT NULL,
  `telefono` varchar(20) DEFAULT NULL,
  `correo` varchar(100) DEFAULT NULL,
  `ciudad` varchar(100) DEFAULT NULL,
  `vereda` varchar(100) DEFAULT NULL,
  `observaciones` text,
  `activo` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`idProveedor`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `proveedor`
--

LOCK TABLES `proveedor` WRITE;
/*!40000 ALTER TABLE `proveedor` DISABLE KEYS */;
INSERT INTO `proveedor` VALUES (1,'Proveedor A','Calle 10 #12-34','3001112233','provA@email.com','Bogotá','Vereda Norte','Entrega puntual',1),(2,'Proveedor B','Carrera 15 #22-45','3102223344','provB@email.com','Medellín','Vereda Sur','Especialista en cacao fino',1),(3,'Proveedor C','Av 30 #45-67','3203334455','provC@email.com','Cali','Vereda Centro','Provee en grandes cantidades',1),(4,'Proveedor D','Cl 50 #10-12','3114445566','provD@email.com','Bucaramanga','Vereda Alta','Requiere confirmación de pago',1),(5,'Proveedor E','Cra 7 #77-20','3125556677','provE@email.com','Barranquilla','Vereda Oriental','Buen historial de entregas',1),(6,'Proveedor F','Cl 12 #33-12','3136667788','provF@email.com','Cartagena','Vereda Occidental','Calidad variable',1),(7,'Proveedor G','Cl 100 #20-90','3147778899','provG@email.com','Santa Marta','Vereda Norte','Siempre entrega antes de tiempo',1),(8,'Proveedor H','Cra 30 #40-55','3158889900','provH@email.com','Pereira','Vereda Sur','Entrega incompleta en ocasiones',1),(9,'Proveedor I','Cl 23 #18-60','3169990011','provI@email.com','Cúcuta','Vereda Centro','Nuevo proveedor',1),(10,'Proveedor J','Cra 50 #60-70','3170001122','provJ@email.com','Manizales','Vereda Oriental','Muy buena calidad',1);
/*!40000 ALTER TABLE `proveedor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuario` (
  `idUsuario` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100) NOT NULL,
  `contrasena` varchar(255) NOT NULL,
  `rol` enum('Admin','Operario','Coordinador','JefeBodega') NOT NULL,
  `activo` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`idUsuario`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` VALUES (1,'Administrador General','admin123','Admin',1),(2,'Carlos Rojas','operario2025','Operario',1),(3,'Laura Gómez','coord2025','Coordinador',1),(4,'Javier Martínez','bodega2025','JefeBodega',1);
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary view structure for view `v_inventario`
--

DROP TABLE IF EXISTS `v_inventario`;
/*!50001 DROP VIEW IF EXISTS `v_inventario`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `v_inventario` AS SELECT 
 1 AS `idInventario`,
 1 AS `idPedido`,
 1 AS `idBodega`,
 1 AS `cantidadTotal`,
 1 AS `cantidadPorBodega`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `v_pedidos_pendientes`
--

DROP TABLE IF EXISTS `v_pedidos_pendientes`;
/*!50001 DROP VIEW IF EXISTS `v_pedidos_pendientes`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `v_pedidos_pendientes` AS SELECT 
 1 AS `idPedido`,
 1 AS `idProveedor`,
 1 AS `fechaEntrega`,
 1 AS `cantidadTotal`,
 1 AS `fechaCreacion`,
 1 AS `pesoTotal`,
 1 AS `recibido`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `v_proveedores_incompletos`
--

DROP TABLE IF EXISTS `v_proveedores_incompletos`;
/*!50001 DROP VIEW IF EXISTS `v_proveedores_incompletos`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `v_proveedores_incompletos` AS SELECT 
 1 AS `idProveedor`,
 1 AS `nombre`,
 1 AS `observaciones`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `v_resumen_pedidos_pendientes`
--

DROP TABLE IF EXISTS `v_resumen_pedidos_pendientes`;
/*!50001 DROP VIEW IF EXISTS `v_resumen_pedidos_pendientes`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `v_resumen_pedidos_pendientes` AS SELECT 
 1 AS `pendientes`,
 1 AS `total_kg`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `v_total_pedido_producto`
--

DROP TABLE IF EXISTS `v_total_pedido_producto`;
/*!50001 DROP VIEW IF EXISTS `v_total_pedido_producto`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `v_total_pedido_producto` AS SELECT 
 1 AS `total`*/;
SET character_set_client = @saved_cs_client;

--
-- Dumping events for database 'gaaf'
--

--
-- Dumping routines for database 'gaaf'
--
/*!50003 DROP PROCEDURE IF EXISTS `sp_insertar_bodega` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_insertar_bodega`(
    IN p_lugar VARCHAR(100),
    IN p_movimiento BOOLEAN
)
BEGIN
    INSERT INTO bodega (lugar, movimiento)
    VALUES (p_lugar, p_movimiento);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_insertar_inventario` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_insertar_inventario`(
    IN p_idPedido INT,
    IN p_idBodega INT,
    IN p_cantidadTotal FLOAT,
    IN p_cantidadPorBodega FLOAT
)
BEGIN
    INSERT INTO inventario (idPedido, idBodega, cantidadTotal, cantidadPorBodega)
    VALUES (p_idPedido, p_idBodega, p_cantidadTotal, p_cantidadPorBodega);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_insertar_pedido` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_insertar_pedido`(
    IN p_idProveedor INT,
    IN p_idUsuario INT,
    IN p_fechaEntrega DATE,
    IN p_fechaCreacion DATE,
    IN p_observaciones TEXT,
    IN p_recibido BOOLEAN
)
BEGIN
    INSERT INTO pedido (idProveedor, idUsuario, fechaEntrega, fechaCreacion, observaciones, recibido)
    VALUES (p_idProveedor, p_idUsuario, p_fechaEntrega, p_fechaCreacion, p_observaciones, p_recibido);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_insertar_pedido_producto` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_insertar_pedido_producto`(
    IN p_idPedido INT,
    IN p_idProducto INT,
    IN p_cantidad INT
)
BEGIN
    INSERT INTO pedido_producto (idPedido, idProducto, cantidad)
    VALUES (p_idPedido, p_idProducto, p_cantidad);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_insertar_producto` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_insertar_producto`(
    IN p_descripcion VARCHAR(150),
    IN p_peso FLOAT,
    IN p_estado VARCHAR(50),
    IN p_humedad FLOAT,
    IN p_fermentacion FLOAT,
    IN p_idPedido INT
)
BEGIN
    INSERT INTO producto (descripcion, peso, estado, humedad, fermentacion, idPedido)
    VALUES (p_descripcion, p_peso, p_estado, p_humedad, p_fermentacion, p_idPedido);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_insertar_proveedor` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_insertar_proveedor`(
    IN p_nombre VARCHAR(100),
    IN p_direccion VARCHAR(150),
    IN p_telefono VARCHAR(20),
    IN p_correo VARCHAR(100),
    IN p_ciudad VARCHAR(100),
    IN p_vereda VARCHAR(100),
    IN p_observaciones TEXT,
    IN p_activo BOOLEAN
)
BEGIN
    INSERT INTO proveedor (nombre, direccion, telefono, correo, ciudad, vereda, observaciones, activo)
    VALUES (p_nombre, p_direccion, p_telefono, p_correo, p_ciudad, p_vereda, p_observaciones, p_activo);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_insertar_usuario` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_insertar_usuario`(
    IN p_nombre VARCHAR(100),
    IN p_contrasena VARCHAR(255),
    IN p_rol ENUM('Admin', 'Operario', 'Coordinador', 'JefeBodega'),
    IN p_activo BOOLEAN
)
BEGIN
    INSERT INTO usuario (nombre, contrasena, rol, activo)
    VALUES (p_nombre, p_contrasena, p_rol, p_activo);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Final view structure for view `v_inventario`
--

/*!50001 DROP VIEW IF EXISTS `v_inventario`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `v_inventario` AS select `inventario`.`idInventario` AS `idInventario`,`inventario`.`idPedido` AS `idPedido`,`inventario`.`idBodega` AS `idBodega`,`inventario`.`cantidadTotal` AS `cantidadTotal`,`inventario`.`cantidadPorBodega` AS `cantidadPorBodega` from `inventario` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `v_pedidos_pendientes`
--

/*!50001 DROP VIEW IF EXISTS `v_pedidos_pendientes`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `v_pedidos_pendientes` AS select `pedido`.`idPedido` AS `idPedido`,`pedido`.`idProveedor` AS `idProveedor`,`pedido`.`fechaEntrega` AS `fechaEntrega`,`pedido`.`cantidadTotal` AS `cantidadTotal`,`pedido`.`fechaCreacion` AS `fechaCreacion`,`pedido`.`pesoTotal` AS `pesoTotal`,`pedido`.`recibido` AS `recibido` from `pedido` where (`pedido`.`recibido` = 0) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `v_proveedores_incompletos`
--

/*!50001 DROP VIEW IF EXISTS `v_proveedores_incompletos`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `v_proveedores_incompletos` AS select `proveedor`.`idProveedor` AS `idProveedor`,`proveedor`.`nombre` AS `nombre`,`proveedor`.`observaciones` AS `observaciones` from `proveedor` where (lower(`proveedor`.`observaciones`) like '%incomplet%') */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `v_resumen_pedidos_pendientes`
--

/*!50001 DROP VIEW IF EXISTS `v_resumen_pedidos_pendientes`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `v_resumen_pedidos_pendientes` AS select count(0) AS `pendientes`,coalesce(sum(`v_pedidos_pendientes`.`pesoTotal`),0) AS `total_kg` from `v_pedidos_pendientes` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `v_total_pedido_producto`
--

/*!50001 DROP VIEW IF EXISTS `v_total_pedido_producto`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `v_total_pedido_producto` AS select coalesce(sum(`pedido_producto`.`cantidad`),0) AS `total` from `pedido_producto` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-11-14 18:52:04
