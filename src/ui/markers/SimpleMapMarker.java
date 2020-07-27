package ui.markers;

import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.Layer;
import org.openstreetmap.gui.jmapviewer.MapMarkerCircle;

import java.awt.*;

public class SimpleMapMarker extends MapMarkerCircle {
    private static final double DEFAULT_MARKER_SIZE = 5.0;
    private static final Color DEFAULT_COLOR = Color.red;

    public SimpleMapMarker(Layer layer, Coordinate coordinate) {
        super(layer, null, coordinate, DEFAULT_MARKER_SIZE, STYLE.FIXED, getDefaultStyle());
        setColor(Color.BLACK);
        setBackColor(DEFAULT_COLOR);
    }
}
