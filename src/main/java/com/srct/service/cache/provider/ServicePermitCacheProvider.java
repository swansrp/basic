/**
 * Title: ServicePermitCacheProvider.java
 * Description: Copyright: Copyright (c) 2019 Company: BHFAE
 *
 * @author Sharp
 * @date 2019-7-28 12:07
 * @description Project Name: Grote
 * @Package: com.srct.service.cache.provider
 */
package com.srct.service.cache.provider;

import com.srct.service.cache.CacheProvider;
import com.srct.service.cache.constant.FrameCacheTypeConst;
import com.srct.service.dao.entity.ServicePermit;
import com.srct.service.dao.mapper.ServicePermitDao;
import com.srct.service.utils.ReflectionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Service
@Slf4j
public class ServicePermitCacheProvider implements CacheProvider {

    private static final String TYPE = FrameCacheTypeConst.SERVICE_PERMIT;
    private static Map<String, Map<String, ServicePermit>> cacheMap = new TreeMap<>();

    @Resource
    private ServicePermitDao dao;

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
        log.info("初始化缓存 {}", getType());
        List<ServicePermit> list = dao.selectAll();
        cacheMap = ReflectionUtil.reflectToDuplicateMap(list, new String[] {"apiId"}, new String[] {"permitId"});
    }

    @Override
    public Map<String, Map<String, ServicePermit>> get() {
        init();
        return cacheMap;
    }

}
