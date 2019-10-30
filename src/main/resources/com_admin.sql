-- MySQL dump 10.13  Distrib 5.7.12, for osx10.9 (x86_64)
--
-- Host: localhost    Database: comb_admin
-- ------------------------------------------------------
-- Server version	5.7.16

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `t_sys_admin`
--

DROP TABLE IF EXISTS `t_sys_admin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_sys_admin` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `account` varchar(11) DEFAULT NULL,
  `user_name` varchar(11) DEFAULT NULL,
  `password` varchar(32) DEFAULT NULL,
  `role_id` int(11) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `create_user` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_sys_admin`
--

LOCK TABLES `t_sys_admin` WRITE;
/*!40000 ALTER TABLE `t_sys_admin` DISABLE KEYS */;
INSERT INTO `t_sys_admin` VALUES (1,NULL,NULL,'admin','admin','12fb7cd21596c809a925fa67ad732e42',1,1,1),(17,'2019-10-28 18:38:57','2019-10-30 11:56:37','edit','','95dda3d45b1b9b930cf347de891e0808',2,1,1),(18,'2019-10-28 18:39:06','2019-10-30 11:56:45','del','','95dda3d45b1b9b930cf347de891e0808',3,1,1),(20,NULL,NULL,NULL,NULL,'123',NULL,NULL,NULL);
/*!40000 ALTER TABLE `t_sys_admin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_sys_menu`
--

DROP TABLE IF EXISTS `t_sys_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_sys_menu` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `menu_name` varchar(100) DEFAULT NULL,
  `menu_code` varchar(20) DEFAULT NULL,
  `menu_icon` varchar(20) DEFAULT NULL,
  `menu_type` int(11) DEFAULT NULL COMMENT '1：目录 2:菜单 3:按钮',
  `menu_url` varchar(20) DEFAULT NULL,
  `order_` int(11) DEFAULT NULL,
  `parent_id` int(11) DEFAULT NULL,
  `parent_name` varchar(20) DEFAULT NULL,
  `create_user` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=79 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_sys_menu`
--

LOCK TABLES `t_sys_menu` WRITE;
/*!40000 ALTER TABLE `t_sys_menu` DISABLE KEYS */;
INSERT INTO `t_sys_menu` VALUES (1,NULL,NULL,'系统管理',NULL,'el-icon-setting',0,'',1,0,NULL,NULL),(2,NULL,'2019-10-28 11:31:50','菜单管理','sys:menu','el-icon-menu',1,'/sys/menu',1,1,'系统管理',NULL),(3,NULL,'2019-10-25 17:29:23','角色管理','sys:role','el-icon-view',1,'/sys/role',2,1,'系统管理',NULL),(4,NULL,NULL,'用户管理','sys:user','el-icon-service',1,'/sys/user',3,1,'系统管理',NULL),(8,NULL,'2019-10-28 11:29:51','编辑','sys:menu:edit',NULL,2,NULL,0,2,'菜单管理',NULL),(11,NULL,'2019-10-25 17:48:57','删除','sys:menu:delete',NULL,2,NULL,0,2,'菜单管理',NULL),(72,'2019-10-30 11:35:19',NULL,'编辑','sys:role:edit','',2,'',0,3,'角色管理',1),(74,'2019-10-30 11:37:45',NULL,'删除','sys:role:delete','',2,'',0,3,'角色管理',1),(75,'2019-10-30 11:38:05',NULL,'编辑','sys:user:edit','',2,'',0,4,'用户管理',1),(77,'2019-10-30 11:39:15',NULL,'删除','sys:user:delete','',2,'',0,4,'用户管理',1);
/*!40000 ALTER TABLE `t_sys_menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_sys_role`
--

DROP TABLE IF EXISTS `t_sys_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_sys_role` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `role_name` varchar(11) DEFAULT NULL,
  `role_desc` varchar(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_sys_role`
--

LOCK TABLES `t_sys_role` WRITE;
/*!40000 ALTER TABLE `t_sys_role` DISABLE KEYS */;
INSERT INTO `t_sys_role` VALUES (1,NULL,NULL,'管理员','admin'),(2,NULL,NULL,'编辑者','拥有添加和编辑权限'),(3,'2019-10-29 11:56:49',NULL,'删除者','拥有删除权限');
/*!40000 ALTER TABLE `t_sys_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_sys_role_menu`
--

DROP TABLE IF EXISTS `t_sys_role_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_sys_role_menu` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `role_id` int(11) DEFAULT NULL,
  `menu_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=186 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_sys_role_menu`
--

LOCK TABLES `t_sys_role_menu` WRITE;
/*!40000 ALTER TABLE `t_sys_role_menu` DISABLE KEYS */;
INSERT INTO `t_sys_role_menu` VALUES (151,'2019-10-30 11:49:29',2,1),(152,'2019-10-30 11:49:29',2,2),(153,'2019-10-30 11:49:29',2,8),(154,'2019-10-30 11:49:29',2,3),(155,'2019-10-30 11:49:29',2,72),(156,'2019-10-30 11:49:29',2,4),(157,'2019-10-30 11:49:29',2,75),(158,'2019-10-30 11:49:35',3,1),(159,'2019-10-30 11:49:35',3,2),(160,'2019-10-30 11:49:35',3,11),(161,'2019-10-30 11:49:35',3,3),(162,'2019-10-30 11:49:35',3,74),(163,'2019-10-30 11:49:35',3,4),(164,'2019-10-30 11:49:35',3,77),(176,'2019-10-30 12:06:46',1,1),(177,'2019-10-30 12:06:46',1,2),(178,'2019-10-30 12:06:46',1,8),(179,'2019-10-30 12:06:46',1,11),(180,'2019-10-30 12:06:46',1,3),(181,'2019-10-30 12:06:46',1,72),(182,'2019-10-30 12:06:46',1,74),(183,'2019-10-30 12:06:46',1,4),(184,'2019-10-30 12:06:46',1,75),(185,'2019-10-30 12:06:46',1,77);
/*!40000 ALTER TABLE `t_sys_role_menu` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-10-30 12:07:58
