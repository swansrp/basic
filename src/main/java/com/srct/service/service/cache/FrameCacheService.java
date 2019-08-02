/**
 * Title: FrameCacheService.java
 * Description: Copyright: Copyright (c) 2019 Company: BHFAE
 *
 * @author Sharp
 * @date 2019-7-26 23:29
 * @description Project Name: Grote
 * @Package: com.srct.service.service
 */
package com.srct.service.service.cache;

import com.srct.service.cache.FrameCacheComposite;
import com.srct.service.cache.base.BaseCacheService;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@Service
public class FrameCacheService extends BaseCacheService {
    @Resource
    private FrameCacheComposite frameCacheComposite;

    @PostConstruct
    @Override
    protected void init() {
        frameCacheComposite.init();
    }

    public String refresh(String cacheType) {
        return frameCacheComposite.refresh(cacheType);
    }

    @Override
    protected <T> T getProvider(Class<T> clazz) {
        return frameCacheComposite.getProvider(clazz);
    }
}
