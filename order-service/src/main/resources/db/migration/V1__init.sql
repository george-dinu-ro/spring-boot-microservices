CREATE TABLE `order_tab` (
`id` bigint(20) NOT NULL AUTO_INCREMENT,
`number` varchar(255) DEFAULT NULL,
`code` int(11),
`quantity` int(11),
`total_price` decimal(10,2),
PRIMARY KEY(`id`)
)