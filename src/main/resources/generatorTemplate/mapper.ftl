package ${mapperPackage};

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import ${entityPackage}.${modelName};
import ${entityPackage}.${modelName}Example;

@Component("${dbName}${modelName}Mapper")
public interface ${modelName}Mapper {

    long countByExample(${modelName}Example example);

    int deleteByExample(${modelName}Example example);

    int deleteByPrimaryKey(Integer id);

    int insert(${modelName} record);

    int insertSelective(${modelName} record);

    List<${modelName}> selectByExample(${modelName}Example example);

    ${modelName} selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ${modelName} record, @Param("example") ${modelName}Example example);

    int updateByExample(@Param("record") ${modelName} record, @Param("example") ${modelName}Example example);

    int updateByPrimaryKeySelective(${modelName} record);

    int updateByPrimaryKey(${modelName} record);
}