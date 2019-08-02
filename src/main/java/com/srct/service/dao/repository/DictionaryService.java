package com.srct.service.dao.repository;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.srct.service.dao.entity.Dictionary;
import com.srct.service.dao.mapper.DictionaryDao;
/**
 * Title: ${NAME}.java
 * Description: Copyright: Copyright (c) 2019 Company: BHFAE
 *
 * @author Sharp
 * @date 2019-7-26 18:24
 * @description
 * Project Name: Grote
 * @Package: ${PACKAGE_NAME}
 */
@Service
public class DictionaryService{

    @Resource
    private DictionaryDao dictionaryDao;


    public int updateBatch(List<Dictionary> list) {
        return dictionaryDao.updateBatch(list);
    }


    public int batchInsert(List<Dictionary> list) {
        return dictionaryDao.batchInsert(list);
    }


    public int insertOrUpdate(Dictionary record) {
        return dictionaryDao.insertOrUpdate(record);
    }


    public int insertOrUpdateSelective(Dictionary record) {
        return dictionaryDao.insertOrUpdateSelective(record);
    }

}
