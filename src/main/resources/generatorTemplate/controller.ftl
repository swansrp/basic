/**   
 * Copyright ?2018 SRC-TJ Service TG. All rights reserved.
 * 
 * @Project Name: ${projectName}
 * @Package: ${controllerPackage} 
 * @author: ${author}   
 * @date: ${date}
 */
package ${controllerPackage};

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ${BASIC_PACKAGE}.config.db.DataSourceCommonConstant;
import ${BASIC_PACKAGE}.config.response.CommonResponse;
import ${commonPackage}.config.response.${projectName}ExceptionHandler;
import ${entityPackage}.${modelName};
import ${repositoryPackage}.${modelName}Dao;
import ${entityVOPackage}.${modelName}EntityVO;
import com.srct.service.utils.BeanUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "${modelName}")
@RestController("${dbPackageName}${modelName}Controller")
@RequestMapping(value = "/portal/admin/${dbPackageName}/${modelNameURL}")
@CrossOrigin(origins = "*")
public class ${modelName}Controller {

    @Autowired
    ${modelName}Dao ${modelNameFL}Dao;

    @ApiOperation(value = "更新${modelName}", notes = "传入${modelName}值,Id为空时为插入,不为空时为更新。")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "body", dataType = "${modelName}EntityVO", name = "vo", value = "${modelName}", required = true) })
    @ApiResponses({ @ApiResponse(code = 200, message = "操作成功"),
        @ApiResponse(code = 500, message = "服务器内部异常"),
        @ApiResponse(code = 403, message = "权限不足") })
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<CommonResponse<Integer>.Resp> update${modelName}(@RequestBody ${modelName}EntityVO vo) {
        ${modelName} ${modelNameFL} = new ${modelName}();
        BeanUtil.copyProperties(vo, ${modelNameFL});
        Integer id = ${modelNameFL}Dao.update${modelName}(${modelNameFL});
        return ${projectName}ExceptionHandler.generateResponse(id);
    }

    @ApiOperation(value = "查询${modelName}", notes = "传入${modelName}值,匹配不为null的域进行查询")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "body", dataType = "${modelName}EntityVO", name = "vo", value = "${modelName}", required = true) })
    @ApiResponses({ @ApiResponse(code = 200, message = "操作成功"),
        @ApiResponse(code = 500, message = "服务器内部异常"),
        @ApiResponse(code = 403, message = "权限不足") })
    @RequestMapping(value = "/selective", method = RequestMethod.POST)
    public ResponseEntity<CommonResponse<List<${modelName}>>.Resp> get${modelName}Selective(
            @RequestBody ${modelName}EntityVO vo
            ) {
        List<${modelName}> res = new ArrayList<>();
        ${modelName} ${modelNameFL} = new ${modelName}();
        BeanUtil.copyProperties(vo, ${modelNameFL});
        res.addAll(${modelNameFL}Dao.get${modelName}Selective(${modelNameFL}));
        return ${projectName}ExceptionHandler.generateResponse(res);
    }

    @ApiOperation(value = "查询${modelName}", notes = "返回id对应的${modelName},id为空返回全部")
    @ApiImplicitParams({ 
        @ApiImplicitParam(paramType = "query", dataType = "Interger", name = "id", value = "${modelName}的主键", required = false)})
    @ApiResponses({ @ApiResponse(code = 200, message = "操作成功"),
        @ApiResponse(code = 500, message = "服务器内部异常"),
        @ApiResponse(code = 403, message = "权限不足") })
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<CommonResponse<List<${modelName}>>.Resp> get${modelName}(
            @RequestParam(value = "id", required = false) Integer id
            ) {
        List<${modelName}> resList = new ArrayList<>();
        if (id == null) {
            resList.addAll(${modelNameFL}Dao.getAll${modelName}List(DataSourceCommonConstant.DATABASE_COMMON_IGORE_VALID));
        } else {
            resList.add(${modelNameFL}Dao.get${modelName}byId(id));
        }
        return ${projectName}ExceptionHandler.generateResponse(resList);
    }
    
    @ApiOperation(value = "软删除${modelName}", notes = "软删除主键为id的${modelName}")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "query", dataType = "Interger", name = "id", value = "${modelName}的主键", required = false)})
    @ApiResponses({ @ApiResponse(code = 200, message = "操作成功"),
        @ApiResponse(code = 500, message = "服务器内部异常"),
        @ApiResponse(code = 403, message = "权限不足") })
    @RequestMapping(value = "", method = RequestMethod.DELETE)
    public ResponseEntity<CommonResponse<Integer>.Resp> del${modelName}(
            @RequestParam(value = "id", required = true) Integer id
            ) {
        ${modelName} ${modelNameFL} = new ${modelName}();
        ${modelNameFL}.setId(id);
        ${modelNameFL}.setValid(DataSourceCommonConstant.DATABASE_COMMON_INVALID);
        Integer delId = ${modelNameFL}Dao.update${modelName}(${modelNameFL});
        return ${projectName}ExceptionHandler.generateResponse(delId);
    }
}
