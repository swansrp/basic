/**
 * Title: ServiceApiCacheProvider.java
 * Description: Copyright: Copyright (c) 2019 Company: BHFAE
 *
 * @author Sharp
 * @date 2019-7-26 23:12
 * @description Project Name: Grote
 * @Package: com.srct.service.cache.provider
 */
package com.srct.service.cache.provider;

import com.srct.service.cache.CacheProvider;
import com.srct.service.cache.constant.FrameCacheTypeConst;
import com.srct.service.dao.entity.ServiceApi;
import com.srct.service.dao.mapper.ServiceApiDao;
import com.srct.service.utils.ReflectionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Service
@Slf4j
public class ServiceApiCacheProvider implements CacheProvider {

    private static final String TYPE = FrameCacheTypeConst.SERVICE_API;
    private static Map<String, ServiceApi> cacheMap = new TreeMap<>();

    @Resource
    private ServiceApiDao dao;

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
        List<ServiceApi> list = dao.selectAll();
        cacheMap = ReflectionUtil.reflectToMap(list, "method", "url", "apiVersion");
    }

    @Override
    public Map<String, ServiceApi> get() {
        init();
        return cacheMap;
    }

}
