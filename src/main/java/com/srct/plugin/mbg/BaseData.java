/**   
 * Copyright ?2018 SRC-TJ Service TG. All rights reserved.
 * 
 * @Project Name: SpringBootCommonLib
 * @Package: com.srct.plugin.mbg 
 * @author: ruopeng.sha   
 * @date: 2018-10-30 19:25
 */
package com.srct.plugin.mbg;

import lombok.Data;

/**
 * @ClassName: BaseData
 * @Description: TODO
 */
@Data
public class BaseData {

	private String tableName;

	private String columnComment;

	private String columnType;

	private String columnName;

}
