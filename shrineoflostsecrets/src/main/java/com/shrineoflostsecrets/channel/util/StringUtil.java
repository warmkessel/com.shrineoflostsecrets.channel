package com.shrineoflostsecrets.channel.util;

public class StringUtil {
	public static String slice(String input, int max) {
		int length = input.length() > max ? max : input.length();		
		return input.substring(0, length);
	}
}
