package com.srct.service.dao.repository;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.srct.service.dao.mapper.DictionaryItemDao;
import com.srct.service.dao.entity.DictionaryItem;
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
public class DictionaryItemService{

    @Resource
    private DictionaryItemDao dictionaryItemDao;


    public int updateBatch(List<DictionaryItem> list) {
        return dictionaryItemDao.updateBatch(list);
    }


    public int batchInsert(List<DictionaryItem> list) {
        return dictionaryItemDao.batchInsert(list);
    }


    public int insertOrUpdate(DictionaryItem record) {
        return dictionaryItemDao.insertOrUpdate(record);
    }


    public int insertOrUpdateSelective(DictionaryItem record) {
        return dictionaryItemDao.insertOrUpdateSelective(record);
    }

}
