package com.wapitia.common.domain.adapter;

import java.util.UUID;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/** Covert to and from an Xml xs:ID type and a java.util.UUID
 *  This is immutable and thread-safe, and a singleton can be accessed
 *  via the {@link #instance()} method.
 */
public class UuidAdapter extends XmlAdapter<String,UUID> {


    /** Thread-safe and reusable,
     *  so use the UuidAdapter singleton via the {@link #instance()} call.
     */
    public UuidAdapter() {
    }

    /** Convert a xs:ID into the UUID type.
     *
     *  @param uuidString string value of the form "8249c7f9-1ca2-4142-952a-3f68f301b5da"
     *  @return a UUID representing the string
     *  @throws IllegalArgumentException If name does not conform to the string
     *              representation as described in {@link UUID#toString()}.
     *  @see javax.xml.bind.annotation.adapters.XmlAdapter#unmarshal(java.lang.Object)
     */
    @Override
    public UUID unmarshal(String uuidString) {
        return parse(uuidString);
    }

    /** Convert from a UUID into a xs:ID String type.
     *
     *  @param uuid string value of the form "8249c7f9-1ca2-4142-952a-3f68f301b5da"
     *  @return a UUID representing the string
     *  @throws IllegalArgumentException If name does not conform to the string
     *              representation as described in {@link UUID#toString()}.
     *  @see javax.xml.bind.annotation.adapters.XmlAdapter#marshal(java.lang.Object)
     */
    @Override
    public String marshal(UUID uuid) {
        return print(uuid);
    }

    private static class Holder {
        static UuidAdapter instance = new UuidAdapter();
    }

    /** The instance of this UuidAdapter.
     *
     *  @return the UuidAdapter instance
     */
    public static UuidAdapter instance() {
        return Holder.instance;
    }

    /** Convert a xs:ID into the UUID type.
     *
     *  @param uuidString string value of the form "8249c7f9-1ca2-4142-952a-3f68f301b5da"
     *  @return a UUID representing the string
     *  @throws IllegalArgumentException If name does not conform to the string
     *              representation as described in {@link UUID#toString()}.
     */
    public static UUID parse(String uuidString) {
        return uuidString != null ? UUID.fromString(uuidString) : (UUID) null;
    }

    /** Convert from a UUID into a xs:ID String type.
     *
     *  @param uuid string value of the form "8249c7f9-1ca2-4142-952a-3f68f301b5da"
     *  @throws IllegalArgumentException If name does not conform to the string
     *              representation as described in {@link UUID#toString()}.
     *  @return the corresponding String, or null if uuid is null
     */
    public static String print(UUID uuid) {
        return uuid != null ? uuid.toString() : (String) null;
    }
}