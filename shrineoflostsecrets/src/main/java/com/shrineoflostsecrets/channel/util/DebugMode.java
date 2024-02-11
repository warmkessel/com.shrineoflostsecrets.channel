package com.shrineoflostsecrets.channel.util;

import com.shrineoflostsecrets.channel.enumerations.ShrineDebug;

public class DebugMode {
	public static String debug = System.getenv("DEBUG");
	
	public static boolean getSystemDebug() {
		return (debug != null && Boolean.valueOf(debug));
	}
	public static boolean isDebug() {
		return isDebug(ShrineDebug.DEBUG);
	}
	public static boolean isDebug(ShrineDebug shrineDebug) {
		return (getSystemDebug()) == shrineDebug.getState() || ShrineDebug.BOTH.equals(shrineDebug);
	}
}