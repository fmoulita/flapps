/*
 * Created on Sep 13, 2005
 *
 */
package com.flapps.web.utils.hqlquery;

import java.util.*;

/**
 * @author pm
 */
public class ConditionContainer implements ICondition {
	String m_op;
	List<ICondition> m_items;

	public ConditionContainer(String op) {
		this.m_op = " " + op + " ";
		this.m_items = new ArrayList<ICondition>();
	}

	public ConditionContainer add(ICondition c) {
		if (c != null) this.m_items.add(c);
		return this;
	}

	public ConditionContainer andContainer() {
		ConditionContainer cc = ConditionContainer.and();
		add(cc);
		return cc;
	}

	public ConditionContainer orContainer() {
		ConditionContainer cc = ConditionContainer.or();
		add(cc);
		return cc;
	}

	public ConditionContainer like(String field, Object val) {
		if (val != null) {
			String sval = val.toString();
			sval = (sval.indexOf('*') >= 0) ? sval.replace('*', '%') : sval + "%";
			add(new Single2ValCondition("lower(%" + field + ")", "like", sval.toLowerCase()));
		}
		return this;
	}

	public ConditionContainer likeInside(String field, Object val) {
		if (val != null) {
			String sval = val.toString();
			sval = (sval.indexOf('*') >= 0) ? sval.replace('*', '%') : "%" + sval + "%";
			add(new Single2ValCondition("lower(%" + field + ")", "like", sval.toLowerCase()));
		}
		return this;
	}

	public ConditionContainer notLike(String field, Object val) {
		if (val != null) {
			String sval = val.toString();
			sval = (sval.indexOf('*') >= 0) ? sval.replace('*', '%') : sval + "%";
			add(new Single2ValCondition("lower(%" + field + ")", "not like", sval.toLowerCase()));
		}
		return this;
	}

	public ConditionContainer eq(String field, Object val) {
		return add(new SingleValCondition(field, "=", val));
	}

	public ConditionContainer notEq(String field, Object val) {
		return add(new SingleValCondition(field, "!=", val));
	}

	public ConditionContainer gt(String field, Object val) {
		return add(new SingleValCondition(field, ">", val));
	}

	public ConditionContainer ge(String field, Object val) {
		return add(new SingleValCondition(field, ">=", val));
	}

	public ConditionContainer lt(String field, Object val) {
		return add(new SingleValCondition(field, "<", val));
	}

	public ConditionContainer le(String field, Object val) {
		return add(new SingleValCondition(field, "<=", val));
	}

	public ConditionContainer eq2(String field1, String field2) {
		return add(new TwoColumnCondition(field1, "=", field2));
	}

	public ConditionContainer notEq2(String field1, String field2) {
		return add(new TwoColumnCondition(field1, "!=", field2));
	}

	public ConditionContainer gt2(String field1, String field2) {
		return add(new TwoColumnCondition(field1, ">", field2));
	}

	public ConditionContainer ge2(String field1, String field2) {
		return add(new TwoColumnCondition(field1, ">=", field2));
	}

	public ConditionContainer lt2(String field1, String field2) {
		return add(new TwoColumnCondition(field1, "<", field2));
	}

	public ConditionContainer le2(String field1, String field2) {
		return add(new TwoColumnCondition(field1, "<=", field2));
	}

	public ConditionContainer isNull(String field) {
		return add(new IsNullCondition(field));
	}

	public ConditionContainer isNotNull(String field) {
		return add(new IsNullCondition(field).not());
	}

	public ConditionContainer in(String field, Object... values) {
		return add(new ArrayCondition(field, "in", values));
	}

	public ConditionContainer in(String field, Collection values) {
		return add(new CollectionCondition(field, "in", values));
	}

	public ConditionContainer notIn(String field, Object[] values) {
		return add(new ArrayCondition(field, "not in", values));
	}

	public ConditionContainer notIn(String field, Collection values) {
		return add(new CollectionCondition(field, "not in", values));
	}

//	public ConditionContainer customCondition(String field, String eq, SimpleHql simpleHql)
//	{
//		return add(new CustomCondition(field, eq, simpleHql.getHql(), simpleHql.getParams()));
//	}

	public void subquery(String field, String condition, HqlQuery<?> query) {
		ParamHelper params = new ParamHelper();
		String qsql = query.getHql(query.getAlias() + ".id", "", false, params);
		add(new CustomCondition(field, condition, qsql, params));
	}

	public void subquery(String field, String condition, HqlQuery<?> query, String subselectField) {
		ParamHelper params = new ParamHelper();
		String qsql = query.getHql(subselectField, "", false, params);
		add(new CustomCondition(field, condition, qsql, params));
	}

	public ConditionContainer sqlCondition(String condition) {
		return add(new SqlCondition(condition));
	}

	public ConditionContainer between(String field, Object value1, Object value2) {
		return add(new BetweenCondition(field, value1, value2));
	}

	public ConditionContainer betweenFromTo(String field, Object value1, Object value2) {
		return andContainer().ge(field, value1).lt(field, value2);
	}

	public ConditionContainer notBetweenFromTo(String field, Object value1, Object value2) {
		return orContainer().lt(field, value1).ge(field, value2);
	}

	public ConditionContainer notBetween(String field, Object value1, Object value2) {
		return add(new BetweenCondition(field, value1, value2).not());
	}

	public ConditionContainer betweenFields(String field1, String field2, Object value) {
		return add(new BetweenFieldsCondition(field1, field2, value));
	}

	public ConditionContainer notBetweenFields(String field1, String field2, Object value) {
		return add(new BetweenFieldsCondition(field1, field2, value).not());
	}

	public ConditionContainer betweenDate(String fieldFrom, String fieldTo, Date value) {
		ConditionContainer cand = this.andContainer();
		cand.isNotNull(fieldFrom);
		cand.le(fieldFrom, value);
		cand.orContainer().ge(fieldTo, value).isNull(fieldTo);
		return this;
	}

	public void setSql(StringBuffer sb, String aliasPref, ParamHelper val) {
		if (!isEmpty()) {
			boolean isfirst = true;
			sb.append("(");
			Iterator<ICondition> i = this.m_items.iterator();
			while (i.hasNext()) {
				ICondition c = i.next();
				if (!c.isEmpty()) {
					if (!isfirst) sb.append(m_op);
					c.setSql(sb, aliasPref, val);
					isfirst = false;
				}
			}
			sb.append(")");
		}
	}

	public boolean isEmpty() {
		Iterator<ICondition> i = this.m_items.iterator();
		while (i.hasNext()) {
			ICondition c = i.next();
			if (!c.isEmpty()) return false;
		}
		return true;
	}

	public static ConditionContainer and() {
		return new ConditionContainer("and");
	}

	public static ConditionContainer or() {
		return new ConditionContainer("or");
	}
}
