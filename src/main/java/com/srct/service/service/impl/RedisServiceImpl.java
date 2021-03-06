package com.srct.service.service.impl;

import com.srct.service.service.RedisService;
import com.srct.service.utils.JSONUtil;
import com.srct.service.utils.log.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class RedisServiceImpl implements RedisService {

    private final static long DEFAULTTIME = 600L;

    @Autowired
    @Qualifier("byteRedisTemplate")
    private RedisTemplate<String, byte[]> redisTemplate;

    @Autowired
    @Qualifier("redisTemplate")
    private RedisTemplate<String, Object> objectRedisTemplate;

    private ZSetOperations<String, Object> opsForZSet;

    /**
     * @param key
     */
    @Override
    public boolean delete(final String key) {
        if (StringUtils.isEmpty(key)) {
            return false;
        }
        boolean result = redisTemplate.delete(key);
        // log.debug("delete key[{}]， result[{}]", key, result);
        return result;
    }

    @Override
    public boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * @param keys
     */
    @Override
    public void delete(final List<String> keys) {
        if (null == keys || keys.isEmpty()) {
            return;
        }
        redisTemplate.delete(keys);
    }

    /**
     * @param key
     * @param expireDate
     */
    public void setExpireTime(final String key, final Date expireDate) {
        if (StringUtils.isEmpty(key)) {
            log.error(" setExpireTime key is null");
            return;
        }
        log.debug(" setExpireTime key[{}], date[{}]", key, expireDate);
        redisTemplate.expireAt(key, expireDate);
    }

    public void publish(final String topic, final Object message) {
        if (StringUtils.isEmpty(topic) || StringUtils.isEmpty(message)) {
            log.error(" publish topic : " + topic + " message : " + JSONUtil.toJSONString(message));
        }
        log.debug(" publish topic[{}], message[{}]", topic, message);
        redisTemplate.convertAndSend(topic, message);
    }

    /**
     * @param topic
     * @param message
     */
    public Long publish(final String topic, final String message) {
        if (StringUtils.isEmpty(topic) || StringUtils.isEmpty(message)) {
            log.error(" publish topic : " + topic + " message : " + message);
            return null;
        }
        log.debug(" publish topic[{}], message[{}]", topic, message);
        Long result = redisTemplate
                .execute((RedisCallback<Long>) connection -> connection.publish(topic.getBytes(), message.getBytes()));
        return result;
    }

    public void subscribe(final MessageListener listener, final String topic) {
        if (StringUtils.isEmpty(topic)) {
            log.error(" subscribe topic is null");
            return;
        }
        log.debug(" subscribe topic[{}]", topic);
        redisTemplate.execute((RedisCallback<String>) connection -> {
            connection.subscribe(listener, topic.getBytes());
            return null;
        });
    }

    public void add(final String key, final Object message, double score) {
        opsForZSet = objectRedisTemplate.opsForZSet();
        opsForZSet.add(key, message, score);
    }

    public Set<Object> rangeByScore(final String key, double min, double max) {
        opsForZSet = objectRedisTemplate.opsForZSet();
        return opsForZSet.rangeByScore(key, min, max);
    }

    /* (non-Javadoc)
     * @see com.srct.service.service.RedisService#set(java.lang.String, java.lang.Object)
     */
    @Override
    public void set(String key, Object value) {
        // TODO Auto-generated method stub
        if (value == null || StringUtils.isEmpty(key)) {
            log.error(" set key[{}], value[{}] is null", key, value);
            return;
        }
        objectRedisTemplate.opsForValue().set(key, value);
        return;
    }

    @Override
    public void set(String key, int seconds, Object value) {
        // TODO Auto-generated method stub
        if (value == null || StringUtils.isEmpty(key)) {
            log.error(" set key[{}], value[{}] is null", key, value);
            return;
        }
        objectRedisTemplate.opsForValue().set(key, value, seconds, TimeUnit.SECONDS);
        return;
    }

    @Override
    public boolean setex(final String key, final int seconds, final String value) {
        if (StringUtils.isEmpty(value) || StringUtils.isEmpty(key)) {
            log.error(" setex key[{}]value[{}] is null", key, value);
            return false;
        }
        boolean result = redisTemplate.execute((RedisCallback<Boolean>) connection -> {
            connection.setEx(key.getBytes(), seconds, value.getBytes());
            return true;
        });
        log.debug(" setex key[{}], value[{}], result[{}]", key, value, result);
        return result;
    }

    @Override
    public boolean setnx(final String key, final String value) {
        if (StringUtils.isEmpty(value) || StringUtils.isEmpty(key)) {
            log.error(" setnx key[" + key + "] or value [" + value + "]is null");
            return false;
        }
        log.debug(" setnx key[" + key + "] or value [" + value + "]");
        boolean result = redisTemplate.execute((RedisCallback<Boolean>) connection -> {
            boolean ret = connection.setNX(key.getBytes(), value.getBytes());
            return ret;
        });
        return result;
    }

    @Override
    public boolean setnx(final String key, final int seconds, final String value) {
        if (StringUtils.isEmpty(value) || StringUtils.isEmpty(key)) {
            log.error(" setnx key[{}] or value [{}] , seconds[{}]", key, value, seconds);
            return false;
        }
        log.debug(" setnx key[{}], value[{}], seconds[{}]", key, value, seconds);
        boolean result = redisTemplate.execute((RedisCallback<Boolean>) connection -> {
            boolean ret = connection.setNX(key.getBytes(), value.getBytes());
            if (ret) {
                connection.expire(key.getBytes(), seconds);
            }
            return ret;
        });
        return result;
    }

    /**
     * @param key
     * @return
     */
    @Override
    public byte[] getByte(final String key) {
        if (StringUtils.isEmpty(key)) {
            log.error(" get key is null error");
            return null;
        }
        byte[] result = redisTemplate.execute((RedisCallback<byte[]>) connection -> {
            byte[] value = connection.get(key.getBytes());
            if (null == value || value.length <= 0) {
                log.error(" get key[{}] value is null error", key);
                return null;
            }
            return value;
        });
        log.debug(" get key[{}], result[{}]", key, result);
        return result;
    }

    public <T> T get(String key) {
        if (StringUtils.isEmpty(key)) {
            log.error(" get key is null error");
            return null;
        }
        Object obj = null;
        try {
            obj = objectRedisTemplate.opsForValue().get(key);
            T result = (T) obj;
            return result;
        } catch (Exception e) {
            log.error(" get key[{}], parse value[{}] exception : {}", key, JSONUtil.toJSONString(obj), e.toString());
            return null;
        }
    }

    /* (non-Javadoc)
     * @see com.srct.service.service.RedisService#get(java.lang.String, java.lang.Class)
     */
    @Override
    public <T> T get(String key, Class<T> clz) {
        return get(key);
    }

    @Override
    public int getInt(String key) {
        String value = get(key);
        if (StringUtils.isEmpty(value)) {
            log.error(" getInt key[{}], value[{}] is null error", key, value);
            return 0;
        }
        log.debug(" getInt key[" + key + "] value[" + value + "]");
        try {
            return Integer.valueOf(value);
        } catch (Exception e) {
            log.error(" getInt key[" + key + "] value[" + value + "] Exception : " + e.toString());
            return 0;
        }
    }

    @Override
    public Long incr(final String key) {
        if (StringUtils.isEmpty(key)) {
            log.error(" incr key[" + key + "] is null error");
            return null;
        }
        Long result = redisTemplate.execute((RedisCallback<Long>) connection -> connection.incr(key.getBytes()));
        log.debug(" incr key[" + key + "] result [" + result + "]");
        return result;
    }

    @Override
    public Long decr(final String key) {
        if (StringUtils.isEmpty(key)) {
            log.error(" decr key[" + key + "] is null error");
            return null;
        }
        Long result = redisTemplate.execute((RedisCallback<Long>) connection -> connection.decr(key.getBytes()));
        log.debug(" incr key[" + key + "] result [" + result + "]");
        return result;
    }

    /**
     * @param pattern
     * @return
     */
    public Set<byte[]> getKeys(final String pattern) {
        if (StringUtils.isEmpty(pattern)) {
            log.error(" getKeys pattern is null error");
            return null;
        }
        log.debug(" getKeys pattern[" + pattern + "]");
        return redisTemplate.execute(new RedisCallback<Set<byte[]>>() {

            @Override
            public Set<byte[]> doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.keys(pattern.getBytes());
            }
        });
    }

    @Override
    public List<String> sMembers(final String key) {
        if (StringUtils.isEmpty(key)) {
            log.error(" sMembers key is empty error");
            return null;
        }
        log.debug(" sMembers key[" + key + "]");
        Set<byte[]> members =
                redisTemplate.execute((RedisCallback<Set<byte[]>>) connection -> connection.sMembers(key.getBytes()));
        if (null == members || members.isEmpty()) {
            log.error(" sMembers key[" + key + "] members is empty error");
            return null;
        }
        List<String> list = new ArrayList<String>();
        String member = null;
        for (byte[] tmp : members) {
            member = new String(tmp);
            log.debug(" sMembers member[" + member + "]");
            list.add(member);
        }
        return list;
    }

    @Override
    public Long sAdd(final String key, final String value) {
        if (StringUtils.isEmpty(value) || StringUtils.isEmpty(key)) {
            log.error(" sAdd key[" + key + "]  value[" + value + "] is null error");
            return null;
        }
        Long result = redisTemplate
                .execute((RedisCallback<Long>) connection -> connection.sAdd(key.getBytes(), value.getBytes()));
        log.debug(" sAdd key[" + key + "]  value[" + value + "] result[" + result + "]");
        return result;
    }

    @Override
    public Long lpush(String key, String... value) {
        if (StringUtils.isEmpty(value) || StringUtils.isEmpty(key)) {
            log.error(" lpush key[" + key + "]  value[" + value + "] is null error");
            return null;
        }
        byte[][] a = new byte[value.length][];
        int index = 0;
        for (String item : value) {
            a[index] = item.getBytes();
            index++;
        }
        Long result = redisTemplate.execute((RedisCallback<Long>) connection -> connection.lPush(key.getBytes(), a));
        log.debug(" lpush key[" + key + "]  value[" + value + "] result[" + result + "]");
        return result;
    }

    @Override
    public Long sRem(final String key, final String value) {
        if (StringUtils.isEmpty(value) || StringUtils.isEmpty(key)) {
            log.error(" sRem key[" + key + "]  value[" + value + "] is null error");
            return null;
        }
        Long result = redisTemplate
                .execute((RedisCallback<Long>) connection -> connection.sRem(key.getBytes(), value.getBytes()));
        log.debug(" sRem key[" + key + "]  value[" + value + "] result[" + result + "]");
        return result;
    }

    @Override
    public String sRandmember(final String key) {
        if (StringUtils.isEmpty(key)) {
            log.error(" sRandmember key[" + key + "] is null error");
            return null;
        }
        String result = redisTemplate.execute((RedisCallback<String>) connection -> {
            byte[] mem = connection.sRandMember(key.getBytes());
            if (null == mem || mem.length <= 0) {
                log.error("sRandmember mem is empty error");
                return null;
            }
            return new String(mem);
        });
        log.debug(" sRandmember key[" + key + "] result[" + result + "]");
        return result;
    }

    @Override
    public boolean hset(final String key, final String field, final String value) {
        if (StringUtils.isEmpty(key) || StringUtils.isEmpty(value) || StringUtils.isEmpty(field)) {
            log.error(" hset key[" + key + "] field[" + field + "] value[" + value + "]  is null error");
            return false;
        }
        boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {

            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.hSet(key.getBytes(), field.getBytes(), value.getBytes());
            }
        });
        log.debug(" hset key[" + key + "] field[" + field + "] value[" + value + "] result[" + result + "]");
        return result;
    }

    @Override
    public Long hdel(final String key, final String field) {
        if (StringUtils.isEmpty(key) || StringUtils.isEmpty(field)) {
            log.error(" hdel key[" + key + "] field[" + field + "]  is null error");
            return null;
        }
        Long result = redisTemplate.execute(new RedisCallback<Long>() {

            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.hDel(key.getBytes(), field.getBytes());
            }
        });
        log.debug(" hdel key[" + key + "] field[" + field + "] result[" + result + "]");
        return result;
    }

    @Override
    public String hget(final String key, final String field) {
        if (StringUtils.isEmpty(key) || StringUtils.isEmpty(field)) {
            log.error(" hget key[" + key + "] field[" + field + "]  is null error");
            return null;
        }
        String result = redisTemplate.execute(new RedisCallback<String>() {

            @Override
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                byte buf[] = connection.hGet(key.getBytes(), field.getBytes());
                if (null == buf || buf.length <= 0) {
                    log.error(" hGet buf is null error key[" + key + "] field[" + field + "]");
                    return null;
                }
                return new String(buf);
            }
        });
        log.debug(" hget key[" + key + "] field[" + field + "] result[" + result + "]");
        return result;
    }

    @Override
    public int hgetInt(String key, String field) {
        String value = hget(key, field);
        if (StringUtils.isEmpty(value)) {
            log.error(" hget key[" + key + "] field[" + field + "] value[" + value + "] is null error");
            return 0;
        }
        log.debug(" hget key[" + key + "] field[" + field + "] value[" + value + "]");
        try {
            return Integer.valueOf(value);
        } catch (Exception e) {
            log.error(" hGetInt Exception : " + e.toString());
            return 0;
        }
    }

    @Override
    public void openPipeline() {
        String result = redisTemplate.execute(new RedisCallback<String>() {

            @Override
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                connection.openPipeline();
                return null;
            }
        });
    }

    @Override
    public List<Object> closePipeline() {
        return redisTemplate.execute(new RedisCallback<List<Object>>() {

            @Override
            public List<Object> doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.closePipeline();
            }
        });
    }

    @Override
    public boolean expire(final String key, final long timeout) {
        if (StringUtils.isEmpty(key)) {
            log.error(" expire key[" + key + "] is null error");
            return false;
        }
        boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {

            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.expire(key.getBytes(), timeout);
            }
        });
        log.error(" expire key[" + key + "] result[" + result + "]");
        return result;
    }
    // /**
    // * 设置有超时时间的KV
    // */
    // @Override
    // public void addData(RedisKeyDto redisKeyDto) {
    // redisTemplate.execute(new RedisCallback<Object>() {
    // public Object doInRedis(RedisConnection connection)
    // throws DataAccessException {
    // connection.set(
    // redisTemplate.getStringSerializer().serialize(redisKeyDto.getKeys()),
    // redisTemplate.getStringSerializer().serialize(redisKeyDto.getValues())
    // );
    // return null;
    // }
    // });
    // }
    //
    // @Override
    // public void delete(RedisKeyDto redisKeyDto) {
    // redisTemplate.execute(new RedisCallback<Object>() {
    // public Object doInRedis(RedisConnection connection)
    // throws DataAccessException {
    // connection.del(redisTemplate.getStringSerializer().serialize(redisKeyDto.getKeys()));
    // return null;
    // }
    // });
    // }
    //
    // @Override
    // public RedisKeyDto redisGet(RedisKeyDto redisKeyDto) {
    // return redisTemplate.execute(new RedisCallback<RedisKeyDto>() {
    // public RedisKeyDto doInRedis(RedisConnection connection) throws
    // DataAccessException {
    // byte[] key =
    // redisTemplate.getStringSerializer().serialize(redisKeyDto.getKeys());
    // if (connection.exists(key)) {
    // byte[] value = connection.get(key);
    // //从redis中取出的需要反序列化--- deserialize
    // String redisValue =
    // redisTemplate.getStringSerializer().deserialize(value);
    // RedisKeyDto re = new RedisKeyDto();
    // re.setKeys(redisKeyDto.getKeys());
    // re.setValues(redisValue);
    // return re;
    // }
    // return null;
    // }
    // });
    // }
    //
    // @Override
    // public void addRedisData(RedisKeyDto redisKeyDto, int outTime) {
    // redisTemplate.execute(new RedisCallback<Object>() {
    // public Object doInRedis(RedisConnection connection)
    // throws DataAccessException {
    // connection.set(
    // redisTemplate.getStringSerializer().serialize(redisKeyDto.getKeys()),
    // redisTemplate.getStringSerializer().serialize(redisKeyDto.getValues())
    // );
    // connection.expire(redisTemplate.getStringSerializer().serialize(redisKeyDto.getKeys()),
    // outTime);
    // return null;
    // }
    // });
    // }
}
