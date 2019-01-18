package com.abhaya.vehicle.tracking.data.audit.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@MappedSuperclass
public abstract class AbstractAuditEntity implements Serializable {

  /**
	 * 
	 */
	private static final long serialVersionUID = 7787725460840663583L;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "created", nullable = true, insertable = true, updatable = false)
  private Date created;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "updated", nullable = true, insertable = false, updatable = true)
  private Date updated;

  @Column(name = "created_by", nullable = true, insertable = true, updatable = false)
  @CreatedBy
  private String createdBy;

  @Column(name = "updated_by", nullable = true, insertable = true, updatable = true)
  @LastModifiedBy
  private String updatedBy;

  @PrePersist
  protected void onCreate() {
    updated = created = new Date();
    createdBy = getUsernameOfLoggedinUser();
  }

  @PreUpdate
  protected void onUpdate() {
    updated = new Date();
    String updatedUser = getUsernameOfLoggedinUser();
    if (!"anonymousUser".equalsIgnoreCase(updatedUser))
      updatedBy = getUsernameOfLoggedinUser();
  }

  public Date getCreated() {
    return created;
  }

  public void setCreated(Date created) {
    this.created = created;
  }

  public Date getUpdated() {
    return updated;
  }

  public void setUpdated(Date updated) {
    this.updated = updated;
  }

  public String getCreatedBy() {
    return createdBy;
  }

  public void setCreatedBy(String createdBy) {
    this.createdBy = createdBy;
  }

  public String getUpdatedBy() {
    return updatedBy;
  }

  public void setUpdatedBy(String updatedBy) {
    this.updatedBy = updatedBy;
  }

  private String getUsernameOfLoggedinUser() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication == null || !authentication.isAuthenticated()) {
      return null;
    }
    return authentication.getName();
  }

}
