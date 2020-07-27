package ui;

import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.JMapViewer;
import org.openstreetmap.gui.jmapviewer.Layer;
import org.openstreetmap.gui.jmapviewer.interfaces.ICoordinate;
import org.openstreetmap.gui.jmapviewer.interfaces.MapMarker;
import org.openstreetmap.gui.jmapviewer.tilesources.BingAerialTileSource;
import query.Query;
import query.QueryManager;
import ui.markers.CustomMapMarker;
import ui.panels.ContentPanel;
import util.Util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;
import java.util.List;
import java.util.Timer;
import java.util.stream.Collectors;

/**
 * The Twitter viewer application
 * Derived from a JMapViewer demo program written by Jan Peter Stotz
 */
public class Application extends JFrame {
    private final ContentPanel contentPanel;
    private BingAerialTileSource bing;
    private QueryManager queryManager;

    /**
     * Constructs the {@code Application}.
     */
    public Application() {
        super("Twitter content viewer");
        setSize(300, 300);
        initialize();
        contentPanel = new ContentPanel(this);
        createGUI();
    }

    private void initialize() {
        queryManager = QueryManager.getInstance();
    }

    private void createGUI() {
        bing = new BingAerialTileSource();

        setLayout(new BorderLayout());
        add(contentPanel, BorderLayout.CENTER);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        setMapProperties();
        bingAttributionHandler();
        displayToolTip();
    }

    private void bingAttributionHandler() {
        Coordinate coordinate = new Coordinate(0, 0);
        Timer bingTimer = new Timer();
        TimerTask bingAttributionCheck = new TimerTask() {
            @Override
            public void run() {
                if (!bing.getAttributionText(0, coordinate, coordinate).equals("Error loading Bing attribution data")) {
                    map().setZoom(2);
                    bingTimer.cancel();
                }
            }
        };
        bingTimer.schedule(bingAttributionCheck, 100, 200);
    }

    private void setMapProperties() {
        map().setMapMarkerVisible(true);
        map().setZoomContolsVisible(true);
        map().setScrollWrapEnabled(true);
        map().setTileSource(bing);
    }

    private void displayToolTip() {
        map().addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                Point p = e.getPoint();
                ICoordinate pos = map().getPosition(p);
                List<MapMarker> markerList = getMarkersCovering(pos, pixelWidth(p));
                if (!markerList.isEmpty()) {
                    MapMarker m = markerList.get(markerList.size() - 1);
                    CustomMapMarker customMapMarker = (CustomMapMarker) m;
                    String tweet = customMapMarker.getTweet();
                    String profilePictureURL = customMapMarker.getProfileImageFromUrl();
                    map().setToolTipText("<html><img src=" + profilePictureURL + " height=\"42\" width=\"42\">" + tweet + "</html>");
                }
            }
        });
    }

    /**
     * A new query has been entered via the User Interface
     * @param   query   The new query object
     */
    public void addQuery(Query query) {
        queryManager.addQuery(query, contentPanel);
    }

    private double pixelWidth(Point p) {
        ICoordinate center = map().getPosition(p);
        ICoordinate edge = map().getPosition(new Point(p.x + 1, p.y));
        return Util.calculateDistanceBetweenTwoCoordinates(center, edge);
    }

    private Set<Layer> getVisibleLayers() {
        return queryManager.getQueryList()
                 .stream()
                 .filter(Query::getVisible)
                 .map(Query::getLayer)
                 .collect(Collectors.toSet());
    }

    private List<MapMarker> getMarkersCovering(ICoordinate pos, double pixelWidth) {
        List<MapMarker> mapMarkerList = new ArrayList<>();
        Set<Layer> visibleLayers = getVisibleLayers();
        for (MapMarker m : map().getMapMarkerList()) {
            if (!visibleLayers.contains(m.getLayer())) {
                continue;
            }
            double distance = Util.calculateDistanceBetweenTwoCoordinates(m.getCoordinate(), pos);
            if (distance < m.getRadius() * pixelWidth) {
                mapMarkerList.add(m);
            }
        }
        return mapMarkerList;
    }

    public JMapViewer map() {
        return contentPanel.getViewer();
    }

    public void updateVisibility() {
        SwingUtilities.invokeLater(() -> {
            System.out.println("Recomputing visible queries");
            queryManager.getQueryList().forEach(query -> {
                JCheckBox box = query.getCheckBox();
                boolean state = box.isSelected();
                query.setVisible(state);
            });
            map().repaint();
        });
    }

    public void terminateQuery(Query query) {
        queryManager.terminateQuery(query);
    }
}
