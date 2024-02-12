package com.shrineoflostsecrets.channel.util;

import com.shrineoflostsecrets.channel.enumerations.ShrineDebugEnum;

public class DebugMode {
	public static String debug = System.getenv("DEBUG");
	
	public static boolean getSystemDebug() {
		return (debug != null && Boolean.valueOf(debug));
	}
	public static boolean isDebug() {
		return isDebug(ShrineDebugEnum.DEBUG);
	}
	public static boolean isDebug(ShrineDebugEnum shrineDebug) {
		return (getSystemDebug()) == shrineDebug.getState() || ShrineDebugEnum.BOTH.equals(shrineDebug);
	}
}