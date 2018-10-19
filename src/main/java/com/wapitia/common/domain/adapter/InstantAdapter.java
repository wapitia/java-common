package com.wapitia.common.domain.adapter;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import javax.xml.bind.annotation.adapters.XmlAdapter;

/** Covert to and from an Xml xs:dateTime type and a java.time.Instant
 *  This is immutable and thread-safe, and a singleton can be accessed
 *  via the {@link #instance()} method.
 */
public class InstantAdapter extends XmlAdapter<String,Instant> {

    private final DateTimeFormatter formatter;

    /** Uses a {@link DateTimeFormatter}, which is thread-safe and reusable,
     *  so use the InstantAdapter singleton via the {@link #instance()} call.
     */
    public InstantAdapter() {

        this.formatter = DateTimeFormatter.ISO_INSTANT;
    }

    @Override
    public Instant unmarshal(String stringValue) {
        return stringValue != null ? formatter.parse(stringValue, Instant::from) : null;
    }

    @Override
    public String marshal(Instant value) {
        return value != null ? formatter.format(value) : null;
    }

    private static class Holder {
        static InstantAdapter instance = new InstantAdapter();
    }

    /** @return the InstanceAdapter
     */
    public static InstantAdapter instance() {
        return Holder.instance;
    }

    /** Parse a xs:dateTime string into its corresponding Instant.
     *  @param dateTimeString string in xs:dateTime format.
     *  @return Instant matching dateTimeString
     */
    public static Instant parse(String dateTimeString) {
        return instance().unmarshal(dateTimeString);
    }

    /** Convert the Instant to a xs:dateTime string via the
     *  {@link DateTimeFormatter#ISO_INSTANT} formatter.
     *  @param instant Instant to covert to string. returns null if this is null
     *  @return the corresponding String, or null if instant is null
     */
    public static String print(Instant instant) {
        return instance().marshal(instant);
    }
}