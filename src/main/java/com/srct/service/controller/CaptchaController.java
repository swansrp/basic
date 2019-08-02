/**
 * Title: CaptchaController.java
 * Description: Copyright: Copyright (c) 2019 Company: BHFAE
 *
 * @author Sharp
 * @date 2019-7-28 22:59
 * @description Project Name: Tanya
 * @Package: com.srct.service.controller
 */
package com.srct.service.controller;

import com.srct.service.cache.constant.CaptchaType;
import com.srct.service.service.CaptchaService;
import com.srct.service.service.cache.FrameCacheService;
import com.srct.service.utils.log.Log;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

@Api(value = "图形验证码", tags = "图形验证码")
@RestController("CaptchaController")
@RequestMapping(value = "")
public class CaptchaController {

    @Resource
    private CaptchaService captchaService;
    @Resource
    private FrameCacheService frameCacheService;

    @ApiOperation(value = "获取图形验证", notes = "利用token获取图形验证码")
    @RequestMapping(value = "/captcha.jpg", method = RequestMethod.GET)
    public void getCaptcha(HttpServletResponse response, @RequestParam(value = "token") String token,
            @RequestParam(value = "captchaType", required = false) String captchaType) throws IOException {

        ServletOutputStream stream = response.getOutputStream();
        try {
            response.setContentType("image/png");
            BufferedImage image;
            if (StringUtils.isNotBlank(captchaType)) {
                CaptchaType type =
                        CaptchaType.valueOf(frameCacheService.getDictItemId(CaptchaType.CAPTCHA_TYPE, captchaType));
                image = captchaService.generateCaptcha(token, type);
            } else {
                image = captchaService.generateCaptcha(token);
            }
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
}
