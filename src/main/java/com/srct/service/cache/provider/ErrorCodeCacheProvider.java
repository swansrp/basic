/**
 * Title: ErrorCodeCacheProvider.java
 * Description: Copyright: Copyright (c) 2019 Company: BHFAE
 *
 * @author Sharp
 * @date 2019-7-26 22:59
 * @description Project Name: Grote
 * @Package: com.srct.service.cache.provider
 */
package com.srct.service.cache.provider;

import com.srct.service.cache.constant.FrameCacheTypeConst;
import com.srct.service.dao.mapper.ErrorCodeDao;
import com.srct.service.dao.entity.ErrorCode;
import com.srct.service.cache.CacheProvider;
import com.srct.service.utils.ReflectionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Service
@Slf4j
public class ErrorCodeCacheProvider implements CacheProvider {

    private static final String TYPE = FrameCacheTypeConst.ERROR_CODE;
    private static Map<String, ErrorCode> cacheMap = new TreeMap<>();

    @Resource
    private ErrorCodeDao dao;

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
        List<ErrorCode> list = dao.selectAll();
        cacheMap = ReflectionUtil.reflectToMap(list, "errCode");
    }

    @Override
    public Map<String, ErrorCode> get() {
        init();
        return cacheMap;
    }

}
