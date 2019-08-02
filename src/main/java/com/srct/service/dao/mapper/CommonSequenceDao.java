/**
 * Title: CommonSequenceDao.java
 * Description: Copyright: Copyright (c) 2019 Company: BHFAE
 *
 * @author Sharp
 * @date 2019-7-30 13:36
 * @description Project Name: Grote
 * @Package: com.srct.service.dao.mapper
 */
package com.srct.service.dao.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface CommonSequenceDao {

    @Select("SELECT f_nextval(#{seqName})")
    String getSeq(String seqName);
    
}
