package Minesweeper;

import javax.swing.*;
import java.awt.event.*;
import java.awt.GridLayout;

/**
 * GUI for the game board
 */
public class BoardGUI extends JPanel implements ActionListener {
    private static JFrame boardFrame;
    private static int size, mines, height, width;
    public static int revealedButtons = 0, flaggedMines = 0;
    private JButton[][] board;
    private MouseAdapter mouseListener;

    private BoardGUI(int height, int width, int mines) {
        initialiseMouseListener();
        revealedButtons = 0;
        flaggedMines = 0;
        size = height * width;
        BoardGUI.height = height;
        BoardGUI.width = width;
        BoardGUI.mines = mines;
        //Create a 2D array of buttons with height * width
        board = new JButton[height][width];
        //Iterate through the board
        for (int w = 0; w < width; w++) {
            //Prepare Gridlayout
            JPanel row = new JPanel(new GridLayout(height, 0));
            for (int h = 0; h < height; h++) {
                //Create a new button on position [h][w]
                //Name contains the coordinates in the board and later information about neighbors and itself
                board[h][w] = new JButton();
                board[h][w].setText("   ");
                board[h][w].setName(Integer.toString(h) + "," + Integer.toString(w));
                board[h][w].setActionCommand("reveal");
                board[h][w].addActionListener(this);
                board[h][w].addMouseListener(mouseListener);
                row.add(board[h][w]);
            }
            add(row);
        }
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
                            } else {
                                triggered.setText("F");
                                flaggedMines++;
                            }
                        }
                        //Check if all mines are flagged
                        if (flaggedMines == mines) {
                            if (MinesweeperFunctions.allMinesFlagged()) {
                                //All mines are flagged -> Win
                                createAndShowWinGUI();
                                boardFrame.dispose();
                                SettingsGUI.settingsFrame.setVisible(true);
                            }
                        }
                        //Check if middle mouse button was activated
                    } else if (SwingUtilities.isMiddleMouseButton(e)) {
                        if (!triggered.isEnabled() || !MinesweeperFunctions.isFlagged(triggered)) {
                            //Reveal all neighbors of clicked button and checks if mine was revealed
                            boolean revealedMine = MinesweeperFunctions.revealButtonNeighbors(board, triggered.getName());
                            if (revealedMine) {
                                //Mine was revealed -> Lose
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
                //Populate each button with necessary informations
                MinesweeperFunctions.prepareStart(board, triggered);
                MinesweeperFunctions.distributeMines(board, height, width, mines);
                MinesweeperFunctions.countNeighbors(board, height, width);
            }
            //Revealed a mine -> Lose
            if (!MinesweeperFunctions.isFlagged(triggered)) {
                if (MinesweeperFunctions.isMine(triggered.getName())) {
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
                        createAndShowWinGUI();
                        boardFrame.dispose();
                        SettingsGUI.settingsFrame.setVisible(true);
                    }
                }
            }
//            System.out.println("NAME: " + triggered.getName());
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
        boardPane.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
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
