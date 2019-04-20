/**
* Copyright ?2018 SRC-TJ Service TG. All rights reserved.
*
* @Project Name: ${projectName}
* @Package: ${controllerPackage}
* @author: ${author}
*/
package ${controllerPackage};

import com.github.pagehelper.PageInfo;
import ${BASIC_PACKAGE}.config.db.DataSourceCommonConstant;
import ${BASIC_PACKAGE}.config.response.CommonResponse;
import ${commonPackage}.config.response.${projectName}ExceptionHandler;
import ${entityPackage}.${modelName};
import ${repositoryPackage}.${modelName}Dao;
import ${entityVOPackage}.${modelName}EntityVO;
import com.srct.service.utils.BeanUtil;
import com.srct.service.utils.DBUtil;
import com.srct.service.vo.QueryRespVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "${modelName}")
@RestController("${dbPackageName}${modelName}Controller")
@RequestMapping(value = "/portal/admin/${dbPackageName}/${modelNameURL}")
@CrossOrigin(origins = "*")
@Profile(value = {"dev", "test"})
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
public ResponseEntity
<CommonResponse
<Integer>.Resp> update${modelName}(@RequestBody ${modelName}EntityVO vo) {
	${modelName} ${modelNameFL} = new ${modelName}();
	BeanUtil.copyProperties(vo, ${modelNameFL});
	Integer id = ${modelNameFL}Dao.update${modelName}(${modelNameFL}).getId();
	return ${projectName}ExceptionHandler.generateResponse(id);
	}

	@ApiOperation(value = "查询${modelName}", notes = "传入${modelName}值,匹配不为null的域进行查询")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType = "body", dataType = "${modelName}EntityVO", name = "vo", value = "${modelName}",
	required = true) })
	@ApiResponses({ @ApiResponse(code = 200, message = "操作成功"),
	@ApiResponse(code = 500, message = "服务器内部异常"),
	@ApiResponse(code = 403, message = "权限不足") })
	@RequestMapping(value = "/selective", method = RequestMethod.POST)
	public ResponseEntity
	<CommonResponse
	<QueryRespVO
	<${modelName}>>.Resp> get${modelName}Selective(
	@RequestBody ${modelName}EntityVO vo
	) {
	QueryRespVO<${modelName}> res = new QueryRespVO<>();
	${modelName} ${modelNameFL} = new ${modelName}();
	BeanUtil.copyProperties(vo, ${modelNameFL});
	PageInfo pageInfo = DBUtil.buildPageInfo(vo);
	PageInfo<${modelName}> ${modelNameFL}List = ${modelNameFL}Dao.get${modelName}Selective(${modelNameFL}, pageInfo);
	res.getInfo().addAll(${modelNameFL}List.getList());
	res.buildPageInfo(${modelNameFL}List);
	return ${projectName}ExceptionHandler.generateResponse(res);
	}

	@ApiOperation(value = "查询${modelName}", notes = "返回id对应的${modelName},id为空返回全部")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType = "query", dataType = "Integer", name = "id", value = "${modelName}的主键"),
	@ApiImplicitParam(paramType = "query", dataType = "Integer", name = "currentPage", value = "当前页"),
	@ApiImplicitParam(paramType = "query", dataType = "Integer", name = "pageSize", value = "每页条目数量")})
	@ApiResponses({ @ApiResponse(code = 200, message = "操作成功"),
	@ApiResponse(code = 500, message = "服务器内部异常"),
	@ApiResponse(code = 403, message = "权限不足") })
	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseEntity
	<CommonResponse
	<QueryRespVO
	<${modelName}>>.Resp> get${modelName}(
	@RequestParam(value = "currentPage", required = false) Integer currentPage,
	@RequestParam(value = "pageSize", required = false) Integer pageSize,
	@RequestParam(value = "id", required = false) Integer id
	) {
	QueryRespVO<${modelName}> res = new QueryRespVO<>();
	if (id == null) {
	PageInfo pageInfo = DBUtil.buildPageInfo(currentPage, pageSize);
	PageInfo<${modelName}> ${modelNameFL}List = ${modelNameFL}Dao
	.getAll${modelName}List(DataSourceCommonConstant.DATABASE_COMMON_IGNORE_VALID, pageInfo);
	res.getInfo().addAll(${modelNameFL}List.getList());
	res.buildPageInfo(${modelNameFL}List);
	} else {
	res.getInfo().add(${modelNameFL}Dao.get${modelName}ById(id));
	}
	return ${projectName}ExceptionHandler.generateResponse(res);
	}

	@ApiOperation(value = "软删除${modelName}", notes = "软删除主键为id的${modelName}")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType = "query", dataType = "Integer", name = "id", value = "${modelName}的主键")})
	@ApiResponses({ @ApiResponse(code = 200, message = "操作成功"),
	@ApiResponse(code = 500, message = "服务器内部异常"),
	@ApiResponse(code = 403, message = "权限不足") })
	@RequestMapping(value = "", method = RequestMethod.DELETE)
	public ResponseEntity
	<CommonResponse
	<Integer>.Resp> del${modelName}(
		@RequestParam(value = "id") Integer id
		) {
		${modelName} ${modelNameFL} = new ${modelName}();
		${modelNameFL}.setId(id);
		${modelNameFL}.setValid(DataSourceCommonConstant.DATABASE_COMMON_INVALID);
		Integer delId = ${modelNameFL}Dao.update${modelName}(${modelNameFL}).getId();
		return ${projectName}ExceptionHandler.generateResponse(delId);
		}
		}
