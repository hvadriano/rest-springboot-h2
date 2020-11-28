CREATE TABLE `transaction` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `fk_account_id` bigint(20) NOT NULL,
  `due_date` datetime(6) NOT NULL,
  `transaction_value` decimal(65,2) NOT NULL,
  `transaction_type` varchar(255) DEFAULT NULL, 
  PRIMARY KEY (`id`),
  FOREIGN KEY (`fk_account_id`) REFERENCES `account` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
