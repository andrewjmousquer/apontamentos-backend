-- MySQL dump 10.13  Distrib 8.0.29, for Linux (x86_64)
--
-- Host: localhost    Database: base_portal
-- ------------------------------------------------------
-- Server version	8.0.29-0ubuntu0.20.04.3

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
-- Table structure for table `access_list`
--

DROP TABLE IF EXISTS `access_list`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `access_list` (
  `acl_id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `mnu_id` int DEFAULT NULL,
  PRIMARY KEY (`acl_id`),
  UNIQUE KEY `uk_access_list` (`name`),
  KEY `fk_access_list_default_route_idx` (`mnu_id`),
  CONSTRAINT `fk_access_list_default_route` FOREIGN KEY (`mnu_id`) REFERENCES `menu` (`mnu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `access_list_checkpoint`
--

DROP TABLE IF EXISTS `access_list_checkpoint`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `access_list_checkpoint` (
  `ckp_id` int NOT NULL,
  `acl_id` int NOT NULL,
  PRIMARY KEY (`ckp_id`,`acl_id`),
  KEY `fk_access_list_checkpoint_access_list1_idx` (`acl_id`),
  CONSTRAINT `fk_access_list_checkpoint_access_list` FOREIGN KEY (`acl_id`) REFERENCES `access_list` (`acl_id`),
  CONSTRAINT `fk_access_list_checkpoint_checkpoint` FOREIGN KEY (`ckp_id`) REFERENCES `checkpoint` (`ckp_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `access_list_menu`
--

DROP TABLE IF EXISTS `access_list_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `access_list_menu` (
  `acl_id` int NOT NULL,
  `mnu_id` int NOT NULL,
  `mnu_order` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`acl_id`,`mnu_id`),
  KEY `idx_access_list_menu` (`mnu_id`),
  KEY `idx_access_list` (`acl_id`),
  CONSTRAINT `fk_access_list` FOREIGN KEY (`acl_id`) REFERENCES `access_list` (`acl_id`),
  CONSTRAINT `fk_access_list_menu` FOREIGN KEY (`mnu_id`) REFERENCES `menu` (`mnu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `address`
--

DROP TABLE IF EXISTS `address`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `address` (
  `add_id` int NOT NULL AUTO_INCREMENT,
  `street` varchar(255) DEFAULT NULL,
  `number` varchar(50) DEFAULT NULL,
  `district` varchar(255) DEFAULT NULL,
  `complement` varchar(255) DEFAULT NULL,
  `zip_code` varchar(45) DEFAULT NULL,
  `latitude` varchar(255) DEFAULT NULL,
  `longitude` varchar(255) DEFAULT NULL,
  `cit_id` int NOT NULL,
  PRIMARY KEY (`add_id`),
  KEY `idx_city` (`cit_id`),
  CONSTRAINT `fk_address_city` FOREIGN KEY (`cit_id`) REFERENCES `city` (`cit_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `audit`
--

DROP TABLE IF EXISTS `audit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `audit` (
  `log_id` int NOT NULL AUTO_INCREMENT,
  `log_date` datetime NOT NULL,
  `ip` varchar(45) NOT NULL,
  `hostname` varchar(100) NOT NULL,
  `username` varchar(100) DEFAULT NULL,
  `operation` varchar(255) NOT NULL,
  `details` text,
  PRIMARY KEY (`log_id`)
) ENGINE=InnoDB AUTO_INCREMENT=99 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `checkpoint`
--

DROP TABLE IF EXISTS `checkpoint`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `checkpoint` (
  `ckp_id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ckp_id`),
  UNIQUE KEY `uq_checkpoint` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `city`
--

DROP TABLE IF EXISTS `city`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `city` (
  `cit_id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `ste_id` int NOT NULL,
  PRIMARY KEY (`cit_id`),
  KEY `idx_city_state` (`ste_id`),
  CONSTRAINT `fk_city_state` FOREIGN KEY (`ste_id`) REFERENCES `state` (`ste_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9718 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `classifier`
--

DROP TABLE IF EXISTS `classifier`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `classifier` (
  `cla_id` int NOT NULL,
  `value` varchar(50) NOT NULL,
  `type` varchar(50) NOT NULL,
  `label` varchar(45) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`cla_id`),
  KEY `uq_classifier` (`value`,`type`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `contact`
--

DROP TABLE IF EXISTS `contact`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `contact` (
  `cot_id` int NOT NULL AUTO_INCREMENT,
  `value` varchar(150) NOT NULL,
  `complement` varchar(150) DEFAULT NULL,
  `type_cla` int NOT NULL,
  `per_id` int NOT NULL,
  PRIMARY KEY (`cot_id`),
  KEY `idx_contact_type` (`type_cla`),
  KEY `fk_contact_person1_idx` (`per_id`),
  CONSTRAINT `fk_contact_person1` FOREIGN KEY (`per_id`) REFERENCES `person` (`per_id`),
  CONSTRAINT `fk_contact_type` FOREIGN KEY (`type_cla`) REFERENCES `classifier` (`cla_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `country`
--

DROP TABLE IF EXISTS `country`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `country` (
  `cou_id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `abbreviation` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`cou_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customer` (
  `cus_id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `cnpj` varchar(100) NOT NULL,
  `hol_id` int NOT NULL,
  `type_cla` int NOT NULL,
  PRIMARY KEY (`cus_id`),
  KEY `fk_customer_holding_idx` (`hol_id`),
  KEY `idx_customer` (`cnpj`),
  KEY `fk_customer_type_idx` (`type_cla`),
  CONSTRAINT `fk_customer_holding` FOREIGN KEY (`hol_id`) REFERENCES `holding` (`hol_id`),
  CONSTRAINT `fk_customer_type` FOREIGN KEY (`type_cla`) REFERENCES `classifier` (`cla_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `document`
--

DROP TABLE IF EXISTS `document`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `document` (
  `doc_id` int NOT NULL AUTO_INCREMENT,
  `file_name` varchar(150) NOT NULL,
  `content_type` varchar(50) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `file_path` varchar(255) NOT NULL,
  `create_date` datetime NOT NULL,
  `usr_id` int NOT NULL,
  `type_cla_id` int NOT NULL,
  PRIMARY KEY (`doc_id`),
  KEY `fk_document_user_idx` (`usr_id`),
  KEY `fk_document_classifier1_idx` (`type_cla_id`),
  CONSTRAINT `fk_document_classifier1` FOREIGN KEY (`type_cla_id`) REFERENCES `classifier` (`cla_id`),
  CONSTRAINT `fk_document_user` FOREIGN KEY (`usr_id`) REFERENCES `user` (`usr_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `holding`
--

DROP TABLE IF EXISTS `holding`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `holding` (
  `hol_id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `logo` longblob,
  `cnpj` varchar(100) DEFAULT NULL,
  `social_name` varchar(100) DEFAULT NULL,
  `state_registration` varchar(100) DEFAULT NULL,
  `municipal_registration` varchar(100) DEFAULT NULL,
  `add_id` int DEFAULT NULL,
  `per_id` int DEFAULT NULL,
  `type_cla` int DEFAULT NULL,
  PRIMARY KEY (`hol_id`),
  KEY `idxAddress` (`add_id`),
  KEY `idx_holding_person` (`per_id`),
  KEY `fk_holding_classifier_idx_idx` (`type_cla`),
  CONSTRAINT `fk_holding_classifier_idx` FOREIGN KEY (`type_cla`) REFERENCES `classifier` (`cla_id`),
  CONSTRAINT `fk_holding_person` FOREIGN KEY (`per_id`) REFERENCES `person` (`per_id`),
  CONSTRAINT `fkAddress` FOREIGN KEY (`add_id`) REFERENCES `address` (`add_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `menu`
--

DROP TABLE IF EXISTS `menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `menu` (
  `mnu_id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `url` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `icon` varchar(45) DEFAULT NULL,
  `type_cla` int NOT NULL,
  `root_id` int DEFAULT NULL,
  PRIMARY KEY (`mnu_id`),
  KEY `idx_menu_root` (`root_id`),
  KEY `idx_menu_type` (`type_cla`),
  CONSTRAINT `fk_menu_root` FOREIGN KEY (`root_id`) REFERENCES `menu` (`mnu_id`),
  CONSTRAINT `fk_menu_type` FOREIGN KEY (`type_cla`) REFERENCES `classifier` (`cla_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `parameter`
--

DROP TABLE IF EXISTS `parameter`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `parameter` (
  `prm_id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `value` text NOT NULL,
  `description` text NOT NULL,
  PRIMARY KEY (`prm_id`),
  UNIQUE KEY `uk_parameter` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `pass_hist`
--

DROP TABLE IF EXISTS `pass_hist`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pass_hist` (
  `pas_id` int NOT NULL AUTO_INCREMENT,
  `password` varchar(60) NOT NULL,
  `change_date` datetime NOT NULL,
  `usr_id` int NOT NULL,
  PRIMARY KEY (`pas_id`),
  KEY `idx_pass_hist_user` (`usr_id`),
  CONSTRAINT `fk_pass_hist_user` FOREIGN KEY (`usr_id`) REFERENCES `user` (`usr_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `person`
--

DROP TABLE IF EXISTS `person`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `person` (
  `per_id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `job_title` varchar(255) DEFAULT NULL,
  `cpf` varchar(11) DEFAULT NULL,
  `rg` varchar(20) DEFAULT NULL,
  `cnpj` varchar(14) DEFAULT NULL,
  `rne` varchar(45) DEFAULT NULL,
  `birthdate` date DEFAULT NULL,
  `type_cla` int NOT NULL COMMENT 'classification_cla_id = Classificação da pessoa ( PJ / PF / Estrangeiro ) ( PERSON_CLASSIFICATION )',
  PRIMARY KEY (`per_id`),
  KEY `fk_person_classifier1_idx` (`type_cla`),
  CONSTRAINT `fk_person_classifier1` FOREIGN KEY (`type_cla`) REFERENCES `classifier` (`cla_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `state`
--

DROP TABLE IF EXISTS `state`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `state` (
  `ste_id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `abbreviation` varchar(2) NOT NULL,
  `cou_id` int NOT NULL,
  PRIMARY KEY (`ste_id`),
  KEY `idx_state_country` (`cou_id`),
  CONSTRAINT `fk_state_country` FOREIGN KEY (`cou_id`) REFERENCES `country` (`cou_id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `usr_id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `password` varchar(60) DEFAULT NULL,
  `enabled` tinyint(1) NOT NULL DEFAULT '1',
  `per_id` int NOT NULL,
  `acl_id` int DEFAULT NULL,
  `change_pass` tinyint(1) NOT NULL DEFAULT '0',
  `expire_pass` tinyint(1) NOT NULL DEFAULT '0',
  `pass_error_count` int NOT NULL DEFAULT '0',
  `forgot_key` varchar(50) DEFAULT NULL,
  `forgot_key_created` datetime DEFAULT NULL,
  `last_pass_change` datetime DEFAULT NULL,
  `blocked` tinyint(1) NOT NULL DEFAULT '0',
  `type_cla` int NOT NULL,
  `last_login` datetime DEFAULT NULL,
  `last_error_count` int DEFAULT NULL,
  `config` text,
  `cus_id` int DEFAULT NULL,
  PRIMARY KEY (`usr_id`),
  UNIQUE KEY `username_UNIQUE` (`username`),
  KEY `idx_user_person` (`per_id`),
  KEY `idx_user_access_list` (`acl_id`),
  KEY `idx_user_type` (`type_cla`),
  KEY `fk_user_customer_idx` (`cus_id`),
  CONSTRAINT `fk_user_access_list` FOREIGN KEY (`acl_id`) REFERENCES `access_list` (`acl_id`),
  CONSTRAINT `fk_user_customer` FOREIGN KEY (`cus_id`) REFERENCES `customer` (`cus_id`),
  CONSTRAINT `fk_user_person` FOREIGN KEY (`per_id`) REFERENCES `person` (`per_id`),
  CONSTRAINT `fk_user_type` FOREIGN KEY (`type_cla`) REFERENCES `classifier` (`cla_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_customer`
--

DROP TABLE IF EXISTS `user_customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_customer` (
  `usr_id` int NOT NULL,
  `cus_id` int NOT NULL,
  PRIMARY KEY (`usr_id`,`cus_id`),
  KEY `idx_user_has_customer_customer` (`cus_id`),
  KEY `idx_user_has_customer_user` (`usr_id`),
  CONSTRAINT `fk_user_has_customer_customer` FOREIGN KEY (`cus_id`) REFERENCES `customer` (`cus_id`),
  CONSTRAINT `fk_user_has_customer_user` FOREIGN KEY (`usr_id`) REFERENCES `user` (`usr_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping routines for database 'base_portal'
--
/*!50003 DROP FUNCTION IF EXISTS `fnGetParentMenu` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `fnGetParentMenu`(p_mnu_id INT) RETURNS text CHARSET latin1
    READS SQL DATA
BEGIN
DECLARE response TEXT;
SELECT base.menuPath into response FROM (
SELECT GROUP_CONCAT(
IF(
ISNULL(mnu.root_id), mnu.name, CONCAT(parent.name , ' > ' ,mnu.name) 
)
SEPARATOR '>') as menuPath
FROM menu mnu
LEFT JOIN menu parent FORCE INDEX (PRIMARY) ON (mnu.root_id = parent.mnu_id)
        WHERE mnu.mnu_id = p_mnu_id
) base;
    
RETURN response;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP FUNCTION IF EXISTS `fnMenu` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `fnMenu`(p_mnu_id INT) RETURNS text CHARSET latin1
    READS SQL DATA
BEGIN
DECLARE response TEXT;
SELECT base.menuPath into response FROM (
SELECT GROUP_CONCAT(
IF(
ISNULL(mnu.root_id), mnu.name, CONCAT(fnGetParentMenu(parent.mnu_id) , ' > ' ,mnu.name) 
)
            SEPARATOR '>') as menuPath
FROM menu mnu
LEFT JOIN menu parent FORCE INDEX (PRIMARY) ON (mnu.root_id = parent.mnu_id)
        WHERE mnu.mnu_id = p_mnu_id
) base;
    
RETURN response;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-06-10 11:52:00
