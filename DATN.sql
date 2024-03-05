-- MySQL dump 10.13  Distrib 8.0.30, for Win64 (x86_64)
--
-- Host: localhost    Database: greeny_shop
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
-- Table structure for table `categories`
--

DROP TABLE IF EXISTS `categories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `categories` (
  `category_id` bigint NOT NULL AUTO_INCREMENT,
  `category_image` varchar(255) DEFAULT NULL,
  `category_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`category_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categories`
--

LOCK TABLES `categories` WRITE;
/*!40000 ALTER TABLE `categories` DISABLE KEYS */;
INSERT INTO `categories` VALUES (4,NULL,'3/4 Helmet'),(5,NULL,'Fullface Helmet'),(6,NULL,'Half Helmet');
/*!40000 ALTER TABLE `categories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comments`
--

DROP TABLE IF EXISTS `comments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `comments` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `content` varchar(255) DEFAULT NULL,
  `rate_date` datetime DEFAULT NULL,
  `rating` double DEFAULT NULL,
  `order_detail_id` bigint DEFAULT NULL,
  `product_id` bigint DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKfwepd0s8syqc9s06xnqa7mdwf` (`order_detail_id`),
  KEY `FK6uv0qku8gsu6x1r2jkrtqwjtn` (`product_id`),
  KEY `FKqi14bvepnwtjbbaxm7m4v44yg` (`user_id`),
  CONSTRAINT `FK6uv0qku8gsu6x1r2jkrtqwjtn` FOREIGN KEY (`product_id`) REFERENCES `products` (`product_id`),
  CONSTRAINT `FKfwepd0s8syqc9s06xnqa7mdwf` FOREIGN KEY (`order_detail_id`) REFERENCES `order_details` (`order_detail_id`),
  CONSTRAINT `FKqi14bvepnwtjbbaxm7m4v44yg` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comments`
--

LOCK TABLES `comments` WRITE;
/*!40000 ALTER TABLE `comments` DISABLE KEYS */;
/*!40000 ALTER TABLE `comments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `favorites`
--

DROP TABLE IF EXISTS `favorites`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `favorites` (
  `favorite_id` bigint NOT NULL AUTO_INCREMENT,
  `product_id` bigint DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`favorite_id`),
  KEY `FK6sgu5npe8ug4o42bf9j71x20c` (`product_id`),
  KEY `FK1uphh0glinnprjknyl68k1hw1` (`user_id`),
  CONSTRAINT `FK1uphh0glinnprjknyl68k1hw1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `FK6sgu5npe8ug4o42bf9j71x20c` FOREIGN KEY (`product_id`) REFERENCES `products` (`product_id`)
) ENGINE=InnoDB AUTO_INCREMENT=81 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `favorites`
--

LOCK TABLES `favorites` WRITE;
/*!40000 ALTER TABLE `favorites` DISABLE KEYS */;
/*!40000 ALTER TABLE `favorites` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_details`
--

DROP TABLE IF EXISTS `order_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_details` (
  `order_detail_id` bigint NOT NULL AUTO_INCREMENT,
  `price` double DEFAULT NULL,
  `quantity` int NOT NULL,
  `order_id` bigint DEFAULT NULL,
  `product_id` bigint DEFAULT NULL,
  PRIMARY KEY (`order_detail_id`),
  KEY `FKjyu2qbqt8gnvno9oe9j2s2ldk` (`order_id`),
  KEY `FK4q98utpd73imf4yhttm3w0eax` (`product_id`),
  CONSTRAINT `FK4q98utpd73imf4yhttm3w0eax` FOREIGN KEY (`product_id`) REFERENCES `products` (`product_id`),
  CONSTRAINT `FKjyu2qbqt8gnvno9oe9j2s2ldk` FOREIGN KEY (`order_id`) REFERENCES `orders` (`order_id`)
) ENGINE=InnoDB AUTO_INCREMENT=78 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_details`
--

LOCK TABLES `order_details` WRITE;
/*!40000 ALTER TABLE `order_details` DISABLE KEYS */;
INSERT INTO `order_details` VALUES (77,630000,2,37,45);
/*!40000 ALTER TABLE `order_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders` (
  `order_id` bigint NOT NULL AUTO_INCREMENT,
  `address` varchar(255) DEFAULT NULL,
  `amount` double DEFAULT NULL,
  `order_date` datetime DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `status` int NOT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`order_id`),
  KEY `FKel9kyl84ego2otj2accfd8mr7` (`user_id`),
  CONSTRAINT `FKel9kyl84ego2otj2accfd8mr7` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES (1,'ha tinh',2,'2022-01-29 00:00:00','0917291997',2,1),(2,'da nang',20000,'2022-01-29 00:00:00','0615856985',2,2),(3,'ha tinh',0,'2022-02-12 00:00:00','0915762565',3,1),(4,'ha tinh',0,'2022-02-12 00:00:00','0915762565',3,1),(6,'68,Đà Nẵng',0,'2022-02-12 00:00:00','0915762565',3,1),(7,'266 Dũng Sĩ Thanh Khê - TP. Đà Nẵng',67900,'2022-02-12 00:00:00','0915746525',2,2),(8,'266 Dũng Sĩ Thanh Khê - TP. Đà Nẵng',169450,'2022-02-12 00:00:00','0915291997',1,2),(9,'268 Dũng Sĩ Thanh Khê - TP. Đà Nẵng',115950,'2022-02-12 00:00:00','0915291997',3,2),(10,'168-Hà Nội',53050,'2022-02-12 00:00:00','0916829635',1,1),(11,'266 Dũng Sĩ Thanh Khê - TP. Đà Nẵng',123,'2022-02-13 00:00:00','0915291997',2,2),(12,'Đà Nẵng',40500,'2022-02-13 00:00:00','0915291997',0,2),(13,'Đà Nẵng',116050,'2022-02-13 00:00:00','0915762565',3,2),(14,'268 Dũng Sĩ Thanh Khê - TP. Đà Nẵng',130750,'2022-02-13 00:00:00','0915291997',3,2),(15,'266 Dũng Sĩ Thanh Khê - TP. Đà Nẵng',362000,'2022-02-13 00:00:00','0915291997',2,2),(16,'Đà Nẵng',83550,'2022-02-13 00:00:00','0915762565',3,2),(17,'Hà Tĩnh',362000,'2022-02-13 00:00:00','0915291997',0,1),(18,'Đà Nẵng',24500,'2022-02-15 00:00:00','0915291997',0,2),(19,'268 Dũng Sĩ Thanh Khê - TP. Đà Nẵng',24250,'2022-02-15 00:00:00','0915762565',2,2),(20,'268 Dũng Sĩ Thanh Khê - TP. Đà Nẵng',54500,'2022-02-15 00:00:00','0915746525',2,2),(21,'268 Dũng Sĩ Thanh Khê - TP. Đà Nẵng',244550,'2022-02-15 00:00:00','0915291997',0,2),(22,'168 Hà Nội',228000,'2022-02-15 00:00:00','0915762565',3,2),(23,'68,Đà Nẵng',40000,'2022-02-16 00:00:00','0915762565',2,2),(24,'Liên Chiểu - Đà Nẵng',55950,'2022-02-16 00:00:00','0915762565',0,2),(25,'268 Dũng Sĩ Thanh Khê - TP. Đà Nẵng',32000,'2022-02-17 00:00:00','0915762565',2,2),(26,'268 Dũng Sĩ Thanh Khê - TP. Đà Nẵng',73750,'2022-02-17 00:00:00','0915291997',2,2),(29,'demo địa chỉ',72500,'2022-02-17 00:00:00','0915291997',2,8),(30,'demo địa chỉ',201500,'2022-02-17 00:00:00','0915291997',2,8),(31,'Đà Nẵng',77050,'2022-10-31 00:00:00','0915769989',3,9),(32,'Hà Nội',67050,'2022-11-01 00:00:00','032315151',2,9),(33,'Hà Nội',10000,'2022-11-01 00:00:00','0915769989',2,9),(34,'Hà Nội',10000000,'2022-11-04 00:00:00','0915769989',2,9),(35,'Hà Nội',16000,'2022-11-04 00:00:00','0323262626',1,9),(36,'Đà Nẵng',48000,'2022-11-04 00:00:00','0915769989',2,9),(37,'Hòa Bình',1134000,'2022-11-08 00:00:00','032566879',2,9);
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
  `description` varchar(1000) DEFAULT NULL,
  `discount` int NOT NULL,
  `entered_date` datetime DEFAULT NULL,
  `price` double NOT NULL,
  `product_image` varchar(255) DEFAULT NULL,
  `product_name` varchar(255) DEFAULT NULL,
  `quantity` int NOT NULL,
  `status` bit(1) DEFAULT NULL,
  `category_id` bigint DEFAULT NULL,
  `favorite` bit(1) NOT NULL,
  PRIMARY KEY (`product_id`),
  KEY `FKog2rp4qthbtt2lfyhfo32lsw9` (`category_id`),
  CONSTRAINT `FKog2rp4qthbtt2lfyhfo32lsw9` FOREIGN KEY (`category_id`) REFERENCES `categories` (`category_id`)
) ENGINE=InnoDB AUTO_INCREMENT=55 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `products`
--

LOCK TABLES `products` WRITE;
/*!40000 ALTER TABLE `products` DISABLE KEYS */;
INSERT INTO `products` VALUES (45,'Mũ Bảo Hiểm 3/4 Thiết Kế Cao Cấp PrimiA Xtreme – OMBRE Mùa Hạ',10,'2022-11-01 00:00:00',630000,'342.jpg','3/4 Helmet',8,NULL,4,_binary '\0'),(46,'MŨ BẢO HIỂM 3/4 CAO CẤP PRIMIA XTREME – MÀU TRƠN – PHONG CÁCH CỔ ĐIỂN',0,'2022-10-31 00:00:00',585000,'341.jpg','3/4 Helmet',5,NULL,4,_binary '\0'),(47,'Mũ Bảo Hiểm 3/4 Thiết Kế Cao Cấp PrimiA Xtreme – OMBRE Cam Tươi',0,'2022-10-30 00:00:00',630000,'344.jpg','Mũ Bảo Hiểm 3/4 Thiết Kế Cao Cấp PrimiA Xtreme – OMBRE Cam Tươi',7,NULL,4,_binary '\0'),(48,'Mũ Nửa Đầu Kính Phi Công HR1 – Iron Man',0,'2022-10-30 00:00:00',130000,'half2.jpg','Mũ Nửa Đầu Kính Phi Công HR1 – Iron Man',10,NULL,6,_binary '\0'),(49,'Mũ bảo hiểm Superman (Đỏ/Hồng) – Mũ bảo hiểm siêu nhân ',0,'2022-10-30 00:00:00',180000,'half1.jpg','Mũ bảo hiểm Superman (Đỏ/Hồng) – Mũ bảo hiểm siêu nhân ',6,NULL,6,_binary '\0'),(50,'HRA Đen Nhám – Mũ Bảo Hiểm Nửa Đầu Kính Phi Công',0,'2022-11-02 00:00:00',280000,'half3.jpg','HRA Đen Nhám – Mũ Bảo Hiểm Nửa Đầu Kính Phi Công',15,NULL,6,_binary '\0'),(51,'Mũ Fullface TORC T18',0,'2022-10-31 00:00:00',1950000,'full1.jpg','Mũ Fullface TORC T18',10,NULL,5,_binary '\0'),(52,'Mũ Fullface LS2 STREAM FF320',0,'2022-10-31 00:00:00',1990000,'full4.jpg','Mũ Fullface LS2 STREAM FF320',8,NULL,5,_binary '\0'),(53,'Mũ Fullface ROC 05',0,'2022-10-31 00:00:00',1100000,'full2.jpg','Mũ Fullface ROC 05',11,NULL,5,_binary '\0'),(54,'Mũ Bảo Hiểm 3/4 Thiết Kế Cao Cấp PrimiA Xtreme – OMBRE Cam Tươi',10,'2022-10-30 00:00:00',630000,'344.jpg','Mũ Bảo Hiểm 3/4 Thiết Kế Cao Cấp PrimiA Xtreme – OMBRE Cam Tươi',7,NULL,4,_binary '\0');
/*!40000 ALTER TABLE `products` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,'ROLE_USER'),(2,'ROLE_USER'),(4,'ROLE_ADMIN'),(5,'ROLE_ADMIN'),(6,'ROLE_USER'),(7,'ROLE_USER'),(8,'ROLE_USER'),(9,'ROLE_USER'),(10,'ROLE_USER');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `user_id` bigint NOT NULL AUTO_INCREMENT,
  `avatar` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `register_date` date DEFAULT NULL,
  `status` bit(1) DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `UKob8kqyqqgmefl0aco34akdtpe` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'user.png','thachi6402@gmail.com','zxczcx','$2a$10$NNFj7.bICFpxqYTsCswbe.BNGHGicL0LZzXmm.UCLz8sWrM3EL3IC','2022-01-29',_binary ''),(2,'user.png','jnkjnk297@gmail.com','czxc','$2a$10$.XOXsgTmumdrVdQc74mqUO180lLS0OefXAyPIdiVw0IshtW5WYDMm','2022-01-29',_binary ''),(3,'user.png','hieu@fpt.edu.vn','user','$2a$10$UtRFor3y5PqxHKFEt1HgAOOaX7tlQs1oEhJA/VLFoUVGKo3OBkISS','2022-01-30',_binary ''),(4,'user.png','helmetshop123vn@gmail.com','Admin Otis Shop','$2a$10$Hd54fYSXGv6Pqve.WjeLO.DyNv2gGIq/S2Drilp12ClD6gYzhh4jO','2022-02-15',_binary ''),(5,'user.png','demo@gmail.com','user1','$2a$10$GtfloNhLVXQaKdXvmueUfu14h6ijuwFHMLb1icK4rsuzpWa6jOh72','2022-02-17',_binary ''),(6,'user.png','demo2@gmail.com','user2','$2a$10$PUWkbGnEa1OdKmxiQfvw/u3oh3I09nGG6zVHmGLxgigSJC2tCz4Ta','2022-02-17',_binary ''),(7,'user.png','demo3@gmail.com','user3','$2a$10$ePg/cUabs6dShg4hC4Buv.QaVFx6VqqBUSlmQBntalEOAaWCfY2Hi','2022-02-17',_binary ''),(8,'user.png','greenyshop.adm@gmail.com','demo tên','$2a$10$zBgbGqKnEPFiMOceXhqwIem4e/JFMYF2rjRElIcuaBAnO.toFIOCm','2022-02-17',_binary ''),(9,'user.png','hieunguyebtrung99@gmail.com','demo hiếu','$2a$10$4Xcix2ICxQV0s92fGh.EP.6v3o17DUtW9.D96KqgQqRDXiUtY7ZG2','2022-10-31',_binary '');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users_roles`
--

DROP TABLE IF EXISTS `users_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users_roles` (
  `user_id` bigint NOT NULL,
  `role_id` bigint NOT NULL,
  KEY `FKt4v0rrweyk393bdgt107vdx0x` (`role_id`),
  KEY `FKgd3iendaoyh04b95ykqise6qh` (`user_id`),
  CONSTRAINT `FKgd3iendaoyh04b95ykqise6qh` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `FKt4v0rrweyk393bdgt107vdx0x` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users_roles`
--

LOCK TABLES `users_roles` WRITE;
/*!40000 ALTER TABLE `users_roles` DISABLE KEYS */;
INSERT INTO `users_roles` VALUES (1,1),(2,2),(3,4),(4,5),(5,6),(6,7),(7,8),(8,9),(9,10);
/*!40000 ALTER TABLE `users_roles` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-11-08 21:54:21
