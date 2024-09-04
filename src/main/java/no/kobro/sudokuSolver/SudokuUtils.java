package no.kobro.sudokuSolver;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;

public class SudokuUtils {

    ObjectMapper mapper = new ObjectMapper();

    public int[][] loadSudokuFromJSON(String filepath, String puzzleName) {
        try (InputStream is = getClass().getClassLoader().getResourceAsStream(filepath)){
            // Load puzzle
            JsonNode root = mapper.readTree(is);
            JsonNode puzzle = root.path(puzzleName);

            // Convert to int[][]
            int[][] sudokuBoard = new int[puzzle.size()][];
            for (int i = 0; i < puzzle.size(); i++) {
                JsonNode row = puzzle.get(i);
                sudokuBoard[i] = new int[row.size()];
                for (int j = 0; j < row.size(); j++) {
                    sudokuBoard[i][j] = row.get(j).asInt();
                }
            }

            return sudokuBoard;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
