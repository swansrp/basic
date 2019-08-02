/**
 * Title: BaseVO.java Description: Copyright: Copyright (c) 2019 Company: Sharp
 *
 * @Project Name: TanyaProduct
 * @Package: com.srct.service.tanya.product.vo
 * @author sharuopeng
 * @date 2019-02-19 13:51:27
 */
package com.srct.service.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author sharuopeng
 */
@Data
public class QueryReqVO extends ReqBaseVO {
    @ApiModelProperty("当前页")
    private Integer currentPage;
    @ApiModelProperty("每页大小")
    private Integer pageSize;

}
