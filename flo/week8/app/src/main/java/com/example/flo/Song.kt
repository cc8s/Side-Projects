package com.example.flo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "SongTable")
data class Song(
    val title : String = "",
    val singer : String = "",
    var second: Int = 0,
    val playTime: Int = 0,
    var isPlaying: Boolean = false,
    var music: String = "",
    val coverImg: Int? = null,
    var isLike: Boolean = false,
    val albumIdx: Int,
    var userId: Int = 0
) {
    @PrimaryKey(autoGenerate = true) var id: Int = 0
}


