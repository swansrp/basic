<#list baseDataList as data>
    <#if ((data.columnName) == "createAt")>
        <#assign FeatureCreateAt="true">
    </#if>
    <#if ((data.columnName) == "updateAt")>
        <#assign FeatureUpdateAt="true">
    </#if>
    <#if ((data.columnName) == "valid")>
        <#assign FeatureValid="true">
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
    public ${modelName} update${modelName}(${modelName} ${modelNameFL}) {
        if (${modelNameFL}.getId() == null) {
            <#if FeatureCreateAt??>
            ${modelNameFL}.setCreateAt(new Date());
            </#if>
            ${modelNameFL}Mapper.insertSelective(${modelNameFL});
        } else {
            <#if FeatureUpdateAt??>
            ${modelNameFL}.setUpdateAt(new Date());
            </#if>
            ${modelNameFL}Mapper.updateByPrimaryKeySelective(${modelNameFL});
        }
        return ${modelNameFL};
    }

    @CacheEvict(value = "${modelName}", allEntries = true)
    public Integer update${modelName}ByExample(${modelName} ${modelNameFL}, ${modelName}Example example) {
        return ${modelNameFL}Mapper.updateByExampleSelective(${modelNameFL}, example);
    }

    @CacheEvict(value = "${modelName}", allEntries = true)
    public Integer del${modelName}(${modelName} ${modelNameFL}) {
        ${modelName}Example example = get${modelName}Example(${modelNameFL});
        <#if FeatureValid??>
        ${modelNameFL}.setValid(DataSourceCommonConstant.DATABASE_COMMON_INVALID);
        return ${modelNameFL}Mapper.updateByExampleSelective(${modelNameFL}, example);
        <#else>
        return ${modelNameFL}Mapper.deleteByExample(example);
        </#if>
    }

    @CacheEvict(value = "${modelName}", allEntries = true)
    public Integer del${modelName}ByExample(${modelName}Example example) {
        <#if FeatureValid??>
        Integer res = 0;
        List<${modelName}> ${modelNameFL}List = get${modelName}ByExample(example);
        for (${modelName} ${modelNameFL} : ${modelNameFL}List) {
            ${modelNameFL}.setValid(DataSourceCommonConstant.DATABASE_COMMON_INVALID);
            res += ${modelNameFL}Mapper.updateByPrimaryKey(${modelNameFL});
        }
        return res;
        <#else>
        return ${modelNameFL}Mapper.deleteByExample(example);
        </#if>
    }

    public Long count${modelName}(${modelName} ${modelNameFL}) {
        ${modelName}Example example = get${modelName}Example(${modelNameFL});
        return ${modelNameFL}Mapper.countByExample(example);
    }

    public Long count${modelName}ByExample(${modelName}Example example) {
        return ${modelNameFL}Mapper.countByExample(example);
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
        ${modelName}Example example = get${modelName}Example(${modelNameFL});
        List<${modelName}> res = ${modelNameFL}Mapper.selectByExample(example);
        return res;
    }

    public List<${modelName}> get${modelName}ByExample(${modelName}Example example) {
        List<${modelName}> res = ${modelNameFL}Mapper.selectByExample(example);
        return res;
    }

    private ${modelName}Example get${modelName}Example(${modelName} ${modelNameFL}) {
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
        return example;
    }
}