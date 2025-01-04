CREATE TABLE `order_tab` (
`id` bigint(20) NOT NULL AUTO_INCREMENT,
`number` varchar(255) DEFAULT NULL,
`code` varchar(255),
`quantity` int (11),
PRIMARY KEY(`id`)
)