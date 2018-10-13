/*
 * Copyright 2016-2018 wapitia.com
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 *
 * - Redistribution of source code must retain the above copyright
 *   notice, this list of conditions and the following disclaimer.
 *
 * - Redistribution in binary form must reproduce the above copyright
 *   notice, this list of conditions and the following disclaimer in the
 *   documentation and/or other materials provided with the distribution.
 *
 * Neither the name of wapitia.com or the names of contributors may be used to
 * endorse or promote products derived from this software without specific
 * prior written permission.
 *
 * This software is provided "AS IS," without a warranty of any kind.
 * ALL EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND WARRANTIES,
 * INCLUDING ANY IMPLIED WARRANTY OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE OR NON-INFRINGEMENT, ARE HEREBY EXCLUDED.
 * WAPITIA.COM ("WAPITIA") AND ITS LICENSORS SHALL NOT BE LIABLE FOR
 * ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR
 * DISTRIBUTING THIS SOFTWARE OR ITS DERIVATIVES. IN NO EVENT WILL WAPITIA OR
 * ITS LICENSORS BE LIABLE FOR ANY LOST REVENUE, PROFIT OR DATA, OR FOR
 * DIRECT, INDIRECT, SPECIAL, CONSEQUENTIAL, INCIDENTAL OR PUNITIVE
 * DAMAGES, HOWEVER CAUSED AND REGARDLESS OF THE THEORY OF LIABILITY,
 * ARISING OUT OF THE USE OF OR INABILITY TO USE THIS SOFTWARE, EVEN IF
 * WAPITIA HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGES.
 */

package com.wapitia.datamodel;
import static com.wapitia.datamodel.DataModelDefinitions.ActiveCol;
import static com.wapitia.datamodel.DataModelDefinitions.BooleanColType;
import static com.wapitia.datamodel.DataModelDefinitions.ChangedByCol;
import static com.wapitia.datamodel.DataModelDefinitions.ChangedTimeCol;
import static com.wapitia.datamodel.DataModelDefinitions.CreatedByCol;
import static com.wapitia.datamodel.DataModelDefinitions.CreatedTimeCol;
import static com.wapitia.datamodel.DataModelDefinitions.NameLength;
import static com.wapitia.datamodel.DataModelUtility.asAuditableEntity;
import static com.wapitia.datamodel.DataModelUtility.fieldTextConsumer;
import static com.wapitia.datamodel.DataModelUtility.textCommaSep;

import java.util.Date;

import javax.persistence.Column;
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
//    @Type(type=BooleanColType)
    private Boolean isActive;

    @Column(name=CreatedByCol, length=CreatedByLength)
    private String createdBy;

    @Column(name=CreatedTimeCol)
    private Date createdTime;

    @Column(name=ChangedByCol, length=ChangeByLength)
    private String changedBy;

    @Column(name=ChangedTimeCol)
    private Date changedTime;

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

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public String getChangedBy() {
        return changedBy;
    }

    public void setChangedBy(String changedBy) {
        this.changedBy = changedBy;
    }

    public Date getChangedTime() {
        return changedTime;
    }

    public void setChangedTime(Date changedTime) {
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
