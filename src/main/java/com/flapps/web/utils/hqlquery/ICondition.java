/*
 * Created on Sep 13, 2005
 *
 */
package com.flapps.web.utils.hqlquery;

/**
 * @author pm
 */
public interface ICondition {
	void setSql(StringBuffer sb, String aliasPref, ParamHelper params);

	boolean isEmpty();
}
