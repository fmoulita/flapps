/*
 * Created on Sep 13, 2005
 *
 */
package com.flapps.web.utils.hqlquery;

/**
 * @author pm
 */
public abstract class AbstractNotCondition implements ICondition {
	private String notSql = "";

	public ICondition not() {
		this.notSql = "not ";
		return this;
	}

	protected String getNotSql() {
		return this.notSql;
	}
}
