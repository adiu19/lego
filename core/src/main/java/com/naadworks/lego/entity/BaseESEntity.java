package com.naadworks.lego.entity;

import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.Date;

public abstract class BaseESEntity<ID> implements Serializable {
    @Id
    private ID id;

    private Date createdOn;

    private Date lastModifiedOn;

    private String createdBy;

    private String lastModifiedBy;

    public BaseESEntity() {
    }

    public BaseESEntity(ID id, Date createdOn, Date lastModifiedOn, String createdBy, String lastModifiedBy) {
        this.id = id;
        this.createdOn = createdOn;
        this.lastModifiedOn = lastModifiedOn;
        this.createdBy = createdBy;
        this.lastModifiedBy = lastModifiedBy;
    }

    public ID getId() {
        return id;
    }

    public void setId(ID id) {
        this.id = id;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public Date getLastModifiedOn() {
        return lastModifiedOn;
    }

    public void setLastModifiedOn(Date lastModifiedOn) {
        this.lastModifiedOn = lastModifiedOn;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }
}
