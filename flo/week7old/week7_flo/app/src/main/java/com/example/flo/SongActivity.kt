package com.example.flo

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.flo.databinding.ActivitySongBinding
import com.google.gson.Gson

class SongActivity : AppCompatActivity() {

    //전역 변수
    lateinit var binding : ActivitySongBinding
    lateinit var timer: Timer
    private var mediaPlayer: MediaPlayer? = null
    private var gson: Gson = Gson()

    val songs = arrayListOf<Song>()
    lateinit var songDB: SongDatabase
    var nowPos = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySongBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initPlayList()
        initSong()
        initClickListener()
    }



    private fun setPlayer(song: Song){
        binding.songMusicTitleTv.text = song.title
        binding.songSingerNameTv.text = song.singer
        binding.songAlbumIv.setImageResource(song.coverImg!!)
        //UI초기값 강제 갱신(피드백 수정)
        binding.songStartTimeTv.text = String.format("%02d:%02d", song.second / 60, song.second % 60)
        binding.songEndTimeTv.text = String.format("%02d:%02d", song.playTime / 60, song.playTime % 60)
        binding.songProgressSb.progress = ((song.second.toFloat() / song.playTime) * 100).toInt()

        val music = resources.getIdentifier(song.music, "raw", this.packageName)
        mediaPlayer = MediaPlayer.create(this, music)

        if (song.isLike){
            binding.songLikeIv.setImageResource(R.drawable.ic_my_like_on)
        } else{
            binding.songLikeIv.setImageResource(R.drawable.ic_my_like_off)
        }

        //저장된 위치로 이동
        mediaPlayer?.seekTo(song.second * 1000)

        //타이머 초기화
        startTimer()

        setPlayerStatus(song.isPlaying)

        //재생완료 리스너
        mediaPlayer?.setOnCompletionListener {
            if (binding.songRepeatActiveIv.visibility == View.VISIBLE){
                //반복재생 상태일때
                mediaPlayer?.seekTo(0)
                if(song.isPlaying) {
                    mediaPlayer?.start()
                }
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
        songs[nowPos].isPlaying = isplaying
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
        timer = Timer(songs[nowPos].playTime,songs[nowPos].isPlaying, songs[nowPos].second)
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

    private fun initPlayList(){
        songDB = SongDatabase.getInstance(this)!!
        songs.addAll(songDB.songDao().getSongs())
    }

    private fun initClickListener(){
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
        //곡 전환
        binding.songNextIv.setOnClickListener{
            moveSong(+1)
        }
        binding.songPreviousIv.setOnClickListener{
            moveSong(-1)
        }
        //좋아요버튼
        binding.songLikeIv.setOnClickListener{
            setLike(songs[nowPos].isLike)
        }
    }

    private fun initSong() {
        val spf = getSharedPreferences("song", MODE_PRIVATE)
        val songId = spf.getInt("songId", -1)
        // val songSecond = spf.getInt("songSecond", 0) // SongActivity가 새로 시작될 때 특정 지점으로 이동시키려면 주석 해제
        val shouldPlay = spf.getBoolean("wasPlaying", true) // 저장된 값이 없으면 기본적으로 재생, 필요에 따라 조정

        if (songId == -1) {
            Toast.makeText(this, "재생할 곡이 없습니다.", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        nowPos = getPlayingSongPosition(songId)
        // songs[nowPos].second = songSecond // 필요시 적용
        songs[nowPos].isPlaying = shouldPlay // 저장된 상태에 따라 재생 여부 설정

        setPlayer(songs[nowPos]) // song.isPlaying 값을 사용하여 재생/일시정지 결정
        // startTimer()는 setPlayer 내부의 setPlayerStatus를 통해 isPlaying이 true일 경우 암시적으로 호출됩니다.
    }

    private fun setLike(isLike: Boolean){
        songs[nowPos].isLike = !isLike
        songDB.songDao().updateIsLikeById(!isLike, songs[nowPos].id)

        if (!isLike){
            binding.songLikeIv.setImageResource(R.drawable.ic_my_like_on)
        } else{
            binding.songLikeIv.setImageResource(R.drawable.ic_my_like_off)
        }

    }

    private fun moveSong(direct: Int){
        if (nowPos + direct < 0){
            Toast.makeText(this, "first song", Toast.LENGTH_SHORT).show()
            return
        }
        if (nowPos + direct >= songs.size){
            Toast.makeText(this, "last song", Toast.LENGTH_SHORT).show()
            return
        }
        nowPos += direct

        timer.interrupt()
        startTimer()

        mediaPlayer?.release()//미디어 플레이어가 갖고 있던 리소스 해제
        mediaPlayer = null// 미디어 플레이어 해제

        setPlayer(songs[nowPos])

    }

    private fun getPlayingSongPosition(songId: Int): Int {
        for (i in 0 until songs.size) {
            if (songs[i].id == songId) {
                return i
            }
        }
        return 0
    }

    // 사용자가 포커스를 잃었을 때 음악이 중지
    override fun onPause() {
        super.onPause()

        // setPlayerStatus(false) 호출 전 상태 캡처
        val currentPositionMillis = mediaPlayer?.currentPosition ?: (songs[nowPos].second * 1000)
        val wasSongActuallyPlaying = songs[nowPos].isPlaying // onPause가 강제로 일시정지하기 전의 실제 재생 상태

        setPlayerStatus(false) // SongActivity의 노래와 타이머를 일시정지하고, songs[nowPos].isPlaying = false로 설정합니다.

        // 곡 객체의 second 업데이트. mediaPlayer.currentPosition 사용이 더 정확합니다.
        songs[nowPos].second = currentPositionMillis / 1000

        // SharedPreferences에 저장
        val sharedPreferences = getSharedPreferences("song", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt("songId", songs[nowPos].id)
        editor.putInt("songSecond", songs[nowPos].second)
        editor.putBoolean("wasPlaying", wasSongActuallyPlaying) // SongActivity 자체가 노래를 일시정지하기 전의 상태 저장
        editor.apply()

        // 이 위치를 더 영구적으로 저장하고 싶다면 데이터베이스에도 업데이트하는 것을 고려할 수 있습니다.
        // songDB.songDao().update(songs[nowPos])
    }

    override fun onDestroy() {
        super.onDestroy()
        timer.interrupt()
        mediaPlayer?.release()//미디어 플레이어가 갖고 있던 리소스 해제
        mediaPlayer = null// 미디어 플레이어 해제
    }
}