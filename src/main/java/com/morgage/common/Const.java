package com.morgage.common;

import org.springframework.core.env.Environment;

public class Const {


    private static Environment env;

    public static class USER_ROLE {
        public static final String USER = "USER";
        public static final String ADMIN = "ADMIN";
    }

    public static class USER_STATUS {
        public static final Integer ACTIVE = 1;
        public static final Integer DEACTIVE = 2;
        public static final Integer NOT_ACTIVE= 3;
    }

    public enum ROLE_TYPE {
        USER,
        ADMIN;

        public static ROLE_TYPE parse(String role) {
            if (role.equals(USER_ROLE.ADMIN)) {
                return ADMIN;
            }
            return USER;
        }

        public String value() {
            switch (this) {
                case USER:
                    return USER_ROLE.USER;
                case ADMIN:
                    return USER_ROLE.ADMIN;
            }
            return null;
        }

        public int getRoleID() {
            switch (this) {
                case USER:
                    return 1;
                case ADMIN:
                    return 2;
            }
            return 1;
        }
    }

    public enum NOTIFICATION_STATUS {

        NOT_SEEN(0),
        SEEN(1);

        public final int value;

        NOTIFICATION_STATUS(int type) {
            value = type;
        }

        public static NOTIFICATION_STATUS getType(int type) {
            if (type == NOT_SEEN.value) {
                return NOT_SEEN;
            }
            return SEEN;
        }
    }

    public static class NOTIFICATION_TYPE {
        public static final Integer LIKE = 1;
        public static final Integer FAVORITE = 2;
        public static final Integer SYSTEM_PAWNER = 3;
        public static final Integer SYSTEM_SHOP = 4;
    }

}
