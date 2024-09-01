package no.kobro.sudokuSolver.strategies;

import no.kobro.sudokuSolver.SudokuBoard;
import no.kobro.sudokuSolver.SudokuCell;
import no.kobro.sudokuSolver.SudokuUtils;

public class EliminationStrategy implements SolvingStrategy {

    @Override
    public boolean apply(SudokuBoard board) {
        boolean progressMade = false;

        for (int row = 0; row < SudokuBoard.SIZE; row++) {
            for (int col = 0; col < SudokuBoard.SIZE; col++) {
                SudokuCell cell = board.getCell(row, col);
                if (cell.isEmpty()) {
                    // Remove invalid candidates
                    for (int value = SudokuCell.MIN_VALUE; value <= SudokuCell.MAX_VALUE; value++) {
                        if (!SudokuUtils.isValidPlacement(board, row, col, value)) {
                            if (cell.getCandidates().remove(value)) {
                                progressMade = true;
                            }
                        }
                    }

                    // if only one candidate remains, set it as the cell's value
                    if (cell.hasSingleCandidate()) {
                        cell.setValue(cell.getSingleCandidate());
                        progressMade = true;
                    }
                }
            }
        }
        return progressMade;
    }
}
