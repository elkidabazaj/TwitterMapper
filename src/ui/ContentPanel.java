package ui;

import org.openstreetmap.gui.jmapviewer.JMapViewer;
import query.Query;

import javax.swing.*;
import java.awt.*;

public class ContentPanel extends JPanel {
    private JSplitPane topLevelSplitPane;
    private JSplitPane querySplitPane;
    private JPanel mainQueryPanel;
    private JMapViewer map;

    private Application app;

    public ContentPanel(Application app) {
        this.app = app;

        map = new JMapViewer();
        map.setMinimumSize(new Dimension(100, 50));
        setLayout(new BorderLayout());

        JPanel newQueryPanel = new NewQueryPanel(app);

        // NOTE: We wrap existingQueryList in a container so it gets a pretty border.
        JPanel layerPanelContainer = new JPanel();
        mainQueryPanel = new JPanel();
        mainQueryPanel.setLayout(new javax.swing.BoxLayout(mainQueryPanel, javax.swing.BoxLayout.Y_AXIS));
        layerPanelContainer.setLayout(new BorderLayout());
        layerPanelContainer.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createTitledBorder("Current Queries"),
                        BorderFactory.createEmptyBorder(5,5,5,5)));
        layerPanelContainer.add(mainQueryPanel, BorderLayout.NORTH);

        querySplitPane = new JSplitPane(0);
        querySplitPane.setDividerLocation(150);
        querySplitPane.setTopComponent(newQueryPanel);
        querySplitPane.setBottomComponent(layerPanelContainer);

        topLevelSplitPane = new JSplitPane(1);
        topLevelSplitPane.setDividerLocation(150);
        topLevelSplitPane.setLeftComponent(querySplitPane);
        topLevelSplitPane.setRightComponent(map);

        add(topLevelSplitPane, "Center");
        revalidate();

        repaint();
    }

    // Add a new query to the set of queries and update the UI to reflect the new query.
    public void addQuery(Query query) {
        ActiveQueryPanel activeQueryPanel = new ActiveQueryPanel(query, app, mainQueryPanel);
        mainQueryPanel.add(activeQueryPanel);
        validate();
    }

    public JMapViewer getViewer() {
        return map;
    }
}
