package com.poly.petcare.domain.utils;

public class ConverCode {
    public static String convertCode(Long codeMax, String DTOCode, String prefix) {
        String code;
        if (DTOCode != null) {
            code = DTOCode;
        } else {
            if (codeMax == null) {
                code = prefix + "0000";
            } else {
                if (codeMax < 9) {
                    code = prefix + "000" + codeMax;
                } else if (codeMax < 99) {
                    code = prefix + "00" + codeMax;
                } else if (codeMax < 999) {
                    code = prefix + "0" + codeMax;
                } else {
                    code = prefix + codeMax;
                }
            }
        }
        return code;
    }
}
