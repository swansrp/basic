/**
 * Title: FrameCacheController.java
 * Description: Copyright: Copyright (c) 2019 Company: BHFAE
 *
 * @author Sharp
 * @date 2019-7-26 23:28
 * @description Project Name: Grote
 * @Package: com.srct.service.controller
 */
package com.srct.service.controller;

import com.srct.service.cache.constant.CacheRefreshStatusConst;
import com.srct.service.service.cache.FrameCacheService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Slf4j
@RestController
@Api(value = "本地缓存刷新", tags = "本地缓存刷新")
public class FrameCacheController {

    @Resource
    private FrameCacheService frameCacheservice;

    @RequestMapping(value = "/cacheRefresh", method = {RequestMethod.POST, RequestMethod.GET})
    public String cacheRefresh(@RequestParam(value = "cacheType", required = false) String cacheType) {
        try {
            String cacheTypeTemp = "";
            if (StringUtils.isNotBlank(cacheType)) {
                cacheTypeTemp = cacheType.toUpperCase();
            }
            return frameCacheservice.refresh(cacheTypeTemp);
        } catch (Exception e) {
            log.info("刷新缓存异常", e);
            return CacheRefreshStatusConst.FAIL;
        }
    }
}
