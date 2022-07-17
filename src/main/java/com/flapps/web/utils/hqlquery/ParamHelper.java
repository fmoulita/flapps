package com.flapps.web.utils.hqlquery;

import javax.persistence.Query;
import java.util.*;

/**
 * Created by slany on 13.5.2015.
 */
public class ParamHelper {

	final private Map<String, Object> params = new HashMap<String, Object>();
	private static int mainCounter = 0;

	public Map<String, Object> getParams() {
		return params;
	}

	public static synchronized String createPrefix() {
		if (mainCounter++ > 10000) mainCounter = 0;
		return "p" + (mainCounter) + "_";
	}

	private int counter = 0;
	private String prefix = createPrefix();

	public String addParamAndGetName(Object obj) {
		String name = prefix + (counter++);
		params.put(name, obj);
		return ":" + name;
	}

	public List<String> addParamsAndGetNames(List<Object> params) {
		if (params == null) return null;
		List<String> ret = new ArrayList<String>(params.size());
		for (Object param : params) {
			ret.add(addParamAndGetName(param));
		}
		return ret;
	}

	public Set<String> getNames() {
		return params.keySet();
	}

	public Collection<Object> getValues() {
		return params.values();
	}

	public String[] getNamesAsArray() {
		return params.keySet().toArray(new String[params.size()]);
	}

	public Object[] getValuesAsArray() {
		return params.values().toArray();
	}

	public void addAll(ParamHelper m_cparams) {
		this.params.putAll(m_cparams.getParams());
	}

	public Query doParamsToQuery(Query q) {
		for (Map.Entry<String, Object> e : params.entrySet()) {
			q.setParameter(e.getKey(), e.getValue());
		}
		return q;
	}

	static public Query doParamsToQuery(Query q, final String[] paramNames, final Object[] values) {
		if (paramNames != null && values != null && values.length == paramNames.length) {
			for (int i = 0; i < paramNames.length; i++) {
				if (values[i] != null && values[i].getClass().isArray()) {
					List vals = Arrays.asList((Object[]) values[i]);
					q.setParameter(paramNames[i], vals);
				} else {
					q.setParameter(paramNames[i], values[i]);
				}
			}
		} else if (!(paramNames == null && values == null)) {
			throw new IllegalArgumentException("Bad input parameters");
		}
		return q;
	}

	static public Query doParamsToQuery(Query q, final Object[] values) {
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				q.setParameter(i + 1, values[i]);
			}
		}
		return q;
	}
}
