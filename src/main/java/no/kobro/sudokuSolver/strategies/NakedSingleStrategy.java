package no.kobro.sudokuSolver.strategies;

import no.kobro.sudokuSolver.SudokuBoard;
import no.kobro.sudokuSolver.SudokuCell;

public class NakedSingleStrategy implements SolvingStrategy{

    @Override
    public boolean apply(SudokuBoard board) {
        boolean progress = false;
        board.updateCandidates();

        for (int row = 0; row < SudokuBoard.SIZE; row++) {
            for (int col = 0; col < SudokuBoard.SIZE; col++) {
                SudokuCell cell = board.getCell(row, col);
                if (cell.isEmpty() && cell.hasSingleCandidate()) {
                    cell.setValue(cell.getSingleCandidate());
                    board.updateCandidates();
                    progress = true;
                }
            }
        }
        return progress;
    }
}
