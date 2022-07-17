package com.flapps.web.utils.hqlquery;

import com.flapps.web.helper.StringHelper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class HqlQuery<T> {
	private Class<T> x_hibDataClass;
	private String m_alias;
	private List<JoinClause> m_joinClauses = new ArrayList<JoinClause>();
	private List<OrderByClause> m_orderByClauses = new ArrayList<OrderByClause>();
	private List<String> m_groupBy = new ArrayList<String>();
	private List<String> m_having = new ArrayList<String>();
	private ConditionContainer m_where = new ConditionContainer("and");

	public HqlQuery(Class<T> hibDataClass, String alias) {
		this.x_hibDataClass = hibDataClass;
		this.m_alias = alias;
	}

	public String getAlias() {
		return m_alias;
	}

	public void addJoin(Class<?> joinCLass, String alias) {
		addJoinClause(new JoinClause(joinCLass, alias));
	}

	public void addInnerJoin(String property, String alias) {
		addJoinClause(new JoinClause("inner", property, alias, false));
	}

	public void addInnerJoinFetch(String property, String alias) {
		addJoinClause(new JoinClause("inner", property, alias, true));
	}

	public void addLeftJoin(String property, String alias) {
		addJoinClause(new JoinClause("left outer", property, alias, false));
	}

	public void addLeftJoinFetch(String property, String alias) {
		addJoinClause(new JoinClause("left outer", property, alias, true));
	}

	public void addRightJoin(String property, String alias) {
		addJoinClause(new JoinClause("right outer", property, alias, false));
	}

	public void addRightJoinFetch(String property, String alias) {
		addJoinClause(new JoinClause("right outer", property, alias, true));
	}

	private void addJoinClause(JoinClause joinClause) {
		Iterator<JoinClause> i = m_joinClauses.iterator();
		while (i.hasNext()) {
			JoinClause jc = i.next();
			if (joinClause.getAlias().equals(jc.getAlias())) return;
		}
		this.m_joinClauses.add(joinClause);
	}

	public void addGroupBy(String property) {
		if (!m_groupBy.contains(property)) {
			m_groupBy.add(property);
		}
	}

	public void addGroupBy(String property, String having) {
		if (!m_groupBy.contains(property)) {
			m_groupBy.add(property);
		}
		if (!m_having.contains(having)) {
			m_having.add(having);
		}
	}

	public ConditionContainer where() {
		return m_where;
	}

	public void addOrderBy(String field, String sort) {
		this.m_orderByClauses.add(new OrderByClause(field, sort));
	}

	public void addOrderBy(String xfield) {
		if (xfield != null) {
			String[] orders = StringHelper.tokenizeTrimStringA(xfield, ",;");
			for (int i = 0; i < orders.length; i++)
				this.m_orderByClauses.add(new OrderByClause(orders[i]));
		}
	}

	public void removeOrderBy() {
		this.m_orderByClauses = new ArrayList<OrderByClause>();
	}

	public boolean hasAlias(String halias) {
		if (this.m_alias.equals(halias)) return true;
		Iterator<JoinClause> i = m_joinClauses.iterator();
		while (i.hasNext()) {
			if ((i.next()).getAlias().equals(halias)) return true;
		}
		return false;
	}

	public String getHql(String selectClause, String aliasPref, ParamHelper params) {
		return getHql(selectClause, aliasPref, true, params);
	}

	public String getHql(String selectClause, String aliasPref, boolean withFetchJoin, ParamHelper params) {
		StringBuffer sb = new StringBuffer();
		// Iterator i = null;
		// select
		if (m_groupBy.size() > 0) {
			// select groubyParams, selectClause
			sb.append("select ");
			Iterator<String> i = m_groupBy.iterator();
			while (i.hasNext()) {
				sb.append(i.next());
				if (i.hasNext()) {
					sb.append(", ");
				}
			}
			if (selectClause != null && selectClause.length() > 0) {
				sb.append(", ");
				sb.append(selectClause);
			}
		} else {
			// select selectClause
			if (selectClause != null && selectClause.length() > 0) {
				sb.append("select ");
				sb.append(selectClause);
			}
		}
		// from
		// sb.append('\n');
		sb.append(" from ");
		sb.append(this.x_hibDataClass.getName());
		sb.append(" as ");
		sb.append(aliasPref).append(m_alias);
		// join

		for (Iterator<JoinClause> i = m_joinClauses.iterator(); i.hasNext(); ) {
			sb.append(' ');
			(i.next()).setSql(sb, aliasPref, withFetchJoin);
		}
		// where
		if (!m_where.isEmpty()) {
			// sb.append('\n');
			sb.append(" where ");
			m_where.setSql(sb, aliasPref, params);
		}
		// group by
		if (m_groupBy.size() > 0) {
			sb.append(" group by ");
			Iterator<String> i = m_groupBy.iterator();
			while (i.hasNext()) {
				sb.append(i.next());
				if (i.hasNext()) sb.append(", ");
			}
		}
		// having
		if (m_having.size() > 0) {
			sb.append(" having ");
			Iterator<String> i = m_having.iterator();
			while (i.hasNext()) {
				sb.append(i.next());
				if (i.hasNext()) sb.append(", ");
			}
		}
		// order by
		if (m_orderByClauses.size() > 0) {
			// sb.append('\n');
			sb.append(" order by ");
			Iterator<OrderByClause> i = m_orderByClauses.iterator();
			while (i.hasNext()) {
				(i.next()).setSql(sb, aliasPref);
				if (i.hasNext()) sb.append(", ");
			}
		}
		String sql = sb.toString();
		return sql;
	}

	public Class<T> getHibDataClass() {
		return this.x_hibDataClass;
	}

	protected class OrderByClause {
		private String x_field;
		private String x_sort;

		public OrderByClause(String xfield) {
			if (xfield != null) {
				int pos = xfield.indexOf(":");
				this.x_field = (pos > 0) ? xfield.substring(0, pos) : xfield;
				this.x_sort = (pos > 0 && pos < xfield.length()) ? xfield.substring(pos + 1) : null;
			}
		}

		public OrderByClause(String field, String sort) {
			this.x_field = field;
			this.x_sort = (sort != null && sort.length() > 0) ? sort : "asc";
		}

		public void setSql(StringBuffer sb, String aliasPref) {
			sb.append(aliasPref).append(x_field);
			if (x_sort != null) sb.append(" ").append(x_sort);
		}
	}

	protected class JoinClause {
		private String x_join;
		private String x_table;
		private String x_alias;
		private boolean x_fetch;

		public JoinClause(String jointype, String property, String alias, boolean fetch) {
			this.x_join = jointype + " join";
			this.x_fetch = fetch;
			this.x_table = property;
			this.x_alias = alias;
		}

		public JoinClause(Class<?> joinClass, String alias) {
			this.x_join = null;
			this.x_table = joinClass.getName();
			this.x_alias = alias;
		}

		public String getAlias() {
			return x_alias;
		}

		public void setSql(StringBuffer sb, String aliasPref, boolean withFetchJoin) {
			if (x_join != null) {
				sb.append(x_join).append(" ");
				if (x_fetch && withFetchJoin) {
					sb.append("fetch ");
				}
				sb.append(aliasPref).append(x_table);
			} else {
				sb.append(", ");
				sb.append(x_table);
			}
			if (x_alias != null && x_alias.length() > 0) {
				sb.append(" as ").append(aliasPref).append(x_alias);
			}
		}
	}

	private int firstResult = -1;

	public int getFirstResult() {
		return firstResult;
	}

	public void setFirstResult(int firstResult) {
		this.firstResult = firstResult;
	}

	private int maxResults = -1;

	public int getMaxResults() {
		return maxResults;
	}

	public void setMaxResults(int maxResults) {
		this.maxResults = maxResults;
	}

	public void setPaging(int aktPage, int pageRowCount) {
		if (pageRowCount > 0 && aktPage > 0) {
			this.firstResult = (aktPage - 1) * pageRowCount;
			this.maxResults = pageRowCount;
		}
	}
}
