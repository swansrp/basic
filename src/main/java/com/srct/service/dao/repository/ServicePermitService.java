package com.srct.service.dao.repository;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.srct.service.dao.mapper.ServicePermitDao;
import com.srct.service.dao.entity.ServicePermit;
/**
 * Title: ${NAME}.java 
 * Description: Copyright: Copyright (c) 2019 Company: BHFAE
 * 
 * @author Sharp
 * @date 2019-7-28 12:06
 * @description
 * Project Name: Grote
 * @Package: ${PACKAGE_NAME}
 */
@Service
public class ServicePermitService{

    @Resource
    private ServicePermitDao servicePermitDao;

    
    public int updateBatch(List<ServicePermit> list) {
        return servicePermitDao.updateBatch(list);
    }

    
    public int batchInsert(List<ServicePermit> list) {
        return servicePermitDao.batchInsert(list);
    }

    
    public int insertOrUpdate(ServicePermit record) {
        return servicePermitDao.insertOrUpdate(record);
    }

    
    public int insertOrUpdateSelective(ServicePermit record) {
        return servicePermitDao.insertOrUpdateSelective(record);
    }

}
