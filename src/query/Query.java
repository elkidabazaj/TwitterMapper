package query;

import filters.BasicFilter;
import filters.Filter;
import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.JMapViewer;
import org.openstreetmap.gui.jmapviewer.Layer;
import twitter4j.Status;
import twitter4j.User;
import ui.marker.CustomMapMarker;
import util.Util;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

/**
 * A query over the twitter stream.
 */
public class Query implements Observer {
    private final JMapViewer map;
    private Layer layer;
    private final Color color;
    private final String queryString;
    private final Filter filter;
    private JCheckBox checkBox;

    private CustomMapMarker customMapMarker;

    /*
    * JUST FOR TESTING
    * */
    public Query() {
        this.map = new JMapViewer();
        this.color = Color.BLACK;
        this.layer = new Layer("");
        this.queryString = "";
        this.filter = new BasicFilter("");
    }

    public Query(String queryString, Color color, JMapViewer map) {
        this.queryString = queryString;
        this.filter = Filter.parse(queryString);
        this.color = color;
        this.layer = new Layer(queryString);
        this.map = map;
    }

    public Color getColor() {
        return color;
    }

    public String getQueryString() {
        return queryString;
    }

    public Filter getFilter() {
        return filter;
    }

    public Layer getLayer() {
        return layer;
    }

    public JCheckBox getCheckBox() {
        return checkBox;
    }

    public void setCheckBox(JCheckBox checkBox) {
        this.checkBox = checkBox;
    }

    public void setVisible(boolean visible) {
        layer.setVisible(visible);
    }

    public boolean getVisible() { return layer.isVisible(); }

    @Override
    public String toString() {
        return "Query: " + queryString;
    }

    public void terminate() {
        layer.setVisible(false);
        map.removeMapMarker(customMapMarker);
    }

    @Override
    public void update(Observable observable, Object obj) {
        Status status = (Status) obj;
        if (filter.matches(status)) {
            Coordinate coordinate = Util.getStatusCoordinate(status);
            User user = status.getUser();
            customMapMarker = new CustomMapMarker(getLayer(), coordinate, getColor(), user.getProfileImageURL(), status.getText());
            map.addMapMarker(customMapMarker);
        }
    }
}

