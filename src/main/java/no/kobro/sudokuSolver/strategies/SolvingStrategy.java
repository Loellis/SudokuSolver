package no.kobro.sudokuSolver.strategies;

import no.kobro.sudokuSolver.SudokuBoard;

public interface SolvingStrategy {

    boolean apply(SudokuBoard board);
}
