package com.chous.puzzle15;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

public class MainActivity extends AppCompatActivity {

    private GameEngine engine;
    private PuzzleBoardView boardView;
    private TextView movesView;
    private GestureDetector gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        engine = new GameEngine();

        ConstraintLayout root = new ConstraintLayout(this);
        root.setId(ConstraintLayout.generateViewId());

        /*
         * Счётчик ходов
         */
        movesView = new TextView(this);
        movesView.setId(ConstraintLayout.generateViewId());
        movesView.setTextSize(24);

        /*
         * Игровое поле
         */
        boardView = new PuzzleBoardView(this);
        boardView.setId(ConstraintLayout.generateViewId());

        root.addView(movesView);
        root.addView(boardView);

        ConstraintSet constraints = new ConstraintSet();
        constraints.clone(root);

        /*
         * movesView:
         * сверху и по центру
         */
        constraints.connect(
                movesView.getId(),
                ConstraintSet.TOP,
                ConstraintSet.PARENT_ID,
                ConstraintSet.TOP,
                dpToPx(280)
        );

        constraints.connect(
                movesView.getId(),
                ConstraintSet.BOTTOM,
                boardView.getId(),
                ConstraintSet.TOP
        );

        constraints.connect(
                movesView.getId(),
                ConstraintSet.START,
                ConstraintSet.PARENT_ID,
                ConstraintSet.START
        );

        constraints.connect(
                movesView.getId(),
                ConstraintSet.END,
                ConstraintSet.PARENT_ID,
                ConstraintSet.END
        );

        /*
         * boardView:
         * по центру экрана
         */
        constraints.connect(
                boardView.getId(),
                ConstraintSet.TOP,
                ConstraintSet.PARENT_ID,
                ConstraintSet.TOP,
                dpToPx(160)
        );

        constraints.connect(
                boardView.getId(),
                ConstraintSet.BOTTOM,
                ConstraintSet.PARENT_ID,
                ConstraintSet.BOTTOM
        );

        constraints.connect(
                boardView.getId(),
                ConstraintSet.START,
                ConstraintSet.PARENT_ID,
                ConstraintSet.START
        );

        constraints.connect(
                boardView.getId(),
                ConstraintSet.END,
                ConstraintSet.PARENT_ID,
                ConstraintSet.END
        );

        constraints.setHorizontalBias(boardView.getId(), 0.5f);
        constraints.setVerticalBias(boardView.getId(), 0.5f);

        constraints.applyTo(root);

        setContentView(root);

        initGestureDetector();

        boardView.setOnTouchListener((v, event) -> {
            gestureDetector.onTouchEvent(event);
            return true;
        });

        updateUi();
    }

    private void initGestureDetector() {
        gestureDetector = new GestureDetector(
                this,
                new GestureDetector.SimpleOnGestureListener() {

                    private static final int SWIPE_THRESHOLD = 100;
                    private static final int SWIPE_VELOCITY_THRESHOLD = 100;

                    @Override
                    public boolean onFling(MotionEvent e1,
                                           MotionEvent e2,
                                           float velocityX,
                                           float velocityY) {

                        if (e1 == null || e2 == null) {
                            return false;
                        }

                        float diffX = e2.getX() - e1.getX();
                        float diffY = e2.getY() - e1.getY();

                        Direction direction = null;

                        if (Math.abs(diffX) > Math.abs(diffY)) {

                            if (Math.abs(diffX) > SWIPE_THRESHOLD
                                    && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {

                                direction = diffX > 0
                                        ? Direction.RIGHT
                                        : Direction.LEFT;
                            }

                        } else {

                            if (Math.abs(diffY) > SWIPE_THRESHOLD
                                    && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {

                                direction = diffY > 0
                                        ? Direction.DOWN
                                        : Direction.UP;
                            }
                        }

                        if (direction != null) {
                            onMove(direction);
                            return true;
                        }

                        return false;
                    }
                }
        );
    }

    private void onMove(Direction direction) {

        boolean moved = engine.move(direction);

        if (!moved) {
            return;
        }

        updateUi();

        if (engine.isWin()) {
            showWinDialog();
        }
    }

    private void updateUi() {
        boardView.render(engine.getGrid());
        movesView.setText("Ходы: " + engine.getMoves());
    }

    private int dpToPx(int dp) {
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dp,
                getResources().getDisplayMetrics()
        );
    }

    private void showWinDialog() {

        new AlertDialog.Builder(this)
                .setTitle("Поздравляем!")
                .setMessage(
                        "Вы собрали пятнашки.\n" +
                                "Количество ходов: " + engine.getMoves()
                )
                .setCancelable(false)
                .setPositiveButton("Новая игра", (dialog, which) -> {
                    engine.newGame();
                    updateUi();
                })
                .show();
    }
}
