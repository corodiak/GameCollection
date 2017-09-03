package Yahtzee;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.util.ArrayList;

/**
 * GUI for the game
 */
public class BoardGUI extends JPanel implements ActionListener {
    private static JFrame boardFrame;
    private JLabel onlyOnesLable, onlyTwosLable, onlyThreesLable, onlyFoursLable, onlyFivesLable, onlySixesLable, bonusLable;
    private JLabel tripletLable, quartetLable, fullHouseLable, smStreetLable, bigStreetLable, yahtzeeLable, chanceLable;
    private JLabel leftScoreLable, rightScoreLable, totalScoreLable;
    private JLabel dice1Label, dice2Label, dice3Label, dice4Label, dice5Label;
    private JTextField onlyOnesTField, onlyTwosTField, onlyThreesTField, onlyFoursTField, onlyFivesTField, onlySixesTField, bonusTField;
    private JTextField tripletTField, quartetTField, fullHouseTField, smStreetTField, bigStreetTField, yahtzeeTField, chanceTField;
    private JTextField leftScoreTField, rightScoreTField, totalScoreTField;
    private JButton throwBtn;
    private JCheckBox dice1CBox, dice2CBox, dice3CBox, dice4CBox, dice5CBox;
    private ArrayList<JCheckBox> throwableDice = new ArrayList<>();
    private int keptDice = 0, numberThrows = 0, turn = 1;

    private BoardGUI() {
        onlyOnesLable = new JLabel("One", JLabel.CENTER);
        onlyOnesLable.setToolTipText("<html>Collect only dice with 1 eye. <br> Points: Sum of collected eyes.</html>");
        onlyOnesLable.setVerticalTextPosition(JLabel.BOTTOM);
        onlyOnesLable.setHorizontalAlignment(JLabel.CENTER);

        onlyTwosLable = new JLabel("Two", JLabel.CENTER);
        onlyTwosLable.setToolTipText("<html>Collect only dice with 2 eyes. <br> Points: Sum of collected eyes.</html>");
        onlyTwosLable.setVerticalTextPosition(JLabel.BOTTOM);
        onlyTwosLable.setHorizontalAlignment(JLabel.CENTER);

        onlyThreesLable = new JLabel("Three", JLabel.CENTER);
        onlyThreesLable.setToolTipText("<html>Collect only dice with 3 eyes. <br> Points: Sum of collected eyes.</html>");
        onlyThreesLable.setVerticalTextPosition(JLabel.BOTTOM);
        onlyThreesLable.setHorizontalAlignment(JLabel.CENTER);

        onlyFoursLable = new JLabel("Four", JLabel.CENTER);
        onlyFoursLable.setToolTipText("<html>Collect only dice with 4 eyes. <br> Points: Sum of collected eyes.</html>");
        onlyFoursLable.setVerticalTextPosition(JLabel.BOTTOM);
        onlyFoursLable.setHorizontalAlignment(JLabel.CENTER);

        onlyFivesLable = new JLabel("Five", JLabel.CENTER);
        onlyFivesLable.setToolTipText("<html>Collect only dice with 5 eyes. <br> Points: Sum of collected eyes.</html>");
        onlyFivesLable.setVerticalTextPosition(JLabel.BOTTOM);
        onlyFivesLable.setHorizontalAlignment(JLabel.CENTER);

        onlySixesLable = new JLabel("Six", JLabel.CENTER);
        onlySixesLable.setToolTipText("<html>Collect only dice with 6 eyes. <br> Points: Sum of collected eyes.</html>");
        onlySixesLable.setVerticalTextPosition(JLabel.BOTTOM);
        onlySixesLable.setHorizontalAlignment(JLabel.CENTER);

        bonusLable = new JLabel("Bonus", JLabel.CENTER);
        bonusLable.setToolTipText("<html>Score of the left section >= 63. <br> Points: 35</html>");
        bonusLable.setVerticalTextPosition(JLabel.BOTTOM);
        bonusLable.setHorizontalAlignment(JLabel.CENTER);

        tripletLable = new JLabel("3 of a kind", JLabel.CENTER);
        tripletLable.setToolTipText("<html>Collect a set of at least 3 dice. <br> Points: Sum of all eyes.</html>");
        tripletLable.setVerticalTextPosition(JLabel.BOTTOM);
        tripletLable.setHorizontalAlignment(JLabel.CENTER);

        quartetLable = new JLabel("4 of a kind", JLabel.CENTER);
        quartetLable.setToolTipText("<html>Collect a set of at least 4 dice. <br> Points: Sum of all eyes.</html>");
        quartetLable.setVerticalTextPosition(JLabel.BOTTOM);
        quartetLable.setHorizontalAlignment(JLabel.CENTER);

        fullHouseLable = new JLabel("Full House", JLabel.CENTER);
        fullHouseLable.setToolTipText("<html>Collect a set of 3 and a set of 2. <br> Points: 25</html>");
        fullHouseLable.setVerticalTextPosition(JLabel.BOTTOM);
        fullHouseLable.setHorizontalAlignment(JLabel.CENTER);

        smStreetLable = new JLabel("Small Street", JLabel.CENTER);
        smStreetLable.setToolTipText("<html>Collect a sequence of 4 dice. <br> Points: 30</html>");
        smStreetLable.setVerticalTextPosition(JLabel.BOTTOM);
        smStreetLable.setHorizontalAlignment(JLabel.CENTER);

        bigStreetLable = new JLabel("Large Street", JLabel.CENTER);
        bigStreetLable.setToolTipText("<html>Collect a sequence of 5 dice. <br> Points: 40</html>");
        bigStreetLable.setVerticalTextPosition(JLabel.BOTTOM);
        bigStreetLable.setHorizontalAlignment(JLabel.CENTER);

        yahtzeeLable = new JLabel("Yahtzee", JLabel.CENTER);
        yahtzeeLable.setToolTipText("<html>Collect a set of 5. <br> Points: 50</html>");
        yahtzeeLable.setVerticalTextPosition(JLabel.BOTTOM);
        yahtzeeLable.setHorizontalAlignment(JLabel.CENTER);

        chanceLable = new JLabel("Chance", JLabel.CENTER);
        chanceLable.setToolTipText("<html>One time Joker. <br> Points: Sum of all eyes</html>");
        chanceLable.setVerticalTextPosition(JLabel.BOTTOM);
        chanceLable.setHorizontalAlignment(JLabel.CENTER);

        leftScoreLable = new JLabel("Total left", JLabel.CENTER);
        leftScoreLable.setToolTipText("<html>Total score of the left section</html>");
        leftScoreLable.setVerticalTextPosition(JLabel.BOTTOM);
        leftScoreLable.setHorizontalAlignment(JLabel.CENTER);

        rightScoreLable = new JLabel("Total right", JLabel.CENTER);
        rightScoreLable.setToolTipText("<html>Total score of the right section</html>");
        rightScoreLable.setVerticalTextPosition(JLabel.BOTTOM);
        rightScoreLable.setHorizontalAlignment(JLabel.CENTER);

        totalScoreLable = new JLabel("Total score", JLabel.CENTER);
        totalScoreLable.setToolTipText("<html>Total score of all sections</html>");
        totalScoreLable.setVerticalTextPosition(JLabel.BOTTOM);
        totalScoreLable.setHorizontalAlignment(JLabel.CENTER);

        dice1Label = new JLabel("1", JLabel.CENTER);
        dice1Label.setFont(new Font("Dialog", Font.BOLD, 20));
        dice1Label.setVerticalTextPosition(JLabel.BOTTOM);
        dice1Label.setHorizontalAlignment(JLabel.CENTER);

        dice2Label = new JLabel("1", JLabel.CENTER);
        dice2Label.setFont(new Font("Dialog", Font.BOLD, 20));
        dice2Label.setVerticalTextPosition(JLabel.BOTTOM);
        dice2Label.setHorizontalAlignment(JLabel.CENTER);

        dice3Label = new JLabel("1", JLabel.CENTER);
        dice3Label.setFont(new Font("Dialog", Font.BOLD, 20));
        dice3Label.setVerticalTextPosition(JLabel.BOTTOM);
        dice3Label.setHorizontalAlignment(JLabel.CENTER);

        dice4Label = new JLabel("1", JLabel.CENTER);
        dice4Label.setFont(new Font("Dialog", Font.BOLD, 20));
        dice4Label.setVerticalTextPosition(JLabel.BOTTOM);
        dice4Label.setHorizontalAlignment(JLabel.CENTER);

        dice5Label = new JLabel("1", JLabel.CENTER);
        dice5Label.setFont(new Font("Dialog", Font.BOLD, 20));
        dice5Label.setVerticalTextPosition(JLabel.BOTTOM);
        dice5Label.setHorizontalAlignment(JLabel.CENTER);

        onlyOnesTField = new IntegerField(5);
        onlyOnesTField.setEnabled(false);

        onlyTwosTField = new IntegerField(5);
        onlyTwosTField.setEnabled(false);

        onlyThreesTField = new IntegerField(5);
        onlyThreesTField.setEnabled(false);

        onlyFoursTField = new IntegerField(5);
        onlyFoursTField.setEnabled(false);

        onlyFivesTField = new IntegerField(5);
        onlyFivesTField.setEnabled(false);

        onlySixesTField = new IntegerField(5);
        onlySixesTField.setEnabled(false);

        bonusTField = new IntegerField(5);
        bonusTField.setEnabled(false);

        tripletTField = new IntegerField(5);
        tripletTField.setEnabled(false);

        quartetTField = new IntegerField(5);
        quartetTField.setEnabled(false);

        fullHouseTField = new IntegerField(5);
        fullHouseTField.setEnabled(false);

        smStreetTField = new IntegerField(5);
        smStreetTField.setEnabled(false);

        bigStreetTField = new IntegerField(5);
        bigStreetTField.setEnabled(false);

        yahtzeeTField = new IntegerField(5);
        yahtzeeTField.setEnabled(false);

        chanceTField = new IntegerField(5);
        chanceTField.setEnabled(false);

        leftScoreTField = new IntegerField(5);
        leftScoreTField.setEnabled(false);

        rightScoreTField = new IntegerField(5);
        rightScoreTField.setEnabled(false);

        totalScoreTField = new IntegerField(5);
        totalScoreTField.setEnabled(false);

        throwBtn = new JButton("Throw");
        throwBtn.setFocusable(false);
        throwBtn.setToolTipText("Throw your dice");
        throwBtn.setVerticalTextPosition(AbstractButton.CENTER);
        throwBtn.setHorizontalTextPosition(AbstractButton.LEADING);
        throwBtn.setActionCommand("throw");
        throwBtn.addActionListener(this);

        dice1CBox = new JCheckBox();
        dice1CBox.setName("1");
        dice1CBox.setToolTipText("Check to safe this die");
        dice1CBox.setActionCommand("check");
        dice1CBox.addActionListener(this);
        throwableDice.add(dice1CBox);

        dice2CBox = new JCheckBox();
        dice2CBox.setName("1");
        dice2CBox.setToolTipText("Check to safe this die");
        dice2CBox.setActionCommand("check");
        dice2CBox.addActionListener(this);
        throwableDice.add(dice2CBox);

        dice3CBox = new JCheckBox();
        dice3CBox.setName("1");
        dice3CBox.setToolTipText("Check to safe this die");
        dice3CBox.setActionCommand("check");
        dice3CBox.addActionListener(this);
        throwableDice.add(dice3CBox);

        dice4CBox = new JCheckBox();
        dice4CBox.setName("1");
        dice4CBox.setToolTipText("Check to safe this die");
        dice4CBox.setActionCommand("check");
        dice4CBox.addActionListener(this);
        throwableDice.add(dice4CBox);

        dice5CBox = new JCheckBox();
        dice5CBox.setName("1");
        dice5CBox.setToolTipText("Check to safe this die");
        dice5CBox.setActionCommand("check");
        dice5CBox.addActionListener(this);
        throwableDice.add(dice5CBox);

        add(onlyOnesLable);
        add(onlyTwosLable);
        add(onlyThreesLable);
        add(onlyFoursLable);
        add(onlyFivesLable);
        add(onlySixesLable);
        add(bonusLable);
        add(tripletLable);
        add(quartetLable);
        add(fullHouseLable);
        add(smStreetLable);
        add(bigStreetLable);
        add(yahtzeeLable);
        add(chanceLable);
        add(leftScoreLable);
        add(rightScoreLable);
        add(totalScoreLable);
        add(dice1Label);
        add(dice2Label);
        add(dice3Label);
        add(dice4Label);
        add(dice5Label);

        add(onlyOnesTField);
        add(onlyTwosTField);
        add(onlyThreesTField);
        add(onlyFoursTField);
        add(onlyFivesTField);
        add(onlySixesTField);
        add(bonusTField);
        add(tripletTField);
        add(quartetTField);
        add(smStreetTField);
        add(bigStreetTField);
        add(fullHouseTField);
        add(yahtzeeTField);
        add(chanceTField);
        add(leftScoreTField);
        add(rightScoreTField);
        add(totalScoreTField);
        add(throwBtn);
        add(dice1CBox);
        add(dice2CBox);
        add(dice3CBox);
        add(dice4CBox);
        add(dice5CBox);
    }

    public void actionPerformed(ActionEvent e) {
        if ("throw".equals(e.getActionCommand())) {
            System.out.println("THROW");
            YahtzeeFunctions.throwDice(throwableDice);
            this.updateDice();
            numberThrows++;
            // if (numberThrows == 3 ) {
            //      write points
            // }
        } else if ("check".equals(e.getActionCommand())) {
            System.out.println("CHECK");
            JCheckBox source = (JCheckBox) e.getSource();
            source.setActionCommand("uncheck");
            throwableDice.remove(source);
            keptDice++;
            if (keptDice == 5) {
                throwBtn.setActionCommand("end");
                //TODO: Prevent resize
                throwBtn.setText("End Turn");
                throwBtn.setToolTipText("Keep all your dice and get points now.");
            }
        } else if ("uncheck".equals(e.getActionCommand())) {
            System.out.println("UNCHECK");
            JCheckBox source = (JCheckBox) e.getSource();
            source.setActionCommand("check");
            throwableDice.add(source);
            keptDice--;
        } else if ("end".equals(e.getActionCommand())) {
            System.out.println("END");
            // write points
        }
    }

    private void updateDice() {
        dice1Label.setText(dice1CBox.getName());
        dice2Label.setText(dice2CBox.getName());
        dice3Label.setText(dice3CBox.getName());
        dice4Label.setText(dice4CBox.getName());
        dice5Label.setText(dice5CBox.getName());
    }

    private static JPanel createBoardGUI() {
        GridBagConstraints gBC = new GridBagConstraints();

        BoardGUI boardPane = new BoardGUI();
        JPanel totalPanel = new JPanel(new GridBagLayout());
        JPanel leftPanel = new JPanel(new GridBagLayout());
        JPanel rightPanel = new JPanel(new GridBagLayout());
        JPanel leftScorePanel = new JPanel(new GridBagLayout());
        JPanel rightScorePanel = new JPanel(new GridBagLayout());
        JPanel scorePanel = new JPanel(new GridBagLayout());
        JPanel dicePanel = new JPanel(new GridBagLayout());

        gBC.fill = GridBagConstraints.HORIZONTAL;
        gBC.insets = new Insets(2, 5, 2, 2);

        //Build leftPanel
        gBC.gridx = 0;
        gBC.gridy = 0;
        leftPanel.add(boardPane.onlyOnesLable, gBC);

        gBC.gridx = 1;
        gBC.gridy = 0;
        leftPanel.add(boardPane.onlyOnesTField, gBC);

        gBC.gridx = 0;
        gBC.gridy = 1;
        leftPanel.add(boardPane.onlyTwosLable, gBC);

        gBC.gridx = 1;
        gBC.gridy = 1;
        leftPanel.add(boardPane.onlyTwosTField, gBC);

        gBC.gridx = 0;
        gBC.gridy = 2;
        leftPanel.add(boardPane.onlyThreesLable, gBC);

        gBC.gridx = 1;
        gBC.gridy = 2;
        leftPanel.add(boardPane.onlyThreesTField, gBC);

        gBC.gridx = 0;
        gBC.gridy = 3;
        leftPanel.add(boardPane.onlyFoursLable, gBC);

        gBC.gridx = 1;
        gBC.gridy = 3;
        leftPanel.add(boardPane.onlyFoursTField, gBC);

        gBC.gridx = 0;
        gBC.gridy = 4;
        leftPanel.add(boardPane.onlyFivesLable, gBC);

        gBC.gridx = 1;
        gBC.gridy = 4;
        leftPanel.add(boardPane.onlyFivesTField, gBC);

        gBC.gridx = 0;
        gBC.gridy = 5;
        leftPanel.add(boardPane.onlySixesLable, gBC);

        gBC.gridx = 1;
        gBC.gridy = 5;
        leftPanel.add(boardPane.onlySixesTField, gBC);

        gBC.gridx = 0;
        gBC.gridy = 6;
        leftPanel.add(boardPane.bonusLable, gBC);

        gBC.gridx = 1;
        gBC.gridy = 6;
        leftPanel.add(boardPane.bonusTField, gBC);

        leftPanel.setBorder(BorderFactory.createLoweredBevelBorder());

        //Build rightPanel
        gBC.gridx = 0;
        gBC.gridy = 0;
        rightPanel.add(boardPane.tripletLable, gBC);

        gBC.gridx = 1;
        gBC.gridy = 0;
        rightPanel.add(boardPane.tripletTField, gBC);

        gBC.gridx = 0;
        gBC.gridy = 1;
        rightPanel.add(boardPane.quartetLable, gBC);

        gBC.gridx = 1;
        gBC.gridy = 1;
        rightPanel.add(boardPane.quartetTField, gBC);

        gBC.gridx = 0;
        gBC.gridy = 2;
        rightPanel.add(boardPane.fullHouseLable, gBC);

        gBC.gridx = 1;
        gBC.gridy = 2;
        rightPanel.add(boardPane.fullHouseTField, gBC);

        gBC.gridx = 0;
        gBC.gridy = 3;
        rightPanel.add(boardPane.smStreetLable, gBC);

        gBC.gridx = 1;
        gBC.gridy = 3;
        rightPanel.add(boardPane.smStreetTField, gBC);

        gBC.gridx = 0;
        gBC.gridy = 4;
        rightPanel.add(boardPane.bigStreetLable, gBC);

        gBC.gridx = 1;
        gBC.gridy = 4;
        rightPanel.add(boardPane.bigStreetTField, gBC);

        gBC.gridx = 0;
        gBC.gridy = 5;
        rightPanel.add(boardPane.yahtzeeLable, gBC);

        gBC.gridx = 1;
        gBC.gridy = 5;
        rightPanel.add(boardPane.yahtzeeTField, gBC);

        gBC.gridx = 0;
        gBC.gridy = 6;
        rightPanel.add(boardPane.chanceLable, gBC);

        gBC.gridx = 1;
        gBC.gridy = 6;
        rightPanel.add(boardPane.chanceTField, gBC);

        rightPanel.setBorder(BorderFactory.createLoweredBevelBorder());

        //Build leftScorePanel
        gBC.gridx = 0;
        gBC.gridy = 0;
        leftScorePanel.add(boardPane.leftScoreLable, gBC);

        gBC.gridx = 1;
        gBC.gridy = 0;
        leftScorePanel.add(boardPane.leftScoreTField, gBC);

        //Build rightScorePanel
        gBC.gridx = 0;
        gBC.gridy = 0;
        rightScorePanel.add(boardPane.rightScoreLable, gBC);

        gBC.gridx = 1;
        gBC.gridy = 0;
        rightScorePanel.add(boardPane.rightScoreTField, gBC);

        //Build scorePanel
        JPanel totalScorePanel = new JPanel(new GridBagLayout());
        gBC.gridx = 0;
        gBC.gridy = 0;
        totalScorePanel.add(boardPane.totalScoreLable, gBC);
        gBC.gridx = 1;
        gBC.gridy = 0;
        totalScorePanel.add(boardPane.totalScoreTField, gBC);

        gBC.gridx = 0;
        gBC.gridy = 0;
        scorePanel.add(totalScorePanel, gBC);
        scorePanel.setBorder(BorderFactory.createMatteBorder(1,0,1,0, Color.BLACK));

        //Build dicePanel
        JPanel diceCollection = new JPanel(new GridBagLayout());
        gBC.gridx = 0;
        gBC.gridy = 0;
        diceCollection.add(boardPane.dice1Label, gBC);
        gBC.gridy = 1;
        diceCollection.add(boardPane.dice1CBox, gBC);
        gBC.gridx = 1;
        gBC.gridy = 0;
        diceCollection.add(boardPane.dice2Label, gBC);
        gBC.gridy = 1;
        diceCollection.add(boardPane.dice2CBox, gBC);
        gBC.gridx = 2;
        gBC.gridy = 0;
        diceCollection.add(boardPane.dice3Label, gBC);
        gBC.gridy = 1;
        diceCollection.add(boardPane.dice3CBox, gBC);
        gBC.gridx = 3;
        gBC.gridy = 0;
        diceCollection.add(boardPane.dice4Label, gBC);
        gBC.gridy = 1;
        diceCollection.add(boardPane.dice4CBox, gBC);
        gBC.gridx = 4;
        gBC.gridy = 0;
        diceCollection.add(boardPane.dice5Label, gBC);
        gBC.gridy = 1;
        diceCollection.add(boardPane.dice5CBox, gBC);

        gBC.gridx = 0;
        gBC.gridy = 0;
        dicePanel.add(diceCollection, gBC);
        gBC.gridx = 1;
        gBC.gridy = 0;
        gBC.fill = GridBagConstraints.VERTICAL;
        gBC.gridwidth = GridBagConstraints.REMAINDER;
        dicePanel.add(boardPane.throwBtn, gBC);
        dicePanel.setBorder(BorderFactory.createLoweredBevelBorder());

        //Build totalPane with left-, right-, score- and dicePanel
        gBC.gridwidth = 1;
        gBC.fill = GridBagConstraints.HORIZONTAL;
        gBC.gridx = 0;
        gBC.gridy = 0;
        totalPanel.add(leftPanel, gBC);
        gBC.gridx = 1;
        gBC.gridy = 0;
        totalPanel.add(rightPanel, gBC);
        gBC.gridx = 0;
        gBC.gridy = 1;
        totalPanel.add(leftScorePanel, gBC);
        gBC.gridx = 1;
        gBC.gridy = 1;
        totalPanel.add(rightScorePanel, gBC);
        gBC.gridx = 0;
        gBC.gridy = 2;
        gBC.gridwidth = GridBagConstraints.REMAINDER;
        totalPanel.add(scorePanel, gBC);
        gBC.fill = GridBagConstraints.VERTICAL;
        gBC.gridx = 0;
        gBC.gridy = 3;
        totalPanel.add(dicePanel, gBC);

        boardPane.add(totalPanel);

        return boardPane;
    }

    public static void createAndShowGUI(int amountPlayer) {
        boardFrame = new JFrame("Yahtzee");
        JTabbedPane tabbedPane = new JTabbedPane();

        for( int i = 0; i < amountPlayer; i++) {
            tabbedPane.addTab("Player " + (i+1), createBoardGUI());
        }

        boardFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        //Check when board is closed and make settings visible again
        boardFrame.addWindowListener(new WindowAdapter() {
//                SettingsGUI.settingsFrame.setVisible(true);
        });
        boardFrame.setResizable(false);
        boardFrame.add(tabbedPane);

        //Display the window
        boardFrame.pack();
        boardFrame.setVisible(true);
    }

    public static void main(String[] args) throws Exception {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI(2);
            }
        });
    }
}
