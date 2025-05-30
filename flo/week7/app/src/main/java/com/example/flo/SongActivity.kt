// ✅ SongActivity.kt (Service 연동 + 셔플/반복 자동재생 포함)

package com.example.flo

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import com.example.flo.databinding.ActivitySongBinding
import com.google.android.material.snackbar.Snackbar

class SongActivity : AppCompatActivity() {

    lateinit var binding : ActivitySongBinding
//    lateinit var timer: Timer
    val songs = arrayListOf<Song>()
    lateinit var songDB: SongDatabase
    var nowPos = 0
    private var isRepeat = false
    private var isShuffle = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySongBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initPlaylist()
        initSong()
        initClickListener()
    }

    override fun onPause() {
        super.onPause()
        songs[nowPos].second = ((binding.songProgressSb.progress * songs[nowPos].playTime)/100)/1000

        // ❗ 현재 상태 그대로 유지
        val isPlaying = songs[nowPos].isPlaying

        getSharedPreferences("song", MODE_PRIVATE).edit().apply {
            putInt("songId", songs[nowPos].id)
            putBoolean("isPlaying", isPlaying) // ✅ 실제 상태 저장
            apply()
        }
//        if (::timer.isInitialized) {
//            timer.interrupt()
//        }
    }

    private fun initPlaylist(){
        songDB = SongDatabase.getInstance(this)!!
        songs.addAll(songDB.songDao().getSongs())
    }

    private fun initClickListener(){
        binding.songDownIb.setOnClickListener { finish() }

        //재생
        binding.songMiniplayerIv.setOnClickListener {
            getSharedPreferences("song", MODE_PRIVATE).edit()
                .putBoolean("isPlaying", true)
                .apply()

            setPlayerStatus(true)
            sendServiceCommand("play", songs[nowPos].music)
        }
        //일시정지
        binding.songPauseIv.setOnClickListener {
            getSharedPreferences("song", MODE_PRIVATE).edit()
                .putBoolean("isPlaying", false)
                .apply()

            setPlayerStatus(false)
            sendServiceCommand("pause", songs[nowPos].music)
        }

        binding.songNextIv.setOnClickListener {
            if (isShuffle) {
                nowPos = (songs.indices).random()
            } else {
                moveSong(+1)
            }
            songs[nowPos].isPlaying = true  // ✅ 현재 곡을 재생 중으로 설정
            setPlayerStatus(true)           // ✅ UI 상태도 바꾸기
            sendServiceCommand("play", songs[nowPos].music)

            getSharedPreferences("song", MODE_PRIVATE).edit().apply {
                putInt("songId", songs[nowPos].id)
                putBoolean("isPlaying", true)
                apply()
            }
        }

        binding.songPreviousIv.setOnClickListener {
            if (isShuffle) {
                nowPos = (songs.indices).random()
            } else {
                moveSong(-1)
            }
            songs[nowPos].isPlaying = true  // ✅ 현재 곡을 재생 중으로 설정
            setPlayerStatus(true)           // ✅ UI 상태도 바꾸기
            sendServiceCommand("play", songs[nowPos].music)

            getSharedPreferences("song", MODE_PRIVATE).edit().apply {
                putInt("songId", songs[nowPos].id)
                putBoolean("isPlaying", true)
                apply()
            }
        }

        binding.songLikeIv.setOnClickListener {
            setLike(songs[nowPos].isLike)
            val snackbar = Snackbar.make(
                binding.root,
                "좋아요가 ${if (songs[nowPos].isLike) "추가되었습니다." else "삭제되었습니다."}",
                Snackbar.LENGTH_SHORT
            )

            val snackbarView = snackbar.view  // 이걸 ViewGroup으로 취급
            val textView = snackbarView.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)

// 아이콘 크기 줄이기
            val iconRes = if (songs[nowPos].isLike) R.drawable.ic_my_like_on else R.drawable.ic_my_like_off
            val icon = ContextCompat.getDrawable(this, iconRes)

// 아이콘 크기를 텍스트에 맞게 조정 (예: 20dp)
            val size = (20 * resources.displayMetrics.density).toInt()
            icon?.setBounds(0, 0, size, size)

// 텍스트 옆에 아이콘 추가
            textView.setCompoundDrawables(icon, null, null, null)
            textView.compoundDrawablePadding = 12
            textView.textAlignment = View.TEXT_ALIGNMENT_CENTER

            snackbar.show()
        }

        binding.songRepeatIv.setOnClickListener {
            isRepeat = true

            updateRepeatUI()
        }

        binding.songRepeatActiveIv.setOnClickListener {
            isRepeat = false
            updateRepeatUI()
        }

        binding.songRandomIv.setOnClickListener {
            isShuffle = true
            updateShuffleUI()
        }

        binding.songRandomActiveIv.setOnClickListener {
            isShuffle = false
            updateShuffleUI()
        }
    }

    private fun updateRepeatUI() {
        binding.songRepeatIv.visibility = if (isRepeat) View.GONE else View.VISIBLE
        binding.songRepeatActiveIv.visibility = if (isRepeat) View.VISIBLE else View.GONE
    }

    private fun updateShuffleUI() {
        binding.songRandomIv.visibility = if (isShuffle) View.GONE else View.VISIBLE
        binding.songRandomActiveIv.visibility = if (isShuffle) View.VISIBLE else View.GONE
    }

    private fun initSong(){
        val spf = getSharedPreferences("song", MODE_PRIVATE)
        val songId = spf.getInt("songId", 0)
        val isPlaying = spf.getBoolean("isPlaying", false)

        nowPos = getPlayingSongPosition(songId)
        songs[nowPos].isPlaying = isPlaying

        setPlayerUI(songs[nowPos])

//        if (isPlaying) {
//            startTimer()
//        }
    }

    private fun getPlayingSongPosition(songId: Int): Int {
        return songs.indexOfFirst { it.id == songId }.takeIf { it >= 0 } ?: 0
    }

    private fun setPlayerUI(song: Song){
        binding.songMusicTitleTv.text = song.title
        binding.songSingerNameTv.text = song.singer
        binding.songStartTimeTv.text = String.format("%02d:%02d", song.second / 60, song.second % 60)
        binding.songEndTimeTv.text = String.format("%02d:%02d", song.playTime / 60, song.playTime % 60)
        binding.songAlbumIv.setImageResource(song.coverImg!!)
        binding.songProgressSb.progress = (song.second * 1000 / song.playTime)

        val likeIcon = if (song.isLike) R.drawable.ic_my_like_on else R.drawable.ic_my_like_off
        binding.songLikeIv.setImageResource(likeIcon)

        setPlayerStatus(song.isPlaying)
    }

    private fun setPlayerStatus(isPlaying: Boolean) {
        songs[nowPos].isPlaying = isPlaying

        // 안전하게 접근
//        if (::timer.isInitialized) {
//            timer.isplaying = isPlaying
//            if (!timer.isAlive) startTimer()
//        }

        binding.songMiniplayerIv.visibility = if (isPlaying) View.GONE else View.VISIBLE
        binding.songPauseIv.visibility = if (isPlaying) View.VISIBLE else View.GONE
    }

    private fun moveSong(direction: Int) {
        val newPos = nowPos + direction
        if (newPos in songs.indices) {
            nowPos = newPos
//            timer.interrupt()
//            startTimer()
            setPlayerUI(songs[nowPos])
        } else {
            Toast.makeText(this, if (direction > 0) "last song" else "first song", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setLike(isLike: Boolean) {
        songs[nowPos].isLike = !isLike
        songDB.songDao().updateIsLikeById(!isLike, songs[nowPos].id)
        val icon = if (!isLike) R.drawable.ic_my_like_on else R.drawable.ic_my_like_off
        binding.songLikeIv.setImageResource(icon)
    }

//    private fun startTimer() {
//        if (::timer.isInitialized && timer.isAlive) timer.interrupt()
//        timer = Timer(songs[nowPos].playTime, songs[nowPos].isPlaying, songs[nowPos].second)
//        timer.start()
//    }

    private fun sendServiceCommand(command: String, music: String? = null) {
        val intent = Intent(this, MusicService::class.java)
        intent.putExtra("command", command)
        music?.let { intent.putExtra("music", it) }
        intent.putExtra("isRepeat", isRepeat)
        startService(intent)
    }

//    inner class Timer(private val playTime: Int, var isplaying: Boolean = true, private var second: Int = 0): Thread() {
//        private var mills: Float = this.second * 1000f
//
//        override fun run() {
//            try {
//                while (second < playTime) {
//                    sleep(50)
//                    if (isplaying) {
//                        mills += 50
//                        if (mills % 1000 == 0f) second++
//                    }
//                    runOnUiThread {
//                        binding.songProgressSb.progress = ((mills / playTime * 1000f) * 100f).toInt()
//                        binding.songStartTimeTv.text = String.format("%02d:%02d", (mills / 1000).toInt() / 60, (mills / 1000).toInt() % 60)
//                    }
//                }
//            } catch (e: InterruptedException) {
//                Log.d("Song", "쓰레드가 죽었습니다: ${e.message}")
//            }
//        }
//    }

    private val timerReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val pos = intent?.getIntExtra("currentPosition", 0) ?: return
            binding.songStartTimeTv.text = String.format("%02d:%02d", pos / 1000 / 60, (pos / 1000) % 60)

            val totalDuration = songs[nowPos].playTime * 1000 // 초 → ms
            binding.songProgressSb.progress = (pos * 100 / totalDuration)
        }
    }

    override fun onStart() {
        super.onStart()
        registerReceiver(timerReceiver, IntentFilter("TIMER_TICK"))
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(timerReceiver)
    }
}