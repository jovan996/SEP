-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema patike
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema patike
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `patike` ;
USE `patike` ;

-- -----------------------------------------------------
-- Table `korisnici`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `korisnici` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `email` VARCHAR(45) NOT NULL,
  `lozinka` VARCHAR(64) NOT NULL,
  `ime` VARCHAR(45) NULL,
  `prezime` VARCHAR(45) NULL,
  `token` VARCHAR(255) NULL,
  `sredstva` DOUBLE NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `adrese`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `adrese` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `korisnici_id` INT NOT NULL,
  `mesto` VARCHAR(45) NULL,
  `postanski_broj` VARCHAR(5) NULL,
  `adresa` VARCHAR(255) NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_adrese_korisnici_idx` (`korisnici_id` ASC),
  CONSTRAINT `fk_adrese_korisnici`
    FOREIGN KEY (`korisnici_id`)
    REFERENCES `korisnici` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `boje`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `boje` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `boja` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `brendovi`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `brendovi` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `brend` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `vrste`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `vrste` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `vrsta` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `artikli`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `artikli` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `naziv` VARCHAR(45) NULL,
  `pol` VARCHAR(1) NULL,
  `cena` DOUBLE NULL,
  `opis` MEDIUMTEXT NULL,
  `istaknut` TINYINT NULL DEFAULT 0,
  `boje_id` INT NOT NULL,
  `brendovi_id` INT NOT NULL,
  `vrste_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_artikli_boje1_idx` (`boje_id` ASC),
  INDEX `fk_artikli_brendovi1_idx` (`brendovi_id` ASC),
  INDEX `fk_artikli_vrste1_idx` (`vrste_id` ASC),
  CONSTRAINT `fk_artikli_boje1`
    FOREIGN KEY (`boje_id`)
    REFERENCES `boje` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_artikli_brendovi1`
    FOREIGN KEY (`brendovi_id`)
    REFERENCES `brendovi` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_artikli_vrste1`
    FOREIGN KEY (`vrste_id`)
    REFERENCES `vrste` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `nacini_placanja`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `nacini_placanja` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `nacin_placanja` VARCHAR(45) NOT NULL,
  `opis` VARCHAR(255) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `porudzbine`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `porudzbine` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `broj` VARCHAR(45) NOT NULL,
  `vreme` DATETIME NULL,
  `iznos` DOUBLE NULL,
  `broj_kartice` VARCHAR(45) NULL,
  `obradjena` TINYINT NULL DEFAULT 0,
  `korisnici_id` INT NOT NULL,
  `adrese_id` INT NOT NULL,
  `nacini_placanja_id` INT NOT NULL,
  `placeno` INT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `broj_UNIQUE` (`broj` ASC),
  INDEX `fk_porudzbine_korisnici1_idx` (`korisnici_id` ASC),
  INDEX `fk_porudzbine_adrese1_idx` (`adrese_id` ASC),
  INDEX `fk_porudzbine_nacini_placanja1_idx` (`nacini_placanja_id` ASC),
  CONSTRAINT `fk_porudzbine_korisnici1`
    FOREIGN KEY (`korisnici_id`)
    REFERENCES `korisnici` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_porudzbine_adrese1`
    FOREIGN KEY (`adrese_id`)
    REFERENCES `adrese` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_porudzbine_nacini_placanja1`
    FOREIGN KEY (`nacini_placanja_id`)
    REFERENCES `nacini_placanja` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `uloge`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `uloge` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `uloga` VARCHAR(45) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `porudzbine_artikli`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `porudzbine_artikli` (
  `porudzbine_id` INT NOT NULL,
  `artikli_id` INT NOT NULL,
  `cena` DOUBLE NULL,
  `kolicina` INT NULL,
  `iznos` DOUBLE NULL,
  `velicina` INT NULL,
  PRIMARY KEY (`porudzbine_id`, `artikli_id`),
  INDEX `fk_porudzbine_has_artikli_artikli1_idx` (`artikli_id` ASC),
  INDEX `fk_porudzbine_has_artikli_porudzbine1_idx` (`porudzbine_id` ASC),
  CONSTRAINT `fk_porudzbine_has_artikli_porudzbine1`
    FOREIGN KEY (`porudzbine_id`)
    REFERENCES `porudzbine` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_porudzbine_has_artikli_artikli1`
    FOREIGN KEY (`artikli_id`)
    REFERENCES `artikli` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `korisnici_uloge`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `korisnici_uloge` (
  `korisnici_id` INT NOT NULL,
  `uloge_id` INT NOT NULL,
  PRIMARY KEY (`korisnici_id`, `uloge_id`),
  INDEX `fk_korisnici_has_uloge_uloge1_idx` (`uloge_id` ASC),
  INDEX `fk_korisnici_has_uloge_korisnici1_idx` (`korisnici_id` ASC),
  CONSTRAINT `fk_korisnici_has_uloge_korisnici1`
    FOREIGN KEY (`korisnici_id`)
    REFERENCES `korisnici` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_korisnici_has_uloge_uloge1`
    FOREIGN KEY (`uloge_id`)
    REFERENCES `uloge` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `korpa`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `korpa` (
  `korisnici_id` INT NOT NULL,
  `artikli_id` INT NOT NULL,
  `kolicina` INT NULL,
  `velicina` INT NULL,
  PRIMARY KEY (`korisnici_id`, `artikli_id`),
  INDEX `fk_korisnici_has_artikli_artikli1_idx` (`artikli_id` ASC),
  INDEX `fk_korisnici_has_artikli_korisnici1_idx` (`korisnici_id` ASC),
  CONSTRAINT `fk_korisnici_has_artikli_korisnici1`
    FOREIGN KEY (`korisnici_id`)
    REFERENCES `korisnici` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_korisnici_has_artikli_artikli1`
    FOREIGN KEY (`artikli_id`)
    REFERENCES `artikli` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

-- -----------------------------------------------------
-- Data for table `boje`
-- -----------------------------------------------------
START TRANSACTION;
INSERT INTO `boje` (`id`, `boja`) VALUES (1, 'Crna');
INSERT INTO `boje` (`id`, `boja`) VALUES (2, 'Bela');
INSERT INTO `boje` (`id`, `boja`) VALUES (3, 'Plava');

COMMIT;


-- -----------------------------------------------------
-- Data for table `brendovi`
-- -----------------------------------------------------
START TRANSACTION;
INSERT INTO `brendovi` (`id`, `brend`) VALUES (1, 'Nike');
INSERT INTO `brendovi` (`id`, `brend`) VALUES (2, 'Adidas');
INSERT INTO `brendovi` (`id`, `brend`) VALUES (3, 'Puma');
INSERT INTO `brendovi` (`id`, `brend`) VALUES (4, 'All Star');
INSERT INTO `brendovi` (`id`, `brend`) VALUES (5, 'Nicola Benson');
INSERT INTO `brendovi` (`id`, `brend`) VALUES (6, 'Nero Giardini');

COMMIT;


-- -----------------------------------------------------
-- Data for table `vrste`
-- -----------------------------------------------------
START TRANSACTION;
INSERT INTO `vrste` (`id`, `vrsta`) VALUES (1, 'Patike');
INSERT INTO `vrste` (`id`, `vrsta`) VALUES (2, 'Cipele');

COMMIT;


-- -----------------------------------------------------
-- Data for table `nacini_placanja`
-- -----------------------------------------------------
START TRANSACTION;
INSERT INTO `nacini_placanja` (`id`, `nacin_placanja`, `opis`) VALUES (1, 'Kartica', 'Roba se plaća platnom karticom pre isporuke.');
INSERT INTO `nacini_placanja` (`id`, `nacin_placanja`, `opis`) VALUES (2, 'Paypall', 'Roba se plaća putem Paypall-a pre isporuke.');
INSERT INTO `nacini_placanja` (`id`, `nacin_placanja`, `opis`) VALUES (3, 'Bitcoin', 'Roba se plaća putem Bincoin-a pre isporuke.');

COMMIT;


-- -----------------------------------------------------
-- Data for table `uloge`
-- -----------------------------------------------------
START TRANSACTION;
INSERT INTO `uloge` (`id`, `uloga`) VALUES (1, 'USER');
INSERT INTO `uloge` (`id`, `uloga`) VALUES (2, 'ADMIN');

COMMIT;
