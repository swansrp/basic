package ${entityPackage};

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
* 实体类
* 根据需要删减无效参数
*
* @author: ${author}
**/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class ${modelName}{
<#list baseDataList as data>

    @ApiModelProperty(value = "${data.columnComment}")
    private ${data.columnType} ${data.columnName};
</#list>
}
