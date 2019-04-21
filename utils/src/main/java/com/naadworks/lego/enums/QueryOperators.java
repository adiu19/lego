package com.naadworks.lego.enums;

public enum QueryOperators {

    SUFFIX_EQUAL_TO("eq"),
    SUFFIX_IS_NULL("is"),
    SUFFIX_IS_NOT_NULL("nn"),
    SUFFIX_NOT_EQUAL_TO("ne"),
    SUFFIX_GREATER_THAN("gt"),
    SUFFIX_GREATER_THAN_EQUAL_TO("ge"),
    SUFFIX_LESS_THAN("lt"),
    SUFFIX_LESS_THAN_EQUAL_TO("le"),
    SUFFIX_LIKE("like"),
    SUFFIX_IN("in"),
    SUFFIX_NOT_IN("nin");

    public String msg;

    QueryOperators(String msg) {
        this.msg = msg;
    }

    public static QueryOperators fromString(String msg) {
        if (msg != null) {
            QueryOperators[] var1 = values();
            int var2 = var1.length;

            for(int var3 = 0; var3 < var2; ++var3) {
                QueryOperators b = var1[var3];
                if (msg.equalsIgnoreCase(b.msg)) {
                    return b;
                }
            }
        }

        return null;
    }
}
