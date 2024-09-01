package no.kobro.sudokuSolver;

import java.util.HashSet;
import java.util.Set;
import java.util.List;

public class SudokuUtils {

    public static boolean isValidPlacement(SudokuBoard board, int row, int col, int value) {
        return !board.getRow(row).contains(value) &&
                !board.getColumn(col).contains(value) &&
                !board.getSubgrid(row, col).contains(value);
    }

    public static Set<SudokuCell> findEmptyCells(SudokuBoard board) {
        Set<SudokuCell> emptyCells = new HashSet<>();
        for (int row = 0; row < SudokuBoard.SIZE; row++) {
            for (int col = 0; col < SudokuBoard.SIZE; col++) {
                SudokuCell cell = board.getCell(row, col);
                if (cell.isEmpty()) {
                    emptyCells.add(cell);
                }
            }
        }
        return emptyCells;
    }

    public static void printBoardWithCandidates(SudokuBoard board){
        for (int row = 0; row < SudokuBoard.SIZE; row++) {
            for (int col = 0; col < SudokuBoard.SIZE; col++) {
                SudokuCell cell = board.getCell(row, col);
                if (cell.isEmpty()) {
                    System.out.print(cell.getCandidates() + " ");
                } else {
                    System.out.print("[" + cell.getValue() + "] ");
                }
                if ((col + 1) % SudokuBoard.SUBGRID_SIZE == 0 && col < SudokuBoard.SIZE - 1) {
                    System.out.print("| ");
                }
            }
            System.out.println();
            if ((row + 1) % SudokuBoard.SUBGRID_SIZE == 0 && row < SudokuBoard.SIZE - 1) {
                System.out.println("---------+---------+---------");
            }
        }
    }

    public static boolean isValidBoard(SudokuBoard board) {
        for (int row = 0; row < SudokuBoard.SIZE; row++) {
            if (hasDuplicates(board.getRow(row))) return false;
        }
        for (int col = 0; col < SudokuBoard.SIZE; col++) {
            if (hasDuplicates(board.getColumn(col))) return false;
        }
        for (int row = 0; row < SudokuBoard.SIZE; row += SudokuBoard.SUBGRID_SIZE) {
            for (int col = 0; col < SudokuBoard.SIZE; col += SudokuBoard.SUBGRID_SIZE) {
                if (hasDuplicates(board.getSubgrid(row, col))) return false;
            }
        }
        return true;
    }

    private static boolean hasDuplicates(List<Integer> values) {
        Set<Integer> seen = new HashSet<>();
        for (int value : values) {
            if (value != SudokuCell.EMPTY && !seen.add(value)) {
                return true;
            }
        }
        return false;
    }

}
