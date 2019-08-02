/**
 * Title: TokenHolder.java
 * Description: Copyright: Copyright (c) 2019 Company: BHFAE
 *
 * @author Sharp
 * @date 2019-7-30 16:36
 * @description Project Name: Grote
 * @Package: com.srct.service.config.holder
 */
package com.srct.service.config.holder;

public class TokenHolder {
    private static final ThreadLocal<String> TOKEN_HOLDER = new ThreadLocal<>();

    private TokenHolder() {
    }

    public static void set(String s) {
        TOKEN_HOLDER.set(s);
    }

    public static String get() {
        return TOKEN_HOLDER.get();
    }

    public static void remove() {
        if (TOKEN_HOLDER.get() != null) {
            TOKEN_HOLDER.remove();
        }
    }

}
