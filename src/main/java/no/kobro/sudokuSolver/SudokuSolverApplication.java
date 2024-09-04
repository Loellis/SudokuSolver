package no.kobro.sudokuSolver;

import no.kobro.sudokuSolver.strategies.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class SudokuSolverApplication {

	public static void main(String[] args) {
		SpringApplication.run(SudokuSolverApplication.class, args);
		// Step 1: Initialize the board (either from input, or hardcoded for testing)
		int[][] initialBoard = {
				{0, 0, 5, 0, 0, 0, 3, 0, 2},
				{4, 0, 7, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 5, 0, 0, 9, 7},
				{1, 3, 0, 4, 0, 9, 6, 8, 5},
				{5, 9, 8, 0, 6, 0, 0, 3, 0},
				{0, 0, 0, 0, 3, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 6, 8, 0, 0},
				{0, 7, 1, 0, 4, 0, 0, 0, 0},
				{0, 0, 6, 8, 0, 0, 0, 0, 0}
		};

		SudokuBoard board = new SudokuBoard(initialBoard);

		// Step 2: Create a list of solving strategies
		List<SolvingStrategy> strategies = Arrays.asList(
				new EliminationStrategy(),
				new NakedSingleStrategy(),
				new HiddenSingleStrategy(),
				new NakedPairsStrategy(),
				new BoxLineReductionStrategy()
		);

		// Step 3: Apply strategies iteratively until the board is solved
		boolean progress;
		do {
			progress = false;
			for (SolvingStrategy strategy : strategies) {
				System.out.println("Applying strategy: " + strategy.toString());
				if (strategy.apply(board)) {
					progress = true;
				}
			}
		} while (progress && !board.isSolved());

		// Step 4: Output the result
		if (board.isSolved()) {
			System.out.println("Sudoku solved successfully!");
			board.printBoard();
		} else {
			System.out.println("No further progress can be made. Current board state:");
			board.printBoard();
			board.printAllCandidates();
		}
	}

}
