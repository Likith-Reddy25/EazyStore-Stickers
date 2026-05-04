-- MySQL dump 10.13  Distrib 8.0.44, for Linux (x86_64)
--
-- Host: localhost    Database: eazystore
-- ------------------------------------------------------
-- Server version	8.0.44

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
-- Table structure for table `address`
--

DROP TABLE IF EXISTS `address`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `address` (
  `address_id` bigint NOT NULL AUTO_INCREMENT,
  `customer_id` bigint NOT NULL,
  `street` varchar(150) NOT NULL,
  `city` varchar(100) NOT NULL,
  `state` varchar(100) NOT NULL,
  `postal_code` varchar(20) NOT NULL,
  `country` varchar(100) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `created_by` varchar(255) NOT NULL DEFAULT 'SYSTEM',
  `updated_at` timestamp NULL DEFAULT NULL,
  `updated_by` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`address_id`),
  UNIQUE KEY `customer_id` (`customer_id`),
  CONSTRAINT `address_ibfk_1` FOREIGN KEY (`customer_id`) REFERENCES `customers` (`customer_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `address`
--

LOCK TABLES `address` WRITE;
/*!40000 ALTER TABLE `address` DISABLE KEYS */;
INSERT INTO `address` VALUES (1,1,'2-17-76/c8/3, Rd Number 10','Hyderabad','Telangana','500039','India','2025-12-29 06:05:40','likith@mail.com',NULL,NULL),(2,5,'Street no:10','New York City','New York ','600018','United States Of America','2025-12-30 10:39:08','arjun@gmail.com','2026-01-01 16:21:23','arjun@gmail.com'),(3,6,'2-17-76/c8/3, Rd Number 10','Hyderabad','Telangana','500039','India','2026-01-05 09:47:22','ram@gmail.com',NULL,NULL);
/*!40000 ALTER TABLE `address` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `contacts`
--

DROP TABLE IF EXISTS `contacts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `contacts` (
  `contact_id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `mobile_number` varchar(15) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `created_by` varchar(20) NOT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  `updated_by` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`contact_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contacts`
--

LOCK TABLES `contacts` WRITE;
/*!40000 ALTER TABLE `contacts` DISABLE KEYS */;
INSERT INTO `contacts` VALUES (1,'Likith Reddy','Likith@gmail.com','1234567890','2025-12-19 06:41:43','Likith Reddy',NULL,NULL),(2,'rithika','rithika010102','8790526654','2025-12-19 06:42:23','rithika',NULL,NULL),(3,'Likith Reddy','likith@gmail.com','1234567890','2025-12-21 11:39:16','Likith Reddy',NULL,NULL),(4,'Arjun Reddy','arjunreddy@gmail.oom','1122334455','2025-12-29 16:32:24','Arjun Reddy',NULL,NULL);
/*!40000 ALTER TABLE `contacts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customers`
--

DROP TABLE IF EXISTS `customers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customers` (
  `customer_id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `mobile_number` varchar(15) NOT NULL,
  `password_hash` varchar(500) NOT NULL,
  `created_by` varchar(100) DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_by` varchar(100) DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`customer_id`),
  UNIQUE KEY `email` (`email`),
  UNIQUE KEY `unique_email` (`email`),
  UNIQUE KEY `unique_mobile_number` (`mobile_number`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customers`
--

LOCK TABLES `customers` WRITE;
/*!40000 ALTER TABLE `customers` DISABLE KEYS */;
INSERT INTO `customers` VALUES (1,'Likith Reddy','likith@mail.com','1234567890','$2a$10$r0E5.bAVECV2uu.v1LQm0eQQUpGNupUnE6rxmJwjdxBJOG5DHuKZq',NULL,'2025-12-26 16:29:10',NULL,NULL),(3,'qefea','qefea@mail.com','0987654321','$2a$10$VQV3iBZ/t4ZPH8Zj77mmnef7538avO/D9ENK1ZML0pqt30pfGEh/S',NULL,'2025-12-26 16:34:11',NULL,NULL),(5,'Arjun Reddy','arjun@gmail.com','1122334455','$2a$10$5vloHX.F3VhzVgS7CYTvTebE3XlmZ5WPDStbMtCPvZCOFoVdc4DaG','Anonymous user','2025-12-29 16:52:30',NULL,NULL),(6,'RamSM','ram@gmail.com','1234512345','$2a$10$1fy3xAmFpuvwzzmxqPMgjebgz8/q9p238Z8q5fVOtjhxxUDOMt652','Anonymous user','2026-01-05 09:45:57',NULL,NULL);
/*!40000 ALTER TABLE `customers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_items`
--

DROP TABLE IF EXISTS `order_items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_items` (
  `order_item_id` bigint NOT NULL AUTO_INCREMENT,
  `order_id` bigint NOT NULL,
  `product_id` bigint NOT NULL,
  `quantity` int NOT NULL,
  `price` decimal(10,2) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `created_by` varchar(20) NOT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  `updated_by` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`order_item_id`),
  KEY `order_id` (`order_id`),
  KEY `product_id` (`product_id`),
  CONSTRAINT `order_items_ibfk_1` FOREIGN KEY (`order_id`) REFERENCES `orders` (`order_id`),
  CONSTRAINT `order_items_ibfk_2` FOREIGN KEY (`product_id`) REFERENCES `products` (`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_items`
--

LOCK TABLES `order_items` WRITE;
/*!40000 ALTER TABLE `order_items` DISABLE KEYS */;
/*!40000 ALTER TABLE `order_items` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders` (
  `order_id` bigint NOT NULL AUTO_INCREMENT,
  `customer_id` bigint NOT NULL,
  `total_price` decimal(10,2) NOT NULL,
  `payment_id` varchar(200) NOT NULL,
  `payment_status` varchar(50) NOT NULL,
  `order_status` varchar(50) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `created_by` varchar(20) NOT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  `updated_by` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`order_id`),
  KEY `customer_id` (`customer_id`),
  CONSTRAINT `orders_ibfk_1` FOREIGN KEY (`customer_id`) REFERENCES `customers` (`customer_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `products`
--

DROP TABLE IF EXISTS `products`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `products` (
  `product_id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(250) NOT NULL,
  `description` varchar(500) NOT NULL,
  `price` decimal(10,2) NOT NULL,
  `popularity` int NOT NULL,
  `image_url` varchar(500) DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `created_by` varchar(20) NOT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  `updated_by` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`product_id`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `products`
--

LOCK TABLES `products` WRITE;
/*!40000 ALTER TABLE `products` DISABLE KEYS */;
INSERT INTO `products` VALUES (1,'Developer','Code Wizard!',5.00,85,'/stickers/developer.png','2025-12-13 10:14:10','admin',NULL,NULL),(2,'Break','Hey, lets take a breather and start fresh on the next line',4.50,40,'/stickers/break.png','2025-12-13 10:14:10','admin',NULL,NULL),(3,'Not a bug','It\'s a surprise functionality.',6.00,98,'/stickers/itsnotabug.png','2025-12-13 10:14:10','admin',NULL,NULL),(4,'Devster','They exist!',5.00,72,'/stickers/EatSleepCode.png','2025-12-13 10:14:10','admin',NULL,NULL),(5,'CodeSmasher','Fearless developer!',7.50,88,'/stickers/BreakingCode.png','2025-12-13 10:14:10','admin',NULL,NULL),(6,'CodeMate','Without you, I\'m incomplete!',2.00,79,'/stickers/youaremycss.png','2025-12-13 10:14:10','admin',NULL,NULL),(7,'Mbappé','Phenomenal!',8.00,55,'/stickers/Mbappe.png','2025-12-13 10:14:10','admin',NULL,NULL),(8,'AstroChill','Cool for gravity!',3.00,52,'/stickers/CoolAstraunaut.png','2025-12-13 10:14:10','admin',NULL,NULL),(9,'Ronaldo','Legendary!',8.00,100,'/stickers/ronaldo.png','2025-12-13 10:14:10','admin',NULL,NULL),(10,'My Driving Scares Me Too','They exist!',5.00,65,'/stickers/MyDrivingScaresMeToo.png','2025-12-13 10:14:10','admin',NULL,NULL),(11,'Three headed dragon symbol','Targaryen dynasty strength',9.00,98,'/stickers/HouseOfTheDragonSymbol.png','2025-12-13 10:14:10','admin',NULL,NULL),(12,'Squid Game','Let\'s play',5.00,70,'/stickers/SquidGame.png','2025-12-13 10:14:10','admin',NULL,NULL),(13,'Shin-Chan','Mischievous!',5.00,70,'/stickers/Shinchan.png','2025-12-13 10:14:10','admin',NULL,NULL),(14,'Game over','Game over!',5.00,50,'/stickers/GameOver.png','2025-12-13 10:14:10','admin',NULL,NULL),(15,'Messi','Magical!',10.00,99,'/stickers/Messi.png','2025-12-13 10:14:10','admin',NULL,NULL),(16,'Virat Kohli','King',9.00,99,'/stickers/Virat.png','2025-12-13 10:14:10','admin',NULL,NULL),(17,'Lazy Cat','Not Today',6.00,60,'/stickers/LazyCat.png','2025-12-13 10:14:10','admin',NULL,NULL),(18,'Busy Brain','Overthinker!',4.00,50,'/stickers/OverThinker.png','2025-12-13 10:14:10','admin',NULL,NULL),(19,'Naruto','Ninja!',6.00,60,'/stickers/Naruto.png','2025-12-13 10:14:10','admin',NULL,NULL),(20,'Goku','Warrior!',6.00,60,'/stickers/Goku.png','2025-12-13 10:14:10','admin',NULL,NULL),(21,'I am okay','Persistent!',6.00,60,'/stickers/IamOkay.png','2025-12-13 10:14:10','admin',NULL,NULL),(22,'Boo','Disapproval!',6.00,60,'/stickers/Boo.png','2025-12-13 10:14:10','admin',NULL,NULL),(23,'EW feeling','Disgust!',6.00,60,'/stickers/EwFeelings.png','2025-12-13 10:14:10','admin',NULL,NULL),(24,'Be wild','Unleashed!',6.00,60,'/stickers/BeWild.png','2025-12-13 10:14:10','admin',NULL,NULL),(25,'SummerCat','Heatwave Whiskers',6.00,60,'/stickers/AestheticSummerCat.png','2025-12-13 10:14:10','admin',NULL,NULL),(26,'Savageness','Your opinion means nothing',6.00,60,'/stickers/YourOpinonMeansNothing.png','2025-12-13 10:14:10','admin',NULL,NULL),(27,'Awkweird','Awkward and Weird',6.00,60,'/stickers/SociallyAwkward.png','2025-12-13 10:14:10','admin',NULL,NULL),(28,'Blue Butterfly','Gracewing',6.00,60,'/stickers/Butterfly.png','2025-12-13 10:14:10','admin',NULL,NULL),(29,'NoHesitation','Always ready to take charge!',6.00,60,'/stickers/IWon_tHesitateSticker.png','2025-12-13 10:14:10','admin',NULL,NULL),(30,'Wardgaze','Protective power of the evil eye',6.00,60,'/stickers/EvilEye.png','2025-12-13 10:14:10','admin',NULL,NULL);
/*!40000 ALTER TABLE `products` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-01-06 15:55:28
