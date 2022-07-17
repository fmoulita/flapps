package com.flapps.web.utils.hqlquery;

public class BetweenFieldsCondition extends AbstractNotCondition {
	private String m_field1, m_field2;
	private Object m_value;

	public BetweenFieldsCondition(String field1, String field2, Object value) {
		this.m_field1 = field1;
		this.m_field2 = field2;
		this.m_value = value;
	}

	public void setSql(StringBuffer sb, String aliasPref, ParamHelper val) {
		if (m_value != null) {
			sb.append("(").append(val.addParamAndGetName(m_value)).append(" ").append(this.getNotSql()).append("between ").append(aliasPref).append(m_field1).append(" and ").append(aliasPref)
				.append(m_field2).append(")");
		}
	}

	public boolean isEmpty() {
		return (m_value == null);
	}
}
