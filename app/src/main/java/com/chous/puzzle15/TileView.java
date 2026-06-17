package com.chous.puzzle15;

import android.content.Context;
import android.util.TypedValue;
import android.view.Gravity;

import androidx.appcompat.widget.AppCompatTextView;

public class TileView extends AppCompatTextView {

    private final int value;

    public TileView(Context context, int value) {
        super(context);

        this.value = value;

        init();
    }

    private void init() {
        setText(String.valueOf(value));

        setGravity(Gravity.CENTER);

        setTextSize(
                TypedValue.COMPLEX_UNIT_SP,
                36
        );

        setBackgroundResource(R.drawable.tile_orange);
    }

    public int getValue() {
        return value;
    }
}