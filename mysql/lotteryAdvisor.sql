-- MySQL Script generated by MySQL Workbench
-- Wed 02 Aug 2017 05:30:39 PM EEST
-- Model: New Model    Version: 1.0
SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema lottery
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `lottery` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;
USE `lottery` ;

-- -----------------------------------------------------
-- Table `lottery`.`lottery_games`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `lottery`.`lottery_games` ;

CREATE TABLE IF NOT EXISTS `lottery`.`lottery_games` (
  `id` INT(11) NOT NULL,
  `name` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `idlottery_games_UNIQUE` (`id` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `lottery`.`lottery_number_frequences`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `lottery`.`lottery_number_frequences` ;

CREATE TABLE IF NOT EXISTS `lottery`.`lottery_number_frequences` (
  `id` BIGINT NOT NULL,
  `ajo` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `start_date` DATE NOT NULL,
  `end_date` DATE NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `idkeno_frekvenssit_UNIQUE` (`id` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `lottery`.`lottery_numbers`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `lottery`.`lottery_numbers` ;

CREATE TABLE IF NOT EXISTS `lottery`.`lottery_numbers` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '		',
  `brand_name` VARCHAR(40) NOT NULL,
  `close_time` DATETIME NULL DEFAULT NULL,
  `draw_time` DATETIME NULL,
  `game_name` VARCHAR(10) NOT NULL,
  `veikkaus_id` INT(11) NOT NULL,
  `primary_results` VARCHAR(100) NOT NULL,
  `secondary_results` VARCHAR(100) NULL,
  `played` TINYINT(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC))
ENGINE = InnoDB
AUTO_INCREMENT = 530
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `lottery`.`lottery_played_draws`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `lottery`.`lottery_played_draws` ;

CREATE TABLE IF NOT EXISTS `lottery`.`lottery_played_draws` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `row` INT(11) NOT NULL,
  `primary_numbers` VARCHAR(30) NOT NULL,
  `secondary_numbers` VARCHAR(30) NULL,
  `lottery_numbers_id` BIGINT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `idkeno_veikkaus_UNIQUE` (`id` ASC),
  INDEX `fk_lottery_played_rows_lottery_numbers1_idx` (`lottery_numbers_id` ASC),
  CONSTRAINT `fk_lottery_played_rows_lottery_numbers1`
    FOREIGN KEY (`lottery_numbers_id`)
    REFERENCES `lottery`.`lottery_numbers` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 26
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `lottery`.`work_queue`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `lottery`.`work_queue` ;

CREATE TABLE IF NOT EXISTS `lottery`.`work_queue` (
  `id` INT NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
