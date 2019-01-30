/**
 * Title: OpenIdBO.java Description: Copyright: Copyright (c) 2019 Company: Sharp
 * 
 * @Project Name: SpringBootCommonLib
 * @Package: com.srct.service.bo.wechat
 * @author Sharp
 * @date 2019-01-30 21:37:42
 */
package com.srct.service.bo.wechat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * 
 * import lombok.Data;
 * 
 * @author Sharp
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OpenIdBO {
    private String openId;
    private String sessionKey;
}
