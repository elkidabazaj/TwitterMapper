package ui.panels;

import org.openstreetmap.gui.jmapviewer.JMapViewer;
import query.Query;
import ui.Application;

import javax.swing.*;
import java.awt.*;

public class ContentPanel extends JPanel {
    private JPanel existingQueryPanel;
    private JMapViewer map;

    private Application app;

    public ContentPanel(Application app) {
        this.app = app;
        createGUI();
    }

    private void createGUI() {
        map = new JMapViewer();
        map.setMinimumSize(new Dimension(100, 50));
        setLayout(new BorderLayout());

        JPanel layerPanelContainer = new JPanel();
        createQueryPanel(layerPanelContainer);
        layerPanelContainer.add(existingQueryPanel, BorderLayout.NORTH);

        JPanel newQueryPanel = new NewQueryPanel(app);
        JSplitPane querySplitPane = createQuerySplitPane(newQueryPanel, layerPanelContainer);
        JSplitPane topLevelSplitPane = createTopLevelSplitPane(querySplitPane);
        add(topLevelSplitPane, "Center");

        revalidate();
        repaint();
    }

    private void createQueryPanel(JPanel layerPanelContainer) {
        existingQueryPanel = new JPanel();
        existingQueryPanel.setLayout(new BoxLayout(existingQueryPanel, BoxLayout.Y_AXIS));
        layerPanelContainer.setLayout(new BorderLayout());
        layerPanelContainer.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createTitledBorder("Current Queries"),
                        BorderFactory.createEmptyBorder(5,5,5,5)));
    }

    private JSplitPane createTopLevelSplitPane(JSplitPane querySplitPane) {
        JSplitPane topLevelSplitPane = new JSplitPane(1);
        topLevelSplitPane.setDividerLocation(150);
        topLevelSplitPane.setLeftComponent(querySplitPane);
        topLevelSplitPane.setRightComponent(map);
        return topLevelSplitPane;
    }

    private JSplitPane createQuerySplitPane(JPanel newQueryPanel, JPanel layerPanelContainer) {
        JSplitPane querySplitPane = new JSplitPane(0);
        querySplitPane.setDividerLocation(150);
        querySplitPane.setTopComponent(newQueryPanel);
        querySplitPane.setBottomComponent(layerPanelContainer);
        return querySplitPane;
    }

    /**
     * @param query - adds the query to existing query panel and refreshed the GUI
     */
    public void addQueryToPanel(Query query) {
        ActiveQueryPanel activeQueryPanel = new ActiveQueryPanel(query, app, existingQueryPanel);
        existingQueryPanel.add(activeQueryPanel);

        validate();
    }

    public JMapViewer getViewer() {
        return map;
    }
}
