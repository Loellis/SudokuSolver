package no.kobro.sudokuSolver.strategies;

import no.kobro.sudokuSolver.SudokuBoard;
import no.kobro.sudokuSolver.SudokuCell;

import java.util.HashSet;
import java.util.Set;

public class NakedPairsStrategy implements SolvingStrategy {

    @Override
    public boolean apply(SudokuBoard board) {
        boolean progressMade = false;

        for (int row = 0; row < SudokuBoard.SIZE; row++) {
            progressMade |= findNakedPairs(board, getRowCells(board, row));
        }

        for (int col = 0; col < SudokuBoard.SIZE; col++) {
            progressMade |= findNakedPairs(board, getColumnCells(board, col));
        }

        for (int row = 0; row < SudokuBoard.SIZE; row += SudokuBoard.SUBGRID_SIZE) {
            for (int col = 0; col < SudokuBoard.SIZE; col += SudokuBoard.SUBGRID_SIZE) {
                progressMade |= findNakedPairs(board, getSubgridCells(board, row, col));
            }
        }

        return progressMade;
    }

    private boolean findNakedPairs(SudokuBoard board, Set<SudokuCell> cells) {
        boolean progressMade = false;

        for (SudokuCell cell1 : cells) {
            if (cell1.getCandidates().size() == 2) {
                for (SudokuCell cell2 : cells) {
                    if (cell1 != cell2 && cell1.getCandidates().equals(cell2.getCandidates())) {
                        Set<Integer> nakedPair = new HashSet<>(cell1.getCandidates());

                        for (SudokuCell cell : cells) {
                            if (cell != cell1 && cell != cell2 && cell.isEmpty()) {
                                for (int value : nakedPair) {
                                    if (cell.getCandidates().remove(value)) { // Her må man undersøke hva som egentlig skjer?? Vil denne gjøre noen som helst endring?
                                        progressMade = true;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        return progressMade;
    }

    private Set<SudokuCell> getRowCells(SudokuBoard board, int row) {
        Set<SudokuCell> cells = new HashSet<>();
        for (int col = 0; col < SudokuBoard.SIZE; col++) {
            cells.add(board.getCell(row, col));
        }
        return cells;
    }

    private Set<SudokuCell> getColumnCells(SudokuBoard board, int col) {
        Set<SudokuCell> cells = new HashSet<>();
        for (int row = 0; row < SudokuBoard.SIZE; row++) {
            cells.add(board.getCell(row, col));
        }
        return cells;
    }

    private Set<SudokuCell> getSubgridCells(SudokuBoard board, int row, int col) {
        Set<SudokuCell> cells = new HashSet<>();
        int startRow = (row / SudokuBoard.SUBGRID_SIZE) * SudokuBoard.SUBGRID_SIZE;
        int startCol = (col / SudokuBoard.SUBGRID_SIZE) * SudokuBoard.SUBGRID_SIZE;

        for (int r = startRow; r < startRow + SudokuBoard.SUBGRID_SIZE; r++) {
            for (int c = startCol; c < startCol + SudokuBoard.SUBGRID_SIZE; c++) {
                cells.add(board.getCell(r, c));
            }
        }
        return cells;
    }
}
