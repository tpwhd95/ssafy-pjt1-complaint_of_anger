CREATE TABLE `ssafyproject1`.`snsuser` (
  `user_no` INT NOT NULL AUTO_INCREMENT,
  `email` VARCHAR(45) NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  `gender` VARCHAR(45) NOT NULL,
  `birth` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`user_no`))
ENGINE = InnoDB DEFAULT CHARACTER SET = utf8 COLLATE = utf8_bin;