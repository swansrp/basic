/**   
 * Copyright © 2018 SRC-TJ Service TG. All rights reserved.
 * 
 * @Package: com.srct.service.utils 
 * @author: xu1223.zhang   
 * @date: 2018-08-02 16:48
 */
package com.srct.service.utils;

/**
 * @ClassName: CommonEnum
 * @Description: TODO
 */
public class CommonEnum {

    private CommonEnum() {
    }

    public static class AccountRoleEnum {

        private AccountRoleEnum() {
        }

        public static final int ACCOUNT_ROLE_ROOT = 0;

        public static final int ACCOUNT_ROLE_OTHERS = 1;
    }

    public static class CommonTimeEnum {

        private CommonTimeEnum() {
        }

        public static final int DURATION_TYPE_HOUR = 1;

        public static final int DURATION_TYPE_DAY = 2;

        public static final int DURATION_TYPE_WEEK = 3;

        public static final int DURATION_TYPE_MONTH = 4;

        public static final int DURATION_TYPE_YEAR = 5;
    }

    public static class SecurityEnum {

        private SecurityEnum() {
        }

        public static final String CHARSET_UTF8 = "UTF-8";

        public static final String CHARSET_GBK = "GBK";

        public static final String AES_ALG = "AES";

        public static final String RSA_ALG = "RSA";

        public static final String SIGN_TYPE_RSA = "RSA";

        public static final String SIGN_TYPE_RSA2 = "RSA2";

        public static final String SIGN_ALGORITHMS = "SHA1WithRSA";

        public static final String SIGN_SHA256RSA_ALGORITHMS = "SHA256WithRSA";
    }
}
