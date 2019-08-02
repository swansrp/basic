/**
 * Title: BaseCacheComposite.java
 * Description: Copyright: Copyright (c) 2019 Company: BHFAE
 *
 * @author Sharp
 * @date 2019-7-26 18:18
 * @description Project Name: Grote
 * @Package: com.srct.service.cache.base
 */
package com.srct.service.cache.base;

import com.srct.service.cache.CacheProvider;

import java.util.Map;
import java.util.TreeMap;

public abstract class BaseCacheComposite implements CacheProvider {

    protected static Map<String, CacheProvider> providerMap = new TreeMap<>();

    @Override
    public synchronized void forceInit() {
        for (CacheProvider cacheProvider : providerMap.values()) {
            cacheProvider.forceInit();
        }
    }

    @Override
    public Map<String, CacheProvider> get() {
        return (Map<String, CacheProvider>) providerMap;
    }

    @Override
    public String getType() {
        return null;
    }

    protected void put(CacheProvider... cacheProvider) {
        for (CacheProvider provider : cacheProvider) {
            providerMap.put(provider.getType(), provider);
        }
    }

    @SuppressWarnings("unchecked")
    public <T> T getProvider(Class<T> clazz) {
        for (CacheProvider cacheProvider : providerMap.values()) {
            if (clazz.isInstance(cacheProvider)) {
                return (T) cacheProvider;
            }
        }
        return null;
    }
}
