/*
 * Created on Sep 14, 2005
 *
 */
package com.flapps.web.utils.hqlquery;

/**
 * @author pm
 */
public class TwoColumnCondition implements ICondition {
	private String m_field1, m_field2;
	private String m_eq;

	public TwoColumnCondition(String field1, String eq, String field2) {
		this.m_field1 = field1;
		this.m_eq = " " + eq + " ";
		this.m_field2 = field2;
	}

	public void setSql(StringBuffer sb, String aliasPref, ParamHelper val) {
		sb.append(aliasPref).append(m_field1).append(m_eq).append(aliasPref).append(m_field2);
	}

	public boolean isEmpty() {
		return false;
	}
}
