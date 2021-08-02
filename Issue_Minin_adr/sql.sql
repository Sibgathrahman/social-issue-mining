/*
SQLyog Community v13.0.1 (64 bit)
MySQL - 5.1.32-community : Database - issue_mining
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`issue_mining` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `issue_mining`;

/*Table structure for table `about_corporation` */

DROP TABLE IF EXISTS `about_corporation`;

CREATE TABLE `about_corporation` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `corporation` varchar(50) DEFAULT NULL,
  `Description` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

/*Data for the table `about_corporation` */

insert  into `about_corporation`(`id`,`corporation`,`Description`) values 
(1,'Muncipal Corporation','The kozhikode corporation is the muncipal corporation responsible for the administration of the city of kozhikode,kerala.The kozhikode corporation is the oldest muncipal corporation in kerala.Established in 1962 the corporations first mayor was H. Manjunatha Rao.\r\nKozhikode corporation has four assembly constituencies Kozhikode North,Kozhikode  South,Beypore and Elathur  all of Which are the part of the kozhikode parliamentary constituency.The corporation is headed by a mayor.For administrative purposes the city is divided into 75 wards form which the members of the corporation council are elected for term of five years. ');

/*Table structure for table `assign_work` */

DROP TABLE IF EXISTS `assign_work`;

CREATE TABLE `assign_work` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `cid` int(11) DEFAULT NULL,
  `work` varchar(50) DEFAULT NULL,
  `completion_date` date DEFAULT NULL,
  `status` varchar(20) DEFAULT NULL,
  `applctid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

/*Data for the table `assign_work` */

insert  into `assign_work`(`id`,`cid`,`work`,`completion_date`,`status`,`applctid`) values 
(1,15,'do it fast','2020-03-15','pending',1);

/*Table structure for table `chat` */

DROP TABLE IF EXISTS `chat`;

CREATE TABLE `chat` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `fid` int(11) DEFAULT NULL,
  `tid` int(11) DEFAULT NULL,
  `message` varchar(50) DEFAULT NULL,
  `date` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

/*Data for the table `chat` */

insert  into `chat`(`id`,`fid`,`tid`,`message`,`date`) values 
(1,4,5,'hi','2020-03-07'),
(2,5,4,'hi','2020-03-07');

/*Table structure for table `complaint` */

DROP TABLE IF EXISTS `complaint`;

CREATE TABLE `complaint` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `lid` int(11) DEFAULT NULL,
  `date` date DEFAULT NULL,
  `complaint` varchar(100) DEFAULT NULL,
  `reply` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

/*Data for the table `complaint` */

insert  into `complaint`(`id`,`lid`,`date`,`complaint`,`reply`) values 
(1,5,'2020-03-07','not good','svszv');

/*Table structure for table `councilor` */

DROP TABLE IF EXISTS `councilor`;

CREATE TABLE `councilor` (
  `cid` int(11) NOT NULL AUTO_INCREMENT,
  `lid` int(11) DEFAULT NULL,
  `first_name` varchar(50) DEFAULT NULL,
  `second_name` varchar(50) DEFAULT NULL,
  `gender` varchar(20) DEFAULT NULL,
  `DOB` date DEFAULT NULL,
  `ward` varchar(50) DEFAULT NULL,
  `phone_no` bigint(20) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `place` varchar(50) DEFAULT NULL,
  `post` varchar(50) DEFAULT NULL,
  `pin` bigint(11) DEFAULT NULL,
  PRIMARY KEY (`cid`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

/*Data for the table `councilor` */

insert  into `councilor`(`cid`,`lid`,`first_name`,`second_name`,`gender`,`DOB`,`ward`,`phone_no`,`email`,`place`,`post`,`pin`) values 
(1,5,'Thufail','Tp','Male','1998-02-26','2',8467591842,'thufail@gmail.com','Valencheri','morayur',676124),
(2,6,'Mohameed','Ijlal','Male','2000-09-25','3',7648524985,'ijlal@gmail.com','Kottakal','Kottakal',676122),
(3,7,'Junaid','Abdulla','Male','1998-08-26','1',9995553480,'junaid@gmail.com','Malappuram','Kootilangadi',676120);

/*Table structure for table `department` */

DROP TABLE IF EXISTS `department`;

CREATE TABLE `department` (
  `did` int(11) NOT NULL AUTO_INCREMENT,
  `lid` int(11) DEFAULT NULL,
  `department` varchar(50) DEFAULT NULL,
  `description` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`did`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;

/*Data for the table `department` */

insert  into `department`(`did`,`lid`,`department`,`description`) values 
(1,8,'Council','Counciling.'),
(2,9,'Town Planning','Architecting.'),
(3,10,'Engineering','Construction.'),
(4,11,'Health','Health Issues.'),
(5,12,'Revenue','Source Of Income.'),
(6,13,'Nipah','Nipah Cell.');

/*Table structure for table `feedback` */

DROP TABLE IF EXISTS `feedback`;

CREATE TABLE `feedback` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `lid` int(11) DEFAULT NULL,
  `date` date DEFAULT NULL,
  `did` int(11) DEFAULT NULL,
  `feedback` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

/*Data for the table `feedback` */

insert  into `feedback`(`id`,`lid`,`date`,`did`,`feedback`) values 
(1,2,'2020-03-07',2,'Not good');

/*Table structure for table `issues` */

DROP TABLE IF EXISTS `issues`;

CREATE TABLE `issues` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `lid` int(11) DEFAULT NULL,
  `ward` varchar(50) DEFAULT NULL,
  `issue` text,
  `description` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;

/*Data for the table `issues` */

insert  into `issues`(`id`,`lid`,`ward`,`issue`,`description`) values 
(1,2,'2','Air, Sound And Water Pollution','we are badly polluting our rivers and agricultural land through the excessive use of chemicals, plastic and careless disposal of waste. Pollution has the ability to reach massive proportions and destroy the ecosystem if we do not stop abusing natural resources before it is too late.'),
(2,2,'2','Hygiene And Sanitation','Sanitation is a basic right of every citizen of the country. Unfortunately, this is one issue the country has not been able to successfully handle in the last 70 years. Even in the cities, there is a dearth of proper drainage and disposal of waste. People still do not segregate dry and wet waste, which causes huge issues in decomposing and recycling.'),
(3,2,'2','Women’s Safety','Women – both adults and minors – are getting raped and reports are coming in from all quarters of the country. Yet, there has not been any significant improvement regarding women’s security and safety. Women still have to feel scared to wander the streets at night, away from their homes, while rapists still roam free. India still has a long way to go in ensuring a safe and secure life for women.'),
(4,2,'2','Air Pollution','Air pollution is one of the most serious problems in the world. It refers to the contamination of the atmosphere by harmful chemicals or biological materials. According to the Worlds Worst Polluted Places by Blacksmith Institute in 2008, two of the worst pollution problems in the world are urban air quality and indoor air pollution. '),
(5,2,'2','pollution of air','in my city the air pollustion in increasing day by day. and we need a solution in this topic\r\n'),
(6,2,'2','Water','Lack of water');

/*Table structure for table `login` */

DROP TABLE IF EXISTS `login`;

CREATE TABLE `login` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  `type` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UNIQUE` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=latin1;

/*Data for the table `login` */

insert  into `login`(`id`,`username`,`password`,`type`) values 
(1,'admin','admin','admin'),
(2,'7356004554','12345678','user'),
(3,'navaf','navaf','Deactive'),
(4,'silvan','silvan','mayor'),
(5,'thufail','thufail','councilor'),
(6,'ijlal','ijlal','councilor'),
(7,'junaid','junaid','councilor'),
(8,'council','council','department'),
(9,'town','town','department'),
(10,'engineering','engineering','department'),
(11,'health','health','department'),
(12,'revenue','revenue','department'),
(13,'nipah','nipah','Deactive'),
(14,'yadhu','yadhu','staff'),
(15,'akhil','akhil','staff'),
(18,'pandath','pandath','staff');

/*Table structure for table `mayor` */

DROP TABLE IF EXISTS `mayor`;

CREATE TABLE `mayor` (
  `mid` int(11) NOT NULL AUTO_INCREMENT,
  `lid` int(11) DEFAULT NULL,
  `year` varchar(50) DEFAULT NULL,
  `first_name` varchar(50) DEFAULT NULL,
  `second_name` varchar(50) DEFAULT NULL,
  `gender` varchar(20) DEFAULT NULL,
  `DOB` date DEFAULT NULL,
  `qualification` varchar(50) DEFAULT NULL,
  `phone_no` bigint(20) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`mid`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

/*Data for the table `mayor` */

insert  into `mayor`(`mid`,`lid`,`year`,`first_name`,`second_name`,`gender`,`DOB`,`qualification`,`phone_no`,`email`) values 
(1,3,'2015','Mohammed','Navaf','Male','1998-08-26','UNDER GRADUATE',8546725948,'navaf@gmail.com'),
(2,4,'2020','Mohammed','Silvan','Male','1998-03-24','UNDER GRADUATE',8654725948,'silvan@gmail.com');

/*Table structure for table `mine_result` */

DROP TABLE IF EXISTS `mine_result`;

CREATE TABLE `mine_result` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `councilor_id` int(11) DEFAULT NULL,
  `mine_result` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

/*Data for the table `mine_result` */

insert  into `mine_result`(`id`,`councilor_id`,`mine_result`) values 
(1,5,'Sanitation is a basic right of every citizen of the country. Unfortunately, this is one issue the country has not been able to successfully handle in the last 70 years. Even in the cities, there is a dearth of proper drainage and disposal of waste. People still do not segregate dry and wet waste, which causes huge issues in decomposing and recycling.'),
(2,5,'Air pollution is one of the most serious problems in the world. It refers to the contamination of the atmosphere by harmful chemicals or biological materials. According to the Worlds Worst Polluted Places by Blacksmith Institute in 2008, two of the worst pollution problems in the world are urban air quality and indoor air pollution. ');

/*Table structure for table `notification` */

DROP TABLE IF EXISTS `notification`;

CREATE TABLE `notification` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `lid` int(11) DEFAULT NULL,
  `date` date DEFAULT NULL,
  `notification` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

/*Data for the table `notification` */

insert  into `notification`(`id`,`lid`,`date`,`notification`) values 
(1,5,'2020-03-07','hello user');

/*Table structure for table `policy` */

DROP TABLE IF EXISTS `policy`;

CREATE TABLE `policy` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `lid` int(11) DEFAULT NULL,
  `policy_name` varchar(50) DEFAULT NULL,
  `description` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

/*Data for the table `policy` */

insert  into `policy`(`id`,`lid`,`policy_name`,`description`) values 
(1,4,'New','banner_4.jpg');

/*Table structure for table `rating` */

DROP TABLE IF EXISTS `rating`;

CREATE TABLE `rating` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `lid` int(11) DEFAULT NULL,
  `did` int(11) DEFAULT NULL,
  `rating` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

/*Data for the table `rating` */

insert  into `rating`(`id`,`lid`,`did`,`rating`) values 
(1,2,2,'3.0');

/*Table structure for table `report` */

DROP TABLE IF EXISTS `report`;

CREATE TABLE `report` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `did` int(11) DEFAULT NULL,
  `title` varchar(50) DEFAULT NULL,
  `date` date DEFAULT NULL,
  `report` varchar(50) DEFAULT NULL,
  `work_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

/*Data for the table `report` */

insert  into `report`(`id`,`did`,`title`,`date`,`report`,`work_id`) values 
(1,9,'done it','2020-03-07','login_2.jpg',1);

/*Table structure for table `staff` */

DROP TABLE IF EXISTS `staff`;

CREATE TABLE `staff` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `lid` int(11) DEFAULT NULL,
  `did` int(11) DEFAULT NULL,
  `first_name` varchar(50) DEFAULT NULL,
  `second_name` varchar(50) DEFAULT NULL,
  `gender` varchar(20) DEFAULT NULL,
  `DOB` date DEFAULT NULL,
  `qualification` varchar(50) DEFAULT NULL,
  `phone_no` bigint(20) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

/*Data for the table `staff` */

insert  into `staff`(`id`,`lid`,`did`,`first_name`,`second_name`,`gender`,`DOB`,`qualification`,`phone_no`,`email`) values 
(1,14,9,'yadhu','krishnan','Male','2012-09-26','UNDER GRADUATE',8546725485,'yadhu@gmail.com'),
(2,15,9,'Akhil','t','Male','2017-10-29','UNDER GRADUATE',8546725984,'nizam@gmail.com'),
(3,18,8,'Mohammed','Navaf','Male','2018-10-29','UNDER GRADUATE',9548265785,'navaf@gmail.com');

/*Table structure for table `suggestion` */

DROP TABLE IF EXISTS `suggestion`;

CREATE TABLE `suggestion` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `lid` int(11) DEFAULT NULL,
  `suggestion` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

/*Data for the table `suggestion` */

insert  into `suggestion`(`id`,`lid`,`suggestion`) values 
(1,5,'improve');

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `lid` int(11) DEFAULT NULL,
  `first_name` varchar(20) DEFAULT NULL,
  `second_name` varchar(20) DEFAULT NULL,
  `gender` varchar(20) DEFAULT NULL,
  `DOB` varchar(20) DEFAULT NULL,
  `ward` int(11) DEFAULT NULL,
  `place` varchar(50) DEFAULT NULL,
  `post` varchar(50) DEFAULT NULL,
  `pin` bigint(10) DEFAULT NULL,
  `district` varchar(50) DEFAULT NULL,
  `phone_no` bigint(10) DEFAULT NULL,
  `E-mail` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

/*Data for the table `user` */

insert  into `user`(`id`,`lid`,`first_name`,`second_name`,`gender`,`DOB`,`ward`,`place`,`post`,`pin`,`district`,`phone_no`,`E-mail`) values 
(1,2,'Mohammed','Salih. Ct','Male','8-10-1998',2,'Manjeri','Karuvambram',676123,'MALAPPURAM',7356004554,'salictmj@gmail.com');

/*Table structure for table `user_application` */

DROP TABLE IF EXISTS `user_application`;

CREATE TABLE `user_application` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` int(11) DEFAULT NULL,
  `subject` varchar(50) DEFAULT NULL,
  `application` varchar(50) DEFAULT NULL,
  `date` date DEFAULT NULL,
  `status` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

/*Data for the table `user_application` */

insert  into `user_application`(`id`,`uid`,`subject`,`application`,`date`,`status`) values 
(1,2,'My Application','Familyp_20190908_182800.jpg','2020-03-07','Accepted');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
