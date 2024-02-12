package com.shrineoflostsecrets.channel.util;

public class StringUtil {
	public static String slice(String input, int max) {
		int length = input.length() > max ? max : input.length();		
		return input.substring(0, length);
	}
	public static String capitalizeFirstLetter(String str) {
	    if (str == null || str.isEmpty()) {
	        return str;
	    }
	    return str.substring(0, 1).toUpperCase() + str.substring(1);
	}
}
