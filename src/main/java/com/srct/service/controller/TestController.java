/**
 * @Title: TestController.java Copyright (c) 2019 Sharp. All rights reserved.
 * @Project Name: SpringBootCommonLib
 * @Package: com.srct.service.controller
 * @author sharp
 * @date 2019-01-23 12:47:57
 */
package com.srct.service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.srct.service.config.response.CommonExceptionHandler;
import com.srct.service.config.response.CommonResponse;
import com.srct.service.service.RestService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * @author sharp
 *
 */
@Api(value = "TestController")
@RestController("TestController")
@RequestMapping(value = "/test")
@CrossOrigin(origins = "*")
public class TestController {

    @Autowired
    private RestService conn;

    @ApiOperation(value = "GET param测试", notes = "guid hzrvxzbpg7")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "query", dataType = "String", name = "guid", value = "UserInfo",
        required = true)})
    @ApiResponses({@ApiResponse(code = 200, message = "操作成功"), @ApiResponse(code = 500, message = "服务器内部异常"),
        @ApiResponse(code = 403, message = "权限不足")})
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<CommonResponse<String>.Resp>
        getParamater(@RequestParam(value = "id", required = false) String guid) {
        String url = "Https://usersstg.rewards.samsung.com.cn/users/info/" + guid;
        HttpHeaders header = new HttpHeaders();
        String res = conn.get(url, header, String.class);
        return CommonExceptionHandler.generateResponse(res);
    }

    @ApiOperation(value = "GET 测试", notes = "guid hzrvxzbpg7")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "path", dataType = "String", name = "guid", value = "UserInfo", required = true)})
    @ApiResponses({@ApiResponse(code = 200, message = "操作成功"), @ApiResponse(code = 500, message = "服务器内部异常"),
        @ApiResponse(code = 403, message = "权限不足")})
    @RequestMapping(value = "/{guid}", method = RequestMethod.GET)
    public ResponseEntity<CommonResponse<String>.Resp> getPathVariable(@PathVariable(value = "guid") String guid) {
        String url = "Https://usersstg.rewards.samsung.com.cn/users/info/" + guid;
        HttpHeaders header = new HttpHeaders();
        String res = conn.get(url, header, String.class);
        return CommonExceptionHandler.generateResponse(res);
    }

    @ApiOperation(value = "POST 测试", notes = "guid hzrvxzbpg7")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "body", dataType = "String", name = "guid", value = "UserInfo", required = true)})
    @ApiResponses({@ApiResponse(code = 200, message = "操作成功"), @ApiResponse(code = 500, message = "服务器内部异常"),
        @ApiResponse(code = 403, message = "权限不足")})
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<CommonResponse<String>.Resp> post(@RequestBody String guid) {
        String url = "Https://usersstg.rewards.samsung.com.cn/users/info/" + guid;
        HttpHeaders header = new HttpHeaders();
        String res = conn.get(url, header, String.class);
        return CommonExceptionHandler.generateResponse(res);
    }
}
