package Minesweeper;

import javax.swing.JButton;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Implements all functions to play Minesweeper
 * <p>
 * - Distribute the mines randomly on the board
 * - Count the number of mines in the neighborhood and appends the button name with this value
 * - Reveal fields recursive if field has no mines around
 */
public class MinesweeperFunctions {
    private static List<JButton> placedMines;

    /**
     * Marks the first clicked button and it's neighbors as start buttons.
     * The middle of these 9 is always 0 and none will be a mine
     * @param board
     * @param startButton
     */
    public static void prepareStart(JButton[][] board, JButton startButton) {
        int startHeight = getButtonCoordinates(startButton.getName())[0];
        int startWidth = getButtonCoordinates(startButton.getName())[1];
        //Set start neighbor value
        //Mark neighbors as start buttons
        for (int x = 1; x >= -1; x--) {
            for (int y = 1; y >= -1; y--) {
                try {
                    board[startHeight - x][startWidth - y].setName(
                            board[startHeight - x][startWidth - y].getName() + ",S");
                } catch (IndexOutOfBoundsException iOBE) {
                    //Do nothing when caught an exception
                    //Therefore continue loop
                }
            }
        }
    }

    /**
     * Distributes randomly a given amount of mines on a board
     *
     * @param board
     * @param height
     * @param width
     * @param mines
     * @return board with randomly distributed mines
     */
    public static void distributeMines(JButton[][] board, int height, int width, int mines) {
        placedMines = new ArrayList<>();
        Random rn = new Random();
        while (mines != 0) {
            int rnHeight = rn.nextInt(height);
            int rnWidth = rn.nextInt(width);
            //Check if random button is already a mine, if not, set it as a mine
            if (!isMine(board[rnHeight][rnWidth].getName()) &&
                    !isStart(board[rnHeight][rnWidth].getName())) {
                //A mine gets identified by it's last char of the button name
                board[rnHeight][rnWidth].setName(board[rnHeight][rnWidth].getName() + ",*");
                placedMines.add(board[rnHeight][rnWidth]);
                mines--;
//                System.out.println("MINE SET AT: " + board[rnHeight][rnWidth].getName());
            }
        }

//        //Prepared Exampleboard for debug purpose
//        board[3][0].setName(board[3][0].getName() + ",*");
//        board[4][4].setName(board[4][4].getName() + ",*");
//        board[0][1].setName(board[0][1].getName() + ",*");
//        board[4][0].setName(board[4][0].getName() + ",*");
//        board[0][3].setName(board[0][3].getName() + ",*");
//        board[0][4].setName(board[0][4].getName() + ",*");
//        board[2][0].setName(board[2][0].getName() + ",*");
//        board[4][2].setName(board[4][2].getName() + ",*");
//        placedMines.add(board[3][0]);
//        placedMines.add(board[4][4]);
//        placedMines.add(board[0][1]);
//        placedMines.add(board[4][0]);
//        placedMines.add(board[0][3]);
//        placedMines.add(board[0][4]);
//        placedMines.add(board[2][0]);
//        placedMines.add(board[4][2]);
    }

    /**
     * Checks if a button is a mine
     */
    public static boolean isMine(String buttonName) {
        return buttonName.contains("*");
    }

    /**
     * Checks if a button is one of the original 8
     */
    private static boolean isStart(String buttonName) {
        return buttonName.contains("S");
    }

    /**
     * Checks if a button is flagged
     */
    public static boolean isFlagged(JButton button) {
        return button.getText().equals("F");
    }

    /**
     * Counts the number of mines around a button
     * Appends the button name with the found amount of mines
     *
     * @param board
     * @return
     */
    public static void countNeighbors(JButton[][] board, int height, int width) {
        int foundMines;
        for (int w = 0; w < width; w++) {
            for (int h = 0; h < height; h++) {
                foundMines = 0;
                if (!isMine(board[h][w].getName())) {
                    //This double loop checks every button around the center, including center, for mines
                    //Only if center is no mine
                    for (int x = 1; x >= -1; x--) {
                        for (int y = 1; y >= -1; y--) {
                            try {
                                if (isMine(board[h - x][w - y].getName())) {
                                    foundMines++;
                                }
                            } catch (IndexOutOfBoundsException iOBE) {
                                //Do nothing when caught an exception
                                //Therefore continue loop
                            }
                        }
                    }
                    board[h][w].setName(board[h][w].getName() + "," + foundMines);
                }
            }
        }
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

    /**
     * Reveal only one button
     * @param board
     * @param buttonName
     */
    public static void revealButton(JButton[][] board, String buttonName) {
        int h = getButtonCoordinates(buttonName)[0];
        int w = getButtonCoordinates(buttonName)[1];

        board[h][w].setEnabled(false);
        board[h][w].setText(getNeighborValue(buttonName));
        BoardGUI.revealedButtons++;
    }

    /**
     * Reveal all neighbors of a button.
     * If one neighbor is also 0, call method again with said neighbor
     * @param board
     * @param buttonName
     * @return
     */
    public static void revealNeighborsRecursive(JButton[][] board, String buttonName) {
        int h = getButtonCoordinates(buttonName)[0];
        int w = getButtonCoordinates(buttonName)[1];
        //Reveal the middle
        board[h][w].setEnabled(false);
//        board[h][w].setText(getNeighborValue(board[h][w].getName()));
        board[h][w].setText("   ");
        BoardGUI.revealedButtons++;

        for (int x = 1; x >= -1; x--) {
            for (int y = 1; y >= -1; y--) {
                try {
                    if (!isFlagged(board[h - x][w - y])) {
                        //If revealed neighbor is also 0, reveal it's neighbors
                        //Reveal only if not already revealed
                        if (getNeighborValue(board[h - x][w - y].getName()).equals("0")
                                && board[h - x][w - y].isEnabled()) {
                            revealNeighborsRecursive(board, board[h - x][w - y].getName());
                        } else if (board[h - x][w - y].isEnabled()) {
                            //Reveal the neighbor and increment total count of revealed buttons
                            board[h - x][w - y].setEnabled(false);
                            board[h - x][w - y].setText(getNeighborValue(board[h - x][w - y].getName()));
                            BoardGUI.revealedButtons++;
                        }
                    }
                } catch (IndexOutOfBoundsException iOBE) {
                    //Do nothing when caught an exception
                    //Therefore continue loop
                }
            }
        }
    }

    /**
     * Reveals all neighbors of only one button
     * @return true when a mine is revealed this way
     */
    public static boolean revealButtonNeighbors(JButton[][] board, String buttonName) {
        int h = getButtonCoordinates(buttonName)[0];
        int w = getButtonCoordinates(buttonName)[1];
        int neighborValue = Integer.parseInt(getNeighborValue(buttonName));
        ArrayList<JButton> neighbors = new ArrayList<>();

        for (int x = 1; x >= -1; x--) {
            for (int y = 1; y >= -1; y--) {
                try {
                    neighbors.add(board[h - x][w - y]);
                } catch (IndexOutOfBoundsException iOBE) {
                    //Do nothing when caught an exception
                    //Therefore continue loop
                }

            }
        }

        if (neighborValue == countFlaggedNeighbors(neighbors)) {
            for (JButton button : neighbors) {
                if (!isFlagged(button) && button.isEnabled()) {
                    if (isMine(button.getName())) {
                        return true;
                    } else {
                        button.setEnabled(false);
                        if (getNeighborValue(button.getName()).equals("0")) {
                            revealNeighborsRecursive(board, button.getName());
                        } else {
                            button.setText(getNeighborValue(button.getName()));
                        }
                        BoardGUI.revealedButtons++;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Counts the neighbors with a flag
     * @param buttons
     * @return
     */
    private static int countFlaggedNeighbors(ArrayList<JButton> buttons) {
        int count = 0;
        for (JButton button : buttons) {
            if (isFlagged(button)) {
                count++;
            }
        }
        return count;
    }

    /**
     * Extracts the height and width coordinate from the button name
     * @param buttonName
     * @return Array with coordinates
     */
    private static int[] getButtonCoordinates(String buttonName) {
        int[] coordinates = new int[2];
        String[] values = buttonName.split(",");
        //h coordinate
        coordinates[0] = Integer.parseInt(values[0]);
        //w coordinate
        coordinates[1] = Integer.parseInt(values[1]);

        return coordinates;
    }

    /**
     * Reveals all mines when game is lost
     * @return
     */
    public static void gameLost() {
        for (JButton mine : placedMines) {
            mine.setEnabled(false);
            mine.setText(getNeighborValue(mine.getName()));
        }
    }

    /**
     * Checks if all mines are flagged
     * @return
     */
    public static boolean allMinesFlagged() {
        int count = 0;
        for (JButton mine : placedMines) {
            if (isFlagged(mine)) {
                count++;
            }
        }
        if (count == placedMines.size()) {
            return true;
        } else {
            return false;
        }
    }
}
