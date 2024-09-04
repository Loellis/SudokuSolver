package no.kobro.sudokuSolver;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
public class SudokuBoardTest {


    private SudokuBoard board;
    private final SudokuUtils utils = new SudokuUtils();

    @BeforeEach
    public void setup(){
        board = new SudokuBoard(utils.loadSudokuFromJSON("sudokuPuzzles.json", "easy"));
    }

    @Test
    public void givenGetFourthRow_whenBoardInitialized_thenReturnsRow() {
        List<Integer> expectedRow = new ArrayList<>(Arrays.asList(4,7,3,0,0,0,0,0,0));
        List<Integer> actualRow = board.getRow(3);

        for (int i = 0; i < actualRow.size(); i++){
            assertEquals(expectedRow.get(i), actualRow.get(i));
        }
    }

    @Test
    public void givenGetFirstColumn_whenBoardInitialized_thenReturnColumn() {
        List<Integer> expectedColumn = new ArrayList<>(Arrays.asList(0,0,0,4,0,0,3,0,0));
        List<Integer> actualColumn = board.getColumn(0);

        for (int i = 0; i < actualColumn.size(); i++) {
            assertEquals(expectedColumn.get(i), actualColumn.get(i));
        }
    }

    @Test
    public void givenFirstSubgrid_whenBoardInitialized_thenReturnsSubgrid() {
        List<Integer> expectedSubgrid = new ArrayList<>(Arrays.asList(0,0,0,0,0,5,0,0,4));
        List<Integer> actualSubgrid = board.getSubgrid(0, 0);

        for (int i = 0; i < actualSubgrid.size(); i++) {
            assertEquals(expectedSubgrid.get(i), actualSubgrid.get(i));
        }
    }

    @Test
    public void givenCenterSubgrid_whenBoardInitialized_thenReturnsSubgrid() {
        List<Integer> expectedSubgrid = new ArrayList<>(Arrays.asList(0,0,0,4,0,5,0,0,0));
        List<Integer> actualSubgrid = board.getSubgrid(3, 3);

        for (int i = 0; i < actualSubgrid.size(); i++) {
            assertEquals(expectedSubgrid.get(i), actualSubgrid.get(i));
        }
    }

    @Test
    public void givenIndexWithinThirdSubgrid_whenBoardInitialized_thenReturnsSubgrid() {
        List<Integer> expectedSubgrid = new ArrayList<>(Arrays.asList(0,6,0,8,1,0,0,0,2));
        List<Integer> actualSubgrid = board.getSubgrid(1, 8);

        for (int i = 0; i < actualSubgrid.size(); i++) {
            assertEquals(expectedSubgrid.get(i), actualSubgrid.get(i));
        }
        board.printBoard();
        board.printAllCandidates();
    }


}
