-- MySQL Script generated by MySQL Workbench
-- Tue 10 Mar 2020 06:11:21 PM CET
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema bank
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema bank
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `patike_pcc` ;
USE `patike_pcc`;

-- -----------------------------------------------------
-- Table `cards`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cards` (
  `pan` VARCHAR(16) NOT NULL,
  `bank_url` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`pan`)
)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Data for table `bank`.`cards`
-- -----------------------------------------------------
START TRANSACTION;

INSERT INTO `cards` (`pan`, `bank_url`) VALUES ('1234432156788790', 'http://localhost:8091/payment/pcc');
INSERT INTO `cards` (`pan`, `bank_url`) VALUES ('1719354780010981', 'http://localhost:8091/payment/pcc');
INSERT INTO `cards` (`pan`, `bank_url`) VALUES ('7171843290650912', 'http://localhost:8092/payment/pcc');

COMMIT;

