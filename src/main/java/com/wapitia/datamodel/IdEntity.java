package com.wapitia.datamodel;

import static com.wapitia.datamodel.DataModelDefinitions.IdCol;
import static com.wapitia.datamodel.DataModelDefinitions.IdLength;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/** Common boilerplate structure for Wapitia persistent entities having an
 *  autoincrement primary key within the javax.persistence framework.
 *
 *  The "id" field is mapped as an @Id @GenerationType.IDENTITY column
 *  which hold a Long.
 */
@MappedSuperclass
public abstract class IdEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /** Auto-increment primary key, a Long identifier. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name=IdCol, length=IdLength, nullable=false, unique=true)
    private Long id;

    /** Every concrete Entity must declare a public void constructor.
     *  This is called from any constructor.
     */
    protected IdEntity() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
