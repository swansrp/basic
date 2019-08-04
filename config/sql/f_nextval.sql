delimiter $$
CREATE
  DEFINER =`root`@`localhost` FUNCTION `f_nextval`(
  `SEQ_NAME` VARCHAR(128)
)
  RETURNS varchar(50) CHARSET utf8
  LANGUAGE SQL
  NOT DETERMINISTIC
  CONTAINS SQL
  SQL SECURITY DEFINER
  COMMENT '获取流水号'
BEGIN
  declare existed int default 0;
  declare cur int default 0;
  declare next int default 0;
  declare _min int default 0;
  declare _max int default 0;
  declare _step int default 0;
  declare _prefix varchar(20) default '';
  declare _suffix varchar(20) default '';
  declare result varchar(50) default '';

  select count(1) into existed from sequence seq where seq.seq_name = SEQ_NAME;
  CASE
    when existed = 0
      then set result = '';
    ELSE
      select value, prefix, suffix, min_value, max_value, step into cur,_prefix,_suffix,_min,_max,_step
      from sequence seq
      where seq.seq_name = SEQ_NAME;
      if cur < _min then
        set cur = _min;
      end if;
      if cur > _max then
        set cur = _min;
      end if;
      set next = cur + _step;
      if next > _max then
        set next = _min;
      end if;
      if _prefix = 'now' then
        set _prefix = date_format(now(), '%Y%m%d%H%i%s');
      end if;
      UPDATE sequence seq SET seq.value=next WHERE seq.seq_name = SEQ_NAME;
      SELECT CONCAT(_prefix, cur, _suffix) into result;
    END CASE;
  return (result);
END $$
delimiter ;
