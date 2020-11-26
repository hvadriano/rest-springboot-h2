CREATE TABLE `account` (
  `id` INT(10) AUTO_INCREMENT PRIMARY KEY,
  `fk_user_id` bigint(20) NOT NULL,  
  `balance` decimal(65,2) NOT NULL,
  `create_date` datetime(6) NOT NULL,
  `enabled` BIT(1) NOT NULL DEFAULT b'1',
  CONSTRAINT `fk_user_account` FOREIGN KEY (`fk_user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
