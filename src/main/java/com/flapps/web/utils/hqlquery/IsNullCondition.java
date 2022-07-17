/*
 * Created on Sep 13, 2005
 *
 */
package com.flapps.web.utils.hqlquery;

/**
 * @author pm
 */
public class IsNullCondition extends AbstractNotCondition {
	private String m_field;

	public IsNullCondition(String field) {
		this.m_field = field;
	}

	public void setSql(StringBuffer sb, String aliasPref, ParamHelper val) {
		if (!isEmpty()) {
			sb.append(aliasPref).append(m_field).append(" is ").append(this.getNotSql()).append("null");
		}
	}

	public boolean isEmpty() {
		return false;
	}
}
