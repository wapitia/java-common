package com.wapitia.common.basictype;

import lombok.Builder;

/** Factory of Builders for BasicTypes
 *
 */
public class WapitiaBasicTypeFactory {

    /** Color builder
     *  @param r red byte
     *  @param g green byte
     *  @param b blue byte
     *  @return Color basic type
     */
    @Builder(builderMethodName = "colorBuilder")
    public static Color newColor(int r, int g, int b)
    {
        return new Color(r & 0xff, g & 0xff, b & 0xff);
    }

    /** Coordinate builder does a range check, ensuring that
     *  latitude and longitude are normalized.
     *  A RuntimeException will be thrown if values are out of range.
     *
     *  @param latitude double must be in range [-90.0 .. 90.0]
     *  @param longitude double must be in range [-180.00 .. 180.0]
     *  @return Coordinate instance
     */
    @Builder(builderMethodName = "coordinateBuilder")
    public static Coordinate newCoordinate(double latitude, double longitude)
    {
        if (latitude < -90.0 || latitude > 90.0)
            throw new RuntimeException(String.format("Latitude %s is out of range, must be in range [-90.0 .. +90.0]", latitude));
        if (longitude < -180.0 || longitude > 180.0)
            throw new RuntimeException(String.format("Longitude %s is out of range, must be in range [-180.00 .. +180.0]", longitude));
        return new Coordinate(latitude, longitude);
    }

}
