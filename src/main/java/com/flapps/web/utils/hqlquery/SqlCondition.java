/*
 * Created on Sep 13, 2005
 *
 */
package com.flapps.web.utils.hqlquery;

/**
 * @author pm
 */
public class SqlCondition implements ICondition {
	private String sqlCondition;

	public SqlCondition(String sql) {
		this.sqlCondition = sql;
	}

	public void setSql(StringBuffer sb, String aliasPref, ParamHelper val) {
		if (!isEmpty()) {
			sb.append(sqlCondition);
		}
	}

	public boolean isEmpty() {
		return (sqlCondition == null || sqlCondition.trim().length() == 0);
	}
}
