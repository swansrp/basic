package com.srct.service.dao.repository;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.srct.service.dao.mapper.ParameterDao;
import com.srct.service.dao.entity.Parameter;
/**
 * Title: ${NAME}.java
 * Description: Copyright: Copyright (c) 2019 Company: BHFAE
 *
 * @author Sharp
 * @date 2019-7-26 18:23
 * @description
 * Project Name: Grote
 * @Package: ${PACKAGE_NAME}
 */
@Service
public class ParameterService{

    @Resource
    private ParameterDao parameterDao;


    public int updateBatch(List<Parameter> list) {
        return parameterDao.updateBatch(list);
    }


    public int batchInsert(List<Parameter> list) {
        return parameterDao.batchInsert(list);
    }


    public int insertOrUpdate(Parameter record) {
        return parameterDao.insertOrUpdate(record);
    }


    public int insertOrUpdateSelective(Parameter record) {
        return parameterDao.insertOrUpdateSelective(record);
    }

}
