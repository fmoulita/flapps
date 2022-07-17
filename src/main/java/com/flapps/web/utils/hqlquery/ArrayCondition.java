package com.flapps.web.utils.hqlquery;

public class ArrayCondition implements ICondition {
	private String m_field;
	private String m_eq;
	private Object[] m_values;

	public ArrayCondition(String field, String eq, Object[] values) {
		this.m_field = field;
		this.m_eq = eq;
		this.m_values = values;
	}

	public void setSql(StringBuffer sb, String aliasPref, ParamHelper val) {
		if (!isEmpty()) {
			sb.append(aliasPref).append(m_field).append(" ").append(m_eq).append(" (");
			for (int i = 0; i < m_values.length; i++) {
				sb.append(val.addParamAndGetName(m_values[i]));
				if (i < m_values.length - 1) sb.append(", ");
			}
			sb.append(")");
		}
	}

	public boolean isEmpty() {
		return (m_values == null || m_values.length == 0);
	}
}
