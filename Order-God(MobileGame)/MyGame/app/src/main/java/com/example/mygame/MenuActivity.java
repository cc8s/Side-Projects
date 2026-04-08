package com.example.mygame;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Button btnStart = findViewById(R.id.btn_start);
        Button btnSettings = findViewById(R.id.btn_settings);
        Button btnExit = findViewById(R.id.btn_exit);

        btnStart.setOnClickListener(v -> {
            startActivity(new Intent(MenuActivity.this, MainActivity.class));
        });

        btnSettings.setOnClickListener(v -> {
            startActivity(new Intent(MenuActivity.this, SettingsActivity.class));
        });

        btnExit.setOnClickListener(v -> {
            // 앱 종료 시 서비스도 함께 종료
            stopService(new Intent(this, MusicService.class));
            finishAffinity();
        });
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
    protected void onResume() {
        super.onResume();
        // 메뉴 화면으로 돌아올 때마다 메뉴 BGM 재생
        Intent musicIntent = new Intent(this, MusicService.class);
        musicIntent.setAction("PLAY");
        startService(musicIntent);
    }
}
