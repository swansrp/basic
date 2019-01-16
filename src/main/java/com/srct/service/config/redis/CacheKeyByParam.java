package com.srct.service.config.redis;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;

import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.stereotype.Component;

import com.srct.service.utils.ReflectionUtil;

@Component("CacheKeyByParam") // @Cacheable(value="XXX",keyGenerator="CacheKeyByParam")
public class CacheKeyByParam implements KeyGenerator {

    @Override
    public Object generate(Object target, Method method, Object... params) {
        // TODO Auto-generated method stub
        StringBuilder key = new StringBuilder();
        if (params.length > 0) {
            for (Object object : params) {
                Class clazz = object.getClass();
                List<Field> field = ReflectionUtil.getFieldList(clazz);
                if (field != null && field.size() > 0) {
                    for (int i = 0; i < field.size(); i++) {
                        String name = field.get(i).getName();
                        String value = null;
                        Object tempObject = null;
                        try {
                            tempObject = ReflectionUtil.getFieldValue(object, name);
                        } catch (IllegalAccessException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        } catch (NoSuchMethodException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        } catch (SecurityException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        } catch (IllegalArgumentException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        if (tempObject != null) {
                            if (field.get(i).getType().equals(Date.class)) {
                                value = String.valueOf(((Date) tempObject).getTime());
                            } else {
                                value = tempObject.toString();
                            }
                            if (i == field.size() - 1) {
                                key.append(name + "_" + value);
                            } else {
                                key.append(name + "_" + value + ",");
                            }
                        }
                    }
                }
            }
        }
        return key.toString();
    }
}
