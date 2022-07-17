package com.flapps.web.utils.hqlquery;

public class Single2ValCondition implements ICondition {
	private String m_field;
	private String m_eq;
	private Object m_value;

	public Single2ValCondition(String field, String eq, Object value) {
		this.m_field = field;
		this.m_eq = eq;
		this.m_value = value;
	}

	public void setSql(StringBuffer sb, String aliasPref, ParamHelper val) {
		if (!isEmpty()) {
			String leftExp = m_field.replaceAll("%", aliasPref != null ? aliasPref : "");
			sb.append(leftExp).append(" ").append(m_eq).append(" ").append(val.addParamAndGetName(m_value));
		}
	}

	public boolean isEmpty() {
		return (m_value == null);
	}
}
