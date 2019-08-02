package com.srct.service.dao.mapper;

import com.srct.service.dao.entity.ServicePermit;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ServicePermitDao extends tk.mybatis.mapper.common.Mapper<ServicePermit> {
    int updateBatch(List<ServicePermit> list);

    int batchInsert(@Param("list") List<ServicePermit> list);

    int insertOrUpdate(ServicePermit record);

    int insertOrUpdateSelective(ServicePermit record);
}