package com.wapitia.common.basictype;

import lombok.Data;

/** Coordinate on planet Earth, or at least relative to some Locale.
 *  <p>
 *  This contains generated getters for final fields
 *  {@code Double latitude} and {@code Double longitude}, as well
 *  as a suitable constructor, toString(), equals(), and hashCode().
 */
@Data
public class Coordinate {

    private final double latitude;
    private final double longitude;
}
