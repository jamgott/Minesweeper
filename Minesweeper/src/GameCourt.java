/**
 * CIS 120 Game HW
 * (c) University of Pennsylvania
 * @version 2.1, Apr 2017
 */

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.NoSuchElementException;

import javax.swing.*;

/**
 * GameCourt
 * 
 * This class holds the primary game logic for how different objects interact with one another. Take
 * time to understand how the timer interacts with the different methods and how it repaints the GUI
 * on every tick().
 */
@SuppressWarnings("serial")
public class GameCourt extends JPanel {
 
    private static boolean newGame;
    private static boolean gameOver = false;
    private static int flips = 0; //Number of cells that have been flipped
    private static int minesRemaining = 10; //10 - number of flags
    private JLabel status; // Current status text, i.e. "Mines Remaining: 10"

    // Game constants
    public static final int COURT_WIDTH = 316;
    public static final int COURT_HEIGHT = 316;
    private static Cell[][] mineField = new Cell[9][9];


    /**
     * The constructor creates the game court, holds the mouse
     * click event listeners, and calls the appropriate
     * functions based on the mouse events and game state.
     * 
     */
    public GameCourt(JLabel status) {
        // creates border around the court area, JComponent method
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        this.status = status;
        addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
                int col = x / 35;
                int row = y / 35;
                if (e.getButton() == MouseEvent.BUTTON3 && !gameOver && 
                        !mineField[row][col].isFlipped()) {
                    rightClick(row, col);
                    status.setText("Mines Remaining: " + Integer.toString(minesRemaining));
                } else if (e.getButton() == MouseEvent.BUTTON1 && !gameOver) {
                    leftClick(row, col);
                }
                if (gameOver && flips != 71) {
                    status.setText("You lose!");
                } else if (flips == 71) {
                    status.setText("You Win!");
                    gameOver = true;
                }
                repaint();
            }
        });
    }
    /**
     * This function executes the appropriate code for when a left
     * click on the gameCourt occurs. If it is the first click in
     * the game, it ensures the first cell and its adjacent cells
     * do not have mines. It then randomly places 10 mines in the
     * remaining cells. Finally it gives the cells adjacent to the
     * mines the proper number of adjacent mines. It then calls
     * the flipCell function. If it is not the first click of the
     * game, then it simply calls the flipCell function.
     * 
     * @param row The row in the mineField array corresponding to
     * the location clicked in the window.
     * @param col The column in the mineField array corresponding to
     * the location clicked in the window.
     */
    public static void leftClick(int row, int col) {
        if (gameOver) {
            return;
        }
        if (newGame) {
            int pos = row * 9 + col;
            ArrayList<Integer> randomPos = new ArrayList<Integer>();
            //loop makes sure no mines in or adjacent to first one clicked
            for (int i = 0; i < 81; i++) { 
                if (i == pos || i == pos + 1 || i == pos - 1 || i == pos - 9
                        || i == pos + 9 || i == pos - 8 || i == pos - 10 || i == pos + 8
                        || i == pos + 10) {
                    continue;
                } else {
                    randomPos.add(i);
                }
            }
            Collections.shuffle(randomPos); //ArrayList used above to call this
            /*
             * This loop takes a shuffled array list
             * of the first 81 integers and picks the
             * first 10 to be the positions of the bombs.
             */
            for (int i = 0; i < 10; i++) {
                int bombPos = randomPos.get(i);
                int mfRow = bombPos / 9; //Change of coordinates
                int mfCol = bombPos % 9;
                (mineField[mfRow][mfCol]).placeMine();
            } //This next loop finds the numbers for cells adjacent to mines
            for (int i = 0; i < 10; i++) {
                int bombPos = randomPos.get(i);
                int mfRow = bombPos / 9;
                int mfCol = bombPos % 9;
                int upper = mfRow - 1;
                boolean u = false;
                int lower = mfRow + 1;
                boolean lo = false;
                int left = mfCol - 1;
                boolean le = false;
                int right = mfCol + 1;
                boolean r = false;
                if (upper >= 0) {
                    u = true;
                    if (!mineField[upper][mfCol].isMine()) {
                        mineField[upper][mfCol].addAdj();
                    }
                }
                if (lower < 9) {
                    lo = true;
                    if (!mineField[lower][mfCol].isMine()) {
                        mineField[lower][mfCol].addAdj();
                    }
                }
                if (left >= 0) {
                    le = true;
                    if (!mineField[mfRow][left].isMine()) {
                        mineField[mfRow][left].addAdj();
                    }
                }
                if (right < 9) {
                    r = true;
                    if (!mineField[mfRow][right].isMine()) {
                        mineField[mfRow][right].addAdj();
                    }
                }
                if (u && le && !mineField[upper][left].isMine()) {
                    mineField[upper][left].addAdj();
                }
                if (u && r && !mineField[upper][right].isMine()) {
                    mineField[upper][right].addAdj();
                }
                if (lo && le && !mineField[lower][left].isMine()) {
                    mineField[lower][left].addAdj();
                }
                if (lo && r && !mineField[lower][right].isMine()) {
                    mineField[lower][right].addAdj();
                }
            }
            newGame = false;
        }
        flipCell(row, col);
        
    }

    /**
     * This is called when a right click is detected and is
     * responsible for the flagging function. If the cell
     * is not flagged or flipped, it will flag the cell and
     * begin the repaint protocol.
     * If the cell is flagged, it will unflag it. If the
     * cell is already flipped or the game is over,
     * it will do nothing.
     * 
     * @param row
     * @param col
     */
    public static void rightClick(int row, int col) {
        if (gameOver || mineField[row][col].isFlipped()) {
            return;
        }
        mineField[row][col].flag();
        if (mineField[row][col].isFlagged()) {
            minesRemaining--;
        } else {
            minesRemaining++;
        }
        if (!mineField[row][col].isFlipped()) {
            mineField[row][col].setColor(Color.LIGHT_GRAY);
        }
    }

    /**
     * This function recursively flips all necessary
     * cells. If the cell clicked was empty and had
     * no adjacent mines, then it will flip all adjacent
     * mines to that mine and so on and so forth until it
     * runs into cells that have adjacent mines. If a bomb is
     * was clicked on, it will flip the bomb and begin the
     * gameOver protocol.
     * 
     * @param row The row in the mineField array corresponding to
     * the location clicked in the window.
     * @param col The column in the mineField array corresponding to
     * the location clicked in the window.
     */
    public static void flipCell(int row, int col) {
        if (gameOver) {
            return;
        }
        Cell c = mineField[row][col];
        //base case 1
        if (c.isFlagged() || c.isFlipped()) {
            return;
        }
        c.flip();
        flips++;
        if (c.isMine()) {
            c.setColor(Color.RED);
            gameOver = true;
            flips--;
            return;
        } else {
            c.setColor(Color.GRAY);
        }
        boolean u = false;
        boolean lo = false;
        boolean le = false;
        boolean r = false;
        if (c.numAdj() != 0) { //base case 2
            return;
        }
        if (col - 1 >= 0) { //keep in bounds
            le = true;
            flipCell(row, col - 1); 
        } 
        if (col + 1 < 9) {
            r = true;
            flipCell(row, col + 1);  
        } 
        if (row - 1 >= 0) {
            u = true;
            flipCell(row - 1, col); 
        } 
        if (row + 1 < 9) {
            lo = true;
            flipCell(row + 1, col);
        } 
        if (le && u) {
            flipCell(row - 1, col - 1);
        } 
        if (le && lo) {
            flipCell(row + 1, col - 1);
        } 
        if (r && u) {
            flipCell(row - 1, col + 1);
        } 
        if (r && lo) {
            flipCell(row + 1, col + 1);
        }
    }

    /**
     * (Re-)set the game to its initial state.
     */
    public void reset() {
        newGame = true;
        gameOver = false;
        flips = 0;
        minesRemaining = 10;
        int row = 0;
        for (int y = 0; y <= 280; y += 35) {
            int col = 0;
            for (int x = 0; x <= 280; x += 35) {
                mineField[row][col] =
                        new Cell(x, y, COURT_WIDTH, COURT_HEIGHT, Color.LIGHT_GRAY,
                        false, 0);
                col++;
            }
            row++;
        }
        status.setText("Mines Remaining: " + Integer.toString(minesRemaining));
        repaint();

        // Make sure that this component has the keyboard focus
        requestFocusInWindow();
    }
    /**
     * This function takes all of the crucial information
     * from each cell object in the mineField array, converts
     * it to a string, and writes it to a file. Each piece will
     * be on a separate line in the file. Finally, it takes the
     * important static information from the game court and writes
     * that to the file.
     */
    public void save() {
        if (newGame) {
            return;
        }
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter("MineSweeperState.txt", false));
        } catch (IOException e) {
            throw new NoSuchElementException();
        }
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                try {
                    writer.write(Boolean.toString(mineField[row][col].isMine()));
                    writer.newLine();
                    writer.write(Boolean.toString(mineField[row][col].isFlipped()));
                    writer.newLine();
                    writer.write(Boolean.toString(mineField[row][col].isFlagged()));
                    writer.newLine();
                    writer.write(Integer.toString(mineField[row][col].numAdj()));
                    writer.newLine();
                    writer.write(Integer.toString(row));
                    writer.newLine();
                    writer.write(Integer.toString(col));
                    writer.newLine();
                } catch (IOException e) {
                   
                }
            }
        }
        try {
            writer.write(Integer.toString(flips));
            writer.newLine();
            writer.write(Boolean.toString(gameOver));
            writer.newLine();
            writer.write(Integer.toString(minesRemaining));
            writer.flush();
        } catch (IOException e) {
        } finally {
            try {
                writer.close();
            } catch (IOException e) {
                throw new NoSuchElementException();
            }
        }
    }
    

    /**
     * This function takes the information from the file,
     * line by line turns it back into a string, and 
     * inputs it appropriately to each cell object. It
     * then recreates the mineField array, reinstates
     * all of the state variables, and makes sure 
     * everything is the proper color before calling redraw.
     */
    public void load() {
        boolean mine = false;
        boolean flipped = true;
        boolean flagged = false;
        int numAdj = 0;
        int row = 0;
        int col = 0;
        Color color;
        BufferedReader reader = null;
        Path path = Paths.get("MineSweeperState.txt");
        try {
            reader = new BufferedReader(new FileReader(path.toAbsolutePath().toString()));
        } catch (IOException e) {
            throw new NoSuchElementException("Must save a game before loading!");
        } 
        for (int i = 0; i < 81; i++) {
            try {
                mine = Boolean.parseBoolean(reader.readLine());
                flipped = Boolean.parseBoolean(reader.readLine());
                flagged = Boolean.parseBoolean(reader.readLine());
                numAdj = Integer.parseInt(reader.readLine());
                row = Integer.parseInt(reader.readLine());
                col = Integer.parseInt(reader.readLine());
            } catch (IOException e) {
            } 
            if (flipped && mine) {
                color = Color.RED;
            } else if (flipped) {
                color = Color.GRAY;
            } else {
                color = Color.LIGHT_GRAY;
            }
            mineField[row][col] = new Cell(col * 35, row * 35, COURT_WIDTH,
                    COURT_HEIGHT, color, mine, numAdj);
            if (flipped) {
                mineField[row][col].flip();
            } else if (flagged) {
                mineField[row][col].flag();
            }
        }
        try {
            flips = Integer.parseInt(reader.readLine());
            gameOver = Boolean.parseBoolean(reader.readLine());
            minesRemaining = Integer.parseInt(reader.readLine());
            newGame = false;
            if (gameOver && flips == 71) {
                status.setText("You Win!");
            } else if (gameOver) {
                status.setText("You Lose!");
            } else {
                status.setText("Mines Remaining: " + Integer.toString(minesRemaining));
            }
        } catch (IOException E) {
        } finally {
            repaint();
            try {
                reader.close();
            } catch (IOException e) {
            }
        }
    }
    
    public void rules() {
        final JFrame frame = new JFrame("Minesweeper Rules");
        JLabel rules =  new JLabel("<html>Rules:<br/>The primary objective "
                + "of minesweeper is to reveal every empty cell. The game is won "
                + "when every empty cell is revealed and it is lost when the player "
                + "reveals a cell containing a mine. The minefield has 10 randomly "
                + "placed mines. The first cell to be revealed will never contain a mine. "
                + "When a mine is overturned, it will turn a dark gray. It may also "
                + "display a number indicating how many cells it is adjacent to that "
                + "contain mines. A player may flag any cell indicating that they believe "
                + "it is a mine. If a cell is flagged, the player will not be able to reveal "
                + "it until they unflag it. When a cell is flagged, the mines remaining count "
                + "will decrease. This does not mean the user has correctly flagged a mine "
                + "but rather is the game telling the user how many flags are left assuming "
                + "the flag was correct. <br/><br/>Mechanics:<br/> To overturn a cell (left) "
                + "click on "
                + "it. Recursively adjacent cells not containing mines may also overturn. "
                + " To flag a cell, right click on it (hold control and click for Mac users). "
                + "To unflag it, right click on it again."
                + "<br/><br/>New Game, Save and Load:<br/>"
                + "A player may start a new game at any time by clicking the button. "
                + "Additonally, a player may save a single game by clicking the save button. "
                + "They "
                + "may then return to it at any time by clicking the load button."
                + "</html>", SwingConstants.LEFT);
        frame.add(rules);
        frame.setPreferredSize(new Dimension(380, 450));
        frame.setLocation(700, 200);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        frame.setVisible(true);
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col ++) {
                mineField[row][col].draw(g);
            }
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(COURT_WIDTH, COURT_HEIGHT);
    }
    
    //For Testing Purposes
    public static void setNewGame(boolean b) {
        newGame = b;
    }
    public static boolean isNewGame() {
        return newGame;
    }
    public static Cell[][] getMineField() {
        Cell[][] mineFieldCopy = new Cell[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                mineFieldCopy[i][j] = mineField[i][j];
            }
        }
        return mineFieldCopy;
    }
    public static boolean isGameOver() {
        return gameOver;
    }
}