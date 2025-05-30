package com.example.flo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.example.flo.databinding.ActivityMainBinding
import com.google.gson.Gson

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    var song: Song = Song(albumIdx = 1)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_FLO)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        inputDummyAlbums()
        inputDummySongs()

        initBottomNavigation()

        binding.mainPlayerCl.setOnClickListener {
            val editor = getSharedPreferences("song", MODE_PRIVATE).edit()
            editor.putInt("songId", song.id)
            editor.apply()

            val intent = Intent(this, SongActivity::class.java)
            startActivity(intent)
        }

        val songDB = SongDatabase.getInstance(this)!!

// ▶️ 재생
        binding.mainMiniplayerBtn.setOnClickListener {
            song.isPlaying = true

            // SharedPreferences 저장
            val editor = getSharedPreferences("song", MODE_PRIVATE).edit()
            editor.putInt("songId", song.id)
            editor.putBoolean("isPlaying", true)
            editor.apply()

            saveAndSetMiniPlayer(song) // UI 반영
            sendServiceCommand("play", song.music) // 서비스에 명령 전달
        }

// ⏸️ 일시정지
        binding.mainPauseBtn.setOnClickListener {
            song.isPlaying = false

            // SharedPreferences 저장
            val editor = getSharedPreferences("song", MODE_PRIVATE).edit()
            editor.putInt("songId", song.id)
            editor.putBoolean("isPlaying", false)
            editor.apply()

            saveAndSetMiniPlayer(song)
            sendServiceCommand("pause", song.music)
        }

// 🔁 이전 곡 버튼
        binding.mainMiniplayerPrevBtn.setOnClickListener {
            val prevSong = songDB.songDao().getSongs().filter { it.id < song.id }.maxByOrNull { it.id }
            if (prevSong != null) {
                song = prevSong
                song.isPlaying = true
                saveAndSetMiniPlayer(song)
                sendServiceCommand("play", song.music)
            } else {
                Toast.makeText(this, "첫 곡입니다.", Toast.LENGTH_SHORT).show()
            }
        }

// 🔁 다음 곡 버튼
        binding.mainMiniplayerNextBtn.setOnClickListener {
            val nextSong = songDB.songDao().getSongs().filter { it.id > song.id }.minByOrNull { it.id }
            if (nextSong != null) {
                song = nextSong
                song.isPlaying = true
                saveAndSetMiniPlayer(song)
                sendServiceCommand("play", song.music)
            } else {
                Toast.makeText(this, "마지막 곡입니다.", Toast.LENGTH_SHORT).show()
            }
        }

//songactivity로
        binding.mainMiniplayerListBtn.setOnClickListener{
            val intent = Intent(this, SongActivity::class.java)
            startActivity(intent)
        }
    }

    fun sendServiceCommand(command: String, music: String? = null) {
        val intent = Intent(this, MusicService::class.java)
        intent.putExtra("command", command)
        music?.let { intent.putExtra("music", it) }
        startService(intent)
    }

    private fun saveAndSetMiniPlayer(song: Song) {
        val editor = getSharedPreferences("song", MODE_PRIVATE).edit()
        editor.putInt("songId", song.id)
        editor.putBoolean("isPlaying", song.isPlaying) // ✅ 상태 저장
        editor.apply()
        setMiniPlayer(song)
    }

    fun setMiniPlayer(song: Song?) {
        if (song == null) return

        binding.mainMiniplayerTitleTv.text = song.title
        binding.mainMiniplayerSingerTv.text = song.singer

        binding.mainMiniplayerBtn.visibility = if (song.isPlaying) android.view.View.GONE else android.view.View.VISIBLE
        binding.mainPauseBtn.visibility = if (song.isPlaying) android.view.View.VISIBLE else android.view.View.GONE
    }


    override fun onStart() {
        super.onStart()

        val spf = getSharedPreferences("song", MODE_PRIVATE)
        val songId = spf.getInt("songId", 0)
        val isPlaying = spf.getBoolean("isPlaying", false)

        val songDB = SongDatabase.getInstance(this)!!
        val defaultId = songDB.songDao().getSongs().firstOrNull()?.id ?: return

        song = if (songId == 0){
            songDB.songDao().getSong(defaultId)
        } else{
            songDB.songDao().getSong(songId)
        }

        song.isPlaying = isPlaying
        setMiniPlayer(song)
    }

    override fun onResume() {
        super.onResume()

        val spf = getSharedPreferences("song", MODE_PRIVATE)
        val songId = spf.getInt("songId", 0)
        val isPlaying = spf.getBoolean("isPlaying", false)

        val songDB = SongDatabase.getInstance(this)!!
        val defaultId = songDB.songDao().getSongs().firstOrNull()?.id ?: return

        song = if (songId == 0){
            songDB.songDao().getSong(defaultId)
        } else{
            songDB.songDao().getSong(songId)
        }

        song.isPlaying = isPlaying  // ✅ 미니플레이어 상태 정확히 반영
        setMiniPlayer(song)
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

    private fun inputDummyAlbums() {
        val songDB = SongDatabase.getInstance(this)!!
        val albums = songDB.albumDao().getAlbums()

        if (albums.isNotEmpty()) return

        songDB.albumDao().insert(Album(1, "LILAC", "아이유 (IU)", R.drawable.img_album_exp2))
        songDB.albumDao().insert(Album(2, "FLU", "아이유 (IU)", R.drawable.img_album_exp2))
        songDB.albumDao().insert(Album(3, "Butter", "방탄소년단 (BTS)", R.drawable.img_album_exp))
        songDB.albumDao().insert(Album(4, "Next Level", "에스파 (AESPA)", R.drawable.img_album_exp3))
        songDB.albumDao().insert(Album(5, "Map of the Soul", "방탄소년단 (BTS)", R.drawable.img_album_exp4))
        songDB.albumDao().insert(Album(6, "Great!", "모모랜드 (MOMOLAND)", R.drawable.img_album_exp5))
    }

    private fun inputDummySongs(){
        val songDB = SongDatabase.getInstance(this)!!
        val songs = songDB.songDao().getSongs()

        //dummy song 중복삽입방지
        if (songs.any { it.title == "Lilac" && it.singer == "아이유 (IU)" }) return
        // → 이미 추가된 곡이 있다면 skip

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
                1
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
                2
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
                3
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
                4
            )
        )

        songDB.songDao().insert(
            Song(
                "Boy with Luv",
                "방탄소년단 (BTS)",
                0,
                230,
                false,
                "music_boy",
                R.drawable.img_album_exp4,
                false,
                5
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
                6
            )
        )

        val _songs = songDB.songDao().getSongs()
        Log.d("DB data", _songs.toString())
    }
}