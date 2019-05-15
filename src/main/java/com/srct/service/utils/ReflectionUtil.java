/**
 * Project Name:SpringBootCommon
 * File Name:ReflectionUtil.java
 * Package Name:com.srct.service.utils
 * Date:2018年4月26日上午11:19:26
 * Copyright (c) 2018, ruopeng.sha All Rights Reserved.
 */
package com.srct.service.utils;

import org.springframework.util.LinkedMultiValueMap;

import javax.validation.constraints.NotNull;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

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

    public static <T> T getFieldValue(@NotNull Object object, @NotNull String fullName)
            throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException,
            InvocationTargetException {
        return getFieldValue(object, fullName, false);
    }

    @SuppressWarnings("unchecked")
    public static <T> T getFieldValue(@NotNull Object object, @NotNull String fieldName, boolean traceable)
            throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException,
            InvocationTargetException {
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

    private static Field searchField(Class<?> c, String targetField, boolean traceable, boolean includeParent) {
        do {
            Field[] fields = null;
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

    @SuppressWarnings("unchecked")
    public static <T> T getValue(Object target, Field field)
            throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException,
            InvocationTargetException {
        if (!field.isAccessible()) {
            // field.setAccessible(true);
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

    public static boolean setFieldValue(@NotNull Object target, @NotNull String fieldName, @NotNull Object value)
            throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException,
            SecurityException {
        return setFieldValue(target, fieldName, value, false);
    }

    public static boolean setFieldValue(@NotNull Object target, @NotNull String fieldName, @NotNull Object value,
            boolean traceable)
            throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException,
            SecurityException {
        return setFieldValue(target, fieldName, value, traceable, false);
    }

    public static boolean setFieldValue(@NotNull Object target, @NotNull String fieldName, @NotNull Object value,
            boolean traceable, boolean includeParent)
            throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException,
            SecurityException {
        Field field = searchField(target.getClass(), fieldName, traceable, includeParent);
        if (field != null) {
            return setValue(field, target, value);
        }
        return false;
    }

    public static boolean setValue(Field field, Object target, Object value)
            throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException,
            SecurityException {
        if (!field.isAccessible()) {
            Method setFieldMethod;
            setFieldMethod = target.getClass()
                    .getMethod("set" + StringUtil.firstUpperCamelCase(field.getName()), field.getType());
            setFieldMethod.invoke(target, value);
            return true;
        }
        field.set(target, value);
        return true;
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
            } catch (IllegalAccessException | NoSuchMethodException | SecurityException | IllegalArgumentException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return res;
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
            } catch (IllegalAccessException | NoSuchMethodException | SecurityException | IllegalArgumentException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return res;
    }

    public static List<Field> getA(@NotNull Object obj) {
        Class<?> aClass = obj.getClass();
        return getFields(aClass);
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

    public static List<?> getFieldList(List<?> list, String fieldName) {
        if (list == null || list.size() == 0) {
            return null;
        }
        List<?> res = new ArrayList<>();
        list.forEach(item -> {
            try {
                res.add(getFieldValue(item, fieldName));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        });
        return res;
    }
}
