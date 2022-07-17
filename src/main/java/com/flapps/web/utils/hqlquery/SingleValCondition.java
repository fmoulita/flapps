/*
 * Created on Sep 13, 2005
 *
 */
package com.flapps.web.utils.hqlquery;

/**
 * @author pm
 */
public class SingleValCondition implements ICondition {
	private String m_field;
	private String m_eq;
	private Object m_value;

	public SingleValCondition(String field, String eq, Object value) {
		this.m_field = field;
		this.m_eq = eq;
		this.m_value = value;
	}

	public void setSql(StringBuffer sb, String aliasPref, ParamHelper val) {
		if (!isEmpty()) {
			sb.append(aliasPref).append(m_field).append(" ").append(m_eq).append(" ").append(val.addParamAndGetName(m_value));
		}
	}

	public boolean isEmpty() {
		return (m_value == null);
	}
}
