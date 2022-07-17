/*
 * Created on Sep 13, 2005
 *
 */
package com.flapps.web.utils.hqlquery;

/**
 * @author pm
 */
public class BetweenCondition extends AbstractNotCondition {
	private String m_field;
	private Object m_value1, m_value2;

	public BetweenCondition(String field, Object value1, Object value2) {
		this.m_field = field;
		this.m_value1 = value1;
		this.m_value2 = value2;
	}

	public void setSql(StringBuffer sb, String aliasPref, ParamHelper val) {
		if (m_value1 != null && m_value2 != null) {
			sb.append("(").append(aliasPref).append(m_field).append(" ").append(this.getNotSql()).append("between ").append(val.addParamAndGetName(m_value1)).append(" and ").append(val.addParamAndGetName(m_value2)).append(")");
		} else if (m_value1 != null) {
			sb.append(aliasPref).append(m_field).append(" >= ").append(val.addParamAndGetName(m_value1));
		} else if (m_value2 != null) {
			sb.append(aliasPref).append(m_field).append(" <= ").append(val.addParamAndGetName(m_value2));
		}
	}

	public boolean isEmpty() {
		return (m_value1 == null && m_value2 == null);
	}
}
