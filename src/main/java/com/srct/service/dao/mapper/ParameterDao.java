package com.srct.service.dao.mapper;

import com.srct.service.dao.entity.Parameter;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ParameterDao extends tk.mybatis.mapper.common.Mapper<Parameter> {
    int updateBatch(List<Parameter> list);

    int batchInsert(@Param("list") List<Parameter> list);

    int insertOrUpdate(Parameter record);

    int insertOrUpdateSelective(Parameter record);
}
