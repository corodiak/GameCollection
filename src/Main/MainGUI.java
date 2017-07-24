package Main;

import Minesweeper.SettingsGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Jann-Niklas on 24.07.2017.
 */
public class MainGUI extends JPanel implements ActionListener {
    private String[] args;
    private JButton minesweeperBtn, yahtzeeBtn, madnBtn;
    private JLabel chooseLabel;
    private static JFrame gameSelectionFrame = new JFrame("Game Collection");

    private MainGUI() {
        chooseLabel = new JLabel("Select a game to play:", JLabel.CENTER);
        chooseLabel.setVerticalTextPosition(JLabel.BOTTOM);
        chooseLabel.setHorizontalAlignment(JLabel.CENTER);

        minesweeperBtn = new JButton("Minesweeper");
        minesweeperBtn.setFocusable(false);
        minesweeperBtn.setVerticalTextPosition(AbstractButton.CENTER);
        minesweeperBtn.setHorizontalTextPosition(AbstractButton.LEADING);
        minesweeperBtn.setActionCommand("minesweeper");
        minesweeperBtn.addActionListener(this);

        yahtzeeBtn = new JButton("Yahtzee");
        yahtzeeBtn.setFocusable(false);
        yahtzeeBtn.setEnabled(false);
        yahtzeeBtn.setVerticalTextPosition(AbstractButton.CENTER);
        yahtzeeBtn.setHorizontalTextPosition(AbstractButton.LEADING);
        yahtzeeBtn.setActionCommand("yahtzee");
        yahtzeeBtn.addActionListener(this);

        madnBtn = new JButton("Mensch ärgere dich nicht");
        madnBtn.setFocusable(false);
        madnBtn.setEnabled(false);
        madnBtn.setVerticalTextPosition(AbstractButton.CENTER);
        madnBtn.setHorizontalTextPosition(AbstractButton.LEADING);
        madnBtn.setActionCommand("madn");
        madnBtn.addActionListener(this);

        add(chooseLabel);
        add(minesweeperBtn);
        add(yahtzeeBtn);
        add(madnBtn);
    }

    public void actionPerformed(ActionEvent e) {
        if ("minesweeper".equals(e.getActionCommand())) {
            gameSelectionFrame.dispose();
            SettingsGUI.main(args);
        } else if ("minesweeper".equals(e.getActionCommand())) {
            gameSelectionFrame.dispose();
        } else if ("minesweeper".equals(e.getActionCommand())) {
            gameSelectionFrame.dispose();
        }
    }

    private static void createAndShowGUI() {
        //Create and set up the window.
        gameSelectionFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameSelectionFrame.setResizable(false);

        //Create and set up the content pane.
        MainGUI selectionPane = new MainGUI();
        selectionPane.setOpaque(true); //content panes must be opaque
        gameSelectionFrame.setContentPane(selectionPane);

        //Create Panels which hold parts of the game
        JPanel selectionPanel = new JPanel(new GridBagLayout());
        JPanel selectionBtnPanel = new JPanel(new GridBagLayout());
        selectionBtnPanel.setBorder(BorderFactory.createLoweredBevelBorder());

        GridBagConstraints gBC = new GridBagConstraints();

        //Constraints for chooseLabel
        gBC.gridx = 0;
        gBC.gridy = 0;
        gBC.insets = new Insets(0, 0, 5, 0);
        gBC.fill = GridBagConstraints.HORIZONTAL;
        selectionPanel.add(selectionPane.chooseLabel, gBC);

        //Constraints for minesweeperBtn
        gBC.gridx = 0;
        gBC.gridy = 0;
        gBC.insets = new Insets(0, 0, 1, 0);
        gBC.fill = GridBagConstraints.HORIZONTAL;
        selectionBtnPanel.add(selectionPane.minesweeperBtn, gBC);

        //Constraints for yahtzeeBtn
        gBC.gridx = 0;
        gBC.gridy = 1;
        gBC.insets = new Insets(1, 0, 1, 0);
        gBC.fill = GridBagConstraints.HORIZONTAL;
        selectionBtnPanel.add(selectionPane.yahtzeeBtn, gBC);

        //Constraints for madnBtn
        gBC.gridx = 0;
        gBC.gridy = 2;
        gBC.insets = new Insets(1, 0, 0, 0);
        gBC.fill = GridBagConstraints.HORIZONTAL;
        selectionBtnPanel.add(selectionPane.madnBtn, gBC);

        //Constraints for the selectBtnPanel
        gBC.gridx = 0;
        gBC.gridy = 1;
        selectionPanel.add(selectionBtnPanel, gBC);
        selectionPane.add(selectionPanel);

        //Display the window.
        gameSelectionFrame.pack();
        gameSelectionFrame.setVisible(true);
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}
