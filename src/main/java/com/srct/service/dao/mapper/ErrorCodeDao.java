package com.srct.service.dao.mapper;

import com.srct.service.dao.entity.ErrorCode;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ErrorCodeDao extends tk.mybatis.mapper.common.Mapper<ErrorCode> {
    int updateBatch(List<ErrorCode> list);

    int batchInsert(@Param("list") List<ErrorCode> list);

    int insertOrUpdate(ErrorCode record);

    int insertOrUpdateSelective(ErrorCode record);
}
