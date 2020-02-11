package com.srct.service.vo.object;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Title: UploadVO
 * Description: Copyright: Copyright (c) 2019 Company: BHFAE
 *
 * @author Sharp
 * @date 2019/10/15 22:26
 * @description Project Name: Grote
 * @Package: com.srct.service.vo.object
 */
@Data
public class UploadVO {
    @ApiModelProperty("文件路径")
    private String fileName;
    @ApiModelProperty("文件大小")
    private Long fileSize;
}
