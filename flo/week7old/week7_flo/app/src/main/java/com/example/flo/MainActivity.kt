package com.example.flo

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import com.example.flo.databinding.ActivityMainBinding
import com.google.gson.Gson

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    var song: Song = Song()
    private var gson: Gson = Gson()

    private lateinit var songList: ArrayList<Song>
    private var nowPos = 0
    private var mediaPlayer: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_FLO)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        inputDummySongs()
//        inputDummyAlbums()
        initBottomNavigation()

        val songTitle = intent.getStringExtra("title")
        val songSinger = intent.getStringExtra("singer")

        if (!songTitle.isNullOrEmpty() && !songSinger.isNullOrEmpty()) {
            Toast.makeText(this, "노래 제목: $songTitle, 가수: $songSinger", Toast.LENGTH_SHORT).show()
        }

    }

    private fun inputDummySongs(){
        val songDB = SongDatabase.getInstance(this)!!
        val songs = songDB.songDao().getSongs()

        if (songs.isNotEmpty()) return

        songDB.songDao().insert(
            Song(
                "Lilac",
                "아이유 (IU)",
                0,
                200,
                false,
                "music_lilac",
                R.drawable.img_album_exp2,
                false,
            )
        )

        songDB.songDao().insert(
            Song(
                "Flu",
                "아이유 (IU)",
                0,
                200,
                false,
                "music_flu",
                R.drawable.img_album_exp2,
                false,
            )
        )

        songDB.songDao().insert(
            Song(
                "Butter",
                "방탄소년단 (BTS)",
                0,
                190,
                false,
                "music_butter",
                R.drawable.img_album_exp,
                false,
            )
        )

        songDB.songDao().insert(
            Song(
                "Next Level",
                "에스파 (AESPA)",
                0,
                210,
                false,
                "music_next",
                R.drawable.img_album_exp3,
                false,
            )
        )


        songDB.songDao().insert(
            Song(
                "Boy with Luv",
                "music_boy",
                0,
                230,
                false,
                "music_lilac",
                R.drawable.img_album_exp4,
                false,
            )
        )


        songDB.songDao().insert(
            Song(
                "BBoom BBoom",
                "모모랜드 (MOMOLAND)",
                0,
                240,
                false,
                "music_bboom",
                R.drawable.img_album_exp5,
                false,
            )
        )

        val _songs = songDB.songDao().getSongs()
        Log.d("DB data", _songs.toString())
    }

    private fun initBottomNavigation(){

        supportFragmentManager.beginTransaction()
            .replace(R.id.main_frm, HomeFragment())
            .commitAllowingStateLoss()

        binding.mainBnv.setOnItemSelectedListener{ item ->
            when (item.itemId) {

                R.id.homeFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, HomeFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }

                R.id.lookFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, LookFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
                R.id.searchFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, SearchFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
                R.id.lockerFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, LockerFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
            }
            false
        }
    }

    // setMiniPlayer에서 진행률이 현재 상태를 반영하도록 합니다.
    fun setMiniPlayer(song: Song) {
        binding.mainMiniplayerTitleTv.text = song.title
        binding.mainMiniplayerSingerTv.text = song.singer

        val currentPositionForProgress = mediaPlayer?.currentPosition ?: (song.second * 1000)
        // song.playTime이 0인 경우 0으로 나누기 오류를 피하기 위해 유효한지 확인합니다.
        val totalDurationForProgress = if (song.playTime > 0) song.playTime * 1000 else (mediaPlayer?.duration ?: 0)

        val progress = if (totalDurationForProgress > 0) {
            (currentPositionForProgress * 100L / totalDurationForProgress).toInt()
        } else {
            0
        }
        binding.mainProgresssb.progress = progress.coerceIn(0, 100) // 0-100 범위로 강제

        binding.mainMiniplayerBtn.setImageResource(
            if (mediaPlayer?.isPlaying == true) R.drawable.btn_miniplay_pause else R.drawable.btn_miniplayer_play
        )
    }

    private fun saveMiniPlayerStateToSpf() {
        val editor = getSharedPreferences("song", MODE_PRIVATE).edit()
        editor.putInt("songId", song.id)
        val currentPositionSeconds = if (mediaPlayer?.isPlaying == true || song.isPlaying) { // 재생 중이거나 재생 예정일 경우
            mediaPlayer?.currentPosition?.div(1000) ?: song.second
        } else {
            song.second // 일시정지된 경우 저장된 second 값 사용
        }
        editor.putInt("songSecond", currentPositionSeconds)
        editor.putBoolean("wasPlaying", song.isPlaying) // MainActivity의 isPlaying 상태가 기준
        editor.apply()
    }


    override fun onStart() {
        super.onStart()

        val songDB = SongDatabase.getInstance(this)!!
        songList = songDB.songDao().getSongs() as ArrayList<Song>

        val spf = getSharedPreferences("song", MODE_PRIVATE)
        val songId = spf.getInt("songId", 0)
        val songSecond = spf.getInt("songSecond", 0)
        val wasPlayingFromSpf = spf.getBoolean("wasPlaying", false)

        song = if (songId == 0) songList[0]
        else songList.find { it.id == songId } ?: songList[0]

        nowPos = songList.indexOfFirst { it.id == song.id }

        binding.mainMiniplayerBtn.setOnClickListener {
            if (mediaPlayer?.isPlaying == true) {
                mediaPlayer?.pause()
                binding.mainMiniplayerBtn.setImageResource(R.drawable.btn_miniplayer_play)
            } else {
                mediaPlayer?.start()
                binding.mainMiniplayerBtn.setImageResource(R.drawable.btn_miniplay_pause)
            }
        }
        //미니플레이어 곡이동
        binding.mainMiniplayerNextBtn.setOnClickListener {
            moveSong(+1)
        }

        binding.mainMiniplayerPrevBtn.setOnClickListener {
            moveSong(-1)
        }
        //songActivity로 넘어가기
        binding.mainPlayerClickArea.setOnClickListener {
            val wasPlayingBeforeNav = mediaPlayer?.isPlaying ?: false // 음악이 재생 중이었는지 확인
            if (wasPlayingBeforeNav) {
                mediaPlayer?.pause() // MainActivity의 플레이어 일시정지
                // 미니플레이어 UI 업데이트 (일시정지를 반영하여 재생 버튼으로 변경 등)
                binding.mainMiniplayerBtn.setImageResource(R.drawable.btn_miniplayer_play)
            }

            // 상태 저장 (곡 ID, 현재 재생 위치, 재생 중이었는지 여부)
            // SongActivity가 이 정보를 사용하여 자동 재생 여부를 결정합니다.
            val spfEditor = getSharedPreferences("song", MODE_PRIVATE).edit()
            spfEditor.putInt("songId", song.id) // song은 MainActivity의 현재 곡 객체입니다.
            spfEditor.putInt("songSecond", mediaPlayer?.currentPosition?.div(1000) ?: song.second)
            spfEditor.putBoolean("wasPlaying", wasPlayingBeforeNav) // SongActivity가 확인할 키
            spfEditor.apply()

            val intent = Intent(this, SongActivity::class.java)
            startActivity(intent)
        }

        // miniplayerGoList.setOnClickListener 에도 비슷한 로직 적용
        binding.miniplayerGoList.setOnClickListener {
            val wasPlayingBeforeNav = mediaPlayer?.isPlaying ?: false
            if (wasPlayingBeforeNav) {
                mediaPlayer?.pause()
            }

            val spfEditor = getSharedPreferences("song", MODE_PRIVATE).edit()
            spfEditor.putInt("songId", song.id)
            spfEditor.putInt("songSecond", mediaPlayer?.currentPosition?.div(1000) ?: song.second)
            spfEditor.putBoolean("wasPlaying", wasPlayingBeforeNav)
            spfEditor.apply()

            val intent = Intent(this, SongActivity::class.java)
            startActivity(intent)
        }

        playSong(song)
    }

    //재생
    // playSong (새로운 곡 재생 시, 이어 재생이 아님)은 관련 필드를 리셋해야 합니다.
    fun playSong(songToPlay: Song) {
        mediaPlayer?.release()
        mediaPlayer = null

        val musicResId = resources.getIdentifier(songToPlay.music, "raw", packageName)
        if (musicResId == 0) {
            Toast.makeText(this, "음악 파일이 없습니다: ${songToPlay.music}", Toast.LENGTH_SHORT).show()
            this.song = songToPlay // 현재 곡 참조 업데이트
            this.song.isPlaying = false // 재생 불가
            this.song.second = 0
            setMiniPlayer(this.song)
            saveMiniPlayerStateToSpf() // 이 상태 저장
            return
        }

        this.song = songToPlay        // MainActivity의 현재 곡 업데이트
        this.song.second = 0          // 새로운 재생이므로 처음부터 시작
        this.song.isPlaying = true    // 재생될 것임

        mediaPlayer = MediaPlayer.create(this, musicResId)
        // 새로운 재생이므로 seek 불필요
        mediaPlayer?.start()

        setMiniPlayer(this.song)      // UI 업데이트
        saveMiniPlayerStateToSpf()    // 새로 재생된 곡의 상태 저장
    }

    private fun moveSong(direct: Int) {
        if (nowPos + direct < 0) {
            Toast.makeText(this, "첫 번째 곡입니다.", Toast.LENGTH_SHORT).show()
            return
        }

        if (nowPos + direct >= songList.size) {
            Toast.makeText(this, "마지막 곡입니다.", Toast.LENGTH_SHORT).show()
            return
        }

        nowPos += direct
        song = songList[nowPos]
        playSong(song)
    }

    private fun initMiniPlayerClickListeners() {
        binding.mainMiniplayerBtn.setOnClickListener {
            if (mediaPlayer?.isPlaying == true) {
                mediaPlayer?.pause()
                song.isPlaying = false
            } else {
                // mediaPlayer가 null이거나 준비되지 않았다면 playSong이 필요할 수 있습니다.
                // 단순화를 위해 이 버튼이 활성화되어 있다면 mediaPlayer가 유효하다고 가정합니다.
                mediaPlayer?.start()
                song.isPlaying = true
            }
            setMiniPlayer(song) // UI 업데이트 (예: 재생/일시정지 버튼 이미지)
            saveMiniPlayerStateToSpf() // 새 상태 지속
        }

        binding.mainMiniplayerNextBtn.setOnClickListener {
            moveSong(+1) // moveSong은 playSong을 호출하고, playSong이 상태 저장을 처리합니다.
        }

        binding.mainMiniplayerPrevBtn.setOnClickListener {
            moveSong(-1) // moveSong은 playSong을 호출합니다.
        }
        // mainPlayerClickArea 와 miniplayerGoList 리스너는 이전에 업데이트되었습니다.
    }


    private fun loadDefaultOrFirstSongAndSetMiniplayer() {
        if (songList.isNotEmpty()) {
            this.song = songList[0]
            this.song.second = 0
            this.song.isPlaying = false // 기본적으로 재생 안 함
        } else {
            this.song = Song() // 비어 있거나 기본 Song 객체
        }
        // 필요하다면 기본 곡에 대해 MediaPlayer 준비, 하지만 자동 재생은 안 함
        mediaPlayer?.release()
        val musicResId = if (this.song.music.isNotEmpty()) resources.getIdentifier(this.song.music, "raw", packageName) else 0
        if (musicResId != 0) {
            mediaPlayer = MediaPlayer.create(this, musicResId)
        } else {
            mediaPlayer = null
        }
        setMiniPlayer(this.song)
        saveMiniPlayerStateToSpf() // 이 기본 상태 저장
    }

    // onResume이 현재 상태에 따라 미니플레이어 UI를 업데이트하도록 합니다.
    override fun onResume() {
        super.onResume()
        // song과 mediaPlayer는 onStart가 실행되었다면 올바르게 설정되어 있어야 합니다.
        // onStart가 실행되지 않았다면 (예: 액티비티가 잠시 일시정지 후 재개된 경우),
        // this.song은 여전히 올바른 상태를 가지고 있을 수 있습니다.
        // UI가 최신 지속 상태와 일치하도록 SPF에서 가져옵니다.
        val spf = getSharedPreferences("song", MODE_PRIVATE)
        val songId = spf.getInt("songId", 0)
        val songSecond = spf.getInt("songSecond", 0)
        val wasPlayingFromSpf = spf.getBoolean("wasPlaying", false)

        if (this.song.id == songId && songId != 0) {
            // 같은 곡이므로, 변경되었다면 SPF에서 상태 업데이트
            this.song.second = songSecond
            // mediaPlayer의 상태와 일치하지 않고 SPF가 변경을 나타내는 경우에만 isPlaying 변경.
            // 일반적으로 UI에 대해서는 실제 mediaPlayer 상태를 먼저 신뢰합니다.
            this.song.isPlaying = mediaPlayer?.isPlaying ?: wasPlayingFromSpf
        } else if (songId != 0) {
            // 이 액티비티가 일시정지된 동안 다른 곡이 활성화되었습니다.
            // 이는 onStart가 처리했어야 합니다. 그렇지 않다면 더 깊은 상태 문제가 있음을 나타냅니다.
            // 안정성을 위해 여기서 곡을 다시 로드할 수 있지만, 이상적으로는 onStart로 충분합니다.
        }
        setMiniPlayer(this.song) // 미니플레이어 UI 새로고침
    }

    override fun onDestroy() {
        mediaPlayer?.release()
        mediaPlayer = null
        super.onDestroy()
    }
}