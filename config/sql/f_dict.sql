delimiter $$
CREATE
  DEFINER =`root`@`localhost` FUNCTION `f_dict`(`DICTIONARY_ID_IN` VARCHAR(50),
                                                `ITEM_ID_IN` VARCHAR(50))
  RETURNS varchar(50) CHARSET utf8
  LANGUAGE SQL
  DETERMINISTIC
  CONTAINS SQL
  SQL SECURITY DEFINER
  COMMENT ''
BEGIN
  declare i INT;
  declare ITEM_NAME VARCHAR(50);
  select COUNT(1) INTO i
  from co_dictionary_item t
  where t.dictionary_id = DICTIONARY_ID_IN
    and t.item_id = ITEM_ID_IN;
  CASE
    WHEN i = 0
      THEN SET ITEM_NAME = ITEM_ID_IN;
    ELSE
      SELECT t.item_name INTO ITEM_NAME
      FROM co_dictionary_item t
      WHERE t.dictionary_id = DICTIONARY_ID_IN
        AND t.item_id = ITEM_ID_IN;
    END CASE;
  return (ITEM_NAME);
END $$
delimiter ;
