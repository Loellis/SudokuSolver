package no.kobro.sudokuSolver.strategies;

import no.kobro.sudokuSolver.SudokuBoard;

public interface SolvingStrategy {

    /**
     * Applies strategy to the provided Sudoku board.
     *
     * @param board - The given sudoku board
     * @return - Boolean value to state if progress was made or not (i.e. updated a cell's value or candidates)
     */
    boolean apply(SudokuBoard board);
}
