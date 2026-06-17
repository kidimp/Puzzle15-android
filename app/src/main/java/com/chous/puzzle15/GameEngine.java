package com.chous.puzzle15;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class GameEngine {

    public static final int SIZE = 4;

    private final int[][] grid = new int[SIZE][SIZE];

    private static final int[] OFFSET_X = {0, 0, -1, 1};
    private static final int[] OFFSET_Y = {-1, 1, 0, 0};

    private int moves;

    public GameEngine() {
        newGame();
    }

    public void newGame() {
        moves = 0;

        int[][] win = getWinGrid();

        for (int i = 0; i < SIZE; i++) {
            System.arraycopy(win[i], 0, grid[i], 0, SIZE);
        }

        shuffle();
    }

    public int[][] getGrid() {
        int[][] copy = new int[SIZE][SIZE];

        for (int i = 0; i < SIZE; i++) {
            System.arraycopy(grid[i], 0, copy[i], 0, SIZE);
        }

        return copy;
    }

    public int getMoves() {
        return moves;
    }

    public MoveResult move(Direction direction) {
        int[] empty = getEmptyTileCoordinates();

        int emptyX = empty[0];
        int emptyY = empty[1];

        int index = direction.getOffsetIndex();

        int tileX = emptyX + OFFSET_X[index];
        int tileY = emptyY + OFFSET_Y[index];

        if (isInside(tileX, tileY)) {
            return null;
        }

        int tileValue = grid[tileY][tileX];

        grid[emptyY][emptyX] = tileValue;
        grid[tileY][tileX] = 0;

        moves++;

        return new MoveResult(
                tileValue,
                tileX,
                tileY,
                emptyX,
                emptyY
        );
    }

    public boolean isWin() {
        return Arrays.deepEquals(grid, getWinGrid());
    }

    private void shuffle() {
        for (int i = 0; i < 1000; i++) {
            Direction direction =
                    Direction.values()[ThreadLocalRandom.current().nextInt(Direction.values().length)];

            moveWithoutCounting(direction);
        }

        moves = 0;
    }

    private void moveWithoutCounting(Direction direction) {
        int[] empty = getEmptyTileCoordinates();

        int emptyX = empty[0];
        int emptyY = empty[1];

        int index = direction.getOffsetIndex();

        int tileX = emptyX + OFFSET_X[index];
        int tileY = emptyY + OFFSET_Y[index];

        if (isInside(tileX, tileY)) {
            return;
        }

        int tile = grid[tileY][tileX];

        grid[emptyY][emptyX] = tile;
        grid[tileY][tileX] = 0;
    }

    private boolean isInside(int x, int y) {
        return x < 0
                || x >= SIZE
                || y < 0
                || y >= SIZE;
    }

    private int[] getEmptyTileCoordinates() {
        for (int y = 0; y < SIZE; y++) {
            for (int x = 0; x < SIZE; x++) {
                if (grid[y][x] == 0) {
                    return new int[]{x, y};
                }
            }
        }

        throw new IllegalStateException("Empty tile not found");
    }

    private int[][] getWinGrid() {
        return new int[][]{
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12},
                {13, 14, 15, 0}
        };
    }
}
