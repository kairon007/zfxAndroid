package com.zifei.corebeau.utils;

import java.util.regex.Pattern;

public class StringUtil {

    // email
    private final static Pattern emailer = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");

    //phone
    private final static Pattern phoner = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,0-9]))\\d{8}$"); 
    
    /**
     * if string is empty
     *
     * @param input
     * @return boolean
     */
    public static boolean isEmpty(String input) {
        if (input == null || "".equals(input))
            return true;

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
                return false;
            }
        }
        return true;
    }

    /**
     * if email is valid
     *
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        if (email == null || email.trim().length() == 0)
            return false;
        return emailer.matcher(email).matches();
    }

    /**
     * 半角转换为全角
     *
     * @param input
     * @return
     */
    public static String toDBC(String input) {
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 12288) {
                c[i] = (char) 32;
                continue;
            }
            if (c[i] > 65280 && c[i] < 65375)
                c[i] = (char) (c[i] - 65248);
        }
        return new String(c);
    }

    /**
     * encrypt phone number
     *
     * @param phone
     * @return
     */
    public static String encryptPhoneNumber(String phone) {
        if (phone == null) {
            return "*****";
        }
        if (phone.length() < 11) {
            return phone;
        }
        return phone.substring(0, 3) + "*****" + phone.substring(8, 11);
    }

    /**
     * convert user credit log's address
     *
     * @param address
     * @return
     */
    public static String convertUserDeliveryAddress(String address) {
        String[] info = address.split("#");
        StringBuffer text = new StringBuffer();
        text
                .append(info[1]) // province
                .append(info[2]) // city
                .append(info[3]) // zone
                .append(info[4]) // street
                .append("\n")
                .append(info[0]) // name
                .append(" ")
                .append(info[6]) // phone
        ;

        return text.toString();
    }
    
    public static String creditFormat(String creditStr){
        StringBuffer buffer = new StringBuffer();
        if (creditStr.contains(".")) {
            int a = creditStr.indexOf(".");
            String string1 = creditStr.substring(0, a);
            String string2 = creditStr.substring(a+1, creditStr.length());
            
            if (string2.length() == 1) {
                string2 = string2 + "0";
            } else {
                string2 = string2.substring(0,2);
            }
            buffer = buffer.append(string1).append(".").append(string2);
            
        } else {
            buffer = buffer.append(creditStr).append(".00");
        }
        
        return buffer.toString();
    }
    
   public static boolean isPhoneNum(String phoneNum) {
    	
    	if (phoneNum == null || phoneNum.trim().length() == 0)
            return false;
		return phoner.matcher(phoneNum).matches();
	}
}
