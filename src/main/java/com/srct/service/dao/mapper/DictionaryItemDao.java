package com.srct.service.dao.mapper;

import com.srct.service.dao.entity.DictionaryItem;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface DictionaryItemDao extends tk.mybatis.mapper.common.Mapper<DictionaryItem> {
    int updateBatch(List<DictionaryItem> list);

    int batchInsert(@Param("list") List<DictionaryItem> list);

    int insertOrUpdate(DictionaryItem record);

    int insertOrUpdateSelective(DictionaryItem record);
}
