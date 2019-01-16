package com.srct.service.config.redis;

import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheWriter;

public class MyRedisCache extends RedisCache {

    private final String name;

    private final RedisCacheWriter cacheWriter;

    protected MyRedisCache(String name, RedisCacheWriter cacheWriter, RedisCacheConfiguration cacheConfig) {
        super(name, cacheWriter, cacheConfig);
        this.cacheWriter = cacheWriter;
        this.name = name;
        // TODO Auto-generated constructor stub
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.springframework.cache.support.AbstractValueAdaptingCache#lookup(java.
     * lang .Object)
     */
    @Override
    protected Object lookup(Object key) {
        byte[] value = cacheWriter.get(name, createAndConvertCacheKey(key));
        Object res = null;
        if (value != null) {
            res = deserializeCacheValue(value);
            put(key, res);
        }
        return res;
    }

    private byte[] createAndConvertCacheKey(Object key) {
        return serializeCacheKey(createCacheKey(key));
    }
}
