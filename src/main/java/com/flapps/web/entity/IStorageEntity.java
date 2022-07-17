package com.flapps.web.entity;

import java.io.Serializable;

/**
 * @author pm
 */
public interface IStorageEntity extends Serializable {
    public long getId();

    public java.util.Date getModified();
}
