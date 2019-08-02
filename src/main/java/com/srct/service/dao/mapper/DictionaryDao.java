package com.srct.service.dao.mapper;

import com.srct.service.dao.entity.Dictionary;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface DictionaryDao extends tk.mybatis.mapper.common.Mapper<Dictionary> {
    int updateBatch(List<Dictionary> list);

    int batchInsert(@Param("list") List<Dictionary> list);

    int insertOrUpdate(Dictionary record);

    int insertOrUpdateSelective(Dictionary record);
}
