/**
 * Title: BaseVO.java Description: Copyright: Copyright (c) 2019 Company: Sharp
 *
 * @Project Name: TanyaProduct
 * @Package: com.srct.service.tanya.product.vo
 * @author sharuopeng
 * @date 2019-02-19 13:51:27
 */
package com.srct.service.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author sharuopeng
 *
 */
@Data
public class QueryReqVO {
    private Integer currentPage;
    private Integer pageSize;
    private Date queryStartAt;
    private Date queryEndAt;
}
