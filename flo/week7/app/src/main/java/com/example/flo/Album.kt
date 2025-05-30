package com.example.flo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "AlbumTable")
data class Album(
    @PrimaryKey val id: Int,
    var title: String? = "",
    var singer: String? = "",
    var coverImg: Int? = null,
)
