/**
 * Title: BaseResVO.java Description: Copyright: Copyright (c) 2019 Company: Sharp
 *
 * @Project Name: TanyaProduct
 * @Package: com.srct.service.tanya.product.vo
 * @author sharuopeng
 * @date 2019-02-19 13:52:13
 */
package com.srct.service.vo;

import com.github.pagehelper.PageInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author sharuopeng
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class QueryRespVO<T> extends QueryReqVO {
    private List<T> info;
    private Integer totalPages;
    private Long totalSize;

    public QueryRespVO(PageInfo pageInfo) {
        this();
        buildPageInfo(pageInfo);
    }

    public QueryRespVO() {
        info = new ArrayList<>();
    }

    public void buildPageInfo(PageInfo pageInfo) {
        this.setTotalPages(pageInfo.getPages());
        this.setTotalSize(pageInfo.getTotal());
        this.setCurrentPage(pageInfo.getPageNum());
        this.setPageSize(pageInfo.getPageSize());
    }

    public void buildPageInfoWithList(PageInfo pageInfo) {
        buildPageInfo(pageInfo);
        if (CollectionUtils.isNotEmpty(pageInfo.getList())) {
            info.addAll(pageInfo.getList());
        }
    }
}
