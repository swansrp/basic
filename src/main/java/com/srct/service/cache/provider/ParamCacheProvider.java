/**
 * Title: ParamCacheProvider.java
 * Description: Copyright: Copyright (c) 2019 Company: BHFAE
 *
 * @author Sharp
 * @date 2019-7-26 23:10
 * @description Project Name: Grote
 * @Package: com.srct.service.cache.provider
 */
package com.srct.service.cache.provider;

import com.srct.service.cache.CacheProvider;
import com.srct.service.cache.constant.FrameCacheTypeConst;
import com.srct.service.dao.entity.Parameter;
import com.srct.service.dao.mapper.ParameterDao;
import com.srct.service.utils.ReflectionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Service
@Slf4j
public class ParamCacheProvider implements CacheProvider {

    private static final String TYPE = FrameCacheTypeConst.PARAM;
    private static Map<String, Parameter> cacheMap = new TreeMap<>();

    @Resource
    private ParameterDao dao;

    @Transactional(rollbackFor = Exception.class)
    public void update(String parameterIdStr, String value) {
        Parameter param = new Parameter();
        param.setParameterId(parameterIdStr);
        param.setValue(value);
        dao.insertOrUpdateSelective(param);
        cacheMap.put(parameterIdStr, param);
    }

    @Override
    public String getType() {
        return TYPE;
    }

    @Override
    public void init() {
        if (cacheMap.isEmpty()) {
            synchronized (cacheMap) {
                if (cacheMap.isEmpty()) {
                    forceInit();
                }
            }
        }
    }

    @Override
    public synchronized void forceInit() {
        log.info("init cache {}", getType());
        List<Parameter> list = dao.selectAll();
        cacheMap = ReflectionUtil.reflectToMap(list, "parameterId");
    }

    @Override
    public Map<String, Parameter> get() {
        init();
        return cacheMap;
    }


}
