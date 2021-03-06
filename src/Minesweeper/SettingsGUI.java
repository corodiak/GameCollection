package Minesweeper;

import Main.MainGUI;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.AbstractButton;
import javax.swing.JLabel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.GroupLayout;
import javax.swing.LayoutStyle;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * GUI for the pre-game settings
 * - Height, width, amount of mines
 */
public class SettingsGUI extends JPanel implements ActionListener {
    private String[] args;
    private JButton startBtn, backBtn;
    private JLabel labelWidth, labelHeight, labelMines;
    private IntegerField tFieldWidth, tFieldHeight, tFieldMines;
    private boolean settingWarnings = false, mines = false;
    public static JFrame settingsFrame = new JFrame("Minesweeper");

    /**
     * Definition for Buttons, labels and textfields
     */
    private SettingsGUI() {
        startBtn = new JButton("Start Game");
        startBtn.setVerticalTextPosition(AbstractButton.CENTER);
        startBtn.setHorizontalTextPosition(AbstractButton.LEADING);
        startBtn.setActionCommand("start");

        backBtn = new JButton("Back to selection");
        backBtn.setFocusable(false);
        backBtn.setVerticalTextPosition(AbstractButton.CENTER);
        backBtn.setHorizontalTextPosition(AbstractButton.LEADING);
        backBtn.setActionCommand("back");

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

        tFieldWidth = new IntegerField(5);
        tFieldHeight = new IntegerField(5);
        tFieldMines = new IntegerField(5);

        //Listen for actions on buttons.
        startBtn.addActionListener(this);
        backBtn.addActionListener(this);

        //Set tooltips
        startBtn.setToolTipText("Click this button to start a new game.");
        backBtn.setToolTipText("Go back to the game selection.");

        //Add Components to container
        add(startBtn);
        add(backBtn);
        add(labelWidth);
        add(labelHeight);
        add(labelMines);
        add(tFieldWidth);
        add(tFieldHeight);
        add(tFieldMines);
    }

    /**
     * Checks if startBtn button is pressed and if all settings are valid
     *
     * @param e
     */
    public void actionPerformed(ActionEvent e) {
        if ("start".equals(e.getActionCommand())) {
            settingWarnings = false;
            checkCorrectnessOfSettings("height");
            checkCorrectnessOfSettings("width");
            checkCorrectnessOfSettings("mines");
            checkCorrectnessOfSettings("amount");
            if (settingWarnings) {
                createAndShowSettingsWarningGUI(mines);
            } else {
                settingsFrame.setVisible(false);
                BoardGUI.createAndShowGUI(Integer.parseInt(tFieldHeight.getText()),
                        Integer.parseInt(tFieldWidth.getText()),Integer.parseInt(tFieldMines.getText()));
            }
        } else if ("back".equals(e.getActionCommand())) {
            settingsFrame.dispose();
            MainGUI.main(args);
        }
    }

    /**
     * Checks if the setting for the given type is valid
     *
     * Check if the amount of mines is more than available field happens later
     * @param type
     */
    private void checkCorrectnessOfSettings(String type) {
        switch (type) {
            case "height":
                settingWarnings = tFieldHeight.getText().isEmpty();
                break;
            case "width":
                settingWarnings = tFieldWidth.getText().isEmpty();
                break;
            case "mines":
                settingWarnings = tFieldMines.getText().isEmpty();
                break;
            case "amount":
                if (!settingWarnings) {
                    if(Integer.parseInt(tFieldHeight.getText()) * Integer.parseInt(tFieldWidth.getText())
                            < Integer.parseInt(tFieldMines.getText()) - 9) {
                        settingWarnings = true;
                        mines = true;
                    }
                }
                break;
        }
    }

    /**
     * Creates a pop-up window with informations about which settings are invalid
     */
    private void createAndShowSettingsWarningGUI(boolean mines) {
        JFrame errorFrame = new JFrame();
        if (mines) {
            JOptionPane.showMessageDialog(errorFrame,
                    "You have set too many mines.",
                    "Invalid settings warning",
                    JOptionPane.WARNING_MESSAGE);

        } else {
            JOptionPane.showMessageDialog(errorFrame,
                    "One or more of your game settings is empty",
                    "Invalid settings warning",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    /**
     * Create the GUI and show it.
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
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
        GroupLayout.SequentialGroup hbuttons = layout.createSequentialGroup();

        hHeight.addComponent(settingsPane.labelHeight);
        hHeight.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED);
        hHeight.addComponent(settingsPane.tFieldHeight);

        hWidth.addComponent(settingsPane.labelWidth);
        hWidth.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED);
        hWidth.addComponent(settingsPane.tFieldWidth);

        hMines.addComponent(settingsPane.labelMines);
        hMines.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED);
        hMines.addComponent(settingsPane.tFieldMines);

        hbuttons.addComponent(settingsPane.backBtn);
        hbuttons.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED);
        hbuttons.addComponent(settingsPane.startBtn);

        //Set horizontal group as parallel group of sequential groups
        layout.setHorizontalGroup(
                layout.createParallelGroup()
                        .addGroup(hHeight)
                        .addGroup(hWidth)
                        .addGroup(hMines)
                        .addGroup(hbuttons)
        );

        //Define Horizontal group
        GroupLayout.ParallelGroup vHeight = layout.createParallelGroup();
        GroupLayout.ParallelGroup vWidth = layout.createParallelGroup();
        GroupLayout.ParallelGroup vMines = layout.createParallelGroup();
        GroupLayout.ParallelGroup vbuttons = layout.createParallelGroup();

        vHeight.addComponent(settingsPane.labelHeight);
        vHeight.addComponent(settingsPane.tFieldHeight);

        vWidth.addComponent(settingsPane.labelWidth);
        vWidth.addComponent(settingsPane.tFieldWidth);

        vMines.addComponent(settingsPane.labelMines);
        vMines.addComponent(settingsPane.tFieldMines);

        vbuttons.addComponent(settingsPane.backBtn);
        vbuttons.addComponent(settingsPane.startBtn);

        //Set vertical group as sequential group of parallel groups
        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addGroup(vHeight)
                        .addGroup(vWidth)
                        .addGroup(vMines)
                        .addGroup(vbuttons)
        );

        //Display the window.
        settingsFrame.pack();
        settingsFrame.setVisible(true);
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}
