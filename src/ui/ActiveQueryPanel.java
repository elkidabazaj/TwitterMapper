package ui;

import query.Query;

import javax.swing.*;
import java.awt.*;

public class ActiveQueryPanel extends JPanel {
    public ActiveQueryPanel(Query query, Application app, JPanel mainQueryPanel) {
        this.setLayout(new GridBagLayout());


        JPanel colorPanel = new JPanel();
        colorPanel.setBackground(query.getColor());
        colorPanel.setPreferredSize(new Dimension(30, 30));
        GridBagConstraints c = new GridBagConstraints();
        this.add(colorPanel, c);


        c = new GridBagConstraints();
        JCheckBox checkbox = new JCheckBox(query.getQueryString());
        checkbox.setSelected(true);
        checkbox.addActionListener(e -> app.updateVisibility());
        query.setCheckBox(checkbox);
        c.weightx = 1.0;
        c.fill = GridBagConstraints.HORIZONTAL;
        this.add(checkbox, c);


        JButton removeButton = new JButton("X");
        removeButton.setPreferredSize(new Dimension(30, 20));
        removeButton.addActionListener(e -> {
            app.terminateQuery(query);
            query.terminate();
            mainQueryPanel.remove(this);
            mainQueryPanel.revalidate();
        });
        this.add(removeButton);
    }
}
