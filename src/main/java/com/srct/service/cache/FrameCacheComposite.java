/**
 * Title: FrameCacheComposite.java
 * Description: Copyright: Copyright (c) 2019 Company: BHFAE
 *
 * @author Sharp
 * @date 2019-7-26 23:16
 * @description Project Name: Grote
 * @Package: com.srct.service.cache
 */
package com.srct.service.cache;

import com.srct.service.cache.base.BaseCacheComposite;
import com.srct.service.cache.constant.CacheRefreshStatusConst;
import com.srct.service.cache.provider.DictCacheProvider;
import com.srct.service.cache.provider.ErrorCodeCacheProvider;
import com.srct.service.cache.provider.ParamCacheProvider;
import com.srct.service.cache.provider.ServiceApiCacheProvider;
import com.srct.service.cache.provider.ServicePermitCacheProvider;
import com.srct.service.constant.ParamFrame;
import com.srct.service.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@Slf4j
public class FrameCacheComposite extends BaseCacheComposite {

    @Resource
    private DictCacheProvider dictCacheProvider;
    @Resource
    private ErrorCodeCacheProvider errorCodeCacheProvider;
    @Resource
    private ParamCacheProvider paramCacheProvider;
    @Resource
    private ServiceApiCacheProvider serviceApiCacheProvider;
    @Resource
    private ServicePermitCacheProvider servicePermitCacheProvider;

    @Override
    public synchronized void init() {
        initMap();
        if (getIsCacheInitBoot()) {
            for (CacheProvider cacheProvider : providerMap.values()) {
                cacheProvider.init();
            }
        }
    }

    private boolean getIsCacheInitBoot() {
        return StringUtil.convertSwitch(paramCacheProvider.get().get(ParamFrame.CACHE_INIT_MODE.toString()).getValue());
    }

    private synchronized void initMap() {
        put(dictCacheProvider, errorCodeCacheProvider, paramCacheProvider, serviceApiCacheProvider,
                servicePermitCacheProvider);
    }

    public synchronized String refresh(String cacheType) {
        String cacheRefreshStatus = CacheRefreshStatusConst.SUCCESS;
        if (StringUtils.isBlank(cacheType)) {
            forceInit();
        } else if (providerMap.containsKey(cacheType)) {
            providerMap.get(cacheType).forceInit();
        } else {
            log.info("缓存类型在该服务器无需刷新 {}", cacheType);
            cacheRefreshStatus = CacheRefreshStatusConst.IGNORE;
        }
        return cacheRefreshStatus;
    }
}

