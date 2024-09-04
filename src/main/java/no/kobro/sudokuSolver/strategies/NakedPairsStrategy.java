package no.kobro.sudokuSolver.strategies;

import no.kobro.sudokuSolver.SudokuBoard;
import no.kobro.sudokuSolver.SudokuCell;

import java.util.HashSet;
import java.util.Set;

public class NakedPairsStrategy implements SolvingStrategy {

    @Override
    public boolean apply(SudokuBoard board){
        boolean progress = false;

        // Apply to rows
        for (int row = 0; row < SudokuBoard.SIZE; row++) {
            progress |= findNakedPairs(board, getRowCells(board, row));
        }

        // Apply to columns
        for (int col = 0; col < SudokuBoard.SIZE; col++) {
            progress |= findNakedPairs(board, getColumnCells(board, col));
        }

        // Apply to subgrids
        for (int row = 0; row < SudokuBoard.SIZE; row += SudokuBoard.SUBGRID_SIZE) {
            for (int col = 0; col < SudokuBoard.SIZE; col += SudokuBoard.SUBGRID_SIZE) {
                progress |= findNakedPairs(board, getSubgridCells(board, row, col));
            }
        }
        return progress;
    }

    private boolean findNakedPairs(SudokuBoard board, Set<SudokuCell> cells) {
        boolean progress = false;

        // If any two cells in unit (row, column, subgrid) contains the same two candidates,
        // these candidates can be removed from all other cell's candidates
        for (SudokuCell cellA : cells) {
            if (cellA.getCandidates().size() == 2) {
                for (SudokuCell cellB : cells) {
                    if (cellA != cellB && cellA.getCandidates().equals(cellB.getCandidates())) {
                        Set<Integer> nakedPair = new HashSet<>(cellA.getCandidates());

                        for (SudokuCell cell: cells) {
                            if (cell != cellA && cell != cellB && cell.isEmpty()) {
                                for (int value : nakedPair) {
                                    if (cell.removeCandidate(value)) {
                                        board.updateCandidates();
                                        progress = true;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        return progress;
    }

    // Utility functions to grab all cells in a row, column or subgrid
    private Set<SudokuCell> getRowCells(SudokuBoard board, int rowIndex) {
        Set<SudokuCell> cells = new HashSet<>();
        for (int col = 0; col < SudokuBoard.SIZE; col++) {
            cells.add(board.getCell(rowIndex, col));
        }
        return cells;
    }

    private Set<SudokuCell> getColumnCells(SudokuBoard board, int columnIndex) {
        Set<SudokuCell> cells = new HashSet<>();
        for (int row = 0; row < SudokuBoard.SIZE; row++) {
            cells.add(board.getCell(row, columnIndex));
        }
        return cells;
    }

    private Set<SudokuCell> getSubgridCells(SudokuBoard board, int rowIndex, int columnIndex) {
        Set<SudokuCell> cells = new HashSet<>();
        // Make sure that the subgrid's initial "position" are 0,3 or 6 for both rows and columns
        int startRow = (rowIndex / SudokuBoard.SUBGRID_SIZE) * SudokuBoard.SUBGRID_SIZE;
        int startColumn = (columnIndex / SudokuBoard.SUBGRID_SIZE) * SudokuBoard.SUBGRID_SIZE;

        // Iterate over subgrid and append to cells
        for (int r = startRow; r < startRow + SudokuBoard.SUBGRID_SIZE; r++) {
            for (int c = startColumn; c < startColumn + SudokuBoard.SUBGRID_SIZE; c++) {
                cells.add(board.getCell(r, c));
            }
        }
        return cells;
    }
}
