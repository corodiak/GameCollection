package Minesweeper;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.*;

/**
 * GUI for the game board
 */
public class BoardGUI extends JPanel implements ActionListener {
    private static JFrame boardFrame;
    private static int size, mines, height, width;
    public static int revealedButtons = 0, flaggedMines = 0;
    private long startTime = 0;
    private JButton[][] board;
    private JLabel leftMinesLabel, elapsedTimeLabel;
    private MouseAdapter mouseListener;
    private Timer timer;

    private BoardGUI(int height, int width, int mines) {
        initialiseMouseListener();
        revealedButtons = 0;
        flaggedMines = 0;
        size = height * width;
        BoardGUI.height = height;
        BoardGUI.width = width;
        BoardGUI.mines = mines;
        leftMinesLabel = new JLabel("Mines left: " + (mines - flaggedMines), JLabel.CENTER);
        leftMinesLabel.setBorder(BorderFactory.createLoweredBevelBorder());
        elapsedTimeLabel = new JLabel("Time: " + startTime, JLabel.LEFT);
        elapsedTimeLabel.setBorder(BorderFactory.createLoweredBevelBorder());
        initialiseTimer();

        //Create a 2D array of buttons with height * width
        board = new JButton[height][width];

        //Create Panels which hold parts of the game
        JPanel totalBoardPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gBC = new GridBagConstraints();

        //Constraints for elapsedTimeLabel
        gBC.fill = GridBagConstraints.HORIZONTAL;
        gBC.gridx = 0;
        gBC.gridy = 0;
        totalBoardPanel.add(elapsedTimeLabel, gBC);

        //Constraints for leftMinesLabel
        gBC.fill = GridBagConstraints.HORIZONTAL;
        gBC.gridx = 2;
        gBC.gridy = 0;
        totalBoardPanel.add(leftMinesLabel, gBC);

        //This panel contains the board
        JPanel boardPanel = new JPanel();
        boardPanel.setBorder(BorderFactory.createLoweredBevelBorder());
        //Iterate through the board
        for (int w = 0; w < width; w++) {
            //Prepare Gridlayout
            JPanel row = new JPanel(new GridLayout(height, 0));
            for (int h = 0; h < height; h++) {
                //Create a new button on position [h][w]
                //Name contains the coordinates in the board and later information about neighbors and itself
                board[h][w] = new JButton();
                board[h][w].setText("   ");
                board[h][w].setForeground(Color.RED);
                board[h][w].setName(Integer.toString(h) + "," + Integer.toString(w));
                board[h][w].setActionCommand("reveal");
                board[h][w].addActionListener(this);
                board[h][w].addMouseListener(mouseListener);
                row.add(board[h][w]);
            }
            //Add the row to the board
            boardPanel.add(row);
        }

        //Constraints for boardPanel
        gBC.fill = GridBagConstraints.HORIZONTAL;
        gBC.gridwidth = 3;
        gBC.gridx = 0;
        gBC.gridy = 1;
        totalBoardPanel.add(boardPanel, gBC);

        //Add all of the board to the container
        add(totalBoardPanel);
    }

    /**
     * Initialise mouseListener and define actions for each mouse button
     */
    private void initialiseMouseListener() {
        mouseListener = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //Only allow when at least one button is revealed
                if (revealedButtons != 0) {
                    JButton triggered = (JButton) e.getSource();
                    //Check if right mouse button was activated
                    if (SwingUtilities.isRightMouseButton(e)) {
                        if (triggered.isEnabled()) {
                            //(Un-)Mark a mine
                            if (MinesweeperFunctions.isFlagged(triggered)) {
                                triggered.setText("   ");
                                flaggedMines--;
                                leftMinesLabel.setText("Mines left: " + (mines - flaggedMines));
                            } else {
                                triggered.setText("F");
                                flaggedMines++;
                                leftMinesLabel.setText("Mines left: " + (mines - flaggedMines));
                            }
                        }
                        //Check if all mines are flagged
                        if (flaggedMines == mines) {
                            if (MinesweeperFunctions.allMinesFlagged()) {
                                //All mines are flagged -> Win
                                timer.stop();
                                createAndShowWinGUI();
                                boardFrame.dispose();
                                SettingsGUI.settingsFrame.setVisible(true);
                            }
                        }
                        //Check if middle mouse button was activated
                    } else if (SwingUtilities.isMiddleMouseButton(e)) {
                        if (!triggered.isEnabled()) {
                            //Reveal all neighbors of clicked button and checks if mine was revealed
                            boolean revealedMine = MinesweeperFunctions.revealButtonNeighbors(board, triggered.getName());
                            if (revealedMine) {
                                //Mine was revealed -> Lose
                                timer.stop();
                                MinesweeperFunctions.gameLost();
                                createAndShowLoseGUI();
                                boardFrame.dispose();
                                SettingsGUI.settingsFrame.setVisible(true);
                            }
                        }
                    }
                }
            }
        };
    }

    private void initialiseTimer(){
        timer = new javax.swing.Timer(1000,
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        long elapsed = System.currentTimeMillis() - startTime;
                        long elapsedSeconds = elapsed / 1000;
                        elapsedTimeLabel.setText("Time: " + Long.toString(elapsedSeconds));
                    }
                });
    }

    /**
     * Checks if a button is pressed and reveals the field
     * <p>
     * Reveals all neighbors if it's a 0
     * Ends the game if it's a mine
     *
     * @param e
     */
    public void actionPerformed(ActionEvent e) {
        if ("reveal".equals(e.getActionCommand())) {
            JButton triggered = (JButton) e.getSource();
            //Check for first button
            if (revealedButtons == 0) {
                startTime = System.currentTimeMillis();
                timer.start();
                //Populate each button with necessary informations
                MinesweeperFunctions.prepareStart(board, triggered);
                MinesweeperFunctions.distributeMines(board, height, width, mines);
                MinesweeperFunctions.countNeighbors(board, height, width);
            }
            //Revealed a mine -> Lose
            if (!MinesweeperFunctions.isFlagged(triggered)) {
                if (MinesweeperFunctions.isMine(triggered.getName())) {
                    timer.stop();
                    MinesweeperFunctions.gameLost();
                    createAndShowLoseGUI();
                    boardFrame.dispose();
                    SettingsGUI.settingsFrame.setVisible(true);
                } else {
                    //If a 0 is revealed, reveal all neighbors
                    if (MinesweeperFunctions.getNeighborValue(triggered.getName()).equals("0")) {
                        MinesweeperFunctions.revealNeighborsRecursive(board, triggered.getName());
                    } else {
                        //Reveal the button
                        MinesweeperFunctions.revealButton(board, triggered.getName());
                    }
                    if (revealedButtons >= size - mines) {
                        //All non mines revealed -> win
                        timer.stop();
                        createAndShowWinGUI();
                        boardFrame.dispose();
                        SettingsGUI.settingsFrame.setVisible(true);
                    }
                }
            }
        }
    }

    public static void createAndShowGUI(int height, int width, int mines) {
        boardFrame = new JFrame("Minesweeper");
        BoardGUI boardPane = new BoardGUI(height, width, mines);

        boardFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        //Check when board is closed and make settings visible again
        boardFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                SettingsGUI.settingsFrame.setVisible(true);
            }
        });
        boardFrame.setResizable(false);
        boardFrame.add(boardPane);

        //Display the window
        boardFrame.pack();
        boardFrame.setVisible(true);
    }

    private void createAndShowLoseGUI() {
        JFrame errorFrame = new JFrame();
        JOptionPane.showMessageDialog(errorFrame,
                "You lost the game",
                "YOU LOST",
                JOptionPane.ERROR_MESSAGE);
    }

    private void createAndShowWinGUI() {
        JFrame errorFrame = new JFrame();
        JOptionPane.showMessageDialog(errorFrame,
                "Congratulations, you won the game!",
                "YOU WON!",
                JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) throws Exception {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI(5, 5, 8);
            }
        });
    }
}
