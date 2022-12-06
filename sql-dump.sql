CREATE DATABASE  IF NOT EXISTS `dropwizard_form` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `dropwizard_form`;
-- MySQL dump 10.13  Distrib 8.0.30, for macos12 (x86_64)
--
-- Host: localhost    Database: dropwizard_form
-- ------------------------------------------------------
-- Server version	8.0.30

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `sectors`
--

DROP TABLE IF EXISTS `sectors`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sectors` (
  `sectors_id` bigint NOT NULL AUTO_INCREMENT,
  `parent_sectors_id` bigint DEFAULT NULL,
  `api_id` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `level` int NOT NULL,
  `sector_value` varchar(128) COLLATE utf8mb4_unicode_ci NOT NULL,
  `modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `ux_order` int NOT NULL,
  PRIMARY KEY (`sectors_id`)
) ENGINE=InnoDB AUTO_INCREMENT=82 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sectors`
--

LOCK TABLES `sectors` WRITE;
/*!40000 ALTER TABLE `sectors` DISABLE KEYS */;
INSERT INTO `sectors` VALUES (1,NULL,'1',1,'Manufacturing','2022-10-02 19:33:15',1),(2,1,'11',2,'Construction materials','2022-10-02 19:33:15',2),(3,1,'12',2,'Electronics and Optics','2022-10-02 19:33:15',3),(4,1,'13',2,'Food and Beverage','2022-10-02 19:33:15',4),(5,13,'131',3,'Bakery and confectionery products','2022-10-02 19:33:15',5),(6,13,'132',3,'Beverages','2022-10-02 19:33:15',6),(7,13,'133',3,'Fish and fish products','2022-10-02 19:33:15',7),(8,13,'134',3,'Meat and meat products','2022-10-02 19:33:15',8),(9,13,'135',3,'Milk and dairy products','2022-10-02 19:33:15',9),(10,13,'136',3,'Other','2022-10-02 19:33:15',10),(11,13,'137',3,'Sweets and snack food','2022-10-02 19:33:15',11),(12,1,'14',2,'Furniture','2022-10-02 19:33:15',12),(13,14,'141',3,'Bathroom/sauna','2022-10-02 19:33:15',13),(14,14,'142',3,'Bedroom','2022-10-02 19:33:15',14),(15,14,'143',3,'Children’s room','2022-10-02 19:33:15',15),(16,14,'144',3,'Kitchen','2022-10-02 19:33:15',16),(17,14,'145',3,'Living room','2022-10-02 19:33:15',17),(18,14,'146',3,'Office','2022-10-02 19:33:15',18),(19,14,'147',3,'Other (Furniture)','2022-10-02 19:33:15',19),(20,14,'148',3,'Outdoor','2022-10-02 19:33:15',20),(21,14,'149',3,'Project furniture','2022-10-02 19:33:15',21),(22,1,'15',2,'Machinery','2022-10-02 19:33:15',22),(23,15,'151',3,'Machinery components','2022-10-02 19:33:15',23),(24,15,'152',3,'Machinery equipment/tools','2022-10-02 19:33:15',24),(25,15,'153',3,'Manufacture of machinery','2022-10-02 19:33:15',25),(26,15,'154',3,'Maritime','2022-10-02 19:33:15',26),(27,154,'1541',4,'Aluminium and steel workboats','2022-10-02 19:33:15',27),(28,154,'1542',4,'Boat/Yacht building','2022-10-02 19:33:15',28),(29,154,'1543',4,'Ship repair and conversion','2022-10-02 19:33:15',29),(30,15,'155',3,'Metal structures','2022-10-02 19:33:15',30),(31,15,'156',3,'Other','2022-10-02 19:33:15',31),(32,15,'157',3,'Repair and maintenance service','2022-10-02 19:33:15',32),(33,1,'16',2,'Metalworking','2022-10-02 19:33:15',33),(34,16,'161',3,'Construction of metal structures','2022-10-02 19:33:15',34),(35,16,'162',3,'Houses and buildings','2022-10-02 19:33:15',35),(36,16,'163',3,'Metal products','2022-10-02 19:33:15',36),(37,16,'164',3,'Metal works','2022-10-02 19:33:15',37),(38,164,'1641',4,'CNC-machining','2022-10-02 19:33:15',38),(39,164,'1642',4,'Forgings, Fasteners','2022-10-02 19:33:15',39),(40,164,'1643',4,'Gas, Plasma, Laser cutting','2022-10-02 19:33:15',40),(41,164,'1644',4,'MIG, TIG, Aluminum welding','2022-10-02 19:33:15',41),(42,1,'17',2,'Plastic and Rubber','2022-10-02 19:33:15',42),(43,17,'171',3,'Packaging','2022-10-02 19:33:15',43),(44,17,'172',3,'Plastic goods','2022-10-02 19:33:15',44),(45,17,'173',3,'Plastic processing technology','2022-10-02 19:33:15',45),(46,173,'1731',4,'Blowing','2022-10-02 19:33:15',46),(47,173,'1732',4,'Moulding','2022-10-02 19:33:15',47),(48,173,'1733',4,'Plastics welding and processing','2022-10-02 19:33:15',48),(49,17,'174',3,'Plastic profiles','2022-10-02 19:33:15',49),(50,1,'18',2,'Printing','2022-10-02 19:33:15',50),(51,18,'181',3,'Advertising','2022-10-02 19:33:15',51),(52,18,'182',3,'Book/Periodicals printing','2022-10-02 19:33:15',52),(53,18,'183',3,'Labelling and packaging printing','2022-10-02 19:33:15',53),(54,1,'19',2,'Textile and Clothing','2022-10-02 19:33:15',54),(55,19,'191',3,'Clothing','2022-10-02 19:33:15',55),(56,19,'192',3,'Textile','2022-10-02 19:33:15',56),(57,1,'10',2,'Wood','2022-10-02 19:33:15',57),(58,10,'101',3,'Other (Wood)','2022-10-02 19:33:15',58),(59,10,'102',3,'Wooden building materials','2022-10-02 19:33:15',59),(60,10,'103',3,'Wooden houses','2022-10-02 19:33:15',60),(61,NULL,'2',1,'Other','2022-10-02 19:33:15',61),(62,2,'21',2,'Creative industries','2022-10-02 19:33:15',62),(63,2,'22',2,'Energy technology','2022-10-02 19:33:15',63),(64,2,'23',2,'Environment','2022-10-02 19:33:15',64),(65,NULL,'3',1,'Service','2022-10-02 19:33:15',65),(66,3,'31',2,'Business services','2022-10-02 19:33:15',66),(67,3,'32',2,'Engineering','2022-10-02 19:33:15',67),(68,3,'33',2,'Information Technology and Telecommunications','2022-10-02 19:33:15',68),(69,33,'331',3,'Data processing, Web portals, E-marketing','2022-10-02 19:33:15',69),(70,33,'332',3,'Programming, Consultancy','2022-10-02 19:33:15',70),(71,33,'333',3,'Software, Hardware','2022-10-02 19:33:15',71),(72,33,'334',3,'Telecommunications','2022-10-02 19:33:15',72),(73,3,'34',2,'Tourism','2022-10-02 19:33:15',73),(74,3,'35',2,'Translation services','2022-10-02 19:33:15',74),(75,3,'36',2,'Transport and Logistics','2022-10-02 19:33:15',75),(76,36,'361',3,'Air','2022-10-02 19:33:15',76),(77,36,'362',3,'Rail','2022-10-02 19:33:15',77),(78,36,'363',3,'Road','2022-10-02 19:33:15',78),(79,36,'364',3,'Water','2022-10-02 19:33:15',79);
/*!40000 ALTER TABLE `sectors` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_data`
--

DROP TABLE IF EXISTS `user_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_data` (
  `user_data_id` bigint NOT NULL AUTO_INCREMENT,
  `uid` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL,
  `sectors` varchar(2048) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `modified` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `terms` tinyint NOT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`user_data_id`)
) ENGINE=InnoDB AUTO_INCREMENT=48 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_data`
--

LOCK TABLES `user_data` WRITE;
/*!40000 ALTER TABLE `user_data` DISABLE KEYS */;
INSERT INTO `user_data` VALUES (1,'a87643afabe14adfa408d0c82f13bde6','137,141,148,153','2022-10-27 19:46:04',1,1);
/*!40000 ALTER TABLE `user_data` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `user_id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `modified` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `name_UNIQUE` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=90 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'test1','2022-10-27 19:46:04');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-10-28 11:56:23
