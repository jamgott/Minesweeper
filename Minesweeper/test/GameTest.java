import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;

import javax.swing.JLabel;

/**
 * You can use this file (and others) to test your implementation.
 */

public class GameTest {

    @BeforeEach
    public void setUp() {
        final JLabel status = new JLabel("");
        final GameCourt court = new GameCourt(status);
        court.reset();
    }

    // Cell object method tests
    @Test
    public void testGetters() {
        Cell c = new Cell(20, 20, 316, 316, Color.LIGHT_GRAY, false, 0);
        assertEquals(20, c.getPx());
        assertEquals(20, c.getPy());
        assertEquals(35, c.getHeight());
        assertEquals(35, c.getWidth());
        assertEquals(Color.LIGHT_GRAY, c.getColor());
        assertFalse(c.isMine());
        assertEquals(0, c.numAdj());
    }

    @Test
    public void testSetters() {
        Cell c = new Cell(20, 20, 316, 316, Color.LIGHT_GRAY, false, 0);
        assertEquals(20, c.getPx());
        assertEquals(20, c.getPy());
        assertEquals(35, c.getHeight());
        assertEquals(35, c.getWidth());
        assertEquals(Color.LIGHT_GRAY, c.getColor());
        assertFalse(c.isMine());
        assertEquals(0, c.numAdj());
        assertFalse(c.isFlagged());
        assertFalse(c.isFlipped());
        c.placeMine();
        assertTrue(c.isMine());
        c.addAdj();
        assertEquals(c.numAdj(), 1);
        c.setColor(Color.BLACK);
        assertEquals(Color.BLACK, c.getColor());
        c.flip();
        assertTrue(c.isFlipped());
        c.flag();
        assertTrue(c.isFlagged());
    }

    // GameCourt Tests
    @Test
    public void testLeftClickNewGameNoSurroundingUpperLCorner() {
        GameCourt.leftClick(0, 0);
        Cell[][] mineField = GameCourt.getMineField();
        assertFalse(mineField[0][0].isMine());
        assertFalse(mineField[0][1].isMine());
        assertFalse(mineField[1][0].isMine());
        assertFalse(mineField[1][1].isMine());
        assertFalse(GameCourt.isNewGame());
    }

    @Test
    public void testLeftClickNewGameNoSurroundingUpperRCorner() {
        GameCourt.leftClick(0, 8);
        Cell[][] mineField = GameCourt.getMineField();
        assertFalse(mineField[0][8].isMine());
        assertFalse(mineField[0][7].isMine());
        assertFalse(mineField[1][8].isMine());
        assertFalse(mineField[1][7].isMine());
        assertFalse(GameCourt.isNewGame());
    }

    @Test
    public void testLeftClickNewGameNoSurroundingLowerLCorner() {
        GameCourt.leftClick(8, 0);
        Cell[][] mineField = GameCourt.getMineField();
        assertFalse(mineField[8][0].isMine());
        assertFalse(mineField[7][0].isMine());
        assertFalse(mineField[8][1].isMine());
        assertFalse(mineField[7][1].isMine());
        assertFalse(GameCourt.isNewGame());
    }

    @Test
    public void testLeftClickNewGameNoSurroundingLowerRCorner() {
        GameCourt.leftClick(8, 8);
        Cell[][] mineField = GameCourt.getMineField();
        assertFalse(mineField[8][8].isMine());
        assertFalse(mineField[7][8].isMine());
        assertFalse(mineField[8][7].isMine());
        assertFalse(mineField[7][7].isMine());
        assertFalse(GameCourt.isNewGame());
    }

    @Test
    public void testLeftClickNewGameNoSurroundingUEdge() {
        GameCourt.leftClick(0, 5);
        Cell[][] mineField = GameCourt.getMineField();
        assertFalse(mineField[0][5].isMine());
        assertFalse(mineField[0][4].isMine());
        assertFalse(mineField[0][6].isMine());
        assertFalse(mineField[1][5].isMine());
        assertFalse(mineField[1][4].isMine());
        assertFalse(mineField[1][6].isMine());
        assertFalse(GameCourt.isNewGame());
    }

    @Test
    public void testLeftClickNewGameNoSurroundingLEdge() {
        GameCourt.leftClick(6, 0);
        Cell[][] mineField = GameCourt.getMineField();
        assertFalse(mineField[6][0].isMine());
        assertFalse(mineField[5][0].isMine());
        assertFalse(mineField[7][0].isMine());
        assertFalse(mineField[6][1].isMine());
        assertFalse(mineField[5][1].isMine());
        assertFalse(mineField[7][1].isMine());
        assertFalse(GameCourt.isNewGame());
    }

    @Test
    public void testLeftClickNewGameNoSurroundingBEdge() {
        GameCourt.leftClick(8, 7);
        Cell[][] mineField = GameCourt.getMineField();
        assertFalse(mineField[8][7].isMine());
        assertFalse(mineField[8][8].isMine());
        assertFalse(mineField[8][6].isMine());
        assertFalse(mineField[7][7].isMine());
        assertFalse(mineField[7][8].isMine());
        assertFalse(mineField[7][6].isMine());
        assertFalse(GameCourt.isNewGame());
    }

    @Test
    public void testLeftClickNewGameNoSurroundingREdge() {
        GameCourt.leftClick(3, 8);
        Cell[][] mineField = GameCourt.getMineField();
        assertFalse(mineField[3][8].isMine());
        assertFalse(mineField[4][8].isMine());
        assertFalse(mineField[2][8].isMine());
        assertFalse(mineField[3][7].isMine());
        assertFalse(mineField[4][7].isMine());
        assertFalse(mineField[2][7].isMine());
        assertFalse(GameCourt.isNewGame());
    }

    @Test
    public void testLeftClickNewGameNoSurroundingInner() {
        GameCourt.leftClick(5, 3);
        Cell[][] mineField = GameCourt.getMineField();
        assertFalse(mineField[5][3].isMine());
        assertFalse(mineField[5][2].isMine());
        assertFalse(mineField[5][4].isMine());
        assertFalse(mineField[4][3].isMine());
        assertFalse(mineField[4][2].isMine());
        assertFalse(mineField[4][4].isMine());
        assertFalse(mineField[6][3].isMine());
        assertFalse(mineField[6][2].isMine());
        assertFalse(mineField[6][4].isMine());
        assertFalse(GameCourt.isNewGame());
    }

    @Test
    public void testFlagAndUnflag() {
        Cell[][] mineField = GameCourt.getMineField();
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                assertFalse(mineField[row][col].isFlagged());
                GameCourt.rightClick(row, col);
                assertTrue(mineField[row][col].isFlagged());
                GameCourt.rightClick(row, col);
                assertFalse(mineField[row][col].isFlagged());
                assertTrue(GameCourt.isNewGame());
            }
        }
    }

    @Test
    public void testFlipCell() {
        Cell[][] mineField = GameCourt.getMineField();
        GameCourt.flipCell(0, 0);
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                assertTrue(mineField[row][col].isFlipped());
                assertTrue(GameCourt.isNewGame());
            }
        }
    }

    @Test
    public void testNoUnflip() {
        Cell[][] mineField = GameCourt.getMineField();
        GameCourt.flipCell(0, 0);
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                assertTrue(mineField[row][col].isFlipped());
                GameCourt.flipCell(row, col);
                assertTrue(mineField[row][col].isFlipped());
                assertTrue(GameCourt.isNewGame());
            }
        }
    }

    @Test
    public void testDoesNotFlipUnclickedMines() {
        Cell[][] mineField = GameCourt.getMineField();
        mineField[2][0].placeMine();
        mineField[2][1].placeMine();
        mineField[2][2].placeMine();
        mineField[1][2].placeMine();
        mineField[0][2].placeMine();
        mineField[1][0].addAdj();
        mineField[1][0].addAdj();
        mineField[1][1].addAdj();
        mineField[1][1].addAdj();
        mineField[0][1].addAdj();
        mineField[0][1].addAdj();
        GameCourt.flipCell(0, 0);
        assertFalse(mineField[2][0].isFlipped());
        assertFalse(mineField[2][1].isFlipped());
        assertFalse(mineField[2][2].isFlipped());
        assertFalse(mineField[1][2].isFlipped());
        assertFalse(mineField[0][2].isFlipped());
        assertTrue(mineField[0][0].isFlipped());
        assertTrue(mineField[0][1].isFlipped());
        assertTrue(mineField[1][0].isFlipped());
        assertTrue(mineField[1][1].isFlipped());
    }

    @Test
    public void testGameOverWhenMineClicked() {
        Cell[][] mineField = GameCourt.getMineField();
        mineField[0][0].placeMine();
        assertFalse(GameCourt.isGameOver());
        GameCourt.leftClick(0, 0);
        assertTrue(GameCourt.isGameOver());
    }

    @Test
    public void testNoEffectAfterGameOver() {
        Cell[][] mineField = GameCourt.getMineField();
        mineField[0][0].placeMine();
        assertFalse(mineField[0][1].isFlagged());
        assertFalse(mineField[0][1].isFlipped());
        GameCourt.leftClick(0, 0);
        GameCourt.leftClick(0, 1);
        GameCourt.rightClick(0, 1);
        assertFalse(mineField[0][1].isFlagged());
        assertFalse(mineField[0][1].isFlipped());
    }

    @Test
    public void testNoFlaggingFlippedCells() {
        Cell[][] mineField = GameCourt.getMineField();
        GameCourt.flipCell(0, 0);
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                assertFalse(mineField[row][col].isFlagged());
                GameCourt.rightClick(row, col);
                assertFalse(mineField[row][col].isFlagged());
            }
        }
    }

}
