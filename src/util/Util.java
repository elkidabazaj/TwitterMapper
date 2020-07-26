package util;

import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.interfaces.ICoordinate;
import twitter4j.GeoLocation;
import twitter4j.Status;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;

/**
 * Helpful methods that don't clearly fit anywhere else.
 */
public class Util {
    private static final int EARTH_RADIUS = 6371000;   // radius of earth in metres
    private static BufferedImage defaultImage;

    static {
        defaultImage = imageFromURL("https://vignette.wikia.nocookie.net/mlp/images/d/d1/Rarity_standing_S1E19_CROPPED.png");
    }

    public static GeoLocation getStatusLocation(Status status) {
        return new GeoLocation(getStatusLatitude(status), getStatusLongitude(status));
    }

    private static double getStatusLatitude(Status status) {
        try {
            GeoLocation bottomRight = getBoundingBoxCoordinates(status)[0][0];
            GeoLocation topLeft = getBoundingBoxCoordinates(status)[0][2];
            return (bottomRight.getLatitude() + topLeft.getLatitude()) / 2;
        } catch (Exception e) {
            return 0d;
        }
    }

    private static GeoLocation[][] getBoundingBoxCoordinates(Status status) {
        return status.getPlace().getBoundingBoxCoordinates();
    }

    private static double getStatusLongitude(Status status) {
        try {
            GeoLocation bottomRight = getBoundingBoxCoordinates(status)[0][0];
            GeoLocation topLeft = getBoundingBoxCoordinates(status)[0][2];
            return (bottomRight.getLongitude() + topLeft.getLongitude()) / 2;
        } catch (Exception e) {
            return 0d;
        }
    }

    public static Coordinate getGeoLocationToCoordinate(GeoLocation location) {
        if (location != null) {
            return new Coordinate(location.getLatitude(), location.getLongitude());
        }
        return new Coordinate(0, 0);
    }

    public static Coordinate getStatusCoordinate(Status status) {
        return new Coordinate(getStatusLatitude(status), getStatusLongitude(status));
    }

    /**
     * Find distance in metres between two lat/lon points
     *
     * @param p1  first point
     * @param p2  second point
     * @return distance between p1 and p2 in metres
     */
    public static double calculateDistanceBetweenTwoCoordinates(ICoordinate p1, ICoordinate p2) {
        double lat1 = p1.getLat() / 180.0 * Math.PI;
        double lat2 = p2.getLat() / 180.0 * Math.PI;
        double deltaLon = (p2.getLon() - p1.getLon()) / 180.0 * Math.PI;
        double deltaLat = (p2.getLat() - p1.getLat()) / 180.0 * Math.PI;

        double a = Math.sin(deltaLat / 2.0) * Math.sin(deltaLat / 2.0)
                + Math.cos(lat1) * Math.cos(lat2)
                * Math.sin(deltaLon / 2.0) * Math.sin(deltaLon / 2.0);
        double c = 2.0 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return c * EARTH_RADIUS;
    }

    public static BufferedImage imageFromURL(String url) {
        try {
            BufferedImage img = ImageIO.read(new URL(url));
            if (img == null) {
                return defaultImage;
            }
            return img;
        } catch (IOException e) {
            return defaultImage;
        }
    }
}
