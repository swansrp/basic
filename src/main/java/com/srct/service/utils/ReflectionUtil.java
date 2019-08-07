/**
 * Project Name:SpringBootCommon
 * File Name:ReflectionUtil.java
 * Package Name:com.srct.service.utils
 * Date:2018年4月26日上午11:19:26
 * Copyright (c) 2018, ruopeng.sha All Rights Reserved.
 */
package com.srct.service.utils;

import com.srct.service.exception.ServiceException;
import org.springframework.util.CollectionUtils;
import org.springframework.util.LinkedMultiValueMap;

import javax.validation.constraints.NotNull;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * ClassName:ReflectionUtil <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2018年4月26日 上午11:19:26 <br/>
 *
 * @author ruopeng.sha
 * @see
 * @since JDK 1.8
 */
public class ReflectionUtil {

    private ReflectionUtil() {
    }

    public static <K, T> List<T> copyList(@NotNull List<K> source, Class<T> clazz) {
        List<T> resList = new ArrayList<>();
        source.forEach(item -> {
            try {
                T obj = clazz.newInstance();
                BeanUtil.copyProperties(item, obj);
                resList.add(obj);
            } catch (InstantiationException | IllegalAccessException e) {
                throw new ServiceException("创建对象失败");
            }
        });
        return resList;
    }

    private static <T> String getFieldKey(String[] outFieldArr, T t) {
        String[] fieldValueArr = new String[outFieldArr.length];
        for (int i = 0; i < outFieldArr.length; i++) {
            try {
                fieldValueArr[i] = getFieldValue(t, outFieldArr[i]);
            } catch (IllegalAccessException e) {
                throw new ServiceException("获取对象单个属性值发生异常", e);
            }
        }
        return StringUtil.join(fieldValueArr);
    }

    public static <T> List<T> getFieldList(List<?> list, String fieldName, Class<T> clazz) {
        if (CollectionUtils.isEmpty(list)) {
            return new ArrayList<>();
        }
        List<T> res = new ArrayList<>();
        list.forEach(item -> {
            try {
                res.add(getFieldValue(item, fieldName));
            } catch (IllegalAccessException e) {
                throw new ServiceException("获取对象单个属性值发生异常", e);
            }
        });
        return res;
    }

    public static <T> T getFieldValue(@NotNull Object object, @NotNull String fullName)
            throws IllegalAccessException, SecurityException, IllegalArgumentException {
        return getFieldValue(object, fullName, false);
    }

    @SuppressWarnings("unchecked")
    public static <T> T getFieldValue(@NotNull Object object, @NotNull String fieldName, boolean traceable)
            throws IllegalAccessException, SecurityException, IllegalArgumentException {
        Field field;
        String[] fieldNames = fieldName.split("\\.");
        for (String targetField : fieldNames) {
            field = searchField(object.getClass(), targetField, traceable, false);
            if (field == null) {
                return null;
            }
            object = getValue(object, field);
        }
        return (T) object;
    }

    public static Field getField(@NotNull Object obj, String fieldName) {
        Class<?> aClass = obj.getClass();
        return getField(aClass, fieldName);
    }

    public static Field getField(Class<?> aClass, String fieldName) {
        List<Field> fieldList = getFields(aClass);
        for (Field field : fieldList) {
            if (fieldName.equals(field.getName())) {
                return field;
            }
        }
        return null;
    }

    public static List<Field> getFields(@NotNull Object obj) {
        Class<?> aClass = obj.getClass();
        return getFields(aClass);
    }

    public static List<Field> getFields(Class<?> aClass) {
        List<Field> fieldList = new ArrayList<>();
        Class tempClass = aClass;
        // 当父类为null的时候说明到达了最上层的父类(Object类).
        while (tempClass != null) {
            fieldList.addAll(Arrays.asList(tempClass.getDeclaredFields()));
            // 得到父类,然后赋给自己
            tempClass = tempClass.getSuperclass();
        }
        return fieldList;
    }

    public static HashMap<String, Object> getHashMap(@NotNull Object obj) {
        Class<?> aClass = obj.getClass();
        List<Field> fieldList = getFields(aClass);
        HashMap<String, Object> res = new HashMap<>(fieldList.size());
        for (Field field : fieldList) {
            try {
                String name = field.getName();
                Object value = getValue(obj, field);
                if (value != null) {
                    res.put(name, value);
                }
            } catch (IllegalAccessException | SecurityException | IllegalArgumentException e) {
                e.printStackTrace();
            }
        }
        return res;
    }

    public static LinkedMultiValueMap<String, Object> getLinkedMultiValueMap(@NotNull Object obj) {
        LinkedMultiValueMap<String, Object> res = new LinkedMultiValueMap<>();
        Class<?> aClass = obj.getClass();
        List<Field> fieldList = getFields(aClass);
        for (Field field : fieldList) {
            try {
                String name = field.getName();
                Object value = getValue(obj, field);
                if (value != null) {
                    res.add(name, value);
                }
            } catch (IllegalAccessException | SecurityException | IllegalArgumentException e) {
                e.printStackTrace();
            }
        }
        return res;
    }

    @SuppressWarnings("unchecked")
    public static <T> T getValue(Object target, Field field)
            throws IllegalAccessException, SecurityException, IllegalArgumentException {
        if (!field.isAccessible()) {
            try {
                Method getFieldMethod =
                        target.getClass().getMethod("get" + StringUtil.firstUpperCamelCase(field.getName()));
                return (T) getFieldMethod.invoke(target);
            } catch (Exception e) {
                try {
                    return (T) field.get(target);
                } catch (Exception ex) {
                    return null;
                }
            }
        }
        return (T) field.get(target);
    }

    public static <T> Map<String, Map<String, T>> reflectToDuplicateMap(List<T> list, String[] outFieldArr,
            String[] inFieldArr) {
        Map<String, Map<String, T>> outMap = new TreeMap<>();
        for (T t : list) {
            String outKey = getFieldKey(outFieldArr, t);
            Map<String, T> inMap = outMap.get(outKey);
            if (inMap == null) {
                inMap = new TreeMap<>();
                outMap.put(outKey, inMap);
            }
            String inKey = getFieldKey(inFieldArr, t);
            inMap.put(inKey, t);
        }
        return outMap;
    }

    public static <T> Map<String, T> reflectToMap(List<T> list, String... fieldArr) {
        Map<String, T> map = new TreeMap<>();
        for (T t : list) {
            String fieldValueArr = getFieldKey(fieldArr, t);
            String key = StringUtil.join(fieldValueArr);
            map.put(key, t);
        }
        return map;
    }

    private static Field searchField(Class<?> c, String targetField, boolean traceable, boolean includeParent) {
        do {
            Field[] fields;
            if (includeParent) {
                fields = c.getFields();
            } else {
                fields = c.getDeclaredFields();
            }
            for (Field f : fields) {
                if (f.getName().equals(targetField)) {
                    return f;
                }
            }
            c = c.getSuperclass();
            traceable = traceable && c != Object.class;
        } while (traceable);
        return null;
    }

    public static boolean setFieldValue(@NotNull Object target, @NotNull String fieldName, @NotNull Object value) {
        return setFieldValue(target, fieldName, value, false);
    }

    public static boolean setFieldValue(@NotNull Object target, @NotNull String fieldName, @NotNull Object value,
            boolean traceable) {
        return setFieldValue(target, fieldName, value, traceable, false);
    }

    public static boolean setFieldValue(@NotNull Object target, @NotNull String fieldName, @NotNull Object value,
            boolean traceable, boolean includeParent) {
        Field field = searchField(target.getClass(), fieldName, traceable, includeParent);
        if (field != null) {
            return setValue(field, target, value);
        }
        return false;
    }

    public static boolean setValue(Field field, Object target, Object value) {
        Method setFieldMethod;
        try {
            setFieldMethod = target.getClass()
                    .getMethod("set" + StringUtil.firstUpperCamelCase(field.getName()), field.getType());
            setFieldMethod.invoke(target, value);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new ServiceException("设置对象单个属性值发生异常", e);
        }
        return true;
    }
}
