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
import com.srct.service.config.response.CommonExceptionHandler;
import com.srct.service.config.response.CommonResponse;
import com.srct.service.dao.entity.DictionaryItem;
import com.srct.service.service.cache.FrameCacheService;
import com.srct.service.vo.KeyValueResVO;
import com.srct.service.vo.QueryRespVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping(value = "/cache")
@Api(value = "本地缓存", tags = "本地缓存操作")
public class FrameCacheController {

    @Resource
    private FrameCacheService frameCacheService;

    @ApiOperation(value = "刷新本地缓存", notes = "刷新本地缓存")
    @RequestMapping(value = "/refresh", method = {RequestMethod.POST})
    public String cacheRefresh(@RequestParam(value = "cacheType", required = false) String cacheType) {
        try {
            String cacheTypeTemp = "";
            if (StringUtils.isNotBlank(cacheType)) {
                cacheTypeTemp = cacheType.toUpperCase();
            }
            return frameCacheService.refresh(cacheTypeTemp);
        } catch (Exception e) {
            log.info("刷新缓存异常", e);
            return CacheRefreshStatusConst.FAIL;
        }
    }

    @ApiOperation(value = "获取本地字典列表", notes = "获取本地字典列表")
    @RequestMapping(value = "/dictionaries", method = {RequestMethod.GET})
    public ResponseEntity<CommonResponse<Set<String>>.Resp> getDict() {
        Set<String> dictItemSet = frameCacheService.getDictList();
        return CommonExceptionHandler.generateResponse(dictItemSet);
    }

    @ApiOperation(value = "获取本地字典", notes = "获取本地字典")
    @RequestMapping(value = "/dictionaryItem", method = {RequestMethod.GET})
    public ResponseEntity<CommonResponse<QueryRespVO<KeyValueResVO>>.Resp> getDictItem(
            @RequestParam(value = "dictId") String dictId,
            @RequestParam(value = "dictItemId", required = false) String dictItemId) {
        QueryRespVO<KeyValueResVO> respVO = new QueryRespVO<>();
        List<DictionaryItem> dictionaryItemList = frameCacheService.getDictItemList((dictId));
        dictionaryItemList.forEach(dictionaryItem -> {
            if (StringUtils.isEmpty(dictItemId) || dictionaryItem.getItemId().equals(dictItemId)) {
                KeyValueResVO res = new KeyValueResVO();
                res.setLabel(dictionaryItem.getItemId());
                res.setValue(dictionaryItem.getItemName());
                respVO.getInfo().add(res);
            }
        });
        return CommonExceptionHandler.generateResponse(respVO);
    }

    @ApiOperation(value = "获取本地参数", notes = "获取本地参数")
    @RequestMapping(value = "/parameter", method = {RequestMethod.GET})
    public ResponseEntity<CommonResponse<String>.Resp> getParameter(
            @RequestParam(value = "parameterId") String parameterId) {
        String res = frameCacheService.getParamValueAvail(parameterId);
        return CommonExceptionHandler.generateResponse(res);
    }
}
