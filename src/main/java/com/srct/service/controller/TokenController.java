/**
 * Title: TokenController.java
 * Description: Copyright: Copyright (c) 2019 Company: BHFAE
 *
 * @author Sharp
 * @date 2019-7-28 22:58
 * @description Project Name: Tanya
 * @Package: com.srct.service.controller
 */
package com.srct.service.controller;

import com.srct.service.config.response.CommonExceptionHandler;
import com.srct.service.config.response.CommonResponse;
import com.srct.service.service.RedisTokenOperateService;
import com.srct.service.vo.TokenVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "TOKEN操作", tags = "TOKEN操作")
@RestController("TokenController")
@RequestMapping(value = "")
public class TokenController {

    @Autowired
    private RedisTokenOperateService tokenService;

    @ApiOperation(value = "获取token", notes = "获取token,确保服务正常")
    @RequestMapping(value = "/token", method = RequestMethod.GET)
    public ResponseEntity<CommonResponse<TokenVO>.Resp> fetchToken() {
        TokenVO res = TokenVO.builder().Token(tokenService.fetchToken()).build();
        return CommonExceptionHandler.generateResponse(res);
    }


}
