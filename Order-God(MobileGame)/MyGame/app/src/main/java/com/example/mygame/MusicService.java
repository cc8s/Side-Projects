package com.example.mygame;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import androidx.annotation.Nullable;

public class MusicService extends Service {

    private MediaPlayer menuBgmPlayer;

    @Override
    public void onCreate() {
        super.onCreate();
        menuBgmPlayer = MediaPlayer.create(this, R.raw.bgm_menu);
        menuBgmPlayer.setLooping(true); // 반복 재생 설정
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String action = intent.getAction();

        if (action != null) {
            switch (action) {
                case "PLAY":
                    if (menuBgmPlayer != null && !menuBgmPlayer.isPlaying()) {
                        menuBgmPlayer.start();
                    }
                    break;
                case "STOP":
                    if (menuBgmPlayer != null && menuBgmPlayer.isPlaying()) {
                        menuBgmPlayer.pause(); // 일시 정지
                    }
                    break;
            }
        }
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (menuBgmPlayer != null) {
            menuBgmPlayer.stop();
            menuBgmPlayer.release();
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
