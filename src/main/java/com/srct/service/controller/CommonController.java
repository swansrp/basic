/**
 * Title: CommonController.java Description: Copyright: Copyright (c) 2019 Company: Sharp
 *
 * @Project Name: SpringBootCommonLib
 * @Package: com.srct.service.controller
 * @author Sharp
 * @date 2019-02-05 22:31:13
 */
package com.srct.service.controller;

import com.srct.service.config.response.CommonExceptionHandler;
import com.srct.service.config.response.CommonResponse;
import com.srct.service.exception.ServiceException;
import com.srct.service.service.CaptchaService;
import com.srct.service.service.RedisTokenOperateService;
import com.srct.service.utils.log.Log;
import com.srct.service.vo.TokenVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @author Sharp
 */
@RestController("CommonController")
@CrossOrigin(origins = "*")
public class CommonController {

    @Autowired
    private CaptchaService captchaService;
    @Autowired
    private RedisTokenOperateService tokenService;

    @RequestMapping(value = "/captcha.jpg", method = RequestMethod.GET)
    public void getCaptcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String token = request.getParameter("token");
        if (token == null) {
            throw new ServiceException("should get token firstly");
        }
        ServletOutputStream stream = response.getOutputStream();
        try {
            response.setContentType("image/png");
            BufferedImage image = captchaService.generateCaptcha(token);
            ImageIO.write(image, "png", stream);
        } catch (Exception e) {
            Log.e(e);
        } finally {
            if (stream != null) {
                stream.flush();
                stream.close();
            }
        }
    }

    @RequestMapping(value = "/token", method = RequestMethod.GET)
    public ResponseEntity<CommonResponse<TokenVO>.Resp> fetchToken() {
        TokenVO res = TokenVO.builder().Token(tokenService.fetchToken()).build();
        return CommonExceptionHandler.generateResponse(res);
    }

}