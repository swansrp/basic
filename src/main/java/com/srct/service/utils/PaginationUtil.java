/**
 * Copyright ?2018 SRC-TJ Service TG. All rights reserved.
 *
 * @Project Name: SpringBootCommon
 * @Package: com.srct.service.utils
 * @author: ruopeng.sha
 * @date: 2018-09-29 10:40
 */
package com.srct.service.utils;

import com.srct.service.constant.ErrCodeSys;
import com.srct.service.validate.Validator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;

/**
 * @ClassName: PaginationUtil
 * @Description: TODO
 */
public class PaginationUtil<T> {

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PaginationInfo {
        private List data;
        /**
         * 上一页
         */
        private int lastPage;
        /**
         * 当前页
         */
        private int nowPage;
        /**
         * 下一页
         */
        private int nextPage;
        /**
         * 每页条数
         */
        private int pageSize;
        /**
         * 总页数
         */
        private int totalPage;
        /**
         * 总数据条数
         */
        private int totalCount;
    }


    public static PaginationInfo getPaginationList(List data, int nowPage, int pageSize) {
        Validator.assertNotEmpty(data, ErrCodeSys.PA_PARAM_NULL, "分页LIST");
        int totalPage = data.size();
        List res = Collections.emptyList();
        int fromIndex = (nowPage - 1) * pageSize;
        if (fromIndex < data.size() && fromIndex > 0) {
            int toIndex = nowPage * pageSize;
            toIndex = toIndex >= totalPage ? totalPage : toIndex;
            res = data.subList(fromIndex, toIndex);
        }

        PaginationInfo info =
                PaginationInfo.builder().data(res).nowPage(nowPage).pageSize(pageSize).totalCount(totalPage)
                        .totalPage((data.size() + pageSize - 1) / pageSize).lastPage(nowPage - 1 > 1 ? nowPage - 1 : 1)
                        .nextPage(nowPage >= totalPage ? totalPage : nowPage + 1).build();
        return info;
    }
}
