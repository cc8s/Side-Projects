package com.example.mygame;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.preference.PreferenceManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CustomView extends View {

    // --- 게임 상태 및 설정 변수 ---
    private static final int GRID_COLS = 4;
    private static final int GRID_ROWS = 6;
    private static final int TOTAL_CELLS = GRID_COLS * GRID_ROWS;
    private static final int STARTING_NUMBERS = 3;
    private static final int BASE_GAME_TIME_LIMIT = 15;
    private static final int URGENT_TIME_THRESHOLD = 5;
    private static final int FEVER_TIME_LIMIT = 7; // 피버 타임 제한 시간

    private int memorizeTimeSeconds;
    private SoundPool soundPool;
    private int correctSoundId, wrongSoundId, bombSoundId, coinSoundId;
    private MediaPlayer bgmPlayer;

    // --- 보너스 스테이지 관련 변수 ---
    private enum CellType { EMPTY, NUMBER, BOMB, BONUS_COIN }
    private boolean isBonusStage = false;
    private int consecutiveClears = 0; // 연속 클리어 횟수
    private int bonusCoinsAcquired = 0; // 획득한 코인 수

    private Cell[] grid = new Cell[TOTAL_CELLS];
    private Drawable[] numberDrawables = new Drawable[10];
    private Drawable bombDrawable, defaultDrawable, coinDrawable;

    private boolean isMemorizePhase = true, isGameOver = false;
    private boolean isHintActive = false, isTimeFrozen = false;
    private int timeToAdd = 0;
    private int currentStage = 1, numbersToFind, bombsToPlace, nextNumberToTap = 1;
    private int screenWidth, screenHeight, cellWidth, cellPadding;
    private Paint textPaint;
    private Context mContext;
    private BackgroundThread thread;

    private static class Cell {
        CellType type = CellType.EMPTY;
        int number = 0;
        boolean revealed = false;
        boolean collected = false; // 코인 획득 여부
    }

    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        init();
    }

    private void init() {
        loadSettings();
        loadResources();
        loadSounds();
        textPaint = new Paint();
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(50);
        textPaint.setTextAlign(Paint.Align.CENTER);
        setupStage();
    }

    private void loadSettings() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext);
        memorizeTimeSeconds = Integer.parseInt(prefs.getString("memorize_time", "3"));
    }

    private void loadResources() {
        numberDrawables[0] = getResources().getDrawable(R.drawable.num_0, null);
        numberDrawables[1] = getResources().getDrawable(R.drawable.num_1, null);
        numberDrawables[2] = getResources().getDrawable(R.drawable.num_2, null);
        numberDrawables[3] = getResources().getDrawable(R.drawable.num_3, null);
        numberDrawables[4] = getResources().getDrawable(R.drawable.num_4, null);
        numberDrawables[5] = getResources().getDrawable(R.drawable.num_5, null);
        numberDrawables[6] = getResources().getDrawable(R.drawable.num_6, null);
        numberDrawables[7] = getResources().getDrawable(R.drawable.num_7, null);
        numberDrawables[8] = getResources().getDrawable(R.drawable.num_8, null);
        numberDrawables[9] = getResources().getDrawable(R.drawable.num_9, null);
        bombDrawable = getResources().getDrawable(R.drawable.bomb, null);
        defaultDrawable = getResources().getDrawable(R.drawable.question, null);
        coinDrawable = getResources().getDrawable(R.drawable.coin, null);
    }

    private void loadSounds() {
        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();
        soundPool = new SoundPool.Builder()
                .setMaxStreams(5)
                .setAudioAttributes(audioAttributes)
                .build();
        correctSoundId = soundPool.load(mContext, R.raw.rightact_1, 1);
        wrongSoundId = soundPool.load(mContext, R.raw.wrong_1, 1);
        bombSoundId = soundPool.load(mContext, R.raw.bomb, 1);
        coinSoundId = soundPool.load(mContext, R.raw.coin_sound, 1);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.screenWidth = w;
        this.screenHeight = h;
        this.cellWidth = w / (GRID_COLS + 1);
        this.cellPadding = (w - (cellWidth * GRID_COLS)) / (GRID_COLS + 1);
    }

    private void setupStage() {
        isBonusStage = (consecutiveClears >= 3); // 3회 이상 연속 클리어 시 보너스 스테이지

        if (isBonusStage) {
            setupBonusStage();
        } else {
            setupNormalStage();
        }

        invalidate();
        stopBGM();
        if (thread != null) { thread.interrupt(); }
        thread = new BackgroundThread();
        thread.start();

        if (mContext instanceof MainActivity) {
            post(() -> {
                ((MainActivity) mContext).updateStageText(isBonusStage ? "BONUS STAGE!" : "Stage: " + currentStage);
                ((MainActivity) mContext).setTimerVisibility(true);
            });
        }
    }

    private void setupNormalStage() {
        isMemorizePhase = true;
        isGameOver = false;
        isHintActive = false;
        isTimeFrozen = false;
        timeToAdd = 0;
        nextNumberToTap = 1;
        numbersToFind = STARTING_NUMBERS + (currentStage - 1);
        if (numbersToFind > TOTAL_CELLS / 2) numbersToFind = TOTAL_CELLS / 2;
        bombsToPlace = currentStage;
        if (bombsToPlace > TOTAL_CELLS / 4) bombsToPlace = TOTAL_CELLS / 4;

        for (int i = 0; i < TOTAL_CELLS; i++) { grid[i] = new Cell(); }
        List<Integer> positions = new ArrayList<>();
        for (int i = 0; i < TOTAL_CELLS; i++) { positions.add(i); }
        Collections.shuffle(positions);
        for (int i = 0; i < numbersToFind; i++) { int pos = positions.remove(0); grid[pos].type = CellType.NUMBER; grid[pos].number = i + 1; }
        for (int i = 0; i < bombsToPlace; i++) { if (positions.isEmpty()) break; int pos = positions.remove(0); grid[pos].type = CellType.BOMB; }
    }

    private void setupBonusStage() {
        isMemorizePhase = false;
        isGameOver = false;
        bonusCoinsAcquired = 0;
        for (int i = 0; i < TOTAL_CELLS; i++) {
            grid[i] = new Cell();
            if (Math.random() < 0.7) {
                grid[i].type = CellType.BONUS_COIN;
            }
        }
        Toast.makeText(mContext, "FEVER TIME!", Toast.LENGTH_SHORT).show();
    }

    public void useHintItem() {
        if (isHintActive) return;
        isHintActive = true;
        invalidate();
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            isHintActive = false;
            invalidate();
        }, 1000);
    }

    public void useTimePlusItem() {
        timeToAdd += 5;
        Toast.makeText(mContext, "+5초!", Toast.LENGTH_SHORT).show();
    }

    public void useTimeFreezeItem() {
        if (isTimeFrozen) return;
        isTimeFrozen = true;
        Toast.makeText(mContext, "시간 정지! (3초)", Toast.LENGTH_SHORT).show();
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            isTimeFrozen = false;
            Toast.makeText(mContext, "시간 정지 해제", Toast.LENGTH_SHORT).show();
        }, 3000);
    }

    public void onItemUsed() {
        consecutiveClears = 0;
    }

    public boolean isGamePhase() {
        return !isMemorizePhase && !isGameOver;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.TRANSPARENT);
        if (screenWidth == 0) return;

        for (int i = 0; i < TOTAL_CELLS; i++) {
            int row = i / GRID_COLS;
            int col = i % GRID_COLS;
            int left = cellPadding + col * (cellWidth + cellPadding);
            int top = cellPadding * 4 + row * (cellWidth + cellPadding);
            int right = left + cellWidth;
            int bottom = top + cellWidth;
            Cell cell = grid[i];
            if (cell == null) continue;

            boolean shouldShow = isMemorizePhase || cell.revealed || isGameOver || isHintActive;
            Drawable toDraw = defaultDrawable;

            if (isBonusStage) {
                if(cell.type == CellType.BONUS_COIN && !cell.collected) {
                    toDraw = coinDrawable;
                }
            } else {
                if (shouldShow) {
                    switch (cell.type) {
                        case NUMBER:
                            if (cell.number > 0 && cell.number < numberDrawables.length)
                                toDraw = numberDrawables[cell.number];
                            break;
                        case BOMB:
                            toDraw = bombDrawable;
                            break;
                        case EMPTY:
                            break;
                    }
                }
            }

            if (toDraw != null) {
                toDraw.setBounds(left, top, right, bottom);
                toDraw.draw(canvas);
            }
        }
        if (isGameOver) {
            textPaint.setTextSize(100);
            textPaint.setColor(Color.RED);
            canvas.drawText("GAME OVER", screenWidth / 2f, screenHeight / 2f, textPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() != MotionEvent.ACTION_DOWN) return false;
        if (isHintActive) return true;
        if (isGameOver) return false;

        float x = event.getX();
        float y = event.getY();
        int col = -1, row = -1;
        for (int i = 0; i < GRID_COLS; i++) {
            int left = cellPadding + i * (cellWidth + cellPadding);
            if (x > left && x < left + cellWidth) {
                col = i;
                break;
            }
        }
        for (int i = 0; i < GRID_ROWS; i++) {
            int top = cellPadding * 4 + i * (cellWidth + cellPadding);
            if (y > top && y < top + cellWidth) {
                row = i;
                break;
            }
        }

        if (col != -1 && row != -1) {
            int index = row * GRID_COLS + col;
            handleTouch(index);
        }
        return true;
    }

    private void handleTouch(int index) {
        if (isBonusStage) {
            handleBonusTouch(index);
        } else if (!isMemorizePhase) {
            handleNormalTouch(index);
        }
    }

    private void handleNormalTouch(int index) {
        Cell touchedCell = grid[index];
        if (touchedCell.revealed) return;
        touchedCell.revealed = true;
        switch (touchedCell.type) {
            case BOMB:
            case EMPTY:
                playSound(wrongSoundId);
                gameOver(false);
                break;
            case NUMBER:
                if (touchedCell.number == nextNumberToTap) {
                    playSound(correctSoundId);
                    nextNumberToTap++;
                    if (nextNumberToTap > numbersToFind) gameWon();
                } else {
                    playSound(wrongSoundId);
                    gameOver(false);
                }
                break;
        }
        invalidate();
    }

    private void handleBonusTouch(int index) {
        Cell touchedCell = grid[index];
        if (touchedCell.type == CellType.BONUS_COIN && !touchedCell.collected) {
            touchedCell.collected = true;
            bonusCoinsAcquired++;
            playSound(coinSoundId);
            invalidate();
        }
    }

    private void gameWon() {
        if (thread != null) thread.interrupt();
        stopBGM();

        if (!isBonusStage) {
            consecutiveClears++;
        }
        updateHighScore();
        Toast.makeText(mContext, "Stage " + currentStage + " Clear!", Toast.LENGTH_SHORT).show();
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            if (!isBonusStage) currentStage++;
            setupStage();
        }, 1000);
    }

    private void endBonusStage() {
        isBonusStage = false;
        consecutiveClears = 0;
        stopBGM();
        Toast.makeText(mContext, "코인 " + bonusCoinsAcquired + "개 획득!", Toast.LENGTH_LONG).show();
        if (mContext instanceof MainActivity) {
            ((MainActivity) mContext).addItems("time_plus", bonusCoinsAcquired);
        }

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            currentStage++;
            setupStage();
        }, 1000);
    }

    private void gameOver(boolean success) {
        if (isGameOver) return;
        isGameOver = true;
        consecutiveClears = 0;
        if (thread != null) thread.interrupt();
        stopBGM();
        if (mContext instanceof MainActivity) {
            ((MainActivity) mContext).setTimerVisibility(false);
        }
        updateHighScore();
        invalidate();
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            Intent intent = new Intent(mContext, ResultActivity.class);
            intent.putExtra("score", currentStage);
            intent.putExtra("success", success);
            mContext.startActivity(intent);
            if (mContext instanceof Activity) {
                ((Activity) mContext).finish();
            }
        }, 2000);
    }

    private void playSound(int soundId) {
        if (soundPool != null) {
            soundPool.play(soundId, 1, 1, 0, 0, 1);
        }
    }

    private void playBGM(int resourceId) {
        stopBGM();
        bgmPlayer = MediaPlayer.create(mContext, resourceId);
        if (bgmPlayer != null) {
            bgmPlayer.setLooping(true);
            bgmPlayer.start();
        }
    }

    private void stopBGM() {
        if (bgmPlayer != null) {
            if (bgmPlayer.isPlaying()) {
                bgmPlayer.stop();
            }
            bgmPlayer.release();
            bgmPlayer = null;
        }
    }

    private void updateHighScore() {
        SharedPreferences prefs = mContext.getSharedPreferences("GamePrefs", Context.MODE_PRIVATE);
        int highScore = prefs.getInt("highScore", 0);
        if (currentStage > highScore) {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt("highScore", currentStage);
            editor.apply();
        }
    }

    class BackgroundThread extends Thread {
        public void run() {
            if (isBonusStage) {
                runBonusTimer();
            } else {
                runNormalTimer();
            }
        }

        private void runNormalTimer() {
            int remainingTime = memorizeTimeSeconds;
            while (remainingTime >= 0 && !isInterrupted()) {
                final int time = remainingTime;
                post(() -> updateTimerUI("암기: " + time));
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    return;
                }
                remainingTime--;
            }
            if (isInterrupted()) return;

            post(() -> {
                isMemorizePhase = false;
                Toast.makeText(mContext, "START!", Toast.LENGTH_SHORT).show();
                playBGM(R.raw.bgm_normal);
                invalidate();
            });

            int gameTimeLimit = Math.max(5, BASE_GAME_TIME_LIMIT - currentStage);
            remainingTime = gameTimeLimit;
            boolean isUrgent = false;

            while (remainingTime >= 0 && !isInterrupted()) {
                if (isTimeFrozen) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        return;
                    }
                    continue;
                }
                if (timeToAdd > 0) {
                    remainingTime += timeToAdd;
                    timeToAdd = 0;
                }
                if (remainingTime <= URGENT_TIME_THRESHOLD && !isUrgent) {
                    isUrgent = true;
                    post(() -> playBGM(R.raw.bgm_urgent));
                }

                final int time = remainingTime;
                post(() -> updateTimerUI("제한시간: " + time));
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    return;
                }
                remainingTime--;
            }
            if (isInterrupted()) return;

            post(() -> {
                Toast.makeText(mContext, "시간 초과!", Toast.LENGTH_SHORT).show();
                gameOver(false);
            });
        }

        private void runBonusTimer() {
            post(() -> playBGM(R.raw.bgm_fever));
            int remainingTime = FEVER_TIME_LIMIT;
            while (remainingTime >= 0 && !isInterrupted()) {
                final int time = remainingTime;
                post(() -> updateTimerUI("FEVER: " + time));
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    return;
                }
                remainingTime--;
            }
            if (isInterrupted()) return;
            post(CustomView.this::endBonusStage);
        }

        private void updateTimerUI(String text) {
            if (mContext instanceof MainActivity) {
                ((MainActivity) mContext).updateTimerText(text);
            }
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (thread != null) {
            thread.interrupt();
        }
        if (soundPool != null) {
            soundPool.release();
            soundPool = null;
        }
        stopBGM();
    }
}
