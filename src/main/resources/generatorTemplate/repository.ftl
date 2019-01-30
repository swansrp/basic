<#list baseDataList as data>
    <#if ((data.columnName) == "createAt")>
        <#assign FeatureCreateAt="true">
    </#if>
    <#if ((data.columnName) == "updateAt")>
        <#assign FeatureUpdateAt="true">
    </#if>
</#list>


/**   
 * Copyright ?2018 SRC-TJ Service TG. All rights reserved.
 * 
 * @Project Name: ${projectName}
 * @Package: ${repositoryPackage} 
 * @author: ${author}   
 * @date: ${date}
 */
package ${repositoryPackage};

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
<#if ((FeatureCreateAt??) || (FeatureUpdateAt??))>
import java.util.Date;
</#if>
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import ${BASIC_PACKAGE}.config.db.DataSourceCommonConstant;
import ${BASIC_PACKAGE}.config.redis.CacheExpire;
import ${BASIC_PACKAGE}.exception.ServiceException;
import ${entityPackage}.${modelName};
import ${entityPackage}.${modelName}Example;
import ${xmlPackage}.${modelName}Mapper;

import ${BASIC_PACKAGE}.utils.ReflectionUtil;
import ${BASIC_PACKAGE}.utils.StringUtil;


/**
 * @ClassName: ${modelName}Dao
 * @Description: TODO
 */
@Repository("${dbPackageName}${modelName}Dao")
public class ${modelName}Dao {

    @Autowired
    ${modelName}Mapper ${modelNameFL}Mapper;

    @CacheEvict(value = "${modelName}", allEntries = true)
    public Integer update${modelName}(${modelName} info) {
        Integer id = null;
        if (info.getId() == null) {
            <#if FeatureCreateAt??>
            info.setCreateAt(new Date());
            </#if>
            ${modelNameFL}Mapper.insertSelective(info);
        } else {
            <#if FeatureUpdateAt??>
            info.setUpdateAt(new Date());
            </#if>
            ${modelNameFL}Mapper.updateByPrimaryKeySelective(info);
        }
        id = info.getId();
        return id;
    }
	
    @Cacheable(value = "${modelName}", key = "'valid_' + #valid")
    @CacheExpire(expire = 3600L)
    public List<${modelName}> getAll${modelName}List(Byte valid) {
        ${modelName}Example example = new ${modelName}Example();
        ${modelName}Example.Criteria criteria = example.createCriteria();
        if (!valid.equals(DataSourceCommonConstant.DATABASE_COMMON_IGORE_VALID)) {
            criteria.andValidEqualTo(valid);
        }

        return ${modelNameFL}Mapper.selectByExample(example);
    }
	
    @Cacheable(value = "${modelName}", key = "'id_' + #id")
	@CacheExpire(expire = 24 * 3600L)
    public ${modelName} get${modelName}byId(Integer id) {
        return ${modelNameFL}Mapper.selectByPrimaryKey(id);
    }

	@Cacheable(value = "${modelName}", keyGenerator = "CacheKeyByParam")
	@CacheExpire(expire = 3600L)
    public List<${modelName}> get${modelName}Selective(${modelName} ${modelNameFL}) {
        ${modelName}Example example = new ${modelName}Example();
        ${modelName}Example.Criteria criteria = example.createCriteria();
        HashMap<String, Object> valueMap = ReflectionUtil.getHashMap(${modelNameFL});
        ReflectionUtil.getFieldList(${modelNameFL}).forEach((field) -> {
            if (valueMap.get(field.getName()) != null) {
                Method criteriaMethod = null;
                try {
                    String criteriaMethodName = "and" + StringUtil.firstUpperCamelCase(field.getName()) + "EqualTo";
                    criteriaMethod = criteria.getClass().getMethod(criteriaMethodName, field.getType());
                } catch (NoSuchMethodException | SecurityException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                if (criteriaMethod == null)
                    throw new ServiceException("");
                try {
                    criteriaMethod.invoke(criteria, valueMap.get(field.getName()));
                } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        });
        List<${modelName}> res = ${modelNameFL}Mapper.selectByExample(example);
        return res;
    }	
}