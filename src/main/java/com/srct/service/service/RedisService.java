package com.srct.service.service;

import java.util.Date;
import java.util.List;
import java.util.Set;

public interface RedisService {

    boolean setex(String key, int seconds, String value);

    boolean setnx(String key, String value);

    boolean setnx(String key, int seconds, String value);

    byte[] getByte(String key);

    int getInt(String key);

    <T> T get(String key, Class<T> clz);

    Long incr(String key);

    Long decr(String key);

    Set<byte[]> getKeys(String pattern);

    boolean delete(String key);

    boolean hasKey(String key);

    void delete(List<String> keys);

    void setExpireTime(String key, Date expireDate);

    boolean expire(String key, long timeout);

    List<String> sMembers(String key);

    Long sAdd(String key, String value);

    Long sRem(String key, String value);

    String sRandmember(String key);

    Long lpush(String key, String... value);

    boolean hset(String key, String field, String value);

    Long hdel(String key, String fields);

    String hget(String key, String field);

    int hgetInt(String key, String field);

    void openPipeline();

    List<Object> closePipeline();

    void set(String key, Object value);

    void set(String key, int seconds, Object value);
}
