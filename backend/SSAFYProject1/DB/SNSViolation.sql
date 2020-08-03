CREATE TABLE `ssafyproject1`.`snsviolation` (
  `violation_no` INT NOT NULL AUTO_INCREMENT,
  `user_no` INT NOT NULL,
  `item` VARCHAR(45) NOT NULL,
  `date` DATE NOT NULL,
  `time` TIME NOT NULL,
  `car_num` VARCHAR(45) NOT NULL,
  `spot` VARCHAR(45) NOT NULL,
  `address` VARCHAR(50) NOT NULL,
  `contents` VARCHAR(100) NOT NULL,
  `video_url` VARCHAR(50) NULL,
  PRIMARY KEY (`violation_no`, `user_no`),
  FOREIGN KEY (`user_no`) REFERENCES `snsuser` (`user_no`))
ENGINE = InnoDB DEFAULT CHARACTER SET = utf8 COLLATE = utf8_bin;