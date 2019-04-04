/**
 * Title: DBUtil
 * Description: Copyright: Copyright (c) 2019 Company: Sharp
 *
 * @author Sharp
 * @date 2019-4-4 15:48
 * @description Project Name: Tanya
 * Package: com.srct.service.utils
 */
package com.srct.service.utils;

import com.github.pagehelper.PageInfo;
import com.srct.service.vo.QueryReqVO;

public class DBUtil {
    public static PageInfo<?> buildPageInfo(QueryReqVO req) {
        PageInfo<?> pageInfo = new PageInfo<>();
        if (req.getCurrentPage() != null) {
            pageInfo.setPageNum(req.getCurrentPage());
        }
        if (req.getPageSize() != null) {
            pageInfo.setPageSize(req.getPageSize());
        }
        return pageInfo;
    }

    public static PageInfo<?> buildPageInfo(Integer currentPage, Integer pageSize) {
        PageInfo<?> pageInfo = new PageInfo<>();
        if (currentPage != null) {
            pageInfo.setPageNum(currentPage);
        }
        if (pageSize != null) {
            pageInfo.setPageSize(pageSize);
        }
        return pageInfo;
    }
}
