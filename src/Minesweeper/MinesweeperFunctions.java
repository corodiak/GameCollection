package Minesweeper;

import javax.swing.JButton;
import java.util.Random;

/**
 * Implements all functions to play Minesweeper
 * <p>
 * - Distribute the mines randomly on the board
 * - Count the number of mines in the neighborhood and appends the button name with this value
 * - Reveal fields recursive if field has no mines around
 */
public class MinesweeperFunctions {

    /**
     * Distributes randomly a given amount of mines on a board
     *
     * @param board
     * @param height
     * @param width
     * @param mines
     * @return board with randomly distributed mines
     */
    public static JButton[][] distributeMines(JButton[][] board, int height, int width, int mines) {
        Random rn = new Random();
        while (mines !=0) {
            int rnHeight = rn.nextInt(height);
            int rnWidth = rn.nextInt(width);
            //Check if random button is already a mine, if not, set it as a mine
            if (!isMine(board[rnHeight][rnWidth].getName())) {
                //A mine gets identified by it's last char of the button name
                board[rnHeight][rnWidth].setName(board[rnHeight][rnWidth].getName() + ",*");
                mines--;
//                System.out.println("MINE SET AT: " + board[rnHeight][rnWidth].getName());
            }
        }

//        board[3][0].setName(board[3][0].getName() + ",*");
//        board[4][4].setName(board[4][4].getName() + ",*");
//        board[0][1].setName(board[0][1].getName() + ",*");
//        board[4][0].setName(board[4][0].getName() + ",*");
//        board[0][3].setName(board[0][3].getName() + ",*");
//        board[0][4].setName(board[0][4].getName() + ",*");
//        board[2][0].setName(board[2][0].getName() + ",*");
//        board[4][2].setName(board[4][2].getName() + ",*");
        return board;
    }

    /**
     * Checks if a button is a mine
     *
     * @param buttonName
     * @return true if button is a mine
     */
    public static boolean isMine(String buttonName) {
        String lastChar = buttonName.substring(buttonName.length() - 1);
        if (lastChar.equals("*")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Counts the number of mines around a button
     * Appends the button name with the found amount of mines
     *
     * @param board
     * @return
     */
    public static JButton[][] countNeighbors(JButton[][] board, int height, int width) {
        int foundMines;
        for (int w = 0; w < width; w++) {
            for (int h = 0; h < height; h++) {
                foundMines = 0;
                if (!isMine(board[h][w].getName())) {
                    //TODO: FIND A SMARTER WAY FOR THIS
                    try {
                        if (isMine(board[h - 1][w - 1].getName())) {
                            foundMines++;
                        }
                    } catch (IndexOutOfBoundsException iOBE) { //Do nothing
                    }
                    try {
                        if (isMine(board[h - 1][w].getName())) {
                            foundMines++;
                        }
                    } catch (IndexOutOfBoundsException iOBE) { //Do nothing
                    }
                    try {
                        if (isMine(board[h - 1][w + 1].getName())) {
                            foundMines++;
                        }
                    } catch (IndexOutOfBoundsException iOBE) { //Do nothing
                    }
                    try {
                        if (isMine(board[h][w - 1].getName())) {
                            foundMines++;
                        }
                    } catch (IndexOutOfBoundsException iOBE) { //Do nothing
                    }
                    try {
                        if (isMine(board[h][w + 1].getName())) {
                            foundMines++;
                        }
                    } catch (IndexOutOfBoundsException iOBE) { //Do nothing
                    }
                    try {
                        if (isMine(board[h + 1][w - 1].getName())) {
                            foundMines++;
                        }
                    } catch (IndexOutOfBoundsException iOBE) { //Do nothing
                    }
                    try {
                        if (isMine(board[h + 1][w].getName())) {
                            foundMines++;
                        }
                    } catch (IndexOutOfBoundsException iOBE) { //Do nothing
                    }
                    try {
                        if (isMine(board[h + 1][w + 1].getName())) {
                            foundMines++;
                        }
                    } catch (IndexOutOfBoundsException iOBE) { //Do nothing
                    }
                    board[h][w].setName(board[h][w].getName() + "," + foundMines);
                }
            }
        }
        return board;
    }

    /**
     * Returns the amount of found mines around a button
     *
     * @param buttonName
     * @return
     */

    public static String getNeighborValue(String buttonName) {
        return buttonName.substring(buttonName.length() - 1);
    }

    public JButton[][] recursiveReveal(JButton[][] board) {
        return board;
    }
}
