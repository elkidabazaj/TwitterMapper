package tests;

import twitter4j.GeoLocation;
import twitter4j.Place;
import twitter4j.RateLimitStatus;

public class MockPlace implements Place {
    @Override
    public String getName() {
        return "Default Place";
    }

    @Override
    public String getStreetAddress() {
        return "Default Street";
    }

    @Override
    public String getCountryCode() {
        return "Default Country Code";
    }

    @Override
    public String getId() {
        return "Default ID";
    }

    @Override
    public String getCountry() {
        return "Default Country";
    }

    @Override
    public String getPlaceType() {
        return null;
    }

    @Override
    public String getURL() {
        return null;
    }

    @Override
    public String getFullName() {
        return null;
    }

    @Override
    public String getBoundingBoxType() {
        return null;
    }

    @Override
    public GeoLocation[][] getBoundingBoxCoordinates() {
        return new GeoLocation[0][];
    }

    @Override
    public String getGeometryType() {
        return null;
    }

    @Override
    public GeoLocation[][] getGeometryCoordinates() {
        return new GeoLocation[0][];
    }

    @Override
    public Place[] getContainedWithIn() {
        return new Place[0];
    }

    @Override
    public int compareTo(Place o) {
        return 0;
    }

    @Override
    public RateLimitStatus getRateLimitStatus() {
        return null;
    }

    @Override
    public int getAccessLevel() {
        return 0;
    }
}
