package com.example.administrator.ydwlxcpt.Utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2017/8/7.
 */

public class StringUtils {

    public static boolean isMobileNO(String mobiles){
        Pattern p = Pattern.compile("^((13[0-9])|(14[0-9])|(15[^4,\\D])|(18[0-9])|(17[0-9]))\\d{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }
    public static boolean isChinese(String strName) {
        char[] ch = strName.toCharArray();
        for (int i = 0; i < ch.length; i++) {
            char c = ch[i];
            if (isChinese(c)) {
                return true;
            }
        }
        return false;
    }
    private static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
            return true;
        }
        return false;
    }
    public static boolean isChepaihao(String str) {
        Pattern p = null;
        Matcher m = null;
        boolean b = false;
        p = Pattern.compile("^[京,津,渝,沪,冀,晋,辽,吉,黑,苏,浙,皖,闽,赣,鲁,豫,鄂,湘,粤,琼,川,贵,云,陕," +
                "秦,甘,陇,青,台,蒙,桂,宁,新,藏,澳,军,海,航,警][A-Z][0-9,A-Z]{5}$"); // 验证车牌号
        m = p.matcher(str);
        b = m.matches();
        return b;
    }
    public static boolean isTime(String time){
        Pattern p = Pattern.compile("^((2017-11-(2[0-9])|(3[0-1]))|(2017-12-|0([0-9])|(1[0-9])|(2[0-9])|(3[0-1]))|(2018-1|0([0-9])(1[0-4])|(2[1-9])|(3[0-1]))|(2018-2(0[0-9])|(1[0-9])|(2[0-8]))|(2018-3(0[0-9])|(1[1-5])))\\d{8}$");
        Matcher m = p.matcher(time);
        return m.matches();
    }
}
