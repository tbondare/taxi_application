CREATE SCHEMA `taxi_schema` DEFAULT CHARACTER SET utf8 ;

CREATE TABLE `taxi_schema`.`manufacturers` (
                                                    `id` BIGINT(11) NOT NULL AUTO_INCREMENT,
                                                    `name` VARCHAR(225) NOT NULL,
                                                    `country` VARCHAR(225) NOT NULL,
                                                    `deleted` TINYINT NOT NULL DEFAULT 0,
                                                    PRIMARY KEY (`id`));

CREATE TABLE `taxi_schema`.`drivers` (
                                                    `id` BIGINT(11) NOT NULL AUTO_INCREMENT,
                                                    `name` VARCHAR(225) NOT NULL,
                                                    `license_number` VARCHAR(225) NOT NULL,
                                                    `login` VARCHAR(225) NOT NULL,
                                                    `password` VARCHAR(225) NOT NULL,
                                                    `deleted` TINYINT NOT NULL DEFAULT 0,
                                                    PRIMARY KEY (`id`));

CREATE TABLE `taxi_schema`.`cars` (
                                                  `car_id` BIGINT(11) NOT NULL AUTO_INCREMENT,
                                                  `model` VARCHAR(225) NOT NULL,
                                                  `manufacturer_id` BIGINT(11) NOT NULL,
                                                  `deleted` TINYINT NOT NULL DEFAULT 0,
                                                  PRIMARY KEY (`car_id`),
                                                  INDEX `car_manufacturer_fk_idx` (`manufacturer_id` ASC) VISIBLE,
                                                  CONSTRAINT `car_manufacturer_fk`
                                                      FOREIGN KEY (`manufacturer_id`)
                                                          REFERENCES `taxi_schema`.`manufacturers` (`id`)
                                                          ON DELETE NO ACTION
                                                          ON UPDATE NO ACTION);

CREATE TABLE `taxi_schema`.`relations` (
                                                    `driver_id` BIGINT(11) NOT NULL,
                                                    `car_id` BIGINT(11) NOT NULL,
                                                    INDEX `car_id_car_fk_idx` (`car_id` ASC) VISIBLE,
                                                    INDEX `driver_id_drivers_fk_idx` (`driver_id` ASC) VISIBLE,
                                                    CONSTRAINT `car_id_cars_fk`
                                                        FOREIGN KEY (`car_id`)
                                                            REFERENCES `taxi_schema`.`cars` (`car_id`)
                                                            ON DELETE NO ACTION
                                                            ON UPDATE NO ACTION,
                                                    CONSTRAINT `driver_id_drivers_fk`
                                                        FOREIGN KEY (`driver_id`)
                                                            REFERENCES `taxi_schema`.`drivers` (`id`)
                                                            ON DELETE NO ACTION
                                                            ON UPDATE NO ACTION);

