/**
 * Title: BaseRepository.java
 * Description: Copyright: Copyright (c) 2019 Company: BHFAE
 *
 * @author Sharp
 * @date 2019-7-31 22:55
 * @description Project Name: Grote
 * @Package: com.srct.service.frame
 */
package com.srct.service.frame;

import com.srct.service.config.db.DataSourceCommonConstant;
import com.srct.service.constant.ErrCodeSys;
import com.srct.service.utils.ReflectionUtil;
import com.srct.service.validate.Validator;
import org.apache.ibatis.session.RowBounds;
import tk.mybatis.mapper.entity.Example;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;

public abstract class BaseRepository<T extends tk.mybatis.mapper.common.Mapper<K>, K> {

    public int delete(K entity) {
        return getDao().delete(entity);
    }

    public int deleteByExample(Example example) {
        return getDao().deleteByExample(example);
    }

    public int deleteByPrimaryKey(Object primaryKey) {
        return getDao().deleteByPrimaryKey(primaryKey);
    }

    public void disable(K entity) {
        List<K> recordList = getDao().select(entity);
        recordList.forEach(record -> disableEntity(record));
    }

    public void disableByExample(Example example) {
        List<K> recordList = getDao().selectByExample(example);
        recordList.forEach(record -> disableEntity(record));
    }

    public void disableByPrimaryKey(Object primaryKey) {
        K record = getDao().selectByPrimaryKey(primaryKey);
        disableEntity(record);
    }

    public boolean existsWithPrimaryKey(Object primaryKey) {
        return getDao().existsWithPrimaryKey(primaryKey);
    }

    public int insert(K entity) {
        return getDao().insert(entity);
    }

    public int insertByExample(K entity) {
        return getDao().insertSelective(entity);
    }

    public List<K> select(K entity) {
        return getDao().select(entity);
    }

    public List<K> selectAll() {
        return getDao().selectAll();
    }

    public List<K> selectByExample(Example example) {
        return getDao().selectByExample(example);
    }

    public List<K> selectByExampleAndRowBounds(Example example, RowBounds rowBounds) {
        return getDao().selectByExampleAndRowBounds(example, rowBounds);
    }

    public List<K> selectByFiledList(String fieldName, List<Object> fieldValueList) {
        Example example = buildFiledListExample(fieldName, fieldValueList);
        return getDao().selectByExample(example);
    }

    public List<K> selectByFiledListAndRowBounds(String fieldName, List<Object> fieldValueList, RowBounds rowBounds) {
        Example example = buildFiledListExample(fieldName, fieldValueList);
        return getDao().selectByExampleAndRowBounds(example, rowBounds);
    }

    public K selectByPrimaryKey(Object primaryKey) {
        return getDao().selectByPrimaryKey(primaryKey);
    }

    public List<K> selectByPropertyMap(Map<String, Object> propertyMap) {
        Example example = buildExample(propertyMap);
        return selectByExample(example);
    }

    public List<K> selectByRowBounds(K entity, RowBounds rowBounds) {
        return getDao().selectByRowBounds(entity, rowBounds);
    }

    public int selectCount(K entity) {
        return getDao().selectCount(entity);
    }

    public int selectCountByExample(Example example) {
        return getDao().selectCountByExample(example);
    }

    public <L> List<L> selectFiledListByExample(Example example, String fieldName, Class<L> clazz) {
        List<K> resList = getDao().selectByExample(example);
        return ReflectionUtil.getFieldList(resList, fieldName, clazz);
    }

    public <L> List<L> selectFiledListByExampleAndRowBounds(Example example, String fieldName, Class<L> clazz,
            RowBounds rowBounds) {
        List<K> resList = getDao().selectByExampleAndRowBounds(example, rowBounds);
        return ReflectionUtil.getFieldList(resList, fieldName, clazz);
    }

    public K selectOne(K entity) {
        return getDao().selectOne(entity);
    }

    public K selectOneByExample(Example example) {
        return getDao().selectOneByExample(example);
    }

    public K selectOneByPropertyMap(Map<String, Object> propertyMap) {
        Example example = buildExample(propertyMap);
        return selectOneByExample(example);
    }

    public K selectOneValidByPropertyMap(Map<String, Object> propertyMap) {
        propertyMap.put("valid", DataSourceCommonConstant.DATABASE_COMMON_VALID);
        return selectOneByPropertyMap(propertyMap);
    }

    public List<K> selectValidByPropertyMap(Map<String, Object> propertyMap) {
        propertyMap.put("valid", DataSourceCommonConstant.DATABASE_COMMON_VALID);
        return selectByPropertyMap(propertyMap);
    }

    public int updateByExample(K entity, Example example) {
        return getDao().updateByExample(entity, example);
    }

    public int updateByExampleSelective(K entity, Example example) {
        return getDao().updateByExampleSelective(entity, example);
    }

    public int updateByPrimaryKey(K entity) {
        return getDao().updateByPrimaryKey(entity);
    }

    public int updateByPrimaryKeySelective(K entity) {
        return getDao().updateByPrimaryKeySelective(entity);
    }

    /**
     * 注入当前dao.
     *
     * @return the dao
     */
    protected abstract T getDao();

    private Example buildExample(Map<String, Object> propertyMap) {
        Class<K> clazz = (Class<K>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];
        Example example = new Example(clazz);
        Example.Criteria criteria = example.createCriteria();
        propertyMap.entrySet().forEach(entry -> criteria.andEqualTo(entry.getKey(), entry.getValue()));
        return example;
    }

    private Example buildFiledListExample(String fieldName, List<Object> fieldValueList) {
        Class<K> clazz = (Class<K>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];
        Example example = new Example(clazz);
        Example.Criteria criteria = example.createCriteria();
        criteria.andIn(fieldName, fieldValueList);
        return example;
    }

    private void disableEntity(K entity) {
        Field validField = ReflectionUtil.getField(entity, "valid");
        Validator.assertNotNull(validField, ErrCodeSys.SYS_ERR_MSG, "不能软删除");
        ReflectionUtil.setValue(validField, entity, DataSourceCommonConstant.DATABASE_COMMON_INVALID);
        getDao().updateByPrimaryKey(entity);
    }

}
