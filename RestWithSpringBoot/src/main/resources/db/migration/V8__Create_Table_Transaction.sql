CREATE TABLE `transaction` (
  `id` INT(10) AUTO_INCREMENT PRIMARY KEY,
  `fk_account_id` bigint(20) NOT NULL,
  `due_date` datetime(6) NOT NULL,
  `transaction_value` decimal(65,2) NOT NULL,
  `transaction_type` varchar(255) DEFAULT NULL, 
  CONSTRAINT `fk_transaction_account` FOREIGN KEY (`fk_account_id`) REFERENCES `account` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
