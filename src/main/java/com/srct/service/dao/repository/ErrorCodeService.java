package com.srct.service.dao.repository;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.srct.service.dao.mapper.ErrorCodeDao;
import com.srct.service.dao.entity.ErrorCode;

/**
 * Title: ${NAME}.java
 * Description: Copyright: Copyright (c) 2019 Company: BHFAE
 *
 * @author Sharp
 * @date 2019-7-26 23:02
 * @description Project Name: Grote
 * @Package: ${PACKAGE_NAME}
 */
@Service
public class ErrorCodeService {

    @Resource
    private ErrorCodeDao errorCodeDao;


    public int updateBatch(List<ErrorCode> list) {
        return errorCodeDao.updateBatch(list);
    }


    public int batchInsert(List<ErrorCode> list) {
        return errorCodeDao.batchInsert(list);
    }


    public int insertOrUpdate(ErrorCode record) {
        return errorCodeDao.insertOrUpdate(record);
    }


    public int insertOrUpdateSelective(ErrorCode record) {
        return errorCodeDao.insertOrUpdateSelective(record);
    }

}

