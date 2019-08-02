/**
 * Title: CommonSequenceService.java
 * Description: Copyright: Copyright (c) 2019 Company: BHFAE
 *
 * @author Sharp
 * @date 2019-7-30 13:35
 * @description Project Name: Grote
 * @Package: com.srct.service.dao.repository
 */
package com.srct.service.dao.repository;

import com.srct.service.dao.mapper.CommonSequenceDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class CommonSequenceService {

    @Resource
    private CommonSequenceDao commonSequenceDao;

    public String getSeq(String seqName) {
        return commonSequenceDao.getSeq(seqName);
    }
}
