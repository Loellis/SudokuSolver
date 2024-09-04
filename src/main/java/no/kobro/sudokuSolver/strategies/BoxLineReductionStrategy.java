package no.kobro.sudokuSolver.strategies;

import no.kobro.sudokuSolver.SudokuBoard;
import no.kobro.sudokuSolver.SudokuCell;

import java.util.HashSet;
import java.util.Set;

public class BoxLineReductionStrategy implements SolvingStrategy {

    @Override
    public boolean apply(SudokuBoard board) {
        boolean progressMade = false;

        for (int value = SudokuCell.MIN_VALUE; value <= SudokuCell.MAX_VALUE; value++) {
            progressMade |= applyToSubgridRows(board, value);
            progressMade |= applyToSubgridCols(board, value);
        }

        return progressMade;
    }

    private boolean applyToSubgridRows(SudokuBoard board, int value) {
        boolean progressMade = false;

        for (int row = 0; row < SudokuBoard.SIZE; row += SudokuBoard.SUBGRID_SIZE) {
            for (int col = 0; col < SudokuBoard.SIZE; col += SudokuBoard.SUBGRID_SIZE) {
                Set<Integer> candidateRows = new HashSet<>();

                for (int r = row; r < row + SudokuBoard.SUBGRID_SIZE; r++) {
                    for (int c = col; c < col + SudokuBoard.SUBGRID_SIZE; c++) {
                        SudokuCell cell = board.getCell(r, c);
                        if (cell.isEmpty() && cell.getCandidates().contains(value)) {
                            candidateRows.add(r);
                        }
                    }
                }

                if (candidateRows.size() == 1) {
                    int targetRow = candidateRows.iterator().next();

                    for (int c = 0; c < SudokuBoard.SIZE; c++) {
                        if (c < col || c >= col + SudokuBoard.SUBGRID_SIZE) {
                            SudokuCell cell = board.getCell(targetRow, c);
                            if (cell.isEmpty() && cell.getCandidates().remove(value)) {
                                progressMade = true;
                            }
                        }
                    }
                }
            }
        }

        return progressMade;
    }

    private boolean applyToSubgridCols(SudokuBoard board, int value) {
        boolean progressMade = false;

        for (int col = 0; col < SudokuBoard.SIZE; col += SudokuBoard.SUBGRID_SIZE) {
            for (int row = 0; row < SudokuBoard.SIZE; row += SudokuBoard.SUBGRID_SIZE) {
                Set<Integer> candidateCols = new HashSet<>();

                for (int c = col; c < col + SudokuBoard.SUBGRID_SIZE; c++) {
                    for (int r = row; r < row + SudokuBoard.SUBGRID_SIZE; r++) {
                        SudokuCell cell = board.getCell(r, c);
                        if (cell.isEmpty() && cell.getCandidates().contains(value)) {
                            candidateCols.add(c);
                        }
                    }
                }

                if (candidateCols.size() == 1) {
                    int targetCol = candidateCols.iterator().next();

                    for (int r = 0; r < SudokuBoard.SIZE; r++) {
                        if (r < row || r >= row + SudokuBoard.SUBGRID_SIZE) {
                            SudokuCell cell = board.getCell(r, targetCol);
                            if (cell.isEmpty() && cell.getCandidates().remove(value)) {
                                progressMade = true;
                            }
                        }
                    }
                }
            }
        }

        return progressMade;
    }
}
