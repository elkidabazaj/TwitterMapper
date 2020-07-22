package ui;


import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.Layer;
import org.openstreetmap.gui.jmapviewer.MapMarkerCircle;
import util.Util;

import java.awt.*;
import java.awt.image.BufferedImage;

public class CustomMapMarker extends MapMarkerCircle {
    public static final double defaultMarkerSize = 17.0;
    public BufferedImage profilePic;
    public String tweet;
    public String profilePicURL;
    public CustomMapMarker(Layer layer, Coordinate coordinate, Color color, String profilePicURL, String tweet) {
        super(layer, null, coordinate, defaultMarkerSize, STYLE.FIXED, getDefaultStyle());
        setColor(Color.BLACK);
        setBackColor(color);
        profilePic = Util.imageFromURL(profilePicURL);
        this.tweet = tweet;
        this.profilePicURL = profilePicURL;
    }

    public String getTweet() {
        return this.tweet;
    }

    public String getProfilePicURL() {
        return this.profilePicURL;
    }

    @Override
    public void paint(Graphics graphics, Point position, int radius) {
        int size = radius * 2;
        if (graphics instanceof Graphics2D && this.getColor() != null) {
            Graphics2D graphics2D = (Graphics2D) graphics;
            Composite oldComposite = graphics2D.getComposite();
            graphics2D.setComposite(AlphaComposite.getInstance(3));
            graphics2D.setPaint(this.getBackColor());
            graphics2D.fillOval(position.x - radius, position.y - radius, size, size);
            graphics2D.setComposite(oldComposite);
            graphics2D.drawImage(profilePic, position.x - 10, position.y - 10, 20,20,null);
        }
    }
}
