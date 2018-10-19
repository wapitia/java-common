package com.wapitia.common.domain.adapter;

import java.time.format.DateTimeFormatter;
import java.util.UUID;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/** Covert to and from an Xml xs:ID type and a java.util.UUID
 *  This is immutable and thread-safe, and a singleton can be accessed
 *  via the {@link #instance()} method.
 */
public class UuidAdapter extends XmlAdapter<String,UUID> {


    /** Uses a {@link DateTimeFormatter}, which is thread-safe and reusable,
     *  so use the InstantAdapter singleton via the {@link #instance()} call.
     */
    public UuidAdapter() {

    }

    /** Convert from a UUID string into the UUID type
     *
     *  @param uuidString string value of the form "8249c7f9-1ca2-4142-952a-3f68f301b5da"
     *  @return a UUID representing the string
     *  @throws IllegalArgumentException If name does not conform to the string
     *              representation as described in {@link UUID#toString()}.
     */
    @Override
    public UUID unmarshal(String uuidString) {
        return UUID.fromString(uuidString);
    }

    @Override
    public String marshal(UUID uuid) {
        return uuid.toString();
    }

    private static class Holder {
        static UuidAdapter instance = new UuidAdapter();
    }

    /** @return the InstanceAdapter
     */
    public static UuidAdapter instance() {
        return Holder.instance;
    }

    /** Parse a xs:dateTime string into its corresponding Instant.
     *  @param dateTimeString string in xs:dateTime format.
     *  @return Instant matching dateTimeString
     */
    public static UUID parse(String dateTimeString) {
        return instance().unmarshal(dateTimeString);
    }

    /** Convert the Instant to a xs:dateTime string via the
     *  {@link DateTimeFormatter#ISO_INSTANT} formatter.
     *  @param instant Instant to covert to string. returns null if this is null
     *  @return the corresponding String, or null if instant is null
     */
    public static String print(UUID instant) {
        return instance().marshal(instant);
    }
}