package com.github.osvaldopina.linkbuilder.utils;

public class Utils {

    public static final Utils INSTANCE = new Utils();

    private Utils() {

    }

    public boolean isPresent(Object value) {
        if (value == null) {
            return false;
        }
        else {
            if (value instanceof String) {
                String strValue = (String) value;
                return !"".equals(strValue.trim());
            }
            return true;
        }
    }
}
