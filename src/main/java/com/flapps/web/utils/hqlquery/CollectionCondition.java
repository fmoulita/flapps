package com.flapps.web.utils.hqlquery;

import java.util.Collection;

public class CollectionCondition implements ICondition {
	private String m_field;
	private String m_eq;
	private Collection m_values;

	public CollectionCondition(String field, String eq, Collection values) {
		this.m_field = field;
		this.m_eq = eq;
		this.m_values = values;
	}

	public void setSql(StringBuffer sb, String aliasPref, ParamHelper val) {
		if (!isEmpty()) {
			sb.append(aliasPref).append(m_field).append(" ").append(m_eq).append(" (");
			if (m_values != null) {
				boolean isFirst = true;
				for (Object o : m_values) {
					if (isFirst) isFirst = false;
					else sb.append(", ");
					sb.append(val.addParamAndGetName(o));
				}
			}
			sb.append(")");
		}
	}

	public boolean isEmpty() {
		return (m_values == null || m_values.size() == 0);
	}
}
