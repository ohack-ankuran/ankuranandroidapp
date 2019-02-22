package com.ankuran;

public class AppConstant {
    public static final String KEY_ACTIVITY_TYPE="Activity Type";

    public enum ACTIVITY_TYPE{
        DUE(1),
        PAYOUT(2);

        private final int type;

        ACTIVITY_TYPE(final int type)
        {
            this.type=type;
        }
    }


}
