package no.kobro.sudokuSolver.strategies;

import no.kobro.sudokuSolver.SudokuBoard;
import no.kobro.sudokuSolver.SudokuCell;

public class HiddenSingleStrategy implements SolvingStrategy {

    @Override
    public boolean apply(SudokuBoard board) {
        boolean progress = false;

        // Make sure board is up to date with candidates
        board.updateCandidates();

        for (int value = SudokuCell.MIN_VALUE; value <= SudokuCell.MAX_VALUE; value++) {
            progress |= findHiddenSingleInRow(board, value);
            progress |= findHiddenSingleInColumn(board, value);
            progress |= findHiddenSingleInSubgrid(board, value);
        }

        return progress;
    }

    private boolean findHiddenSingleInUnit(SudokuBoard board, int value, boolean isRow) {
        boolean progress = false;

        for (int i = 0; i < SudokuBoard.SIZE; i++) {
            int possiblePosition = -1;
            boolean found = false;

            for (int j = 0; j < SudokuBoard.SIZE; j++) {
                SudokuCell cell = isRow ? board.getCell(i, j) : board.getCell(j, i);
                if (cell.isEmpty() && cell.getCandidates().contains(value)) {
                    if (found) {
                        // More than one cell has a candidate with the given value
                        possiblePosition = -1;
                        break;
                    } else {
                        found = true;
                        possiblePosition = j;
                    }
                }
            }

            if (possiblePosition != -1) {
                if (isRow) {
                    board.getCell(i, possiblePosition).setValue(value);
                } else {
                    board.getCell(possiblePosition, i).setValue(value);
                }
                board.updateCandidates();
                progress = true;
            }
        }

        return progress;
    }

    private boolean findHiddenSingleInRow(SudokuBoard board, int value) {
        return findHiddenSingleInUnit(board, value, true);
    }

    private boolean findHiddenSingleInColumn(SudokuBoard board, int value) {
        return findHiddenSingleInUnit(board, value, false);
    }

    private boolean findHiddenSingleInSubgrid(SudokuBoard board, int value) {
        boolean progress = false;

        // First we iterate over the "start" indicies of the subgrids
        for (int row = 0; row < SudokuBoard.SIZE; row += SudokuBoard.SUBGRID_SIZE) {
            for (int col = 0; col < SudokuBoard.SIZE; col += SudokuBoard.SUBGRID_SIZE) {
                int possibleRow = -1;
                int possibleCol = -1;
                boolean found = false;

                // Then we iterate over the cells within the subgrid
                for (int r = row; r < row + SudokuBoard.SUBGRID_SIZE; r++) {
                    for (int c = col; c < col + SudokuBoard.SUBGRID_SIZE; c++) {
                        SudokuCell cell = board.getCell(r, c);
                        if (cell.isEmpty() && cell.getCandidates().contains(value)) {
                            if (found) {
                                // More than one cell has a candidate with the given value
                                possibleRow = -1;
                                possibleCol = -1;
                                break;
                            } else {
                                found = true;
                                possibleRow = r;
                                possibleCol = c;
                            }
                        }
                    }
                }

                if (possibleRow != -1 && possibleCol != -1) {
                    board.getCell(possibleRow, possibleCol).setValue(value);
                    board.updateCandidates();
                    progress = true;
                }
            }
        }
        return progress;
    }
}
