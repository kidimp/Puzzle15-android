package com.chous.puzzle15;

public class MoveResult {

    private final int tile;

    private final int fromX;
    private final int fromY;

    private final int toX;
    private final int toY;

    public MoveResult(
            int tile,
            int fromX,
            int fromY,
            int toX,
            int toY
    ) {
        this.tile = tile;

        this.fromX = fromX;
        this.fromY = fromY;

        this.toX = toX;
        this.toY = toY;
    }

    public int getTile() {
        return tile;
    }

    public int getFromX() {
        return fromX;
    }

    public int getFromY() {
        return fromY;
    }

    public int getToX() {
        return toX;
    }

    public int getToY() {
        return toY;
    }
}
