package com.example.flo

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.flo.databinding.ActivitySongBinding
import com.google.gson.Gson

class SongActivity : AppCompatActivity() {

    //전역 변수
    lateinit var binding : ActivitySongBinding
    lateinit var song: Song
    lateinit var timer: Timer
    private var mediaPlayer: MediaPlayer? = null
    private var gson: Gson = Gson()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySongBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initSong()
        setPlayer(song)

        binding.songDownIb.setOnClickListener{
            finish()
        }
        //재생 일시정지 전환
        binding.songMiniplayerIv.setOnClickListener {
            setPlayerStatus(true)
        }
        binding.songPauseIv.setOnClickListener {
            setPlayerStatus(false)
        }
        //반복 비반복 전환
        binding.songRepeatIv.setOnClickListener {
            setRepeatStatus(false)
        }
        binding.songRepeatActiveIv.setOnClickListener {
            setRepeatStatus(true)
        }
        //셔플 비셔플 전환
        binding.songRandomIv.setOnClickListener {
            setRandomStatus(false)
        }
        binding.songRandomActiveIv.setOnClickListener {
            setRandomStatus(true)
        }
    }

    private fun initSong(){
        val sharedPreferences = getSharedPreferences("song", MODE_PRIVATE)
        val songJason = sharedPreferences.getString("songData", null)
        song = if (songJason != null){
            gson.fromJson(songJason, Song::class.java)
        } else{
            Song(
                intent.getStringExtra("title")!!,
                intent.getStringExtra("singer")!!,
                intent.getIntExtra("second", 0),
                intent.getIntExtra("playTime", 0),
                intent.getBooleanExtra("isPlaying", false),
                intent.getStringExtra("music")!!
            )
        }
        startTimer()
    }

    private fun setPlayer(song: Song){
        binding.songMusicTitleTv.text = intent.getStringExtra("title")!!
        binding.songSingerNameTv.text = intent.getStringExtra("singer")!!
        song.coverImg?.let {
            binding.songAlbumIv.setImageResource(it)
        }
        //UI초기값 강제 갱신(피드백 수정)
        binding.songStartTimeTv.text = String.format("%02d:%02d", song.second / 60, song.second % 60)
        binding.songEndTimeTv.text = String.format("%02d:%02d", song.playTime / 60, song.playTime % 60)
        binding.songProgressSb.progress = ((song.second.toFloat() / song.playTime) * 100).toInt()

        val music = resources.getIdentifier(song.music, "raw", this.packageName)
        mediaPlayer = MediaPlayer.create(this, music)

        //저장된 위치로 이동
        mediaPlayer?.seekTo(song.second * 1000)

        setPlayerStatus(song.isPlaying)

        //재생완료 리스너
        mediaPlayer?.setOnCompletionListener {
            if (binding.songRepeatActiveIv.visibility == View.VISIBLE){
                //반복재생 상태일때
                mediaPlayer?.seekTo(0)
                mediaPlayer?.start()
                timer.interrupt()
                startTimer()
            }
            else{
                //반복재생 상태가 아닐때
                setPlayerStatus(false)
                timer.interrupt()
                song.second = 0
                binding.songStartTimeTv.text = "00:00"
                binding.songProgressSb.progress = 0
            }
        }
    }

    //재생 일시정지 전환
    private fun setPlayerStatus(isplaying : Boolean){
        song.isPlaying = isplaying
        timer.isplaying = isplaying

        if(isplaying){
            binding.songMiniplayerIv.visibility = View.GONE
            binding.songPauseIv.visibility = View.VISIBLE
            mediaPlayer?.start()

            //타이머 다시 시작
            if (!timer.isAlive){
                startTimer()
            }
        }
        else{
            binding.songMiniplayerIv.visibility = View.VISIBLE
            binding.songPauseIv.visibility = View.GONE
            if(mediaPlayer?.isPlaying == true){
                mediaPlayer?.pause()
            }
        }
    }
    //반복 비반복 전환
    fun setRepeatStatus(notrepeating : Boolean){
        if(notrepeating){
            binding.songRepeatIv.visibility = View.VISIBLE
            binding.songRepeatActiveIv.visibility = View.GONE
        }
        else{
            binding.songRepeatIv.visibility = View.GONE
            binding.songRepeatActiveIv.visibility = View.VISIBLE
        }
    }
    //셔플 비셔플 전환
    private fun setRandomStatus(notrandom : Boolean){
        if(notrandom){
            binding.songRandomIv.visibility = View.VISIBLE
            binding.songRandomActiveIv.visibility = View.GONE
        }
        else{
            binding.songRandomIv.visibility = View.GONE
            binding.songRandomActiveIv.visibility = View.VISIBLE
        }
    }

    private fun startTimer(){
        if (::timer.isInitialized && timer.isAlive){
            timer.interrupt()
        }
        timer = Timer(song.playTime,song.isPlaying, song.second)
        timer.start()
    }

    inner class Timer(private val playTime: Int, var isplaying: Boolean = true, private var second: Int = 0): Thread() {

        private var mills: Float = (second * 1000).toFloat()

        override fun run() {
            super.run()
            try {
                while (true) {
                    if (second >= playTime) break

                    sleep(50) // ← break보다 먼저 실행

                    if (isplaying) {
                        mills += 50
                        if (mills % 1000 == 0f) {
                            second++
                        }
                    }

                    runOnUiThread {
                        binding.songProgressSb.progress = ((mills / playTime) * 100).toInt()
                        binding.songStartTimeTv.text = String.format(
                            "%02d:%02d",
                            (mills / 1000).toInt() / 60,
                            (mills / 1000).toInt() % 60
                        )
                    }
                }
            } catch (e: InterruptedException) {
                Log.d("Song", "쓰레드가 죽었습니다. ${e.message}")
            }
        }
    }

    // 사용자가 포커스를 잃었을 때 음악이 중지
    override fun onPause() {
        super.onPause()
        setPlayerStatus(false)
        song.second = ((binding.songProgressSb.progress * song.playTime)/100)/1000
        val sharedPreferences = getSharedPreferences("song", MODE_PRIVATE)
        val editor = sharedPreferences.edit() //에디터
        val songJson = gson.toJson(song)
        editor.putString("songData", songJson)

        editor.apply()
    }

    override fun onDestroy() {
        super.onDestroy()
        timer.interrupt()
        mediaPlayer?.release()//미디어 플레이어가 갖고 있던 리소스 해제
        mediaPlayer = null// 미디어 플레이어 해제
    }
}