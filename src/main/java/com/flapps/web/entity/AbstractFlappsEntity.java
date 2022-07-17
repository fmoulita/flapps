package com.flapps.web.entity;

import javax.persistence.*;

@MappedSuperclass
public class AbstractFlappsEntity implements IStorageEntity {
	@Id
	@GeneratedValue
	@Column(name = "ID")
	private long id;

	@Version
	@Column(name = "MODIFIED")
	private java.sql.Timestamp modified;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public java.sql.Timestamp getModified() {
		return modified;
	}

	public void setModified(java.sql.Timestamp modified) {
		this.modified = modified;
	}

}
