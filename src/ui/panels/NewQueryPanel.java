package ui.panels;

import query.Query;
import ui.Application;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

import static javax.swing.BoxLayout.*;

/**
 * A UI panel for entering new queries.
 */
public class NewQueryPanel extends JPanel {
    private final JTextField newQueryTextField = new JTextField(10);;
    private final JPanel colorSetter;
    private final Application app;

    public NewQueryPanel(Application app) {
        this.app = app;
        this.colorSetter = new JPanel();
        createGUI();
    }

    private void createGUI() {
        setLayout(new BoxLayout(this, Y_AXIS));

        JLabel searchLabel = new JLabel("Enter Search: ");
        searchLabel.setLabelFor(newQueryTextField);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridwidth = GridBagConstraints.RELATIVE;
        constraints.fill = GridBagConstraints.NONE;
        constraints.gridy = 0;
        constraints.gridx = 0;
        add(searchLabel, constraints);

        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        newQueryTextField.setMaximumSize(new Dimension(200, 20));
        constraints.gridx = 1;
        add(newQueryTextField, constraints);

        add(Box.createRigidArea(new Dimension(5, 5)));

        JLabel colorLabel = new JLabel("Select Color: ");
        colorSetter.setBackground(getRandomColor());

        constraints.gridwidth = GridBagConstraints.RELATIVE;
        constraints.fill = GridBagConstraints.NONE;
        constraints.gridy = 1;
        constraints.gridx = 0;
        add(colorLabel, constraints);

        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridx = 1;
        colorSetter.setMaximumSize(new Dimension(200, 20));
        add(colorSetter, constraints);

        add(Box.createRigidArea(new Dimension(5, 5)));

        JButton addQueryButton = new JButton("Add New Search");
        constraints.gridx = GridBagConstraints.RELATIVE;       //aligned with button 2
        constraints.gridwidth = 2;   //2 columns wide
        constraints.gridy = GridBagConstraints.RELATIVE;       //third row
        add(addQueryButton, constraints);

        setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createTitledBorder("New Search"),
                        BorderFactory.createEmptyBorder(5,5,5,5)));

        addQueryButton.addActionListener(this::addQueryAction);
        app.getRootPane().setDefaultButton(addQueryButton);

        colorSetter.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    setColorAction();
                }
            }
        });
    }

    private void addQueryAction(ActionEvent e) {
        if (!"".equals(newQueryTextField.getText())) {
            addQuery(newQueryTextField.getText().toLowerCase());
            newQueryTextField.setText("");
        }
    }

    private void addQuery(String newQuery) {
        Query query = new Query(newQuery, colorSetter.getBackground(), app.map());
        app.addQuery(query);

        resetColorSetter();
    }

    private void setColorAction() {
        Color newColor = JColorChooser.showDialog(app, "Choose Background Color", colorSetter.getBackground());
        if (newColor != null) {
            colorSetter.setBackground(newColor);
        }
    }

    private void resetColorSetter() {
        colorSetter.setBackground(getRandomColor());
    }

    private Color getRandomColor() {
        Random randomObject = new Random();
        final float hue = randomObject.nextFloat();
        final float saturation = (randomObject.nextInt(2000) + 1000) / 10000f;
        final float luminance = 0.9f;
        return Color.getHSBColor(hue, saturation, luminance);
    }

}
