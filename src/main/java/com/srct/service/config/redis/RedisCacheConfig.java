/**
 * Project Name:SpringBootCommon
 * File Name:RedisCacheConfig.java Package Name:com.srct.service.config.redis
 * Date:May 14, 2018 10:17:27 AM
 * Copyright (c) 2018, ruopeng.sha All Rights Reserved.
 * 
 */
package com.srct.service.config.redis;

import java.lang.reflect.Method;
import java.time.Duration;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.session.SessionRepository;
import org.springframework.session.data.redis.RedisOperationsSessionRepository;
import org.springframework.session.data.redis.config.ConfigureRedisAction;

/**
 * ClassName:RedisCacheConfig <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: May 14, 2018 10:17:27 AM <br/>
 * 
 * @author ruopeng.sha
 * @version
 * @since JDK 1.8
 * @see
 */
@EnableCaching
@Configuration
public class RedisCacheConfig extends CachingConfigurerSupport {

    /**
     * Set RedisTemplate add Serializer key StringRedisSerializer value GenericJackson2JsonRedisSerializer another
     * easier way , change defaultSerializer implementation
     * 
     * @return
     */
    @Bean(name = "byteRedisTemplate")
    public RedisTemplate<String, byte[]> byteRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, byte[]> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setDefaultSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    /**
     * Set RedisTemplate add Serializer key StringRedisSerializer value GenericJackson2JsonRedisSerializer another
     * easier way , change defaultSerializer implementation
     * 
     * @return
     */
    @Bean(name = "redisTemplate")
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setDefaultSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    @Bean(name = "objectRedisTemplate")
    public RedisTemplate<Object, Object> objectRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setDefaultSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.setKeySerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.setHashKeySerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    /**
     * Set RedisCacheManager Use Bean Manage Redis Cache
     *
     * @return
     */
    @Bean
    CacheManager cacheManager(RedisConnectionFactory connectionFactory) {
        // Init RedisCacheWriter
        RedisCacheWriter redisCacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(connectionFactory);
        // Set CacheManager Value serialize as JdkSerializationRedisSerializer,
        // But,Actually RedisCacheConfiguration use StringRedisSerializer to
        // serialize
        // key、
        // JdkSerializationRedisSerializer serialize value as default, So
        // following
        // commented code is default implementation
        // ClassLoader loader = this.getClass().getClassLoader();
        // JdkSerializationRedisSerializer jdkSerializer = new
        // JdkSerializationRedisSerializer(loader);
        // RedisSerializationContext.SerializationPair<Object> pair =
        // RedisSerializationContext.SerializationPair.fromSerializer(jdkSerializer);
        // RedisCacheConfiguration
        // defaultCacheConfig=RedisCacheConfiguration.defaultCacheConfig().serializeValuesWith(pair);
        // Set Default TTL as 10 mins, serialize factory is
        // GenericJackson2JsonRedisSerializer
        GenericJackson2JsonRedisSerializer serializer = new GenericJackson2JsonRedisSerializer();
        RedisSerializationContext.SerializationPair<Object> pair =
            RedisSerializationContext.SerializationPair.fromSerializer(serializer);
        RedisCacheConfiguration defaultCacheConfig =
            RedisCacheConfiguration.defaultCacheConfig().serializeValuesWith(pair).entryTtl(Duration.ofSeconds(600));
        // Init RedisCacheManager
        RedisCacheManager cacheManager = new MyRedisCacheManager(redisCacheWriter, defaultCacheConfig);
        return cacheManager;
    }

    /**
     * Customer generate redis-key
     *
     * @return
     */
    @Bean
    @Override
    public KeyGenerator keyGenerator() {
        return new KeyGenerator() {

            @Override
            public String generate(Object target, Method method, Object... params) {
                StringBuilder key = new StringBuilder();
                key.append(method.getDeclaringClass().getName());
                key.append(".");
                key.append(method.getName());
                key.append("(");
                key.append(getParams(method, params));
                key.append(")");
                // Log.i(key.toString());
                return key.toString();
            }

            private String getParams(Method method, Object... args) {
                LocalVariableTableParameterNameDiscoverer u = new LocalVariableTableParameterNameDiscoverer();
                String[] names = u.getParameterNames(method);
                Class<?>[] types = method.getParameterTypes();
                String res = "";
                if (args != null && args.length > 0) {
                    res = types[0].getSimpleName() + " " + names[0] + "<" + args[0].toString() + ">";
                    for (int i = 1; i < names.length; i++) {
                        res += ", ";
                        res += types[i].getSimpleName() + " " + names[i] + "<" + args[i].toString() + ">";
                    }
                }
                return res;
            }
        };
    }

    @Bean
    public ConfigureRedisAction configureRedisAction() {
        return ConfigureRedisAction.NO_OP;
    }

    /**
     * 设置spring session redis 序列化方式
     * 
     * @param factory
     * @return
     */
    @Bean
    public SessionRepository sessionRepository(RedisConnectionFactory factory) {
        RedisOperationsSessionRepository sessionRepository =
            new RedisOperationsSessionRepository(objectRedisTemplate(factory));
        sessionRepository.setDefaultSerializer(new GenericJackson2JsonRedisSerializer());
        sessionRepository.setDefaultMaxInactiveInterval(36000);
        return sessionRepository;
    }

}
