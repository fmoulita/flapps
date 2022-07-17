package com.flapps.web.entity;

import com.flapps.web.data.ApprovedStatusEnum;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "asp_tms_attendance_log")
public class AttendanceLog extends AbstractFlappsEntity {

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created")
    protected Date created;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", referencedColumnName = "id", nullable = false)
    protected UserEntity createdBy;

    // history values or info about changes
    @Column(name = "backup_of_id")
    protected Long backupOfId;

    @Column(name = "version")
    protected int version = 0;

    // data part
    @Column(name = "time")
    protected Date time;

    @Column(name = "card_id")
    protected String cardId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    protected UserEntity user;

    @Column(name = "operation_id")
    protected Long operationId;

    // calculated data ...
    @Column(name = "manually")
    protected boolean manualChanged;

    @Column(name = "source")
    protected String source;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "APPROVE_STATUS", nullable = false)
    private ApprovedStatusEnum approvedStatus;

    @Column()

    public void setSource(String source) {
        this.source = StringUtils.left(source, 99);
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public UserEntity getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(UserEntity createdBy) {
        this.createdBy = createdBy;
    }

    public Long getBackupOfId() {
        return backupOfId;
    }

    public void setBackupOfId(Long backupOfId) {
        this.backupOfId = backupOfId;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public Long getOperationId() {
        return operationId;
    }

    public void setOperationId(Long operationId) {
        this.operationId = operationId;
    }

    public boolean isManualChanged() {
        return manualChanged;
    }

    public void setManualChanged(boolean manualChanged) {
        this.manualChanged = manualChanged;
    }

    public String getSource() {
        return source;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Column(name = "NOTE", length = 250)
    private String note;

    public ApprovedStatusEnum getApprovedStatus() {
        return approvedStatus;
    }

    public void setApprovedStatus(ApprovedStatusEnum approvedStatus) {
        this.approvedStatus = approvedStatus;
    }
}
