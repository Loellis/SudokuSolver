package no.kobro.sudokuSolver.strategies;

import no.kobro.sudokuSolver.SudokuBoard;
import no.kobro.sudokuSolver.SudokuCell;

public class HiddenSingleStrategy implements SolvingStrategy {

    @Override
    public boolean apply(SudokuBoard board) {
        boolean progressMade = false;

        for (int value = SudokuCell.MIN_VALUE; value <= SudokuCell.MAX_VALUE; value++) {
            progressMade |= applyToUnit(board, value, true);
            progressMade |= applyToUnit(board, value, false);
            progressMade |= applyToSubgrids(board, value);
        }

        return progressMade;
    }

    private boolean applyToUnit(SudokuBoard board, int value, boolean isRow) {
        boolean progressMade = false;

        for (int i = 0; i < SudokuBoard.SIZE; i++) {
           int possiblePosition = -1;
           boolean found = false;

           for (int j = 0; j < SudokuBoard.SIZE; j++) {
               SudokuCell cell = isRow ? board.getCell(i, j) : board.getCell(j, i);
               if (cell.isEmpty() && cell.getCandidates().contains(value)) {
                   if (found) {
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
               progressMade = true;
           }
        }

        return progressMade;
    }

    private boolean applyToSubgrids(SudokuBoard board, int value) {
        boolean progressMade = false;

        for (int row = 0; row < SudokuBoard.SIZE; row += SudokuBoard.SUBGRID_SIZE) {
            for (int col = 0; col < SudokuBoard.SIZE; col += SudokuBoard.SUBGRID_SIZE) {
                int possibleRow = -1;
                int possibleCol = -1;
                boolean found = false;

                for (int r = row; r < row + SudokuBoard.SUBGRID_SIZE; r++) {
                    for (int c = col; c < col + SudokuBoard.SUBGRID_SIZE; c++) {
                        SudokuCell cell = board.getCell(r, c);
                        if (cell.isEmpty() && cell.getCandidates().contains(value)) {
                            if (found) {
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
                    progressMade = true;
                }
            }
        }

        return progressMade;
    }
}
