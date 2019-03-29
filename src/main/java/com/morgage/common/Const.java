package com.morgage.common;

import org.springframework.core.env.Environment;

public class Const {


    private static Environment env;

    public static int DEFAULT_PAWNEE_ID = 4;
    public static int DEFAULT_PAWNEE_INFO_ID = 4;
    public static int DEFAULT_TRANSACTION_ID = 4;

    public static class USER_ROLE {
        public static final String PAWNER = "PAWNER";
        public static final String SHOP = "SHOP";
        public static final String ADMIN = "ADMIN";
    }

    public static class USER_STATUS {
        public static final Integer ACTIVE = 1;
        public static final Integer DEACTIVE = 2;
        public static final Integer NOT_ACTIVE = 3;
    }

    public enum ROLE_TYPE {
        PAWNER,
        SHOP,
        ADMIN;

        public static ROLE_TYPE parse(String role) {
            if (role.equals(USER_ROLE.ADMIN)) {
                return ADMIN;
            } else if (role.equals(USER_ROLE.SHOP)) {
                return SHOP;
            } else
                return PAWNER;
        }

        public String value() {
            switch (this) {
                case PAWNER:
                    return USER_ROLE.PAWNER;
                case ADMIN:
                    return USER_ROLE.ADMIN;
                case SHOP:
                    return USER_ROLE.SHOP;
            }
            return null;
        }

        public int getRoleID() {
            switch (this) {
                case PAWNER:
                    return 2;
                case ADMIN:
                    return 4;
                case SHOP:
                    return 3;
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
        public static final Integer TRANSACTION_PAWNEE = 1;
        public static final Integer ITEM_PAWNEE = 2;
        public static final Integer TRANSACTION_SHOP = 3;
        public static final Integer TRANSACTIONITEM_SHOP = 4;

    }

    public static class TRANSACTION_STATUS {
        public static final Integer WAIT_FOR_LIQUIDATION = 2;
        public static final Integer REDEEMED = 3;
        public static final Integer LATE = 4;
        public static final Integer LIQUIDATION = 5;
        public static final Integer CANCELED = 6;
        public static final Integer NOT_YET_OVERDUE = 1;
        public static final Integer OVERDUE = 7;
        public static final Integer DEFAULT = 8;

    }

    public static class SHOP_STATUS {
        public static final Integer UNACTIVE = 1;
        public static final Integer ACTIVE = 2;
        public static final Integer BANNED = 3;
    }

    public static class ITEM_STATUS {
        public static final Integer WAIT_FOR_LIQUIDATION = 1;
        public static final Integer LIQUIDATED = 2;
        public static final Integer REDEEMED = 3;
    }

    public static class PAYMENT_TYPE {
        public static final Integer DAY = 1;
        public static final Integer WEEK = 2;
        public static final Integer MONTH = 3;
    }

    public static class TRANSACTION_LOG_STATUS {
        public static final Integer PAID = 1;
        public static final Integer UNPAID = 2;
    }

    public static class PICTURE_STATUS {
        public static final Integer TRANSACTION = 1;
        public static final Integer ITEM = 2;
        public static final Integer SHOP = 3;
    }

    public static class TRANSACTION_HISTORY {
        public static final String REDEEM = "Chuộc đồ";
        public static final String CLOSE = "Đóng";
        public static final String PAID = "Đóng lãi";
        public static final String OPEN_AGAIN = "Mở lại";
        public static final String CHANGE_STATUS = "Thay đổi trạng thái ";
        public static final String CREATE = "Khởi tạo";
    }

    public static final Integer DEFAULT_ITEM_PER_PAGE = 5;

    public static class SORT_SHOP {
        public static final Integer RATING = 1;

        public static final Integer VIEW = 2;
        public static final Integer ALL = 3;
    }

    public static class SORT_ITEM {
        public static final Integer DATE_CREATED = 1;
        public static final Integer VIEW = 2;
        public static final Integer PRICE_ASC = 5;
        public static final Integer LIKE = 3;
        public static final Integer PRICE_DESC = 4;
    }
}
