/**
 * Title: ClientTypeHolder.java
 * Description: Copyright: Copyright (c) 2019 Company: BHFAE
 *
 * @author Sharp
 * @date 2019-7-31 13:15
 * @description Project Name: Grote
 * @Package: com.srct.service.account.config
 */
package com.srct.service.config.holder;

public class ClientTypeHolder {

    private static final ThreadLocal<String> CLIENT_TYPE_HOLDER = new ThreadLocal<>();

    private ClientTypeHolder() {
    }

    public static void set(String s) {
        CLIENT_TYPE_HOLDER.set(s);
    }

    public static String get() {
        return CLIENT_TYPE_HOLDER.get();
    }

    public static void remove() {
        if (CLIENT_TYPE_HOLDER.get() != null) {
            CLIENT_TYPE_HOLDER.remove();
        }
    }
}
