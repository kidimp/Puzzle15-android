package com.chous.puzzle15;

import android.content.Context;
import android.util.TypedValue;
import android.widget.FrameLayout;

import java.util.HashMap;
import java.util.Map;

public class PuzzleBoardView extends FrameLayout {

    private static final int SIZE = 4;

    private final Map<Integer, TileView> tiles = new HashMap<>();

    private int tileSizePx;
    private int marginPx;

    public PuzzleBoardView(Context context) {
        super(context);

        init();
    }

    private void init() {
        tileSizePx = dpToPx(80);
        marginPx = dpToPx(3);

        createTiles();

        int boardSize = SIZE * (tileSizePx + marginPx * 2);

        LayoutParams params = new LayoutParams(boardSize, boardSize);

        setLayoutParams(params);
    }

    private void createTiles() {
        for (int value = 1; value <= 15; value++) {

            TileView tile =
                    new TileView(
                            getContext(),
                            value
                    );

            LayoutParams params = new LayoutParams(tileSizePx, tileSizePx);

            tile.setLayoutParams(params);

            tiles.put(value, tile);

            addView(tile);
        }
    }

    public void render(int[][] grid) {
        for (int row = 0; row < SIZE; row++) {

            for (int col = 0; col < SIZE; col++) {

                int value = grid[row][col];

                if (value == 0) {
                    continue;
                }

                TileView tile = tiles.get(value);

                if (tile == null) {
                    continue;
                }

                tile.setX(getCellX(col));
                tile.setY(getCellY(row));
            }
        }
    }

    public void animateMove(MoveResult move, Runnable onFinished) {
        TileView tile = tiles.get(move.getTile());

        if (tile == null) {

            if (onFinished != null) {
                onFinished.run();
            }

            return;
        }

        tile.animate()
                .x(getCellX(move.getToX()))
                .y(getCellY(move.getToY()))
                .setDuration(75)
                .withEndAction(onFinished)
                .start();
    }

    private float getCellX(int col) {
        return col * (tileSizePx + marginPx * 2) + marginPx;
    }

    private float getCellY(int row) {
        return row * (tileSizePx + marginPx * 2) + marginPx;
    }

    private int dpToPx(int dp) {
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dp,
                getResources().getDisplayMetrics()
        );
    }
}
