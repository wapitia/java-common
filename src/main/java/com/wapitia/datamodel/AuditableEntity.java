package com.wapitia.datamodel;

import static com.wapitia.datamodel.DataModelDefinitions.ActiveCol;
import static com.wapitia.datamodel.DataModelDefinitions.ChangedByCol;
import static com.wapitia.datamodel.DataModelDefinitions.ChangedTimeCol;
import static com.wapitia.datamodel.DataModelDefinitions.CreatedByCol;
import static com.wapitia.datamodel.DataModelDefinitions.CreatedTimeCol;
import static com.wapitia.datamodel.DataModelDefinitions.NameLength;
import static com.wapitia.datamodel.DataModelUtility.asAuditableEntity;
import static com.wapitia.datamodel.DataModelUtility.fieldTextConsumer;
import static com.wapitia.datamodel.DataModelUtility.textCommaSep;

import java.time.Instant;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.MappedSuperclass;

//import org.hibernate.annotations.Type;

/**
 * Common structure for Wapitia database tables having an
 * autoincrement primary Id key (inherited from {@link IdEntity}),
 * and having active, created by name and time, changed by name and time
 * for audit purposes.
 */
@MappedSuperclass
abstract public class AuditableEntity extends IdEntity {

    private static final long serialVersionUID = 1L;

    private static final int ChangeByLength = NameLength;
    private static final int CreatedByLength = NameLength;

    @Column(name=ActiveCol)
    private Boolean isActive;

    @Column(name=CreatedByCol, length=CreatedByLength)
    private String createdBy;

    @Column(name=CreatedTimeCol)
//    private Date createdTime;
    @Convert(converter=TimestampInstantConverter.class)
    private Instant createdTime;

    @Column(name=ChangedByCol, length=ChangeByLength)
    private String changedBy;

    @Column(name=ChangedTimeCol)
//    private Date changedTime;
    @Convert(converter=TimestampInstantConverter.class)
    private Instant changedTime;

    public Boolean isActive() {
        return isActive;
    }

    public void setActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

//    public Date getCreatedTime() {
//        return createdTime;
//    }
//
//    public void setCreatedTime(Date createdTime) {
//        this.createdTime = createdTime;
//    }
    public Instant getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Instant createdTime) {
        this.createdTime = createdTime;
    }

    public String getChangedBy() {
        return changedBy;
    }

    public void setChangedBy(String changedBy) {
        this.changedBy = changedBy;
    }
//
//    public Date getChangedTime() {
//        return changedTime;
//    }
//
//    public void setChangedTime(Date changedTime) {
//        this.changedTime = changedTime;
//    }

    public Instant getChangedTime() {
        return changedTime;
    }

    public void setChangedTime(Instant changedTime) {
        this.changedTime = changedTime;
    }

    @Override
    public String toString() {
        StringBuilder bldr = new StringBuilder();
        asAuditableEntity(bldr, this, true, true);
        return bldr.toString();
    }

    public abstract void extraParams(StringBuilder target);

    public void asAuditableParams(StringBuilder target) {
        textCommaSep(target,
            fieldTextConsumer(ActiveCol, isActive),
            fieldTextConsumer(CreatedByCol, createdBy),
            fieldTextConsumer(CreatedTimeCol, createdTime),
            fieldTextConsumer(ChangedByCol, changedBy),
            fieldTextConsumer(ChangedTimeCol, changedTime));
    }
}
