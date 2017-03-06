package com.storyReader.tw;

public class Util {
	
	public static String append(Object... aObjects) {
		return append(null, aObjects);
	}
	
	public static String append(Character aSeparator, Object... aObjects) {
		return appendStringWithSeparator(aSeparator == null ? null : String.valueOf(aSeparator), aObjects);
	}
	
	private static String appendStringWithSeparator(String aSepatator, Object... aObjects) {
		if (aObjects == null | aObjects.length == 0 | aObjects[0] == null) {
			return null;
		}
		
		StringBuffer sb = new StringBuffer("");
		for (Object obj : aObjects) {
			sb.append(obj);
			if (aSepatator != null) {
				sb.append(aSepatator);
			}
		}
		return sb.toString().substring(0, sb.toString().length() - 1);
	}
}