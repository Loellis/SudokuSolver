package no.kobro.sudokuSolver;


import java.util.HashSet;
import java.util.Set;

public class SudokuCell {

    private int value;
    private Set<Integer> candidates;
    private boolean isFixed;

    public static final int EMPTY = 0;
    public static final int MIN_VALUE = 1;
    public static final int MAX_VALUE = 9;

    public SudokuCell() {
        this.value = EMPTY;
        this.isFixed = false;
        this.candidates = new HashSet<>();
        for (int i = MIN_VALUE; i <= MAX_VALUE; i++) {
            this.candidates.add(i);
        }
    }

    public SudokuCell(int value) {
        if (value < MIN_VALUE || value > MAX_VALUE) {
            throw new IllegalArgumentException("Invalid cell value.");
        }
        this.value = value;
        this.isFixed = true;
        this.candidates = new HashSet<>();
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        if (isFixed) {
            throw new UnsupportedOperationException("You cannot change the value of a fixed cell.");
        }
        if (value < MIN_VALUE || value > MAX_VALUE) {
            throw new IllegalArgumentException("Invalid cell value, must be between 1 and 9.");
        }
        this.value = value;
        this.candidates.clear();
        this.isFixed = true;
    }

    public boolean isEmpty() {
        return this.value == EMPTY;
    }

    public Set<Integer> getCandidates() {
        return candidates;
    }

    public void removeCandidate(int candidate) {
        this.candidates.remove(candidate);
    }

    public boolean hasSingleCandidate() {
        return this.candidates.size() == 1;
    }

    public int getSingleCandidate() {
        if (hasSingleCandidate()) {
            return this.candidates.iterator().next();
        }
        throw new IllegalStateException("This cell has more than one candidate.");
    }

    @Override
    public String toString() {
        return isEmpty() ? "." : String.valueOf(value);
    }
}
