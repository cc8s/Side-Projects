package com.example.mygame;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import com.bumptech.glide.Glide;

public class MainActivity extends AppCompatActivity {

    private CustomView customView;
    private TextView tvStage, tvHighScore, tvTimer;
    private ImageView ivBackground;

    // 아이템 UI 요소
    private ImageView ivItemHint, ivItemTimePlus, ivItemTimeFreeze;
    private TextView tvItemHintCount, tvItemTimePlusCount, tvItemTimeFreezeCount;

    // 아이템 개수 변수
    private int hintCount, timePlusCount, timeFreezeCount;
    private SharedPreferences itemPrefs;
    private static final String PREFS_NAME = "ItemPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        itemPrefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        initViews();
        loadItemCounts();
        setupItemClickListeners();

        updateHighScoreText();
        loadBackgroundTheme();
    }

    @Override
    protected void onStart() {
        super.onStart();
        // 게임 화면에 진입하면 메뉴 BGM을 정지
        Intent musicIntent = new Intent(this, MusicService.class);
        musicIntent.setAction("STOP");
        startService(musicIntent);
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (isFinishing()) {
            // 메뉴 BGM을 다시 시작
            Intent musicIntent = new Intent(this, MusicService.class);
            musicIntent.setAction("PLAY");
            startService(musicIntent);
        }
    }

    private void initViews() {
        customView = findViewById(R.id.custom_view);
        tvStage = findViewById(R.id.tv_stage);
        tvHighScore = findViewById(R.id.tv_highscore);
        ivBackground = findViewById(R.id.iv_background);
        tvTimer = findViewById(R.id.tv_timer);

        ivItemHint = findViewById(R.id.iv_item_hint);
        ivItemTimePlus = findViewById(R.id.iv_item_time_plus);
        ivItemTimeFreeze = findViewById(R.id.iv_item_time_freeze);

        tvItemHintCount = findViewById(R.id.tv_item_hint_count);
        tvItemTimePlusCount = findViewById(R.id.tv_item_time_plus_count);
        tvItemTimeFreezeCount = findViewById(R.id.tv_item_time_freeze_count);
    }

    private void setupItemClickListeners() {
        ivItemHint.setOnClickListener(v -> useItem("hint"));
        ivItemTimePlus.setOnClickListener(v -> useItem("time_plus"));
        ivItemTimeFreeze.setOnClickListener(v -> useItem("time_freeze"));
    }

    private void useItem(String itemName) {
        if (customView.isGamePhase()) {
            customView.onItemUsed();
            switch (itemName) {
                case "hint":
                    if (hintCount > 0) {
                        customView.useHintItem();
                        hintCount--;
                        saveItemCounts();
                        updateItemCountUI();
                    } else {
                        Toast.makeText(this, "힌트 아이템이 없습니다.", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case "time_plus":
                    if (timePlusCount > 0) {
                        customView.useTimePlusItem();
                        timePlusCount--;
                        saveItemCounts();
                        updateItemCountUI();
                    } else {
                        Toast.makeText(this, "시간 추가 아이템이 없습니다.", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case "time_freeze":
                    if (timeFreezeCount > 0) {
                        customView.useTimeFreezeItem();
                        timeFreezeCount--;
                        saveItemCounts();
                        updateItemCountUI();
                    } else {
                        Toast.makeText(this, "시간 정지 아이템이 없습니다.", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        } else {
            Toast.makeText(this, "지금은 아이템을 사용할 수 없습니다.", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadItemCounts() {
        if (!itemPrefs.contains("hint_count")) {
            SharedPreferences.Editor editor = itemPrefs.edit();
            editor.putInt("hint_count", 1);
            editor.putInt("time_plus_count", 1);
            editor.putInt("time_freeze_count", 1);
            editor.apply();
        }

        hintCount = itemPrefs.getInt("hint_count", 1);
        timePlusCount = itemPrefs.getInt("time_plus_count", 1);
        timeFreezeCount = itemPrefs.getInt("time_freeze_count", 1);
        updateItemCountUI();
    }

    private void saveItemCounts() {
        SharedPreferences.Editor editor = itemPrefs.edit();
        editor.putInt("hint_count", hintCount);
        editor.putInt("time_plus_count", timePlusCount);
        editor.putInt("time_freeze_count", timeFreezeCount);
        editor.apply();
    }

    private void updateItemCountUI() {
        tvItemHintCount.setText("x " + hintCount);
        tvItemTimePlusCount.setText("x " + timePlusCount);
        tvItemTimeFreezeCount.setText("x " + timeFreezeCount);
    }

    public void updateTimerText(String text) {
        tvTimer.setText(text);
    }

    public void setTimerVisibility(boolean visible) {
        tvTimer.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    public void updateStageText(String text) {
        tvStage.setText(text);
    }

    public void updateHighScoreText() {
        SharedPreferences prefs = getSharedPreferences("GamePrefs", Context.MODE_PRIVATE);
        int highScore = prefs.getInt("highScore", 0);
        tvHighScore.setText("High Score: " + highScore);
    }

    public void addItems(String itemName, int count) {
        if (itemName.equals("time_plus")) {
            timePlusCount += count;
            saveItemCounts();
            updateItemCountUI();
        }
    }

    private void loadBackgroundTheme() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String theme = prefs.getString("background_theme", "default");
        int resourceId;
        switch (theme) {
            case "space":
                resourceId = R.drawable.bg_space;
                break;
            case "forest":
                resourceId = R.drawable.bg_forest;
                break;
            default:
                resourceId = R.drawable.bg_default;
                break;
        }
        Glide.with(this).asGif().load(resourceId).centerCrop().into(ivBackground);
    }
}
