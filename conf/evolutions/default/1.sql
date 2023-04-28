# BasicForm schema

# --- !Ups
CREATE TABLE IF NOT EXISTS `new_schema`.`basicform` (
  `id` VARCHAR(15) NOT NULL ,
  `company_id` VARCHAR(15) NOT NULL ,
  `emp_id` VARCHAR(15) NOT NULL ,
  `age` INT(2) NOT NULL,
  PRIMARY KEY (`id`)
  )

DEFAULT CHARACTER SET = utf8

# --- !Downs
drop table 'basicform'