package no.kobro.sudokuSolver;

public class SudokuBoard {

    private final SudokuCell[][] board;

    public static final int SIZE = 9;
    public static final int SUBGRID_SIZE = 3;

    public SudokuBoard() {
        board = new SudokuCell[SIZE][SIZE];
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                board[row][col] = new SudokuCell();
            }
        }
    }


}
