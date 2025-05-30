package com.example.flo

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import java.util.Timer
import java.util.TimerTask
import android.os.Handler
import android.os.Looper

class MusicService : Service() {

    private var mediaPlayer: MediaPlayer? = null
    private var currentMusic: String? = null
    private var currentIndex = 0
    private lateinit var playlist: List<Song>
    private var isRepeat = false

    override fun onCreate() {
        super.onCreate()
        // 앱 전체에서 사용할 곡 리스트를 DB에서 불러오기
        val songDB = SongDatabase.getInstance(this)!!
        playlist = songDB.songDao().getSongs()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val command = intent?.getStringExtra("command")
        val musicName = intent?.getStringExtra("music")
        isRepeat = intent?.getBooleanExtra("isRepeat", false) ?: false

        when (command) {
            "play" -> {
                val newIndex = playlist.indexOfFirst { it.music == musicName }.takeIf { it >= 0 } ?: 0

                if (mediaPlayer != null && currentMusic == musicName) {
                    // 같은 곡이라면 다시 생성하지 않고 resume만
                    mediaPlayer?.start()
                    startTimer()
                } else {
                    currentIndex = newIndex
                    play(playlist[currentIndex]) // 이건 새로 시작할 때만
                }
            }

            "pause" -> {
                mediaPlayer?.pause()
            }

            "next" -> {
                currentIndex = (currentIndex + 1) % playlist.size
                play(playlist[currentIndex])
            }

            "prev" -> {
                currentIndex = if (currentIndex - 1 < 0) playlist.lastIndex else currentIndex - 1
                play(playlist[currentIndex])
            }
        }

        return START_STICKY
    }

    private fun play(song: Song) {
        if (song.music != currentMusic) {
            currentMusic = song.music
            mediaPlayer?.release()
            val resId = resources.getIdentifier(song.music, "raw", packageName)
            mediaPlayer = MediaPlayer.create(this, resId)
        }
        mediaPlayer?.seekTo(song.second * 1000)  // 진행 시간 복원
        mediaPlayer?.start()
        startTimer()

        //main에서의 재생여부 체크
        getSharedPreferences("song", MODE_PRIVATE).edit()
            .putBoolean("isPlaying", true)
            .apply()

        // ✅ 반복 재생 처리
        mediaPlayer?.setOnCompletionListener {
            if (isRepeat) {
                mediaPlayer?.seekTo(0)
                mediaPlayer?.start()
            } else {
                stopSelf()
            }
        }
    }

    private val handler = Handler(Looper.getMainLooper())
    private val timerRunnable = object : Runnable {
        override fun run() {
            val currentPosition = mediaPlayer?.currentPosition ?: 0
            val intent = Intent("TIMER_TICK")
            intent.putExtra("currentPosition", currentPosition)
            sendBroadcast(intent)

            handler.postDelayed(this, 1000)
        }
    }

    private fun startTimer() {
        stopTimer()
        handler.post(timerRunnable)
    }

    private fun stopTimer() {
        handler.removeCallbacks(timerRunnable)
    }

    override fun onDestroy() {
        mediaPlayer?.release()
        mediaPlayer = null
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? = null
}
