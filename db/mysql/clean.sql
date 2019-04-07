SET FOREIGN_KEY_CHECKS = 0;

SET @table_names = NULL;

SELECT GROUP_CONCAT('`', table_name, '`') INTO @table_names
FROM information_schema.TABLES
WHERE table_schema = 'test';

SELECT IFNULL(@table_names, 'dummy') INTO @tables;

SET @table_names = CONCAT('DROP TABLE IF EXISTS ', @table_names);

PREPARE stmt FROM @table_names;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

SET FOREIGN_KEY_CHECKS = 1;
