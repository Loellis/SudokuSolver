package no.kobro.sudokuSolver;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class SudokuCellTest {

    private SudokuCell fixedCell;
    private SudokuCell emptyCell;

    @BeforeEach
    public void setup() {
        fixedCell = new SudokuCell(5);
        emptyCell = new SudokuCell();
    }

    @Test
    public void givenEmptyCell_whenSetValue_thenValueIsSetCandidatesAreCleared() {
        emptyCell.setValue(2);

        assertEquals(2, emptyCell.getValue());
        assertTrue(emptyCell.getCandidates().isEmpty());
    }

    @Test
    public void givenFixedCell_whenSetValue_thenThrowsUnsupportedOperationException() {
        UnsupportedOperationException ex = assertThrows(
                UnsupportedOperationException.class,
                () -> fixedCell.setValue(2)
        );

        assertEquals("You cannot change the value of a fixed cell.", ex.getMessage());
    }

    @Test
    public void givenEmptyCell_whenValueInvalid_thenThrowsIllegalArgumentException() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> emptyCell.setValue(11)
        );

        assertEquals("Invalid cell value, must be between 1 and 9.", ex.getMessage());
    }

    @Test
    public void givenGetCandidates_whenEmptyCell_thenReturnsSetOfNineIntegers() {
        Set<Integer> candidates = emptyCell.getCandidates();
        Set<Integer> expectedCandidates = new HashSet<>(Arrays.asList(1,2,3,4,5,6,7,8,9));

        assertEquals(9, candidates.size());
        assertTrue(candidates.containsAll(expectedCandidates));
    }

    @Test
    public void givenRemoveCandidate_whenEmptyCell_thenRemovesCandidateFromSet() {
        Set<Integer> expectedCandidates = new HashSet<>(Arrays.asList(1,2,3,7,8,9));

        emptyCell.removeCandidate(4);
        emptyCell.removeCandidate(5);
        emptyCell.removeCandidate(6);

        Set<Integer> candidates = emptyCell.getCandidates();

        assertEquals(6, candidates.size());
        assertTrue(candidates.containsAll(expectedCandidates));
    }
}
