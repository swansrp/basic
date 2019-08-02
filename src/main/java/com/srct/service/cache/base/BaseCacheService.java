/**
 * Title: BaseCacheService.java
 * Description: Copyright: Copyright (c) 2019 Company: BHFAE
 *
 * @author Sharp
 * @date 2019-7-26 18:19
 * @description Project Name: Grote
 * @Package: com.srct.service.cache.base
 */
package com.srct.service.cache.base;

import com.srct.service.cache.provider.DictCacheProvider;
import com.srct.service.cache.provider.ErrorCodeCacheProvider;
import com.srct.service.cache.provider.ParamCacheProvider;
import com.srct.service.cache.provider.ServiceApiCacheProvider;
import com.srct.service.cache.provider.ServicePermitCacheProvider;
import com.srct.service.constant.CommonConst;
import com.srct.service.constant.Dict;
import com.srct.service.constant.Param;
import com.srct.service.dao.entity.DictionaryItem;
import com.srct.service.dao.entity.ErrorCode;
import com.srct.service.dao.entity.Parameter;
import com.srct.service.dao.entity.ServiceApi;
import com.srct.service.dao.entity.ServicePermit;
import com.srct.service.format.CommonFormat;
import com.srct.service.format.ConfigFormat;
import com.srct.service.utils.SortUtil;
import com.srct.service.utils.StringUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public abstract class BaseCacheService {

    protected abstract void init();

    protected abstract <T> T getProvider(Class<T> clazz);

    // --------------------------------

    private String getParamValue(String parameterIdStr) {
        Parameter obj = getProvider(ParamCacheProvider.class).get().get(parameterIdStr);
        if (obj == null) {
            return null;
        } else {
            return obj.getValue();
        }
    }

    public String getParamValueAvail(String parameterIdStr) {
        CommonFormat.checkNotBlank(parameterIdStr, "参数名");
        String value = getParamValue(parameterIdStr);
        ConfigFormat.checkNotNull(value, "参数", parameterIdStr);
        return value;
    }

    public String getParamValueAvail(Param parameterId) {
        CommonFormat.checkNotNull(parameterId, "参数名");
        return getParamValueAvail(parameterId.toString());
    }

    public boolean getParamSwitch(Param parameterId) {
        return CommonConst.YES.equals(getParamValueAvail(parameterId));
    }

    public long getParamLong(Param parameterId) {
        return Long.valueOf(getParamValueAvail(parameterId));
    }

    public int getParamInt(Param parameterId) {
        return Integer.valueOf(getParamValueAvail(parameterId));
    }

    public double getParamDouble(Param parameterId) {
        return Double.valueOf(getParamValueAvail(parameterId));
    }

    // --------------------------------

    public Map<String, DictionaryItem> getDictItemMap(String dictIdStr) {
        String key = dictIdStr;
        Map<String, DictionaryItem> map = getProvider(DictCacheProvider.class).get().get(key);
        ConfigFormat.checkNotEmpty(map, "字典项", key);
        return map;
    }

    private Map<String, DictionaryItem> getDictItemMap(Dict dictId) {
        return getDictItemMap(dictId.toString());
    }

    public String getDictItemName(Dict dictId, String itemId) {
        DictionaryItem config = getDictItemMap(dictId).get(itemId);
        ConfigFormat.checkNotNull(config, "字典条目", dictId + " " + itemId);
        return config.getItemName();
    }

    public String getDictItemId(Dict dictId, String itemName) {
        Collection<DictionaryItem> list = getDictItemMap(dictId).values();
        for (DictionaryItem dictionaryItem : list) {
            if (dictionaryItem.getItemName().equals(itemName)) {
                return dictionaryItem.getItemId();
            }
        }
        return null;
    }


    public List<DictionaryItem> getDictItemList(Dict dictId) {
        return getDictItemList(dictId.toString());
    }

    public List<DictionaryItem> getDictItemList(String dictIdStr) {
        List<DictionaryItem> list = new ArrayList<>(getDictItemMap(dictIdStr).values());
        SortUtil.sortList(list, "itemOrder", SortUtil.ASC);
        return list;
    }

    // --------------------------------

    public ErrorCode getErrorCode(String errorCode) {
        String key = StringUtil.join(errorCode);
        ErrorCode config = getProvider(ErrorCodeCacheProvider.class).get().get(key);
        return config;
    }

    // --------------------------------

    public ServiceApi getServiceApi(String method, String url, String apiVersion) {
        String key = StringUtil.join(method, url, apiVersion);
        ServiceApi config = getProvider(ServiceApiCacheProvider.class).get().get(key);
        ConfigFormat.checkNotNull(config, "接口功能", key);
        return config;
    }

    public List<String> getPermitIdList(String apiId) {
        String key = apiId;
        Map<String, ServicePermit> map = getProvider(ServicePermitCacheProvider.class).get().get(key);
        ConfigFormat.checkNotEmpty(map, "接口权限", key);
        List<String> list = new ArrayList<>(map.keySet());
        return list;
    }

}
