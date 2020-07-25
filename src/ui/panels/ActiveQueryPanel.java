package ui.panels;

import query.Query;
import ui.Application;

import javax.swing.*;
import java.awt.*;

public class ActiveQueryPanel extends JPanel {

    public ActiveQueryPanel(Query query, Application app, JPanel mainQueryPanel) {
        this.setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();
        this.add(createColorPanel(query), c);

        c = new GridBagConstraints();
        c.weightx = 1.0;
        c.fill = GridBagConstraints.HORIZONTAL;
        this.add(createActiveCheckBox(query, app), c);

        this.add(createRemoveButton(query, app, mainQueryPanel));
    }

    private JPanel createColorPanel(Query query) {
        JPanel colorPanel = new JPanel();
        colorPanel.setBackground(query.getColor());
        colorPanel.setPreferredSize(new Dimension(30, 30));
        return colorPanel;
    }

    private JCheckBox createActiveCheckBox(Query query, Application app) {
        JCheckBox checkbox = new JCheckBox(query.getQueryString());
        checkbox.setSelected(true);
        checkbox.addActionListener(e -> app.updateVisibility());
        query.setCheckBox(checkbox);
        return checkbox;
    }

    private JButton createRemoveButton(Query query, Application app, JPanel mainQueryPanel) {
        JButton removeButton = new JButton("X");
        removeButton.setPreferredSize(new Dimension(30, 20));
        removeButton.addActionListener(e -> removeAction(query, app, mainQueryPanel));
        return removeButton;
    }

    private void removeAction(Query query, Application app, JPanel mainQueryPanel) {
        app.terminateQuery(query);
        query.terminate();
        mainQueryPanel.remove(this);
        mainQueryPanel.revalidate();
    }

}
