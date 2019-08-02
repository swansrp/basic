/**
 * Title: CacheProvider.java
 * Description: Copyright: Copyright (c) 2019 Company: BHFAE
 *
 * @author Sharp
 * @date 2019-7-26 18:19
 * @description Project Name: Grote
 * @Package: com.srct.service.cache.base
 */
package com.srct.service.cache;

public interface CacheProvider {

    String getType();

    void init();

    void forceInit();

    Object get();
}

