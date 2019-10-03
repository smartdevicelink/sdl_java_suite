package com.smartdevicelink.util;

public class CompareUtils {

    public static boolean areStringsEqual(String string1, String string2, boolean ignoreCase, boolean nullIsEqual){

        if (string1 == null) {
            if (string2 == null) {
                return nullIsEqual;
            } else {
                return false;
            }
        } else {
            //At least String 1 is not null, use it for the remaining checks
            if (ignoreCase) {
                return string1.equalsIgnoreCase(string2);
            } else {
                return string1.equals(string2);
            }
        }
    }

}
