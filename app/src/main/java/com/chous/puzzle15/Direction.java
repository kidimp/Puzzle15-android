package com.chous.puzzle15;

public enum Direction {

    UP(1),
    DOWN(0),
    LEFT(3),
    RIGHT(2);

    private final int offsetIndex;

    Direction(int offsetIndex) {
        this.offsetIndex = offsetIndex;
    }

    public int getOffsetIndex() {
        return offsetIndex;
    }
}
