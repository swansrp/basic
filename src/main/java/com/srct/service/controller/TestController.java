/**
 * @Title: TestController.java Copyright (c) 2019 Sharp. All rights reserved.
 * @Project Name: SpringBootCommonLib
 * @Package: com.srct.service.controller
 * @author sharp
 * @date 2019-01-23 12:47:57
 */
package com.srct.service.controller;

import com.srct.service.config.response.CommonExceptionHandler;
import com.srct.service.config.response.CommonResponse;
import com.srct.service.service.RestService;
import com.srct.service.utils.log.Log;
import com.srct.service.utils.security.EncryptUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author sharp
 */
@Api(value = "TestController")
@RestController("TestController")
@RequestMapping(value = "/test")
@CrossOrigin(origins = "*")
//@Profile(value = {"dev", "test"})
public class TestController {

    @Autowired
    private RestService conn;

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String sender;


    @ApiOperation(value = "GET param测试", notes = "guid hzrvxzbpg7")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "guid", value = "UserInfo", required = true)})
    @ApiResponses({@ApiResponse(code = 200, message = "操作成功"), @ApiResponse(code = 500, message = "服务器内部异常"),
            @ApiResponse(code = 403, message = "权限不足")})
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<CommonResponse<String>.Resp> getParamater(
            @RequestParam(value = "id", required = false) String guid) {
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

    @RequestMapping(value = "/aes", method = RequestMethod.GET)
    public ResponseEntity<CommonResponse<String>.Resp> aes(
            @RequestParam(value = "token", required = false) String token) {

        String encryptToken = EncryptUtil.encryptBase64(token, "Tanya");
        Log.i("{}  -  {}", token, encryptToken);

        String oriToken = EncryptUtil.decryptBase64(encryptToken, "Tanya");
        Log.i("{}  -  {}", oriToken, encryptToken);

        return CommonExceptionHandler.generateResponse("");
    }

    @RequestMapping(value = "/email", method = RequestMethod.GET)
    public ResponseEntity<CommonResponse<String>.Resp> email(
            @RequestParam(value = "token", required = false) String token) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(sender);
        message.setTo(sender); //自己给自己发送邮件
        message.setSubject("主题：简单邮件");
        message.setText("测试邮件内容" + token);
        mailSender.send(message);

        return CommonExceptionHandler.generateResponse("");
    }
}
