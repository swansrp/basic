/**
 * Title: DictCacheProvider.java
 * Description: Copyright: Copyright (c) 2019 Company: BHFAE
 *
 * @author Sharp
 * @date 2019-7-26 22:45
 * @description Project Name: Grote
 * @Package: com.srct.service.cache.provider
 */
package com.srct.service.cache.provider;

import com.srct.service.cache.constant.FrameCacheTypeConst;
import com.srct.service.dao.mapper.DictionaryItemDao;
import com.srct.service.dao.entity.DictionaryItem;
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
public class DictCacheProvider implements CacheProvider {

    private static final String TYPE = FrameCacheTypeConst.DICT;
    private static Map<String, Map<String, DictionaryItem>> cacheMap = new TreeMap<>();

    @Resource
    private DictionaryItemDao dao;

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
        List<DictionaryItem> list = dao.selectAll();
        cacheMap = ReflectionUtil.reflectToDuplicateMap(list, new String[] {"dictionaryId"}, new String[] {"itemId"});
    }

    @Override
    public Map<String, Map<String, DictionaryItem>> get() {
        init();
        return cacheMap;
    }

}
