package com.srct.service.dao.mapper;

import com.srct.service.dao.entity.ServiceApi;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ServiceApiDao extends tk.mybatis.mapper.common.Mapper<ServiceApi> {
    int updateBatch(List<ServiceApi> list);

    int batchInsert(@Param("list") List<ServiceApi> list);

    int insertOrUpdate(ServiceApi record);

    int insertOrUpdateSelective(ServiceApi record);
}