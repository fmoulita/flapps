/*
 * Created on Sep 13, 2005
 *
 */
package com.flapps.web.utils.hqlquery;

/**
 * @author pm
 */
public class QC {
	public static String field(String alias, String field) {
		return (alias != null && alias.length() > 0) ? alias + "." + field : field;
	}

	public static Object val(boolean b) {
		return new Boolean(b);
	}

	public static Object val(int b) {
		return new Integer(b);
	}

	public static Object val(long b) {
		return new Long(b);
	}

	public static Object val(double b) {
		return new Double(b);
	}

	public static Object val(float b) {
		return new Float(b);
	}
}
