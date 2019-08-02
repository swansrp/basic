package com.srct.service.dao.repository;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.srct.service.dao.entity.ServiceApi;
import com.srct.service.dao.mapper.ServiceApiDao;

/**
 * Title: ${NAME}.java
 * Description: Copyright: Copyright (c) 2019 Company: BHFAE
 *
 * @author Sharp
 * @date 2019-7-26 18:24
 * @description Project Name: Grote
 * @Package: ${PACKAGE_NAME}
 */
@Service
public class ServiceApiService {

    @Resource
    private ServiceApiDao serviceApiDao;


    public int updateBatch(List<ServiceApi> list) {
        return serviceApiDao.updateBatch(list);
    }


    public int batchInsert(List<ServiceApi> list) {
        return serviceApiDao.batchInsert(list);
    }


    public int insertOrUpdate(ServiceApi record) {
        return serviceApiDao.insertOrUpdate(record);
    }


    public int insertOrUpdateSelective(ServiceApi record) {
        return serviceApiDao.insertOrUpdateSelective(record);
    }

}






