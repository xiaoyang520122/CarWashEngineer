package com.gb.cwsm.engineer.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtil {

	public static boolean isMobileNO(String mobiles) {
		
		
//		Pattern p = Pattern .compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
//		Pattern p = Pattern .compile("^1(3[0-9]|4[57]|5[0-35-9]|7[0135678]|8[0-9])\\d{8}$");
//		Matcher m = p.matcher(mobiles);
		
		
		/**
	     * 手机号码
	     * 移动：134,135,136,137,138,139,147,150,151,152,157,158,159,170,178,182,183,184,187,188
	     * 联通：130,131,132,145,152,155,156,1709,171,176,185,186
	     * 电信：133,134,153,1700,177,180,181,189
	     */
//	    String str = "^1(3[0-9]|4[57]|5[0-35-9]|7[01678]|8[0-9])\\d{8}$";
	    String str = "^1(3[0-9]|4[57]|5[0-35-9]|7[0135678]|8[0-9])\\d{8}$";
		Pattern pattern = Pattern.compile(str);
		Matcher matcher = pattern.matcher(mobiles);
		return matcher.matches();
		} 
}
