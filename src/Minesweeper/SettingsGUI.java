package Minesweeper;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.AbstractButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.GroupLayout;
import javax.swing.LayoutStyle;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * GUI for the pre-game settings
 * - Height, width, amount of mines
 */
public class SettingsGUI extends JPanel implements ActionListener {
    private JButton start;
    private JLabel labelWidth, labelHeight, labelMines;
    private JTextField tFieldWidth, tFieldHeight, tFieldMines;
    private ArrayList<String> settingWarnings = new ArrayList<>();
    public static JFrame settingsFrame = new JFrame("Minesweeper");

    /**
     * Definition for Buttons, labels and textfields
     */
    private SettingsGUI() {
        start = new JButton("Start Game");
        start.setVerticalTextPosition(AbstractButton.CENTER);
        start.setHorizontalTextPosition(AbstractButton.LEADING);
        start.setActionCommand("start");

        labelWidth = new JLabel("Field width:", JLabel.LEFT);
        labelWidth.setVerticalTextPosition(JLabel.BOTTOM);
        labelWidth.setHorizontalTextPosition(JLabel.CENTER);
        labelWidth.setMinimumSize(new Dimension(100, 15));

        labelHeight = new JLabel("Field height:", JLabel.LEFT);
        labelHeight.setVerticalTextPosition(JLabel.BOTTOM);
        labelHeight.setHorizontalTextPosition(JLabel.CENTER);
        labelHeight.setMinimumSize(new Dimension(100, 15));

        labelMines = new JLabel("Amount of mines:", JLabel.LEFT);
        labelMines.setVerticalTextPosition(JLabel.BOTTOM);
        labelMines.setHorizontalTextPosition(JLabel.CENTER);
        labelMines.setMinimumSize(new Dimension(100, 15));

        tFieldWidth = new JTextField(5);
        tFieldHeight = new JTextField(5);
        tFieldMines = new JTextField(5);

        //Listen for actions on buttons.
        start.addActionListener(this);

        //Set tooltips
        start.setToolTipText("Click this button to start a new game.");

        //Add Components to container
        add(start);
        add(labelWidth);
        add(labelHeight);
        add(labelMines);
        add(tFieldWidth);
        add(tFieldHeight);
        add(tFieldMines);
    }

    /**
     * Checks if start button is pressed and if all settings are valid
     *
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if ("start".equals(e.getActionCommand())) {
            settingWarnings.clear();
            checkCorrectnessOfSettings("height");
            checkCorrectnessOfSettings("width");
            checkCorrectnessOfSettings("mines");
            if (!settingWarnings.isEmpty()) {
                createAndShowSettingsWarningGUI();
            } else {
                settingsFrame.setVisible(false);
            }
        }
    }

    /**
     * Checks if the setting for the given type is valid
     * @param type
     */
    private void checkCorrectnessOfSettings(String type) {
        String value = "";
        switch (type) {
            case "height":
                value = tFieldHeight.getText();
                break;
            case "width":
                value = tFieldWidth.getText();
                break;
            case "mines":
                value = tFieldMines.getText();
                break;
        }

        try {
            Integer.parseInt(value);
        } catch (NumberFormatException nFE) {
            settingWarnings.add(type);
        }
    }

    /**
     * Creates a pop-up window with informations about which settings are invalid
     */
    private void createAndShowSettingsWarningGUI() {
        String invalidSettings = "";
        String warningMessage;
        for (String warning : settingWarnings) {
            invalidSettings = invalidSettings.concat(warning + ", ");
        }
        invalidSettings = invalidSettings.substring(0, invalidSettings.length() - 2);

        if (settingWarnings.size() > 1) {
            warningMessage = "Your " + invalidSettings + " game settings contain non numeric characters or are empty.";
        } else { //settingWarnings.size() == 1
            warningMessage = "Your " + invalidSettings + " game setting contains non numeric characters or is empty.";
        }

        JFrame errorFrame = new JFrame();
        JOptionPane.showMessageDialog(errorFrame,
                warningMessage,
                "Invalid settings warning",
                JOptionPane.WARNING_MESSAGE);
    }

    /**
     * Create the GUI and show it.
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
//        JFrame settingsFrame = new JFrame("Minesweeper");
        settingsFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        settingsFrame.setResizable(false);

        //Create and set up the content pane.
        SettingsGUI settingsPane = new SettingsGUI();
        settingsPane.setOpaque(true); //content panes must be opaque
        settingsFrame.setContentPane(settingsPane);

        //Definition of the layout
        GroupLayout layout = new GroupLayout(settingsPane);
        settingsPane.setLayout(layout);

        //Automatic gap insertion
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        //Define Horizontal group
        GroupLayout.SequentialGroup hHeight = layout.createSequentialGroup();
        GroupLayout.SequentialGroup hWidth = layout.createSequentialGroup();
        GroupLayout.SequentialGroup hMines = layout.createSequentialGroup();

        hHeight.addComponent(settingsPane.labelHeight);
        hHeight.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED);
        hHeight.addComponent(settingsPane.tFieldHeight);

        hWidth.addComponent(settingsPane.labelWidth);
        hWidth.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED);
        hWidth.addComponent(settingsPane.tFieldWidth);

        hMines.addComponent(settingsPane.labelMines);
        hMines.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED);
        hMines.addComponent(settingsPane.tFieldMines);

        //Set horizontal group as parallel group of sequential groups
        layout.setHorizontalGroup(
                layout.createParallelGroup()
                        .addGroup(hHeight)
                        .addGroup(hWidth)
                        .addGroup(hMines)
                        .addComponent(settingsPane.start, GroupLayout.Alignment.CENTER)
        );

        //Define Horizontal group
        GroupLayout.ParallelGroup vHeight = layout.createParallelGroup();
        GroupLayout.ParallelGroup vWidth = layout.createParallelGroup();
        GroupLayout.ParallelGroup vMines = layout.createParallelGroup();

        vHeight.addComponent(settingsPane.labelHeight);
        vHeight.addComponent(settingsPane.tFieldHeight);

        vWidth.addComponent(settingsPane.labelWidth);
        vWidth.addComponent(settingsPane.tFieldWidth);

        vMines.addComponent(settingsPane.labelMines);
        vMines.addComponent(settingsPane.tFieldMines);

        //Set vertical group as sequential group of parallel groups
        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addGroup(vHeight)
                        .addGroup(vWidth)
                        .addGroup(vMines)
                        .addComponent(settingsPane.start)
        );

        //Display the window.
        settingsFrame.pack();
        settingsFrame.setVisible(true);
    }

    public static void main(String[] args) throws Exception {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}
