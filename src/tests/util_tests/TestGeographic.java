package tests.util_tests;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.interfaces.ICoordinate;
import tests.MockStatus;
import twitter4j.GeoLocation;
import twitter4j.Status;
import util.Util;

import static org.junit.jupiter.api.Assertions.*;

class TestGeographic {

    @Test
    void statusLocation() {
        Status status = Mockito.mock(Status.class);
        GeoLocation geoLocation = new GeoLocation(0, 0);
        assertEquals(geoLocation, Util.getStatusLocation(status));
    }

    @Test
    void testGetGeoLocationToCoordinateWithNullParameter() {
        ICoordinate coordinate = Util.getGeoLocationToCoordinate(null);
        assertNotNull(coordinate);
        assertEquals(0, coordinate.getLat());
        assertEquals(0, coordinate.getLon());
    }

    @Test
    void testGetGeoLocationToCoordinate() {
        GeoLocation location = Mockito.mock(GeoLocation.class);
        Mockito.when(location.getLatitude()).thenReturn(1000d);
        Mockito.when(location.getLongitude()).thenReturn(2000d);

        ICoordinate coordinate = Util.getGeoLocationToCoordinate(location);
        assertNotNull(coordinate);
        assertEquals(1000, coordinate.getLat());
        assertEquals(2000, coordinate.getLon());
    }

    @Test
    void testStatusCoordinateWithEmptyStatusObject() {
        Status status = Mockito.mock(Status.class);
        Coordinate expectedCoordinate = new Coordinate(0, 0);
        assertEquals(expectedCoordinate, Util.getStatusCoordinate(status));
    }

    @Test
    void testStatusCoordinate() {
        Status status = new MockStatus("test status");
        Coordinate expectedCoordinate = new Coordinate(0, 0);
        assertEquals(expectedCoordinate, Util.getStatusCoordinate(status));
    }

    @Test
    void testDistanceFromAPointToItself() {
        ICoordinate firstPoint = Mockito.mock(ICoordinate.class);
        ICoordinate secondPoint = Mockito.mock(ICoordinate.class);

        Mockito.when(firstPoint.getLat()).thenReturn(1000d);
        Mockito.when(firstPoint.getLon()).thenReturn(1000d);
        Mockito.when(secondPoint.getLat()).thenReturn(1000d);
        Mockito.when(secondPoint.getLon()).thenReturn(1000d);

        assertEquals(0, Util.calculateDistanceBetweenTwoCoordinates(firstPoint, secondPoint));
    }

    @Test
    void testDistanceBetweenTwoDifferentPoints() {
        ICoordinate firstPoint = Mockito.mock(ICoordinate.class);
        ICoordinate secondPoint = Mockito.mock(ICoordinate.class);

        Mockito.when(firstPoint.getLat()).thenReturn(1000000d);
        Mockito.when(firstPoint.getLon()).thenReturn(1000000d);
        Mockito.when(secondPoint.getLat()).thenReturn(2000000d);
        Mockito.when(secondPoint.getLon()).thenReturn(2000000d);

        assertTrue(Util.calculateDistanceBetweenTwoCoordinates(firstPoint, secondPoint) != 0);
    }
}
