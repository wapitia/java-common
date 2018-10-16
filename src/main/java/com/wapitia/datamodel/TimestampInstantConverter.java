package com.wapitia.datamodel;

import java.time.Instant;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/** Convert from database SQL date to java.time.Instant */
@Converter
public class TimestampInstantConverter
   implements AttributeConverter<Instant, java.sql.Timestamp> {

    @Override
    public java.sql.Timestamp convertToDatabaseColumn(Instant instant) {

        return instant == null ? (java.sql.Timestamp) null
            : java.sql.Timestamp.from(instant);
    }

    @Override
    public Instant convertToEntityAttribute(java.sql.Timestamp dbDate) {
        return dbDate == null ? (Instant) null
            : dbDate.toInstant();
    }

}
