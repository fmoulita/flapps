package com.flapps.web.utils.hqlquery;

/**
 * @author pm
 */
public class CustomCondition implements ICondition {
	private String m_field;
	private String m_eq;
	private String m_csql;
	private ParamHelper m_cparams;

	public CustomCondition(String field, String eq, String sql, ParamHelper params) {
		this.m_field = field;
		this.m_eq = eq;
		this.m_csql = sql;
		this.m_cparams = params;
	}

//	public CustomCondition(String field, String eq, String sql, Object[] params)
//	{
//		this.m_field = field;
//		this.m_eq = eq;
//		this.m_csql = sql;
//		this.m_cparams = params != null ? Arrays.asList(params) : null;
//	}

	public void setSql(StringBuffer sb, String aliasPref, ParamHelper params) {
		if (m_cparams == null) m_cparams = new ParamHelper();

		sb.append(aliasPref).append(m_field).append(" ").append(m_eq).append(" ").append("(").append(m_csql).append(")");
		params.addAll(m_cparams);
	}

	public boolean isEmpty() {
		return (m_csql == null || m_csql.length() == 0);
	}
}
