/**   
 * Copyright ?2018 SRC-TJ Service TG. All rights reserved.
 * 
 * @Project Name: SpringBootCommon
 * @Package: com.srct.service.utils.email 
 * @author: ruopeng.sha   
 * @date: 2018-08-23 09:53
 */
package com.srct.service.utils.email;

/**
 * @ClassName: EmailAttachment
 * @Description: TODO
 */
public class EmailAttachment {

    private Object data;

    private String name;

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
