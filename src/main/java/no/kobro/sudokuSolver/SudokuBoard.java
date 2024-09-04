package no.kobro.sudokuSolver;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class SudokuBoard {

    private final SudokuCell[][] board;
    public static final int SIZE = 9;
    public static final int SUBGRID_SIZE = 3;

    public SudokuBoard(int[][] initialValues) {
        if (initialValues.length != SIZE || initialValues[0].length != SIZE) {
            throw new IllegalArgumentException("Initial values must be a 9x9 grid.");
        }

        board = new SudokuCell[SIZE][SIZE];
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                int value = initialValues[row][col];
                board[row][col] = value == SudokuCell.EMPTY ? new SudokuCell() : new SudokuCell(value);
            }
        }
    }

    public SudokuCell getCell(int row, int col) {
        validateIndices(row, col);
        return board[row][col];
    }

    public void setCellValue(int row, int col, int value) {
        validateIndices(row, col);
        board[row][col].setValue(value);
        updateCandidates();
    }

    public List<Integer> getRow(int row) {
        validateRowIndex(row);
        List<Integer> rowValues = new ArrayList<>();
        for (int col = 0; col < SIZE; col++) {
            rowValues.add(board[row][col].getValue());
        }
        return rowValues;
    }

    public List<Integer> getColumn(int col) {
        validateColIndex(col);
        List<Integer> colValues = new ArrayList<>();
        for (int row = 0; row < SIZE; row++) {
            colValues.add(board[row][col].getValue());
        }
        return colValues;
    }

    public List<Integer> getSubgrid(int row, int col) {
        validateIndices(row, col);
        List<Integer> subgridValues = new ArrayList<>();
        int startRow = (row / SUBGRID_SIZE) * SUBGRID_SIZE;
        int startCol = (col / SUBGRID_SIZE) * SUBGRID_SIZE;

        for (int r = startRow; r < startRow + SUBGRID_SIZE; r++) {
            for (int c = startCol; c < startCol + SUBGRID_SIZE; c++) {
                subgridValues.add(board[r][c].getValue());
            }
        }
        return subgridValues;
    }

    public boolean isSolved() {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (board[row][col].isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }

    public void printBoard() {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                System.out.print(board[row][col] + " ");
                if ((col + 1) % SUBGRID_SIZE == 0 && col < SIZE - 1) {
                    System.out.print("| ");
                }
            }
            System.out.println();
            if ((row + 1) % SUBGRID_SIZE == 0 && row < SIZE - 1) {
                System.out.println("------+-------+------");
            }
        }
    }

    private void validateIndices(int row, int col) {
        validateRowIndex(row);
        validateColIndex(col);
    }

    private void validateRowIndex(int row) {
        if (row < 0 || row >= SIZE) {
            throw new IndexOutOfBoundsException("Row index out of bounds: " + row);
        }
    }

    private void validateColIndex(int col) {
        if (col < 0 || col >= SIZE) {
            throw new IndexOutOfBoundsException("Column index out of bounds: " + col);
        }
    }

    public boolean canPlaceValue(int row, int col, int value) {
        validateIndices(row, col);

        return !getRow(row).contains(value) &&
                !getColumn(col).contains(value) &&
                !getSubgrid(row, col).contains(value);
    }

    public void updateCandidates() {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                SudokuCell cell = board[row][col];
                if (cell.isEmpty()) {
                    Set<Integer> candidates = cell.getCandidates();
                    candidates.clear();

                    for (int value = SudokuCell.MIN_VALUE; value <= SudokuCell.MAX_VALUE; value++) {
                        if (canPlaceValue(row, col, value)) {
                            candidates.add(value);
                        }
                    }
                }
            }
        }
    }

    public void printAllCandidates() {
        updateCandidates();
        int size = SIZE;
        int subgridSize = SUBGRID_SIZE;

        StringBuilder sb = new StringBuilder();

        for (int row = 0; row < size; row++) {
            if (row % subgridSize == 0) {
                sb.append("+---------+---------+---------+\n");
            }

            for (int col = 0; col < size; col++) {
                if (col % subgridSize == 0) {
                    sb.append("|");
                }

                SudokuCell cell = getCell(row, col);
                Set<Integer> candidates = cell.getCandidates();

                // Print the candidates within the cell
                if (!cell.isEmpty()) {
                    sb.append(String.format("(%d) ", cell.getValue()));
                } else {
                    sb.append("(");
                    for (int i = 1; i <= size; i++) {
                        if (candidates.contains(i)) {
                            sb.append(i);
                        }
                    }
                    sb.append(") ");
                }
            }

            sb.append("|\n");
        }
        sb.append("+---------+---------+---------+\n");

        System.out.print(sb);
    }
}
