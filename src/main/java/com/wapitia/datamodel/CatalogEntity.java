package com.wapitia.datamodel;

import static com.wapitia.datamodel.DataModelDefinitions.CodeCol;
import static com.wapitia.datamodel.DataModelDefinitions.CodeLength;
import static com.wapitia.datamodel.DataModelDefinitions.DescriptionCol;
import static com.wapitia.datamodel.DataModelDefinitions.DescriptionLength;
import static com.wapitia.datamodel.DataModelDefinitions.NameCol;
import static com.wapitia.datamodel.DataModelDefinitions.NameLength;
import static com.wapitia.datamodel.DataModelUtility.fieldTextConsumer;
import static com.wapitia.datamodel.DataModelUtility.textCommaSep;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * Entity for catalog tables, those tables that contain an enumeration
 * of containing canned  values suitable for drop-down lists.
 *
 * <p>Three columns that all catalog entities have:
 *
 * <ul>
 *   <li><b>code</b>: Presentation key used to uniquely evaluate
 *                    the element in a drop-down list, list item, etc.
 *                    It is what's used as the option value, the ul key,
 *                    etc. May not be null.
 *   <li><b>name</b>: Small-ish name expected to be used as the human-
 *                    readable display contents of the catalog value.
 *                    This may be null, in which case the display client
 *                    expects to substitute the code for the name.
 *   <li><b>description</b>: Long description as a full explanation of
 *                    the catalog entity.
 * </ul>
 */
@MappedSuperclass
public class CatalogEntity extends AuditableEntity {

    @Column(name=CodeCol, length=CodeLength, unique=true, nullable=false)
    private String code;

    @Column(name=NameCol, length=NameLength, nullable=true)
    private String name;

    @Column(name=DescriptionCol, length=DescriptionLength, nullable=true)
    private String description;

    public CatalogEntity() {}

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public void extraParams(StringBuilder target) {
        textCommaSep(target,
            fieldTextConsumer(CodeCol, code),
            fieldTextConsumer(NameCol, name),
            fieldTextConsumer(DescriptionCol, description));
    }

}
