/**
 * Title: CaptchaController.java Description: Copyright: Copyright (c) 2019 Company: Sharp
 * 
 * @Project Name: SpringBootCommonLib
 * @Package: com.srct.service.controller
 * @author Sharp
 * @date 2019-02-05 22:31:13
 */
package com.srct.service.controller;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.srct.service.config.response.CommonExceptionHandler;
import com.srct.service.config.response.CommonResponse;
import com.srct.service.utils.CaptchaUtil;

/**
 * @author Sharp
 *
 */
// @Controller
public class CaptchaController {

    public static final String KEY_CAPTCHA = "KEY_CAPTCHA";

    public static final String KEY_TIME = "KEY_TIME";

    @RequestMapping(name = "/captcha.jpg", method = RequestMethod.GET)
    public void getCaptcha(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        // 设置相应类型,告诉浏览器输出的内容为图片
        response.setContentType("image/jpeg");
        // 不缓存此内容
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expire", 0);
        try {

            HttpSession session = request.getSession();

            CaptchaUtil tool = new CaptchaUtil();
            StringBuffer code = new StringBuffer();
            BufferedImage image = tool.genRandomCodeImage(code);

            session.removeAttribute(KEY_CAPTCHA);
            session.removeAttribute(KEY_TIME);
            session.setAttribute(KEY_CAPTCHA, code.toString());
            session.setAttribute(KEY_TIME, LocalDateTime.now());

            // 将内存中的图片通过流动形式输出到客户端
            ImageIO.write(image, "JPEG", response.getOutputStream());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @RequestMapping(value = "/captcha.jpg", method = RequestMethod.POST)
    public ResponseEntity<CommonResponse<String>.Resp> validImage(@RequestBody String code, HttpSession session) {
        Object verCode = session.getAttribute("KEY_CAPTCHA");
        if (null == verCode) {
            return CommonExceptionHandler.generateResponse("验证码失效");
        }
        String verCodeStr = verCode.toString();
        LocalDateTime localDateTime = (LocalDateTime)session.getAttribute("codeTime");
        long past = localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        long now = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        if (verCodeStr == null || code == null || code.isEmpty() || !verCodeStr.equalsIgnoreCase(code)) {
            return CommonExceptionHandler.generateResponse("验证码错误");
        } else if ((now - past) / 1000 / 60 > 5) {
            return CommonExceptionHandler.generateResponse("验证码已过期");
        } else {
            // 验证成功，删除存储的验证码
            session.removeAttribute("KEY_CAPTCHA");
            return CommonExceptionHandler.generateResponse("");
        }
    }

}