package com.chous.puzzle15;

import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.GridLayout;

import androidx.appcompat.widget.AppCompatTextView;

public class PuzzleBoardView extends GridLayout {

    private static final int SIZE = 4;

    private final AppCompatTextView[][] tiles = new AppCompatTextView[SIZE][SIZE];

    public PuzzleBoardView(Context context) {
        super(context);

        setRowCount(SIZE);
        setColumnCount(SIZE);

        initGrid();
    }

    private void initGrid() {

        int tileSize = dpToPx(80);
        int margin = dpToPx(3);

        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {

                AppCompatTextView tile = new AppCompatTextView(getContext());

                tile.setGravity(Gravity.CENTER);

                tile.setTextSize(TypedValue.COMPLEX_UNIT_SP, 36);

                tile.setBackgroundResource(R.drawable.tile_orange);

                GridLayout.LayoutParams params =
                        new GridLayout.LayoutParams();

                params.width = tileSize;
                params.height = tileSize;

                params.rowSpec = GridLayout.spec(row);
                params.columnSpec = GridLayout.spec(col);

                params.setMargins(
                        margin,
                        margin,
                        margin,
                        margin
                );

                tile.setLayoutParams(params);

                tiles[row][col] = tile;

                addView(tile);
            }
        }
    }

    public void render(int[][] grid) {

        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {

                int value = grid[row][col];

                AppCompatTextView tile = tiles[row][col];

                if (value == 0) {

                    tile.setText("");
                    tile.setBackgroundColor(Color.TRANSPARENT);

                } else {

                    tile.setText(String.valueOf(value));
                    tile.setBackgroundResource(R.drawable.tile_orange);
                }
            }
        }
    }

    private int dpToPx(int dp) {
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dp,
                getResources().getDisplayMetrics()
        );
    }
}
