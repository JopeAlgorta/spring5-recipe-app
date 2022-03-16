# Create DBs
CREATE DATABASE recipe_dev;
CREATE DATABASE recipe_prod;

# Create Users
CREATE USER 'jose_dev'@'localhost' IDENTIFIED BY 'algorta';
CREATE USER 'jose_prod'@'localhost' IDENTIFIED BY 'algorta';
CREATE USER 'jose_dev'@'%' IDENTIFIED BY 'algorta';
CREATE USER 'jose_prod'@'%' IDENTIFIED BY 'algorta';

# User permissions
GRANT SELECT ON recipe_dev.* TO 'jose_dev'@'localhost';
GRANT INSERT ON recipe_dev.* TO 'jose_dev'@'localhost';
GRANT DELETE ON recipe_dev.* TO 'jose_dev'@'localhost';
GRANT UPDATE ON recipe_dev.* TO 'jose_dev'@'localhost';

GRANT SELECT ON recipe_prod.* TO 'jose_prod'@'localhost';
GRANT INSERT ON recipe_prod.* TO 'jose_prod'@'localhost';
GRANT DELETE ON recipe_prod.* TO 'jose_prod'@'localhost';
GRANT UPDATE ON recipe_prod.* TO 'jose_prod'@'localhost';

GRANT SELECT ON recipe_dev.* TO 'jose_dev'@'%';
GRANT INSERT ON recipe_dev.* TO 'jose_dev'@'%';
GRANT DELETE ON recipe_dev.* TO 'jose_dev'@'%';
GRANT UPDATE ON recipe_dev.* TO 'jose_dev'@'%';

GRANT SELECT ON recipe_prod.* TO 'jose_prod'@'%';
GRANT INSERT ON recipe_prod.* TO 'jose_prod'@'%';
GRANT DELETE ON recipe_prod.* TO 'jose_prod'@'%';
GRANT UPDATE ON recipe_prod.* TO 'jose_prod'@'%';