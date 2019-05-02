package com.srct.service.service;

import java.awt.image.BufferedImage;

/**
 * Title: CaptchaService
 * Description: Copyright: Copyright (c) 2019 Company: Sharp
 *
 * @author Sharp
 * @date 2019-4-25 16:27
 * @description Project Name: Tanya
 * Package: com.srct.service.service
 */
public interface CaptchaService {

    BufferedImage generateCaptcha(String token);

    void validateCaptcha(String token, String code);

}
