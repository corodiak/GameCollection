package Minesweeper;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.GridLayout;

/**
 * GUI for the game board
 */
public class BoardGUI extends JPanel implements ActionListener {
    private static JFrame boardFrame;
    private static int size, mines, height, width;
    public static int revealedButtons = 0;
    private JButton[][] board;

    private BoardGUI(int height, int width, int mines) {
        revealedButtons = 0;
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
                board[h][w].setText("  ");
                board[h][w].setName(Integer.toString(h) + "," + Integer.toString(w));
//                board[h][w].setText(board[h][w].getName());
                board[h][w].setActionCommand("reveal");
                board[h][w].addActionListener(this);
                row.add(board[h][w]);
            }
            add(row);
        }
//        //Populate each button with necessary informations
//        board = MinesweeperFunctions.distributeMines(board, height, width, mines);
//        board = MinesweeperFunctions.countNeighbors(board, height, width);
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
            System.out.println("REVEAL");
            JButton triggered = (JButton) e.getSource();
            System.out.println("NAME: " + triggered.getName());
            if (revealedButtons == 0) {
                //Populate each button with necessary informations
                board = MinesweeperFunctions.distributeMines(board, height, width, mines);
                board = MinesweeperFunctions.countNeighbors(board, height, width);
            }
            //Revealed a mine -> lose
            if (MinesweeperFunctions.isMine(triggered.getName())) {
                MinesweeperFunctions.gameLost(board);
                createAndShowLoseGUI();
                boardFrame.dispose();
                SettingsGUI.settingsFrame.setVisible(true);
            } else {
                //If a 0 is revealed, reveal all neighbors
                if (MinesweeperFunctions.getNeighborValue(triggered.getName()).equals("0")) {
                    MinesweeperFunctions.revealNeighbors(board, triggered.getName());
                } else {
                    //Reveal the button
                    triggered.setEnabled(false);
                    triggered.setText(MinesweeperFunctions.getNeighborValue(triggered.getName()));
                    revealedButtons++;
                }
                //All non mines revealed -> win
                if (revealedButtons >= size - mines) {
                    createAndShowWinGUI();
                    boardFrame.dispose();
                    SettingsGUI.settingsFrame.setVisible(true);
                }
            }
        }
    }

    public static void createAndShowGUI(int height, int width, int mines) {
        //TODO: Prevent GUI shift when board is too big and 0 is revealed
        boardFrame = new JFrame("Minesweeper");
        BoardGUI boardPane = new BoardGUI(height, width, mines);

        boardFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        boardFrame.setResizable(false);
        boardPane.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
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