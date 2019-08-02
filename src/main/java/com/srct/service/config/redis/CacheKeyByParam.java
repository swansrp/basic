package com.srct.service.config.redis;

import com.srct.service.exception.ServiceException;
import com.srct.service.utils.ReflectionUtil;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;

@Component("CacheKeyByParam") // @Cacheable(value="XXX",keyGenerator="CacheKeyByParam")
public class CacheKeyByParam implements KeyGenerator {

    @Override
    public Object generate(Object target, Method method, Object... params) {
        // TODO Auto-generated method stub
        StringBuilder key = new StringBuilder();
        if (params.length > 0) {
            for (Object object : params) {
                Class clazz = object.getClass();
                List<Field> field = ReflectionUtil.getFields(object);
                if (field != null && field.size() > 0) {
                    for (int i = 0; i < field.size(); i++) {
                        String name = field.get(i).getName();
                        String value = null;
                        Object tempObject = null;
                        try {
                            tempObject = ReflectionUtil.getFieldValue(object, name);
                        } catch (IllegalAccessException | SecurityException | IllegalArgumentException e) {
                            throw new ServiceException("获取单体数值失败", e);
                        }
                        if (tempObject != null) {
                            if (field.get(i).getType().equals(Date.class)) {
                                value = String.valueOf(((Date) tempObject).getTime());
                            } else {
                                value = tempObject.toString();
                            }
                            key.append(name + "_" + value + ",");
                        }
                    }
                }
            }
        }
        return key.substring(0, key.length() - 1);
    }
}
