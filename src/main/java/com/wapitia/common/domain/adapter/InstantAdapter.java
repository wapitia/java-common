package com.wapitia.common.domain.adapter;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import javax.xml.bind.annotation.adapters.XmlAdapter;

/** Covert to and from an Xml xs:dateTime type and a java.time.Instant
 *  This is immutable and thread-safe, and a singleton can be accessed
 *  via the {@link #instance()} method.
 */
public class InstantAdapter extends XmlAdapter<String,Instant> {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_INSTANT;

    /** Uses a static {@link DateTimeFormatter} which is thread-safe and
     *  reusable, so use the InstantAdapter singleton via the
     *  {@link #instance()} call.
     */
    public InstantAdapter() {
    }

    /** Parse a xs:dateTime string into its corresponding Instant.
     *  @param stringValue string in xs:dateTime format.
     *  @return Instant matching dateTimeString
     *  @see javax.xml.bind.annotation.adapters.XmlAdapter#unmarshal(java.lang.Object)
     */
    @Override
    public Instant unmarshal(String stringValue) {
        return parse(stringValue);
    }

    /** Convert the Instant to a xs:dateTime string via the
     *  {@link DateTimeFormatter#ISO_INSTANT} formatter.
     *  @param instant Instant to covert to string. returns null if this is null
     *  @return the corresponding String, or null if instant is null
     *  @see javax.xml.bind.annotation.adapters.XmlAdapter#marshal(java.lang.Object)
     */
    @Override
    public String marshal(Instant instant) {
        return print(instant);
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
     *  @param stringValue string in xs:dateTime format.
     *  @return Instant matching dateTimeString
     */
    public static Instant parse(String stringValue) {
        return stringValue != null ? formatter.parse(stringValue, Instant::from) : null;
    }

    /** Convert the Instant to a xs:dateTime string via the
     *  {@link DateTimeFormatter#ISO_INSTANT} formatter.
     *  @param instant Instant to covert to string. returns null if this is null
     *  @return the corresponding String, or null if instant is null
     */
    public static String print(Instant instant) {
        return instant != null ? formatter.format(instant) : null;
    }
}