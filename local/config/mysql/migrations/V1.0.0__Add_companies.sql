CREATE TABLE `nasdaq`.`companies` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `exchange` VARCHAR(20) NOT NULL,
    `symbol` VARCHAR(10) NOT NULL,
    `name` VARCHAR(200) NOT NULL,
    `sector` VARCHAR(100) NOT NULL,
    `subsector` VARCHAR(100) NOT NULL,
    `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`));