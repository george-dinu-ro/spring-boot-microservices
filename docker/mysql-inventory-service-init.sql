USE `inventory-db`;

CREATE TABLE `inventory_tab` (
`id` bigint(20) NOT NULL AUTO_INCREMENT,
`code` int(11),
`quantity` int(11),
PRIMARY KEY(`id`)
);

INSERT INTO `inventory_tab` (`code`, `quantity`) VALUES 
(10,100),
(20,50),
(20,50),
(30,10),
(110,20),
(120,50),
(200,50);