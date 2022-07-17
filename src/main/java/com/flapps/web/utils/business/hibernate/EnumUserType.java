package com.flapps.web.utils.business.hibernate;

import org.hibernate.HibernateException;
import org.hibernate.usertype.UserType;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public abstract class EnumUserType<T extends Enum<T>> implements UserType {
	private Class<T> m_enumType;
	private Object[] enumValues;

	protected EnumUserType(Class<T> enumType, Object[] enumValues) {
		this.m_enumType = enumType;
		this.enumValues = enumValues;
	}

	private static final int[] SQL_TYPES = {Types.TINYINT};

	public int[] sqlTypes() {
		return SQL_TYPES;
	}

	public Class<T> returnedClass() {
		return m_enumType;
	}

	public Object nullSafeGet(ResultSet resultSet, String[] names, Object owner) throws HibernateException, SQLException {
		Object result = null;
		Object oordinal = resultSet.getObject(names[0]);
		if (oordinal instanceof Number) {
			result = enumValues[((Number) oordinal).intValue()];
		}
		return result;
	}

	public void nullSafeSet(PreparedStatement preparedStatement, Object value, int index) throws HibernateException, SQLException {
		if (value instanceof Enum) {
			preparedStatement.setInt(index, ((Enum<?>) value).ordinal());
		} else if (value instanceof Number) {
			preparedStatement.setInt(index, ((Number) value).intValue());
		} else {
			preparedStatement.setNull(index, Types.TINYINT);
		}
	}

	public Object deepCopy(Object value) throws HibernateException {
		return value;
	}

	public boolean isMutable() {
		return false;
	}

	public Object assemble(Serializable cached, Object owner) throws HibernateException {
		return cached;
	}

	public Serializable disassemble(Object value) throws HibernateException {
		return (Serializable) value;
	}

	public Object replace(Object original, Object target, Object owner) throws HibernateException {
		return original;
	}

	public int hashCode(Object x) throws HibernateException {
		return x.hashCode();
	}

	public boolean equals(Object x, Object y) throws HibernateException {
		if (x == y) return true;
		if (null == x || null == y) return false;
		return x.equals(y);
	}
}
